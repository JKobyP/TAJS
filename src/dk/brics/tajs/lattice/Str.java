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

import edu.oakland.stringabs.AbstractString;

/**
 * 'String' facet for abstract values.
 */
public interface Str {

    /**
     * Returns true if this value is maybe any string.
     */
    boolean isMaybeAnyStr();

    /**
     * Returns true if value is maybe a singleton string.
     */
    boolean isMaybeSingleStr();

    /**
     * Returns true if this value is maybe any UInt string.
     */
    boolean isMaybeStrUInt();

    /**
     * Returns true if this value is maybe some UInt string.
     */
    boolean isMaybeStrSomeUInt();

    /**
     * Returns true if this value is maybe a non-UInt string.
     */
    boolean isMaybeStrSomeNonUInt();

    /**
     * Returns true if this value is maybe any (unbounded) non-UInt number string, including Infinity, -Infinity, and NaN.
     */
    boolean isMaybeStrOtherNum();

    /**
     * Returns true if this value is maybe any identifier string.
     */
    boolean isMaybeStrIdentifier();

    /**
     * Returns true if this value is maybe any string consisting of identifier parts.
     */
    boolean isMaybeStrIdentifierParts();

    /**
     * Returns true if this value is maybe a fixed nonempty string followed by identifier parts.
     */
    boolean isMaybeStrPrefixedIdentifierParts();

    /**
     * Returns true if this value is maybe any non-number, non-identifier-parts string.
     */
    boolean isMaybeStrOther();

    /**
     * Returns true if this value maybe originates from a JSON source.
     */
    boolean isMaybeStrJSON();

    /**
     * Returns true if this value is definitely originating from a JSON source.
     */
    boolean isStrJSON();

    /**
     * Returns true if this value is definitely an identifier or identifier-parts string.
     */
    boolean isStrIdentifierOrIdentifierParts();

    /**
     * Returns true if this value is definitely an identifier string.
     */
    boolean isStrIdentifier();

    /**
     * Returns true if this value is maybe any UInt string but not a non-UInt string.
     */
    boolean isMaybeStrOnlyUInt();

    /**
     * Returns true if this value may be a non-string.
     */
    boolean isMaybeOtherThanStr();

    /**
     * Returns true if this value is maybe a non-singleton string.
     */
    boolean isMaybeFuzzyStr();

    /**
     * Returns the singleton string value, or null if definitely not a singleton string.
     */
    String getStr();

    /**
     * Returns the prefix value, or null if definitely not a fixed nonempty string followed by identifier parts.
     */
    String getPrefix();

    /**
     * Returns true if this value is definitely not a string.
     */
    boolean isNotStr();

    /**
     * Constructs a value as the join of this value and any string.
     */
    Value joinAnyStr();

    /**
     * Constructs a value as the join of this value and any UInt string.
     */
    Value joinAnyStrUInt();

    /**
     * Constructs a value as the join of this value and any non-UInt number string (excluding NaN and +/-Infinity).
     */
    Value joinAnyStrOtherNum();

    /**
     * Constructs a value as the join of this value and any identifier string.
     */
    Value joinAnyStrIdentifier();

    /**
     * Constructs a value as the join of this value and any identifier-parts string.
     */
    Value joinAnyStrIdentifierParts();

    /**
     * Constructs a value as the join of this value and any non-number, non-identifier-parts string (including NaN and +/-Infinity).
     */
    Value joinAnyStrOther();

    /**
     * Constructs a value as the join of this value and the given concrete string.
     */
    Value joinStr(String v);

    /**
     * Constructs a value from this value where only the string facet is considered.
     */
    Value restrictToStr();

    /**
     * Constructs a value from this value but definitely not a string.
     */
    Value restrictToNotStr();

    /**
     * Checks whether the given string is matched by this value.
     */
    boolean isMaybeStr(String s);

    /**
     * Gets the AbstractString object this value holds
     */
    AbstractString getAbstractStr(); // Picker & Maldonado
    Value strConcatenate(Str s, Str s2); // Picker & Maldonado
}
