package edu.oakland.stringabs;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;
import dk.brics.automaton.BasicOperations;
import dk.brics.tajs.lattice.Value;

/**
 * Created by koby on 6/7/16.
 */
public class AbstractOperations {
    public static Automaton newStr(String str) {
        return BasicAutomata.makeString(str);
    }

    public static Automaton concat(Automaton a1, Automaton a2) {
        return BasicOperations.concatenate(a1, a2);
    }

    //FIXME: This should only return true when it can be verified that c shows up in all paths to accepting states
    public static boolean contains(Automaton a, char c) {
        return !BasicOperations.isEmpty(
                    BasicOperations.intersection(a,
                        BasicAutomata.makeAnyString()
                        .concatenate(BasicAutomata.makeChar(c)
                        .concatenate(BasicAutomata.makeAnyString()))));
    }
}