/*
 * Copyright 2009-2015 Aarhus University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dk.brics.tajs.lattice;

import dk.brics.tajs.flowgraph.SourceLocation;
import dk.brics.tajs.lattice.ObjectLabel.Kind;
import dk.brics.tajs.options.Options;
import dk.brics.tajs.util.AnalysisException;
import dk.brics.tajs.util.Strings;
import edu.oakland.stringabs.AbstractString;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import static dk.brics.tajs.util.Collections.newSet;

/**
 * Abstract value.
 * Value objects are immutable.
 */
public final class Value implements Undef, Null, Bool, Num, Str {

    private final static int BOOL_TRUE = 0x0000001; // true

    private final static int BOOL_FALSE = 0x0000002; // false

    private final static int UNDEF = 0x0000004; // undefined

    private final static int NULL = 0x0000008; // null

    private final static int STR = 0x00000010; // String

    private final static int STR_JSON = 0x0000400; // strings originating from a JSON source

    private final static int NUM_NAN = 0x0001000; // NaN

    private final static int NUM_INF = 0x0002000; // +/-Infinity

    private final static int NUM_UINT = 0x0004000; // UInt32 numbers

    private final static int NUM_OTHER = 0x0008000; // numbers that are not UInt32, not NaN, and not +/-Infinity

    private final static int ATTR_DONTENUM = 0x0010000; // [[DontEnum]] property

    private final static int ATTR_NOTDONTENUM = 0x0020000; // not [[DontEnum]] property

    private final static int ATTR_READONLY = 0x0040000; // [[ReadOnly]] property

    private final static int ATTR_NOTREADONLY = 0x0080000; // not [[ReadOnly]] property

    private final static int ATTR_DONTDELETE = 0x0100000; // [[DontDelete]] property

    private final static int ATTR_NOTDONTDELETE = 0x0200000; // not [[DontDelete]] property

    private final static int MODIFIED = 0x1000000; // maybe modified property (since function entry)

    private final static int ABSENT = 0x2000000; // maybe absent property

    private final static int PRESENT = 0x4000000; // maybe present property, only used if var!=null

    private final static int UNKNOWN = 0x8000000; // unknown (lazy propagation)

    private final static int EXTENDEDSCOPE = 0x10000000; // for extended scope registers (for-in and finally blocks)

    private final static int BOOL = BOOL_TRUE | BOOL_FALSE;

    private final static int NUM = NUM_NAN | NUM_INF | NUM_UINT | NUM_OTHER;

    private final static int ATTR_DONTENUM_ANY = ATTR_DONTENUM | ATTR_NOTDONTENUM;

    private final static int ATTR_READONLY_ANY = ATTR_READONLY | ATTR_NOTREADONLY;

    private final static int ATTR_DONTDELETE_ANY = ATTR_DONTDELETE | ATTR_NOTDONTDELETE;

    private final static int ATTR = ATTR_DONTENUM_ANY | ATTR_READONLY_ANY | ATTR_DONTDELETE_ANY;

    private final static int PROPERTYDATA = ATTR | MODIFIED;

    private final static int PRIMITIVE = UNDEF | NULL | BOOL | NUM | STR;

    private static Map<Value, WeakReference<Value>> value_cache;

    private static int value_cache_hits;

    private static int value_cache_misses;

    private static Map<Set<ObjectLabel>, WeakReference<Set<ObjectLabel>>> objset_cache;

    private static int objset_cache_hits;

    private static int objset_cache_misses;

    private static Value theNone;

    private static Value theNoneModified;

    private static Value theUndef;

    private static Value theNull;

    private static Value theBoolTrue;

    private static Value theBoolFalse;

    private static Value theBoolAny;

    private static Value theStrAny;

    private static Value theStrUInt;

    private static Value theStrNotUInt;

    private static Value theJSONStr;

    private static Value theNumAny;

    private static Value theNumUInt;

    private static Value theNumNotNaNInf;

    private static Value theNumOther;

    private static Value theNumNaN;

    private static Value theNumInf;

    private static Value theAbsent;

    private static Value theAbsentModified;

    private static Value theUnknown;

    /*
     * Representation invariant:
     * !((flags & (STR_UINT | STR_OTHERNUM | STR_IDENTIFIER | STR_IDENTIFIERPARTS | STR_OTHER)) != 0 && str != null)
     * &&
     * !((flags & STR_PREFIX) != 0 && (str == null || str.length == 0))
     * &&
     * !((flags & STR_IDENTIFIER) != 0 && (flags & STR_IDENTIFIERPARTS) != 0)
     * &&
     * !((flags & NUM_ANY) != 0 && num != null)
     * &&
     * !(object_labels != null && object_labels.isEmpty())
     * &&
     * !(num != null && Double.isNaN(num))
     * &&
     * !((flags & UNKNOWN) != 0 && ((flags & ~UNKNOWN) != 0 || str != null || num != null || !object_labels.isEmpty()))
     * &&
     * !(var != null && ((flags & PRIMITIVE) != 0 || str != null || num != null || !object_labels.isEmpty()))
     * &&
     * !((flags & PRESENT) != 0 && var == null)
     * 
     * For the String facet, note that the various categories are not all disjoint.
     * Also, at most one of STR_PREFIX, STR_IDENTIFIER, and STR_IDENTIFIERPARTS can be set,
     * although STR_IDENTIFIERPARTS subsumes the other two (and STR_UINT).
     */

    /**
     * Value flags.
     */
    private int flags; // see invariant above

    /**
     * Constant number, may be +/-Infinity but not NaN.
     */
    private Double num;

    /**
     * Constant string or prefix.
     */
    private AbstractString str; // Picker & Maldonado

    /**
     * Counts how many times the Str facet has been 'joined'
     */
    public int joinCount; // Picker & Maldonado

    /**
     * Property reference for polymorphic value.
     */
    private PropertyReference var; // polymorphic if non-null

    /**
     * Possible values regarding object references.
     */
    private Set<ObjectLabel> object_labels;

    /**
     * Hash code.
     */
    private int hashcode;

    private static boolean canonicalizing; // set during canonicalization

    static {
        init();
    }

    private static void init() {
        value_cache = new WeakHashMap<>();
        objset_cache = new WeakHashMap<>();
        value_cache_hits = 0;
        value_cache_misses = 0;
        objset_cache_hits = 0;
        objset_cache_misses = 0;
        theNone = reallyMakeNone();
        theNoneModified = reallyMakeNoneModified();
        theUndef = reallyMakeUndef(null);
        theNull = reallyMakeNull(null);
        theBoolTrue = reallyMakeBool(true);
        theBoolFalse = reallyMakeBool(false);
        theBoolAny = reallyMakeBool(null);
        theStrAny = reallyMakeAnyStr();
        theStrUInt = reallyMakeAnyStrUInt();
        theStrNotUInt = reallyMakeAnyStrNotUInt();
        theJSONStr = reallyMakeJSONStr();
        theNumAny = reallyMakeAnyNum();
        theNumUInt = reallyMakeAnyUInt();
        theNumNotNaNInf = reallyMakeAnyNumNotNaNInf();
        theNumOther = reallyMakeAnyNumOther();
        theNumNaN = reallyMakeNumNaN();
        theNumInf = reallyMakeNumInf();
        theAbsent = reallyMakeAbsent();
        theAbsentModified = reallyMakeAbsentModified();
        theUnknown = reallyMakeUnknown();
    }

    /**
     * Constructs a new none-value.
     */
    private Value() {
        flags = 0;
        num = null;
        str = null;
        joinCount = 0; // Picker & Maldonado
        object_labels = null;
        var = null;
        hashcode = 0;
    }

    /**
     * Constructs a shallow clone of the given value object.
     */
    private Value(Value v) {
        flags = v.flags;
        num = v.num;
        str = v.str;
        joinCount = v.joinCount; // Picker & Maldonado
        object_labels = v.object_labels;
        var = v.var;
        hashcode = v.hashcode;
    }

    /**
     * Put the value into canoncial form.
     */
    private static Value canonicalize(Value v) {
        v.hashcode = v.flags * 17
                + (v.var != null ? v.var.hashCode() : 0)
                + (v.num != null ? v.num.hashCode() : 0)
                + (v.str != null ? v.str.hashCode() : 0)
                + (v.object_labels != null ? v.object_labels.hashCode() : 0);
        if (Options.get().isDebugOrTestEnabled()) { // checking representation invariants
            String msg = null;
            if ((v.flags & NUM) != 0 && v.num != null)
                msg = "number facet inconsistent";
            else if (v.num != null && Double.isNaN(v.num))
                msg = "number constant is NaN";
            else if (v.object_labels != null && v.object_labels.isEmpty())
                msg = "empty set of object labels";
            else if ((v.flags & UNKNOWN) != 0 && ((v.flags & ~UNKNOWN) != 0 || v.str != null || v.num != null || (v.object_labels != null && !v.object_labels.isEmpty())))
                msg = "'unknown' inconsistent with other flags";
            else if (v.var != null && ((v.flags & PRIMITIVE) != 0 || v.str != null || v.num != null || (v.object_labels != null && !v.object_labels.isEmpty())))
                msg = "mix of polymorphic and ordinary value";
            else if ((v.flags & PRESENT) != 0 && v.var == null)
                msg = "PRESENT set for non-polymorphic value";
            if (msg != null)
                throw new AnalysisException("Invalid value (0x" + Integer.toHexString(v.flags) + ","
                        + Strings.escape(v.getStr()) + "," + v.num + "," + v.object_labels + "), " + msg);
            if (Options.get().isPolymorphicDisabled() && v.isPolymorphic())
                throw new AnalysisException("Unexpected polymorphic value");
        }
        canonicalizing = true;
        if (v.object_labels != null)
            v.object_labels = canonicalize(v.object_labels);
        WeakReference<Value> ref2 = value_cache.get(v);
        Value cv = ref2 != null ? ref2.get() : null;
        if (cv == null) {
            cv = v;
            value_cache.put(v, new WeakReference<>(v));
            value_cache_misses++;
        } else
            value_cache_hits++;
        canonicalizing = false;
        return cv;
    }

    /**
     * Put the object label set into canonical form.
     * The resulting set is immutable.
     */
    private static Set<ObjectLabel> canonicalize(Set<ObjectLabel> objlabels) { // TODO: use this method for all immutable object label sets (but only for those that are immutable!)
        Set<ObjectLabel> res;
        WeakReference<Set<ObjectLabel>> ref1 = objset_cache.get(objlabels);
        Set<ObjectLabel> so = ref1 != null ? ref1.get() : null;
        if (so == null) {
            objset_cache.put(objlabels, new WeakReference<>(objlabels));
            res = objlabels;
            objset_cache_misses++;
        } else {
            res = so;
            objset_cache_hits++;
        }
        if (Options.get().isDebugOrTestEnabled())
            return Collections.unmodifiableSet(res);
        return res;
    }

    /**
     * Returns the value cache size.
     */
    public static int getValueCacheSize() {
        return value_cache.size();
    }

    /**
     * Returns the number of value cache misses.
     */
    public static int getNumberOfValueCacheMisses() {
        return value_cache_misses;
    }

    /**
     * Returns the number of value cache hits.
     */
    public static int getNumberOfValueCacheHits() {
        return value_cache_hits;
    }

    /**
     * Returns the object set cache size.
     */
    public static int getObjectSetCacheSize() {
        return objset_cache.size();
    }

    /**
     * Returns the number of object set cache misses.
     */
    public static int getNumberOfObjectSetCacheMisses() {
        return objset_cache_misses;
    }

    /**
     * Returns the number of object set cache hits.
     */
    public static int getNumberOfObjectSetCacheHits() {
        return objset_cache_hits;
    }

    /**
     * Resets the cache.
     */
    public static void reset() {
        init();
    }

    /**
     * Checks whether this value is polymorphic.
     */
    public boolean isPolymorphic() {
        return var != null;
    }

    /**
     * Checks whether this value is polymorphic or 'unknown'.
     */
    public boolean isPolymorphicOrUnknown() {
        return var != null || (flags & UNKNOWN) != 0;
    }

    /**
     * Returns the property reference.
     * Only to be called if the value is polymorphic.
     */
    public PropertyReference getPropertyReference() {
        return var;
    }

    /**
     * Constructs a fresh polymorphic value from the attributes (including absence and presence) of this value.
     */
    public Value makePolymorphic(PropertyReference prop) {
        Value r = new Value();
        r.var = prop;
        r.flags |= flags & (ATTR | ABSENT | PRESENT | EXTENDEDSCOPE);
        if (isMaybePresent())
            r.flags |= PRESENT;
        return canonicalize(r);
    }

    /**
     * Constructs a fresh non-polymorphic value using the attributes (excluding presence) of the given value.
     */
    public Value makeNonPolymorphic() {
        if (var == null)
            return this;
        Value r = new Value(this);
        r.var = null;
        r.flags &= ~PRESENT;
        return canonicalize(r);
    }

    /**
     * Asserts that the value is not 'unknown'.
     *
     * @throws AnalysisException if the value is 'unknown'.
     */
    private void checkNotUnknown() {
        if (isUnknown())
            throw new AnalysisException("Unexpected 'unknown' value!");
    }

    /**
     * Asserts that the value is not polymorphic nor 'unknown'.
     *
     * @throws AnalysisException if the value is polymorphic or 'unknown'.
     */
    private void checkNotPolymorphicOrUnknown() {
        if (isPolymorphic())
            throw new AnalysisException("Unexpected polymorphic value!");
        if (isUnknown())
            throw new AnalysisException("Unexpected 'unknown' value!");
    }

    private static Value reallyMakeNone() {
        return canonicalize(new Value());
    }

    /**
     * Constructs the empty abstract value (= bottom, if not considering 'unknown').
     */
    public static Value makeNone() {
        return theNone;
    }

    private static Value reallyMakeNoneModified() {
        return canonicalize(new Value().joinModified());
    }

    /**
     * Constructs the empty abstract value that is marked as modified.
     */
    public static Value makeNoneModified() {
        return theNoneModified;
    }

    /**
     * Returns true if this is the abstract value representing no concrete values.
     * (If a property value is none, the abstract object represents zero concrete objects.)
     * The modified flag is ignored.
     */
    public boolean isNone() {
        if (var == null)
            return (flags & ~MODIFIED) == 0 && num == null && str == null && object_labels == null;
        else
            return (flags & (ABSENT | PRESENT)) == 0;
    }

    /**
     * Checks whether this value is marked as maybe modified.
     */
    public boolean isMaybeModified() {
        if (Options.get().isModifiedDisabled())
            return !isUnknown(); // if we don't use the modified flag, use the fact that unknown implies non-modified
        return (flags & MODIFIED) != 0;
    }

    /**
     * Constructs a value as a copy of this value but marked as maybe modified.
     */
    public Value joinModified() {
        checkNotUnknown();
        if (isMaybeModified())
            return this;
        Value r = new Value(this);
        r.flags |= MODIFIED;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but marked as definitely not modified.
     */
    public Value restrictToNotModified() {
        if (!isMaybeModified())
            return this;
        Value r = new Value(this);
        r.flags &= ~MODIFIED;
        return canonicalize(r);
    }

    /**
     * Constructs the absent value.
     */
    public static Value makeAbsent() {
        return theAbsent;
    }

    /**
     * Constructs the absent modified value.
     */
    public static Value makeAbsentModified() {
        return theAbsentModified;
    }

    /**
     * Constructs the unknown value.
     */
    public static Value makeUnknown() {
        return theUnknown;
    }

    /**
     * Constructs a value as a copy of this value but definitely not absent.
     */
    public Value restrictToNotAbsent() {
        checkNotUnknown();
        if (isNotAbsent())
            return this;
        Value r = new Value(this);
        r.flags &= ~ABSENT;
        if (r.var != null && (r.flags & PRESENT) == 0)
            r.var = null;
        return canonicalize(r);
    }

    /**
     * Returns true if this value belongs to a maybe absent property.
     */
    public boolean isMaybeAbsent() {
        checkNotUnknown();
        return (flags & ABSENT) != 0;
    }

    /**
     * Returns true if this value belongs to a definitely present property.
     */
    public boolean isNotAbsent() {
        return !isMaybeAbsent() && isMaybePresent();
    }

    /**
     * Returns true if this value is 'unknown'.
     */
    public boolean isUnknown() {
        return (flags & UNKNOWN) != 0;
    }

    /**
     * Constructs a value as a copy of this value but marked as maybe absent.
     */
    public Value joinAbsent() {
        checkNotUnknown();
        if (isMaybeAbsent())
            return this;
        Value r = new Value(this);
        r.flags |= ABSENT;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but marked as maybe absent and maybe modified.
     */
    public Value joinAbsentModified() {
        checkNotUnknown();
        if (isMaybeAbsent() && isMaybeModified())
            return this;
        Value r = new Value(this);
        r.flags |= ABSENT | MODIFIED;
        return canonicalize(r);
    }

    private static Value reallyMakeAbsent() {
        Value r = new Value();
        r.flags |= ABSENT;
        return canonicalize(r);
    }

    private static Value reallyMakeAbsentModified() {
        Value r = new Value();
        r.flags |= ABSENT | MODIFIED;
        return canonicalize(r);
    }

    private static Value reallyMakeUnknown() {
        Value r = new Value();
        r.flags |= UNKNOWN;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of the given value but with all attributes definitely not set.
     */
    public Value removeAttributes() {
        checkNotUnknown();
        Value r = new Value(this);
        r.flags &= ~ATTR;
        r.flags |= ATTR_NOTDONTDELETE | ATTR_NOTDONTENUM | ATTR_NOTREADONLY;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with attributes set as in the given value.
     */
    public Value setAttributes(Value from) {
        checkNotUnknown();
        from.checkNotUnknown();
        Value r = new Value(this);
        r.flags &= ~ATTR;
        r.flags |= from.flags & ATTR;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with no information that only makes sense for object property values.
     */
    public Value setBottomPropertyData() {
        checkNotUnknown();
        Value r = new Value(this);
        r.flags &= ~PROPERTYDATA;
        return canonicalize(r);
    }

    /**
     * Returns true is this value belongs to a property which definitely has DontEnum set.
     */
    public boolean isDontEnum() {
        checkNotUnknown();
        return (flags & ATTR_DONTENUM_ANY) == ATTR_DONTENUM;
    }

    /**
     * Returns true is this value belongs to a property which maybe has DontEnum set.
     */
    public boolean isMaybeDontEnum() {
        checkNotUnknown();
        return (flags & ATTR_DONTENUM) != 0;
    }

    /**
     * Returns true is this value belongs to a property which definitely does not have DontEnum set.
     */
    public boolean isNotDontEnum() {
        checkNotUnknown();
        return (flags & ATTR_DONTENUM_ANY) == ATTR_NOTDONTENUM;
    }

    /**
     * Returns true is this value belongs to a property which maybe does not have DontEnum set.
     */
    public boolean isMaybeNotDontEnum() {
        checkNotUnknown();
        return (flags & ATTR_NOTDONTENUM) != 0;
    }

    /**
     * Returns true if this value has DontEnum information.
     */
    public boolean hasDontEnum() {
        checkNotUnknown();
        return (flags & ATTR_DONTENUM_ANY) != 0;
    }

    /**
     * Constructs a value as a copy of this value but with DontEnum definitely set.
     */
    public Value setDontEnum() {
        checkNotUnknown();
        if (isDontEnum())
            return this;
        Value r = new Value(this);
        r.flags &= ~ATTR_DONTENUM_ANY;
        r.flags |= ATTR_DONTENUM;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with DontEnum definitely not set.
     */
    public Value setNotDontEnum() {
        checkNotUnknown();
        if (isNotDontEnum())
            return this;
        Value r = new Value(this);
        r.flags &= ~ATTR_DONTENUM_ANY;
        r.flags |= ATTR_NOTDONTENUM;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with DontEnum maybe not set.
     */
    public Value joinNotDontEnum() {
        checkNotUnknown();
        if (isMaybeNotDontEnum())
            return this;
        Value r = new Value(this);
        r.flags |= ATTR_NOTDONTENUM;
        return canonicalize(r);
    }

    /**
     * Returns true is this value belongs to a property which definitely has DontDelete set.
     */
    public boolean isDontDelete() {
        checkNotUnknown();
        return (flags & ATTR_DONTDELETE_ANY) == ATTR_DONTDELETE;
    }

    /**
     * Returns true is this value belongs to a property which maybe has DontDelete set.
     */
    public boolean isMaybeDontDelete() {
        checkNotUnknown();
        return (flags & ATTR_DONTDELETE) != 0;
    }

    /**
     * Returns true is this value belongs to a property which definitely does not have DontDelete set.
     */
    public boolean isNotDontDelete() {
        checkNotUnknown();
        return (flags & ATTR_DONTDELETE_ANY) == ATTR_NOTDONTDELETE;
    }

    /**
     * Returns true is this value belongs to a property which maybe does not have DontDelete set.
     */
    public boolean isMaybeNotDontDelete() {
        checkNotUnknown();
        return (flags & ATTR_NOTDONTDELETE) != 0;
    }

    /**
     * Returns true if this value has DontDelete information.
     */
    public boolean hasDontDelete() {
        checkNotUnknown();
        return (flags & ATTR_DONTDELETE_ANY) != 0;
    }

    /**
     * Constructs a value as a copy of this value but with DontDelete definitely set.
     */
    public Value setDontDelete() {
        checkNotUnknown();
        if (isDontDelete())
            return this;
        Value r = new Value(this);
        r.flags &= ~ATTR_DONTDELETE_ANY;
        r.flags |= ATTR_DONTDELETE;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with DontDelete definitely not set.
     */
    public Value setNotDontDelete() {
        checkNotUnknown();
        if (isNotDontDelete())
            return this;
        Value r = new Value(this);
        r.flags &= ~ATTR_DONTDELETE_ANY;
        r.flags |= ATTR_NOTDONTDELETE;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with DontDelete maybe not set.
     */
    public Value joinNotDontDelete() {
        checkNotUnknown();
        if (isMaybeNotDontDelete())
            return this;
        Value r = new Value(this);
        r.flags |= ATTR_NOTDONTDELETE;
        return canonicalize(r);
    }

    /**
     * Returns true is this value belongs to a property which definitely has ReadOnly set.
     */
    public boolean isReadOnly() {
        checkNotUnknown();
        return (flags & ATTR_READONLY_ANY) == ATTR_READONLY;
    }

    /**
     * Returns true is this value belongs to a property which maybe has ReadOnly set.
     */
    public boolean isMaybeReadOnly() {
        checkNotUnknown();
        return (flags & ATTR_READONLY) != 0;
    }

    /**
     * Returns true is this value belongs to a property which definitely does not have ReadOnly set.
     */
    public boolean isNotReadOnly() {
        checkNotUnknown();
        return (flags & ATTR_READONLY_ANY) == ATTR_NOTREADONLY;
    }

    /**
     * Returns true is this value belongs to a property which maybe does not have ReadOnly set.
     */
    public boolean isMaybeNotReadOnly() {
        checkNotUnknown();
        return (flags & ATTR_NOTREADONLY) != 0;
    }

    /**
     * Returns true if this value has ReadOnly information.
     */
    public boolean hasReadOnly() {
        checkNotUnknown();
        return (flags & ATTR_READONLY_ANY) != 0;
    }

    /**
     * Constructs a value as a copy of this value but with ReadOnly definitely set.
     */
    public Value setReadOnly() {
        checkNotUnknown();
        if (isReadOnly())
            return this;
        Value r = new Value(this);
        r.flags &= ~ATTR_READONLY_ANY;
        r.flags |= ATTR_READONLY;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with ReadOnly definitely not set.
     */
    public Value setNotReadOnly() {
        checkNotUnknown();
        if (isNotReadOnly())
            return this;
        Value r = new Value(this);
        r.flags &= ~ATTR_READONLY_ANY;
        r.flags |= ATTR_NOTREADONLY;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with ReadOnly maybe not set.
     */
    public Value joinNotReadOnly() {
        checkNotUnknown();
        if (isMaybeNotReadOnly())
            return this;
        Value r = new Value(this);
        r.flags |= ATTR_NOTREADONLY;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with the given attributes.
     */
    public Value setAttributes(boolean dontenum, boolean dontdelete, boolean readonly) {
        checkNotUnknown();
        Value r = new Value(this);
        r.flags &= ~ATTR;
        if (dontdelete)
            r.flags |= ATTR_DONTDELETE;
        else
            r.flags |= ATTR_NOTDONTDELETE;
        if (readonly)
            r.flags |= ATTR_READONLY;
        else
            r.flags |= ATTR_NOTREADONLY;
        if (dontenum)
            r.flags |= ATTR_DONTENUM;
        else
            r.flags |= ATTR_NOTDONTENUM;
        return canonicalize(r);
    }

    /**
     * Constructs a value as the join of this value and the given value.
     */
    public Value join(Value v) {
        if (v == this)
            return this;
        Value r = new Value(this);
        if (r.joinMutable(v)) {
            return canonicalize(r);
        }
        return this;
    }

    /**
     * Constructs a value as the join of the given collection of values.
     */
    public static Value join(Iterable<Value> values) {
        Value r = null;
        boolean first = true;
        for (Value v : values)
            if (first) {
                r = new Value(v);
                first = false;
            } else
                r.joinMutable(v);
        if (r == null)
            r = makeNone();
        return canonicalize(r);
    }

    /**
     * Constructs a value as the join of the given collection of values.
     */
    public static Value join(Value... values) {
        Value r = null;
        boolean first = true;
        for (Value v : values)
            if (first) {
                r = new Value(v);
                first = false;
            } else
                r.joinMutable(v);
        if (r == null)
            r = makeNone();
        return canonicalize(r);
    }

    /**
     * Joins the given value into this one.
     */
    private boolean joinMutable(Value v) {
        if (v.isUnknown())
            return false;
        if (isPolymorphic() && v.isPolymorphic() && !var.equals(v.var))
            throw new AnalysisException("Attempt to join polymorphic values of different name!");
        if (isUnknown() || (isPolymorphic() && !v.isPolymorphic())) {
            flags = v.flags;
            num = v.num;
            str = v.str;
            object_labels = v.object_labels;
            var = v.var;
            return true;
        }
        boolean modified = false;
        int oldflags = flags;
        if (!v.isPolymorphic()) {
            // numbers
            if (num != null)
                if (v.num != null) {
                    // both this and v are single numbers
                    if (!num.equals(v.num)) {
                        // both this and v are single numbers, and the numbers are different
                        joinSingleNumberAsFuzzy(num);
                        joinSingleNumberAsFuzzy(v.num);
                        num = null;
                        modified = true;
                    } // otherwise this and v are equal single numbers, so do nothing
                } else {
                    // this is a single number, v is not a single number
                    if ((v.flags & NUM) != 0) {
                        // this is a single number, v is fuzzy
                        joinSingleNumberAsFuzzy(num);
                        num = null;
                        modified = true;
                    } // otherwise v is empty. so do nothing
                }
            else if (v.num != null) {
                // this is not a single number, v is a single number
                if ((flags & NUM) != 0) {
                    // this is a fuzzy number, v is a single number
                    joinSingleNumberAsFuzzy(v.num);
                } else {
                    // this is empty, v is a single number
                    num = v.num;
                    modified = true;
                }
            } // otherwise, neither is a single number, so do nothing
            // strings
            modified |= joinCount < 4 && v.joinCount < 4 ? joinAbstractStringWithLub(v) : joinSingleStringWithWiden(v); // Picker & Maldonado
//            joinCount =  ? joinCount : v.joinCount;
            // objects
            if (v.object_labels != null) {
                if (object_labels == null) {
                    modified = true;
                    object_labels = v.object_labels;
                } else if (!object_labels.containsAll(v.object_labels)) {
                    modified = true;
                    object_labels = newSet(object_labels);
                    object_labels.addAll(v.object_labels);
                }
            }
        }
        // flags
        flags |= v.flags;
        if (var == null)
            flags &= ~PRESENT;
        if (flags != oldflags)
            modified = true;
        return modified;
    }

    /**
     * Checks whether the given object is equal to this one.
     */
    @Override
    public boolean equals(Object obj) {
        if (!canonicalizing) // use object identity as equality, except during canonicalization
            return obj == this;
        if (obj == this)
            return true;
        if (!(obj instanceof Value))
            return false;
        Value v = (Value) obj;



        //noinspection StringEquality,NumberEquality
        return flags == v.flags
                && (var == v.var || (var != null && v.var != null && var.equals(v.var)))
                && (num == v.num || (num != null && v.num != null && num.equals(v.num)))
                && (str == v.str || (str != null && v.str != null && str.equals(v.str)))
                && (object_labels == v.object_labels || (object_labels != null && v.object_labels != null && object_labels.equals(v.object_labels)));
    }

//    /**
//     * Returns a copy of this value where all parts that are also in the given value have been removed.
//     * Note that the resulting value may be an over-approximation, for example if removing a single string from AnyString.
//     */
//    public Value remove(Value v) {
//        if (v == this && !isPolymorphicOrUnknown())
//            return makeNone();
//        if (v.isPolymorphicOrUnknown() || isPolymorphicOrUnknown() || v.isNone())
//            return this;
//        Value r = new Value(this);
//        if (r.object_labels != null && v.object_labels != null) {
//            r.object_labels = newSet(r.object_labels);
//            r.object_labels.removeAll(v.object_labels);
//            if (r.object_labels.isEmpty())
//                r.object_labels = null;
//        }
//        if (r.num != null && v.num != null && r.num == v.num)
//            r.num = null;
//        if (r.str != null && v.str != null && (r.flags & STR_PREFIX) == (v.flags & STR_PREFIX) && r.str.equals(v.str)) {
//            r.str = null;
//            r.flags &= ~STR_PREFIX;
//        }
//        if (r.str != null && (v.flags & (STR_UINT | STR_OTHERNUM | STR_IDENTIFIERPARTS | STR_OTHER)) == (STR_UINT | STR_OTHERNUM | STR_IDENTIFIERPARTS | STR_OTHER)) {
//            r.str = null;
//            r.flags &= ~STR_PREFIX;
//        }
//        if ((v.flags & (STR_IDENTIFIER | STR_IDENTIFIERPARTS)) != 0)
//            r.flags &= ~STR_IDENTIFIER;
//        r.flags &= ~(v.flags & (BOOL | NUM | UNDEF | NULL | STR_UINT | STR_OTHERNUM | STR_JSON | STR_IDENTIFIERPARTS | STR_OTHER));
//        return canonicalize(r);
//    }

    /**
     * Returns a description of the changes from the old value to this value.
     * It is assumed that the old value is less than this value.
     *
     * @param old The old value to diff against.
     * @param b   The string builder to print the diff to.
     */
    public void diff(Value old, StringBuilder b) {
        Value v = new Value(this);
        v.flags &= ~old.flags; // TODO: see Value.remove above (anyway, diff is only used for debug output)
        if (v.object_labels != null && old.object_labels != null) {
            v.object_labels = newSet(v.object_labels);
            v.object_labels.removeAll(old.object_labels);
        }
        b.append(v);
    }

    /**
     * Returns the hash code for this value.
     */
    @Override
    public int hashCode() {
        return hashcode;
    }

    /**
     * Returns the source locations of the objects in this value.
     */
    public Set<SourceLocation> getObjectSourceLocations() {
        Set<SourceLocation> res = newSet();
        if (object_labels != null)
            for (ObjectLabel objlabel : object_labels)
                res.add(objlabel.getSourceLocation());
        return res;
    }

    /**
     * Produces a string description of this value.
     * Ignores attributes and modified flag.
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        boolean any = false;
        if (isUnknown()) {
            b.append("?");
            any = true;
        } else if (isPolymorphic()) {
            b.append("^(").append(var).append('[');
            if (isMaybeAbsent()) {
                b.append("absent");
                any = true;
            }
            if (isMaybePresent()) {
                if (any)
                    b.append('|');
                b.append("present");
            }
            b.append("])");
//            if (var_summarized != null)
//            b.append('<').append(var_summarized).append('>');    
            any = true;
        } else {
            if (isMaybeUndef()) {
                b.append("Undef");
                any = true;
            }
            if (isMaybeNull()) {
                if (any)
                    b.append('|');
                b.append("Null");
                any = true;
            }
            if (isMaybeAnyBool()) {
                if (any)
                    b.append('|');
                b.append("Bool");
                any = true;
            } else if (isMaybeTrueButNotFalse()) {
                if (any)
                    b.append('|');
                b.append("true");
                any = true;
            } else if (isMaybeFalseButNotTrue()) {
                if (any)
                    b.append('|');
                b.append("false");
                any = true;
            }
            if (isMaybeAnyNum()) {
                if (any)
                    b.append('|');
                b.append("Num");
                any = true;
            } else {
                if (isMaybeNumUInt()) {
                    if (any)
                        b.append('|');
                    b.append("UInt");
                    any = true;
                }
                if (isMaybeNumOther()) {
                    if (any)
                        b.append('|');
                    b.append("NotUInt");
                    any = true;
                }
                if (isMaybeNaN()) {
                    if (any)
                        b.append('|');
                    b.append("NaN");
                    any = true;
                }
                if (isMaybeInf()) {
                    if (any)
                        b.append('|');
                    b.append("Inf");
                    any = true;
                }
                if (num != null) {
                    if (any)
                        b.append('|');
                    b.append(num);
                    any = true;
                }
            }
            if (isMaybeAnyStr()) { // Picker & Maldonado
                if (any)
                    b.append('|');
                b.append("Str");
                any = true;
            } else if (isMaybeSingleStr()) {
                if(any)
                b.append('|');
                b.append(str);
                any = true;
            } else if (str != null) {
                if(any)
                    b.append('|');
                b.append(str);
                any = true;
            }
            else {
                if (isMaybeStrUInt()) {
                    if (any)
                        b.append('|');
                    b.append("UIntStr");
                    any = true;
                } else if (isMaybeStrOtherNum()) {
                    if (any)
                        b.append('|');
                    b.append("NotUIntStr"); // TODO: change to NotUIntNumStr
                    any = true;
                }
                if (isMaybeStrIdentifier()) {
                    if (any)
                        b.append('|');
                    b.append("IdentStr");
                    any = true;
                } else if (isMaybeStrIdentifierParts()) {
                    if (any)
                        b.append('|');
                    b.append("IdentPartsStr");
                    any = true;
                }
                if (isMaybeStrOther()) {
                    if (any)
                        b.append('|');
                    b.append("OtherStr");
                    any = true;
                }
                if (isStrJSON()) {
                    if (any)
                        b.append("|");
                    b.append("JSONStr");
                    any = true;
                }
//                if (str != null && !isMaybeSingleStr()) {
//                    if (any)
//                        b.append('|');
////                    b.append(Strings.escape(str.toString()));
//                    b.append(str.toString());
//                    any = true;
//                }
            }
            if (object_labels != null) {
                if (any)
                    b.append('|');
                b.append(object_labels);
                any = true;
            }
            if (isMaybeAbsent()) {
                if (any)
                    b.append('|');
                b.append("absent");
                any = true;
            }
        }
        if (!any)
            b.append("<no value>");
//         if (isMaybeModified())
//         b.append("%");
        return b.toString();
    }

    /**
     * Produces a string description of the attributes of this value.
     */
    public String printAttributes() {
        checkNotUnknown();
        StringBuilder b = new StringBuilder();
        if (hasDontDelete()) {
            b.append("(DontDelete");
            if (isMaybeDontDelete())
                b.append("+");
            if (isMaybeNotDontDelete())
                b.append("-");
            b.append(")");
        }
        if (hasDontEnum()) {
            b.append("(DontEnum");
            if (isMaybeDontEnum())
                b.append("+");
            if (isMaybeNotDontEnum())
                b.append("-");
            b.append(")");
        }
        if (hasReadOnly()) {
            b.append("(ReadOnly");
            if (isMaybeReadOnly())
                b.append("+");
            if (isMaybeNotReadOnly())
                b.append("-");
            b.append(")");
        }
        return b.toString();
    }

    /* the Undef facet */

    @Override
    public boolean isMaybeUndef() {
        checkNotPolymorphicOrUnknown();
        return (flags & UNDEF) != 0;
    }

    @Override
    public boolean isNotUndef() {
        checkNotPolymorphicOrUnknown();
        return (flags & UNDEF) == 0;
    }

    @Override
    public boolean isMaybeOtherThanUndef() {
        checkNotPolymorphicOrUnknown();
        return (flags & (NULL | BOOL | NUM | STR)) != 0 || num != null || str != null || object_labels != null;
    }

    @Override
    public Value joinUndef() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeUndef())
            return this;
        else
            return reallyMakeUndef(this);
    }

    @Override
    public Value restrictToNotUndef() {
        checkNotPolymorphicOrUnknown();
        if (isNotUndef())
            return this;
        Value r = new Value(this);
        r.flags &= ~UNDEF;
        return canonicalize(r);
    }

    @Override
    public Value restrictToUndef() {
        checkNotPolymorphicOrUnknown();
        if (isNotUndef())
            return theNone;
        return theUndef;
    }

    private static Value reallyMakeUndef(Value v) {
        Value r = (v == null) ? new Value() : new Value(v);
        r.flags |= UNDEF;
        return canonicalize(r);
    }

    /**
     * Constructs the value describing definitely undefined.
     */
    public static Value makeUndef() {
        return theUndef;
    }

    /* The Null facet */

    @Override
    public boolean isMaybeNull() {
        checkNotPolymorphicOrUnknown();
        return (flags & NULL) != 0;
    }

    @Override
    public boolean isNotNull() {
        checkNotPolymorphicOrUnknown();
        return (flags & NULL) == 0;
    }

    @Override
    public boolean isMaybeOtherThanNull() {
        checkNotPolymorphicOrUnknown();
        return (flags & (UNDEF | BOOL | NUM | STR)) != 0 || num != null || str != null || object_labels != null;
    }

    @Override
    public Value joinNull() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeNull())
            return this;
        else
            return reallyMakeNull(this);
    }

    @Override
    public Value restrictToNotNull() {
        checkNotPolymorphicOrUnknown();
        if (isNotNull())
            return this;
        Value r = new Value(this);
        r.flags &= ~NULL;
        return canonicalize(r);
    }

    @Override
    public Value restrictToNull() {
        checkNotPolymorphicOrUnknown();
        if (isNotNull())
            return theNone;
        return theNull;
    }

    /**
     * Returns true if this value is definitely null or undefined.
     */
    public boolean isNullOrUndef() {
        checkNotPolymorphicOrUnknown();
        return (flags & (NULL | UNDEF)) != 0
                && (flags & (NUM | BOOL)) == 0 && num == null && str == null && object_labels == null;
    }

    /**
     * Constructs a value as a copy of this value but definitely not null nor undefined.
     */
    public Value restrictToNotNullNotUndef() {
        checkNotPolymorphicOrUnknown();
        if (!isMaybeNull() && !isMaybeUndef())
            return this;
        Value r = new Value(this);
        r.flags &= ~(NULL | UNDEF);
        return canonicalize(r);
    }

    private static Value reallyMakeNull(Value v) {
        Value r = (v == null) ? new Value() : new Value(v);
        r.flags |= NULL;
        return canonicalize(r);
    }

    /**
     * Constructs the value describing definitely null.
     */
    public static Value makeNull() {
        return theNull;
    }

    /* The Bool facet */

    @Override
    public boolean isMaybeAnyBool() {
        checkNotPolymorphicOrUnknown();
        return (flags & BOOL) == BOOL;
    }

    @Override
    public boolean isMaybeTrueButNotFalse() {
        checkNotPolymorphicOrUnknown();
        return (flags & BOOL) == BOOL_TRUE;
    }

    @Override
    public boolean isMaybeFalseButNotTrue() {
        checkNotPolymorphicOrUnknown();
        return (flags & BOOL) == BOOL_FALSE;
    }

    @Override
    public boolean isMaybeTrue() {
        checkNotPolymorphicOrUnknown();
        return (flags & BOOL_TRUE) == BOOL_TRUE;
    }

    @Override
    public boolean isMaybeFalse() {
        checkNotPolymorphicOrUnknown();
        return (flags & BOOL_FALSE) == BOOL_FALSE;
    }

    @Override
    public boolean isNotBool() {
        checkNotPolymorphicOrUnknown();
        return (flags & BOOL) == 0;
    }

    @Override
    public boolean isMaybeOtherThanBool() {
        checkNotPolymorphicOrUnknown();
        return (flags & (UNDEF | NULL | NUM | STR)) != 0 || num != null || str != null || object_labels != null;
    }

    @Override
    public Value joinAnyBool() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeAnyBool())
            return this;
        Value r = new Value(this);
        r.flags |= BOOL;
        return canonicalize(r);
    }

    @Override
    public Value joinBool(boolean x) {
        checkNotPolymorphicOrUnknown();
        if (isMaybeAnyBool() || (x ? isMaybeTrueButNotFalse() : isMaybeFalseButNotTrue()))
            return this;
        Value r = new Value(this);
        r.flags |= x ? BOOL_TRUE : BOOL_FALSE;
        return canonicalize(r);
    }

    @Override
    public Value joinBool(Value x) {
        checkNotPolymorphicOrUnknown();
        x.checkNotPolymorphicOrUnknown();
        if (isMaybeAnyBool() || x.isMaybeAnyBool() || (isMaybeTrue() && x.isMaybeFalse()) || (isMaybeFalse() && x.isMaybeTrue()))
            return theBoolAny;
        if (isNotBool())
            return x;
        else
            return this;
    }

    private static Value reallyMakeBool(Boolean b) {
        Value r = new Value();
        if (b == null)
            r.flags |= BOOL;
        else if (b)
            r.flags |= BOOL_TRUE;
        else
            r.flags |= BOOL_FALSE;
        return canonicalize(r);
    }

    /**
     * Constructs the value representing any boolean.
     */
    public static Value makeAnyBool() {
        return theBoolAny;
    }

    /**
     * Constructs the value describing the given boolean.
     */
    public static Value makeBool(boolean b) {
        if (b)
            return theBoolTrue;
        else
            return theBoolFalse;
    }

    /**
     * Constructs the value describing the given Bool.
     */
    public static Value makeBool(Bool b) {
        if (b.isMaybeAnyBool())
            return theBoolAny;
        else if (b.isMaybeTrueButNotFalse())
            return theBoolTrue;
        else if (b.isMaybeFalseButNotTrue())
            return theBoolFalse;
        else
            return theNone;
    }

    /**
     * Constructs a value from this value where only the boolean facet is considered.
     */
    public Value restrictToBool() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeAnyBool())
            return theBoolAny;
        else if (isMaybeTrueButNotFalse())
            return theBoolTrue;
        else if (isMaybeFalseButNotTrue())
            return theBoolFalse;
        else
            return theNone;
    }

    /**
     * Constructs a value as a copy of this value but definitely not falsy.
     */
    public Value restrictToTruthy() {
        checkNotPolymorphicOrUnknown();
        Value r = new Value(this);
        if (r.str != null && r.str.hasIntersection(AbstractString.getEmptyString())) // Picker & Maldonado
            r.str = r.str.intersect(AbstractString.getEmptyString().complement());
        if (r.num != null && Math.abs(r.num) == 0.0)
            r.num = null;
        r.flags &= ~(BOOL_FALSE | NULL | UNDEF | NUM_NAN);
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but definitely not truthy.
     */
    public Value restrictToFalsy() {
        checkNotPolymorphicOrUnknown();
        Value r = new Value(this);
        if (r.str != null) //TODO : make this more precise // Picker & Maldonado
            r.str = null;
        if (r.num != null && Math.abs(r.num) != 0.0)
            r.num = null;
        r.object_labels = null;
        r.flags &= ~(BOOL_TRUE);
        return canonicalize(r);
    }

    /**
     * Constructs a value from this value where only the string/boolean/number facets are considered.
     */
    public Value restrictToStrBoolNum() {
        checkNotPolymorphicOrUnknown();
        Value r = new Value(this);
        r.object_labels = null;
        r.flags &= STR | BOOL | NUM;
        return canonicalize(r);
    }

    /* the Num facet */

    @Override
    public boolean isMaybeAnyNum() {
        checkNotPolymorphicOrUnknown();
        return (flags & NUM) == NUM;
    }

    @Override
    public boolean isMaybeSingleNum() {
        checkNotPolymorphicOrUnknown();
        return num != null;
    }

    @Override
    public boolean isMaybeSingleNumUInt() {
        checkNotPolymorphicOrUnknown();
        return num != null && isUInt32(num);
    }

    @Override
    public boolean isMaybeFuzzyNum() {
        checkNotPolymorphicOrUnknown();
        return (flags & NUM) != 0;
    }

    @Override
    public boolean isMaybeNaN() {
        checkNotPolymorphicOrUnknown();
        return (flags & NUM_NAN) != 0;
    }

    @Override
    public boolean isNaN() {
        checkNotPolymorphicOrUnknown();
        return (flags & NUM) == NUM_NAN && !isMaybeOtherThanNum();
    }

    @Override
    public boolean isMaybeInf() {
        checkNotPolymorphicOrUnknown();
        return (flags & NUM_INF) != 0;
    }

    @Override
    public boolean isInf() {
        checkNotPolymorphicOrUnknown();
        return (flags & NUM) == NUM_INF && !isMaybeOtherThanNum();
    }

    @Override
    public boolean isMaybeNumUInt() {
        checkNotPolymorphicOrUnknown();
        return (flags & NUM_UINT) != 0;
    }

    @Override
    public boolean isMaybeNumOther() {
        checkNotPolymorphicOrUnknown();
        return (flags & NUM_OTHER) != 0;
    }

    @Override
    public boolean isMaybeOtherThanNum() {
        checkNotPolymorphicOrUnknown();
        return ((flags & (UNDEF | NULL | BOOL | STR)) != 0) || str != null || object_labels != null;
    }

    @Override
    public boolean isMaybeOtherThanNumUInt() {
        checkNotPolymorphicOrUnknown();
        return ((flags & (UNDEF | NULL | BOOL | STR | NUM_INF | NUM_NAN | NUM_OTHER)) != 0) || str != null || object_labels != null;
    }

    @Override
    public Double getNum() {
        checkNotPolymorphicOrUnknown();
        return num != null ? num : (flags & NUM_NAN) != 0 ? Double.NaN : null;
    }

    @Override
    public boolean isNotNum() {
        checkNotPolymorphicOrUnknown();
        return (flags & NUM) == 0 && num == null;
    }

    @Override
    public Value joinAnyNum() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeAnyNum())
            return this;
        Value r = new Value(this);
        r.num = null;
        r.flags |= NUM;
        return canonicalize(r);
    }

    @Override
    public Value joinAnyNumUInt() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeNumUInt())
            return this;
        Value r = new Value(this);
        r.flags |= NUM_UINT;
        r.num = null;
        if (num != null)
            r.joinSingleNumberAsFuzzy(num);
        return canonicalize(r);
    }

    @Override
    public Value joinAnyNumOther() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeNumOther())
            return this;
        Value r = new Value(this);
        r.flags |= NUM_OTHER;
        r.num = null;
        if (num != null)
            r.joinSingleNumberAsFuzzy(num);
        return canonicalize(r);
    }

    @Override
    public Value restrictToNotNaN() {
        checkNotPolymorphicOrUnknown();
        if (!isMaybeNaN())
            return this;
        Value r = new Value(this);
        r.flags &= ~NUM_NAN;
        return canonicalize(r);
    }

    private static boolean checkUInt32(double v) {
        return v >= 0 && v <= Integer.MAX_VALUE * 2.0 + 1 && (v % 1) == 0;
    }

    private static boolean isUInt32(double v) {
        return !Double.isNaN(v) && !Double.isInfinite(v) && checkUInt32(v);
    }

    /**
     * Joins the given single number as a fuzzy value.
     */
    private void joinSingleNumberAsFuzzy(double v) {
        if (Double.isNaN(v))
            flags |= NUM_NAN;
        else if (Double.isInfinite(v))
            flags |= NUM_INF;
        else if (isUInt32(v))
            flags |= NUM_UINT;
        else
            flags |= NUM_OTHER;
    }

    @Override
    public Value joinNum(double v) {
        checkNotPolymorphicOrUnknown();
        if (Double.isNaN(v))
            return joinNumNaN();
        if (num != null && num == v)
            return this;
        Value r = new Value(this);
        if (isNotNum())
            r.num = v;
        else {
            if (num != null) {
                r.num = null;
                r.joinSingleNumberAsFuzzy(num);
            }
            r.joinSingleNumberAsFuzzy(v);
        }
        return canonicalize(r);
    }

    @Override
    public Value joinNumNaN() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeNaN())
            return this;
        Value r = new Value(this);
        r.flags |= NUM_NAN;
        r.num = null;
        if (num != null)
            r.joinSingleNumberAsFuzzy(num);
        return canonicalize(r);
    }

    @Override
    public Value joinNumInf() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeInf())
            return this;
        Value r = new Value(this);
        r.flags |= NUM_INF;
        r.num = null;
        if (num != null)
            r.joinSingleNumberAsFuzzy(num);
        return canonicalize(r);
    }

    private static Value reallyMakeAnyUInt() {
        Value r = new Value();
        r.flags = NUM_UINT;
        return canonicalize(r);
    }

    private static Value reallyMakeAnyNumOther() {
        Value r = new Value();
        r.flags = NUM_OTHER;
        return canonicalize(r);
    }

    private static Value reallyMakeAnyNumNotNaNInf() {
        Value r = new Value();
        r.flags = NUM_UINT | NUM_OTHER;
        return canonicalize(r);
    }

    private static Value reallyMakeNumNaN() {
        Value r = new Value();
        r.flags = NUM_NAN;
        return canonicalize(r);
    }

    private static Value reallyMakeNumInf() {
        Value r = new Value();
        r.flags = NUM_INF;
        return canonicalize(r);
    }

    private static Value reallyMakeAnyNum() {
        Value r = new Value();
        r.flags = NUM;
        return canonicalize(r);
    }

    /**
     * Constructs the value describing the given number.
     */
    public static Value makeNum(double d) {
        if (Double.isNaN(d))
            return theNumNaN;
        if (Double.isInfinite(d))
            return theNumInf;
        Value r = new Value();
        r.num = d;
        return canonicalize(r);
    }

    /**
     * Constructs the value describing NaN.
     */
    public static Value makeNumNaN() {
        return theNumNaN;
    }

    /**
     * Constructs the value describing +/-Inf.
     */
    public static Value makeNumInf() {
        return theNumInf;
    }

    /**
     * Constructs the value describing any number.
     */
    public static Value makeAnyNum() {
        return theNumAny;
    }

    /**
     * Constructs the value describing any UInt number.
     */
    public static Value makeAnyNumUInt() {
        return theNumUInt;
    }

    /**
     * Constructs the value describing any non-UInt, non-+/-Inf, non-NaN number.
     */
    public static Value makeAnyNumOther() {
        return theNumOther;
    }

    /**
     * Constructs the value describing number except NaN and infinity.
     */
    public static Value makeAnyNumNotNaNInf() {
        return theNumNotNaNInf;
    }

    @Override
    public Value restrictToNum() {
        checkNotPolymorphicOrUnknown();
        Value r = new Value();
        r.flags = flags & NUM;
        r.num = num;
        return canonicalize(r);
    }

    @Override
    public Value restrictToNotNum() {
        checkNotPolymorphicOrUnknown();
        Value r = new Value(this);
        r.flags &= ~NUM;
        r.num = null;
        return canonicalize(r);
    }

    /* the Str facet */ // Picker & Maldonado BASICALLY THIS ENTIRE FACET
    @Override
    public boolean isMaybeAnyStr() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.equals(AbstractString.getAnyString());
    }

    @Override
    public boolean isMaybeStrUInt() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.hasIntersection(AbstractString.uIntString());
    }

    @Override
    public boolean isMaybeStrSomeUInt() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.hasIntersection(AbstractString.uIntString());
    }

    @Override
    public boolean isMaybeStrSomeNonUInt() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.hasIntersection(AbstractString.uIntString().complement());
    }

    @Override
    public boolean isMaybeStrOtherNum() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.equals(AbstractString.otherNumString());
    }

    @Override
    public boolean isMaybeStrIdentifier() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.hasIntersection(AbstractString.getIdentifierString());
    }

    @Override
    public boolean isMaybeStrIdentifierParts() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.hasIntersection(AbstractString.getIdentifierPartsString());
    }

    //no longer used
    @Override
    public boolean isMaybeStrPrefixedIdentifierParts() {
        checkNotPolymorphicOrUnknown();
        return isMaybeStrIdentifierParts();
    }

    public boolean isMaybeManyStrings() {
        return str != null && str.getDfa().getFiniteStrings(1) == null;
    }

    @Override
    public boolean isMaybeStrOther() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.isSubset(AbstractString.getStringOther());
    }

    @Override
    public boolean isMaybeStrJSON() { // FIXME: check STR_JSON, e.g. at join
        checkNotPolymorphicOrUnknown();
        return (flags & STR_JSON) != 0;
    }

    @Override
    public boolean isStrJSON() {
        checkNotPolymorphicOrUnknown();
        return (flags & PRIMITIVE) == STR_JSON && str == null && num == null && object_labels == null;
    }

    @Override
    public boolean isStrIdentifierOrIdentifierParts() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.isSubset((AbstractString.getIdentifierPartsString()));
    }

    @Override
    public boolean isStrIdentifier() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.isSubset(AbstractString.getIdentifierString());
    }

    @Override
    public boolean isMaybeStrOnlyUInt() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.isSubset(AbstractString.uIntString());
    }

    @Override
    public boolean isMaybeSingleStr() {
        checkNotPolymorphicOrUnknown();
        return str != null && str.stringValue() != null;
    }

    @Override
    public boolean isMaybeFuzzyStr() {
        checkNotPolymorphicOrUnknown();
        return str != null && !isMaybeSingleStr();
    }

    @Override
    public String getStr() {
        checkNotPolymorphicOrUnknown();
        return str != null ? str.stringValue() : null;
    }

    /**
     * Concats this with s, and joins it with l
     */
    public Value strConcatenate(Str s, Str l) {
        Value r = new Value((Value)l);
        if(r.str != null) {
            r.str.leastUpperBound(AbstractString.concat(str, s.getAbstractStr()));
        } else {
            r.str = AbstractString.concat(str, s.getAbstractStr());
        }
        return r;
    }

    public AbstractString getAbstractStr() {
        return str;
    }

    @Override
    public String getPrefix() {
        checkNotPolymorphicOrUnknown();
        return str.getCommonPrefix();
    }

    @Override
    public boolean isNotStr() {
        checkNotPolymorphicOrUnknown();
        return str == null;
    }

    @Override
    public boolean isMaybeOtherThanStr() {
        checkNotPolymorphicOrUnknown();
        return (flags & (UNDEF | NULL | BOOL | NUM)) != 0 || num != null || object_labels != null;
    }

    @Override
    public Value joinAnyStr() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeAnyStr())
            return this;
        Value r = new Value(this);
        if(str != null) {
            r.str = str.leastUpperBound(AbstractString.getAnyString());
        } else {
            r.str = AbstractString.newEmptyAbstractString().leastUpperBound((AbstractString.getAnyString()));
        }
        return canonicalize(r);
    }

    @Override
    public Value joinAnyStrUInt() {
        checkNotPolymorphicOrUnknown();
        if (str != null && str.equals(AbstractString.uIntString()))
            return this;
        Value r = new Value(this);
        if(str != null) {
            r.str = str.leastUpperBound(AbstractString.uIntString());
        } else {
            r.str = AbstractString.newEmptyAbstractString().leastUpperBound((AbstractString.uIntString()));
        }
        return canonicalize(r);
    }

    @Override
    public Value joinAnyStrOtherNum() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeStrOtherNum())
            return this;
        Value r = new Value(this);
        if(str != null) {
            r.str = str.leastUpperBound(AbstractString.otherNumString());
        } else {
            r.str = AbstractString.newEmptyAbstractString().leastUpperBound((AbstractString.otherNumString()));
        }
        return canonicalize(r);
    }

    // not used
    @Override
    public Value joinAnyStrIdentifier() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeStrIdentifier() || isMaybeStrIdentifierParts())
            return this;
        Value r = new Value(this);
        r.str = AbstractString.getIdentifierString();
        r.joinAbstractStringWithLub(this);
        return canonicalize(r);
    }

    // no longer used
    @Override
    public Value joinAnyStrIdentifierParts() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeStrIdentifierParts())
            return this;
        Value r = new Value(this);
        r.str = null;
        r.joinAbstractStringWithLub(this);
        return canonicalize(r);
    }

    // not used
    @Override
    public Value joinAnyStrOther() {
        checkNotPolymorphicOrUnknown();
        if (isMaybeStrOther())
            return this;
        Value r = new Value(this);
        r.str = r.str.leastUpperBound(AbstractString.getStringOther());
        return canonicalize(r);
    }

    @Override
    public Value joinStr(String s) {
        checkNotPolymorphicOrUnknown();
        if (str != null && str.equals(s))
            return this;
        Value r = new Value(this);
        Value tmp = new Value();
        tmp.str = new AbstractString(s);
        r.str = tmp.str.leastUpperBound(r.str);
        return canonicalize(r);
    }

    /**
     *
     * @param v value to join with this value
     * @param f if true, use LUB, else use widening operator
     * @return the result of joining the two abstract string parts of the Values
     */
    private boolean joinAbstractString(Value v, boolean f) {
        AbstractString oldstr = str;
        if(str != null) {
            if (v.str != null){
                if (f)
                    str = str.leastUpperBound(v.getAbstractStr());
                else
                    str = str.widen(v.getAbstractStr());
            }
        } else if (v.str != null) {
            str = v.str;
            return true;
        } else {
            return false;
        }
        return !oldstr.equals(str);
    }

    private boolean joinSingleStringWithWiden(Value v) {
        return joinAbstractString(v, false);
    }

    /**
     * Joins the single string or prefix string part of the given value into this value.
     * No other parts of v are used.
     * Also converts the existing single string or prefix string to a fuzzy value if necessary.
     *
     * @return true if this value is modified
     */
    private boolean joinAbstractStringWithLub(Value v) { // TODO: could be more precise in some cases...
        return joinAbstractString(v, true);
    }

    @Override
    public boolean isMaybeStr(String s) {
        checkNotPolymorphicOrUnknown();
        return str != null && str.run(s);
    }

    private static Value reallyMakeAnyStr() {
        Value r = new Value();
        r.str = AbstractString.getAnyString();
        return canonicalize(r);
    }

    private static Value reallyMakeAnyStrUInt() {
        Value r = new Value();
        r.str = AbstractString.uIntString();
        return canonicalize(r);
    }

    private static Value reallyMakeAnyStrNotUInt() {
        Value r = new Value();
        r.str = AbstractString.uIntString().complement();
        return canonicalize(r);
    }

    /**
     * Constructs the value describing any string.
     */
    public static Value makeAnyStr() {
        return theStrAny;
    }

    /**
     * Constructs the value describing any UInt string.
     */
    public static Value makeAnyStrUInt() {
        return theStrUInt;
    }

    /**
     * Constructs the value describing any non-UInt string.
     */
    public static Value makeAnyStrNotUInt() {
        return theStrNotUInt;
    }

    private static Value reallyMakeJSONStr() {
        Value r = new Value();
        r.flags |= STR_JSON;
        return canonicalize(r);
    }

    /**
     * Constructs the value describing any JSON string.
     */
    public static Value makeJSONStr() {
        return theJSONStr;
    }

    /**
     * Constructs the value describing the given string.
     */
    public static Value makeStr(String s) {
        Value r = new Value();
        r.str = new AbstractString(s);
        return canonicalize(r);
    }

    /**
     * Constructs a temporary value describing the given string.
     * The object is not canonicalized and should therefore not be stored in abstract states.
     */
    public static Str makeTemporaryStr(String s) {
        Value r = new Value();
        r.str = new AbstractString(s);
        return r;
    }

    @Override
    public Value restrictToStr() {
        checkNotPolymorphicOrUnknown();
        Value r = new Value();
        r.flags = flags & STR;
        r.str = str;
        return canonicalize(r);
    }

    @Override
    public Value restrictToNotStr() {
        checkNotPolymorphicOrUnknown();
        Value r = new Value(this);
        r.flags &= ~STR;
        r.str = null;
        return canonicalize(r);
    }

    /* object labels */

    /**
     * Constructs the value describing the given object label.
     */
    public static Value makeObject(ObjectLabel v) {
        if (v == null)
            throw new NullPointerException();
        Value r = new Value();
        r.object_labels = newSet();
        r.object_labels.add(v);
        return canonicalize(r);
    }

    /**
     * Constructs the value describing the given object labels.
     */
    public static Value makeObject(Set<ObjectLabel> v) {
        Value r = new Value();
        if (!v.isEmpty())
            r.object_labels = newSet(v);
        return canonicalize(r);
    }

    /**
     * Constructs a value as the join of this value and the given object label.
     */
    public Value joinObject(ObjectLabel objlabel) {
        checkNotPolymorphicOrUnknown();
        if (object_labels != null && object_labels.contains(objlabel))
            return this;
        Value r = new Value(this);
        if (r.object_labels == null)
            r.object_labels = newSet();
        else
            r.object_labels = newSet(r.object_labels);
        r.object_labels.add(objlabel);
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but only with object values.
     */
    public Value restrictToObject() {
        checkNotPolymorphicOrUnknown();
        if (!isMaybePrimitive())
            return this;
        Value r = new Value(this);
        r.flags &= ~PRIMITIVE;
        r.num = null;
        r.str = null;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but only with non-object values.
     * Unknown and polymorphic values are returned unmodified.
     */
    public Value restrictToNotObject() {
        if (object_labels == null)
            return this;
        Value r = new Value(this);
        r.object_labels = null;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with the given object labels removed.
     */
    public Value removeObjects(Set<ObjectLabel> objs) {
        checkNotPolymorphicOrUnknown();
        if (object_labels == null)
            return this;
        Value r = new Value(this);
        r.object_labels = newSet(r.object_labels);
        r.object_labels.removeAll(objs);
        if (r.object_labels.isEmpty())
            r.object_labels = null;
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with object labels summarized.
     * If s is null or the value is unknown or polymorphic, this value is returned instead.
     */
    public Value summarize(Summarized s) {
        if (s == null || isUnknown() || isPolymorphic())
            return this;
        Set<ObjectLabel> ss = s.summarize(getObjectLabels());
        if (ss.equals(getObjectLabels()))
            return this;
        Value r = new Value(this);
        if (ss.isEmpty())
            ss = null;
        r.object_labels = ss;
        r.flags |= MODIFIED;
        return canonicalize(r);
    }

    /**
     * Returns true if this value is maybe present.
     */
    public boolean isMaybePresent() {
        checkNotUnknown();
        if (isPolymorphic())
            return (flags & PRESENT) != 0;
        else
            return (flags & PRIMITIVE) != 0 || num != null || str != null || object_labels != null;
    }

    /**
     * Returns true if this value is maybe present in the polymorphic part.
     */
    public boolean isMaybePolymorphicPresent() {
        return (flags & PRESENT) != 0;
    }

    /**
     * Returns true if this value is maybe present or 'unknown'.
     */
    public boolean isMaybePresentOrUnknown() {
        return isUnknown() || isMaybePresent();
    }

    /**
     * Returns true if this value is definitely not present.
     */
    public boolean isNotPresent() {
        checkNotUnknown();
        return !isMaybePresent();
    }

    /**
     * Returns a value as this one but marked as having extended scope.
     */
    public Value makeExtendedScope() {
        checkNotUnknown();
        if (isExtendedScope())
            return this;
        Value r = new Value(this);
        r.flags |= EXTENDEDSCOPE;
        return canonicalize(r);
    }

    /**
     * Returns true if this value is marked as having extended scope.
     */
    public boolean isExtendedScope() {
        return (flags & EXTENDEDSCOPE) != 0;
    }

    /**
     * Returns true if this value maybe represents an object.
     */
    public boolean isMaybeObject() {
        checkNotPolymorphicOrUnknown();
        return object_labels != null;
    }

    /**
     * Returns true if this value maybe represents something that isn't a object.
     */
    public boolean isMaybeOtherThanObject() {
        checkNotPolymorphicOrUnknown();
        return (flags & (NULL | UNDEF | BOOL | NUM | STR)) != 0 || num != null || str != null;
    }

    /**
     * Returns true if this value may be a non-object, including undefined and null.
     */
    public boolean isMaybePrimitive() {
        checkNotPolymorphicOrUnknown();
        return (flags & PRIMITIVE) != 0 || num != null || str != null;
    }

    /**
     * Returns the (immutable) set of object labels.
     * Returns the empty set for polymorphic and 'unknown' values.
     */
    public Set<ObjectLabel> getObjectLabels() {
        if (object_labels == null)
            return Collections.emptySet();
        if (Options.get().isDebugOrTestEnabled())
            return Collections.unmodifiableSet(object_labels);
        return object_labels;
    }

    /**
     * Returns a copy of this value where the given object label has been replaced, if present.
     *
     * @param oldlabel The object label to replace.
     * @param newlabel The object label to replace oldlabel with.
     */
    public Value replaceObjectLabel(ObjectLabel oldlabel, ObjectLabel newlabel) {
        if (oldlabel.equals(newlabel))
            throw new AnalysisException("Equal object labels not expected");
        if (object_labels == null || !object_labels.contains(oldlabel))
            return this;
        Set<ObjectLabel> newobjlabels = newSet(object_labels);
        newobjlabels.remove(oldlabel);
        newobjlabels.add(newlabel);
        Value r = new Value(this);
        r.object_labels = newobjlabels;
        return canonicalize(r);
    }

    /**
     * Returns a copy of this value where the object labels have been replaced according to the given map.
     * Does not change modified flags. Object labels not in the key set of the map are unchanged.
     *
     * @param m A map between old object labels and new object labels.
     * @return A copy of the old value with the object labels replaced according to the map.
     */
    public Value replaceObjectLabels(Map<ObjectLabel, ObjectLabel> m) {
        if (isPolymorphic()) {
            PropertyReference pr = Renaming.apply(m, var);
            if (pr.getObjectLabel().equals(var.getObjectLabel()))
                return this;
            Value r = new Value(this);
            r.var = pr;
            return canonicalize(r);
        }
        if (object_labels == null || m.isEmpty())
            return this;
        Set<ObjectLabel> newobjlabels = newSet();
        for (ObjectLabel objlabel : object_labels)
            newobjlabels.add(Renaming.apply(m, objlabel));
        Value r = new Value(this);
        r.object_labels = newobjlabels;
        return canonicalize(r);
    }

    /**
     * Checks that this value is non-empty (or polymorphic).
     *
     * @throws AnalysisException if empty
     */
    public void assertNonEmpty() {
        checkNotUnknown();
        if (isPolymorphic())
            return;
        if ((flags & PRIMITIVE) == 0 && num == null && str == null && object_labels == null
                && !Options.get().isPropagateDeadFlow())
            throw new AnalysisException("Empty value");
    }

    /**
     * Returns the number of different types of this value.
     * The possible types are here boolean/string/number/function/array/native/dom/other. Undef and null are ignored, except if they are the only value.
     * Polymorphic and unknown values also count as 0.
     */
    public int typeSize() {
        if (isUnknown() || isPolymorphic())
            return 0;
        int c = 0;
        if (!isNotBool())
            c++;
        if (!isNotStr())
            c++;
        if (!isNotNum())
            c++;
        if (object_labels != null) {
            boolean is_function = false;
            boolean is_array = false;
            boolean is_native = false;
            boolean is_dom = false;
            boolean is_other = false;
            for (ObjectLabel objlabel : object_labels) {
                if (objlabel.getKind() == Kind.FUNCTION)
                    is_function = true;
                else if (objlabel.getKind() == Kind.ARRAY)
                    is_array = true;
                else if (objlabel.isHostObject()) {
                    switch (objlabel.getHostObject().getAPI().getShortName()) {
                        case "native":
                            is_native = true;
                            break;
                        case "dom":
                            is_dom = true;
                            break;
                        default:
                            is_other = true;
                    }
                } else
                    is_other = true;
            }
            if (is_function)
                c++;
            if (is_array)
                c++;
            if (is_native)
                c++;
            if (is_dom)
                c++;
            if (is_other)
                c++;
        }
        if (c == 0 && (isMaybeNull() || isMaybeUndef())) {
            c = 1;
        }
        return c;
    }

    /**
     * Constructs a value as a copy of this value but for reading attributes.
     */
    public Value restrictToAttributes() {
        Value r = new Value(this);
        r.num = null;
        r.str = null;
        r.var = null;
        r.flags &= ATTR | ABSENT | UNKNOWN;
        if (!isUnknown() && isMaybePresent())
            r.flags |= UNDEF; // just a dummy value, to satisfy the representation invariant for PRESENT
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of this value but with all attributes set to bottom.
     */
    public Value restrictToNonAttributes() {
        Value r = new Value(this);
        r.flags &= ~(PROPERTYDATA | ABSENT | PRESENT);
        return canonicalize(r);
    }

    /**
     * Constructs a value as a copy of the given value but with the attributes from this value.
     */
    public Value replaceValue(Value v) {
        Value r = new Value(v);
        r.flags &= ~(PROPERTYDATA | ABSENT | PRESENT);
        r.flags |= flags & (PROPERTYDATA | ABSENT);
        if (r.var != null && (flags & PRESENT) != 0)
            r.flags |= PRESENT;
        return canonicalize(r);
    }

    /**
     * Returns true is this value contains exactly one object label.
     */
    public boolean isMaybeSingleObjectLabel() {
        checkNotPolymorphicOrUnknown();
        return object_labels != null && object_labels.size() == 1;
    }

    /**
     * Returns true is this value contains exactly one object source location.
     */
    public boolean isMaybeSingleAllocationSite() {
        checkNotPolymorphicOrUnknown();
        return object_labels != null && newSet(getObjectSourceLocations()).size() == 1;
    }

    /**
     * Returns true if this value does not contain a summarized object label.
     */
    public boolean isNotASummarizedObject() {
        checkNotPolymorphicOrUnknown();
        if (object_labels == null) {
            return true;
        }
        for (ObjectLabel object_label : object_labels) {
            if (!object_label.isSingleton()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if this value does not contain a singleton object label.
     */
    public boolean isNotASingletonObject() {
        checkNotPolymorphicOrUnknown();
        if (object_labels == null) {
            return true;
        }
        for (ObjectLabel object_label : object_labels) {
            if (object_label.isSingleton()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if this value contains the given object label.
     */
    public boolean containsObjectLabel(ObjectLabel objlabel) {
        if (object_labels == null)
            return false;
        return object_labels.contains(objlabel);
    }
}
