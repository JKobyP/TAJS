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

package dk.brics.tajs.analysis.nativeobjects;

import dk.brics.tajs.analysis.Conversion;
import dk.brics.tajs.analysis.FunctionCalls.CallInfo;
import dk.brics.tajs.analysis.InitialStateBuilder;
import dk.brics.tajs.analysis.NativeFunctions;
import dk.brics.tajs.analysis.Solver;
import dk.brics.tajs.lattice.ObjectLabel;
import dk.brics.tajs.lattice.ObjectLabel.Kind;
import dk.brics.tajs.lattice.State;
import dk.brics.tajs.lattice.UnknownValueResolver;
import dk.brics.tajs.lattice.Value;

/**
 * 15.6 native Boolean functions.
 */
public class JSBoolean {

    private JSBoolean() {
    }

    /**
     * Evaluates the given native function.
     */
    public static Value evaluate(ECMAScriptObjects nativeobject, CallInfo call, Solver.SolverInterface c) {
        if (nativeobject != ECMAScriptObjects.BOOLEAN)
            if (NativeFunctions.throwTypeErrorIfConstructor(call, c))
                return Value.makeNone();

        State state = c.getState();
        switch (nativeobject) {

            case BOOLEAN: {
                NativeFunctions.expectParameters(nativeobject, call, c, 0, 1);
                Value b = Conversion.toBoolean(NativeFunctions.readParameter(call, state, 0));
                if (call.isConstructorCall()) { // 15.6.2
                    ObjectLabel objlabel = new ObjectLabel(call.getSourceNode(), Kind.BOOLEAN);
                    state.newObject(objlabel);
                    state.writeInternalValue(objlabel, b);
                    state.writeInternalPrototype(objlabel, Value.makeObject(InitialStateBuilder.BOOLEAN_PROTOTYPE));
                    return Value.makeObject(objlabel);
                } else // 15.6.1
                    return b;
            }

            case BOOLEAN_TOSTRING: { // 15.6.4.2
                NativeFunctions.expectParameters(nativeobject, call, c, 0, 0);
                if (NativeFunctions.throwTypeErrorIfWrongKindOfThis(nativeobject, call, state, c, Kind.BOOLEAN))
                    return Value.makeNone();
                Value val = state.readInternalValue(state.readThisObjects());
                val = UnknownValueResolver.getRealValue(val, c.getState());

                Value result = Value.makeNone();
                if (val.isMaybeTrue()) {
                    result = result.joinStr("true");
                }
                if (val.isMaybeFalse()) {
                    result = result.joinStr("false");
                }
                // TODO: treat {"true","false"} specially in Value?
                return result;
            }

            case BOOLEAN_VALUEOF: { // 15.6.4.3
                NativeFunctions.expectParameters(nativeobject, call, c, 0, 0);
                if (NativeFunctions.throwTypeErrorIfWrongKindOfThis(nativeobject, call, state, c, Kind.BOOLEAN))
                    return Value.makeNone();
                return state.readInternalValue(state.readThisObjects());
            }

            default:
                return null;
        }
    }
}
