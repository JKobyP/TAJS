package edu.oakland.stringabs;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;
import dk.brics.automaton.BasicOperations;
/**
 * Created by koby on 6/7/16.
 */
public interface AbstractOperations {
    AbstractString concat(AbstractString s);
    AbstractString contains(char c);

    boolean isLessThan(AbstractString s);
    AbstractString intersect(AbstractString s);
    AbstractString leastUpperBound(AbstractString s);
    AbstractString widen(AbstractString s);
}
