package edu.oakland.stringabs;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.SpecialOperations;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by koby on 6/7/16.
 * Conributions on functions by christian
 */
public interface AbstractOperations {
    AbstractString concat(AbstractString s);
    AbstractString contains(char c);

    boolean isSubset(AbstractString s);
    AbstractString intersect(AbstractString s);
    AbstractString leastUpperBound(AbstractString s);
    AbstractString widen(AbstractString s);
}

