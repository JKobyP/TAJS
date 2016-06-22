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

    /**
     * NOTE: Returns a new Automaton object. It does not do change in place.
     */
    public static Automaton singleton_toUpper(Automaton a) {
        String str = a.getCommonPrefix();
        char[] char_str = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (char_str[i] >= 'a' && char_str[i] <= 'z') {
                char_str[i] = (char) ('A' + (char_str[i] - 'a'));
            }
        }
        str = String.valueOf(char_str);
        return BasicAutomata.makeString(str);
    }

    public static Automaton toUpper(Automaton a) {
        Set<String> str_set = a.getFiniteStrings();
        char[] char_str = {};

        for (Iterator<String> itr = str_set.iterator(); itr.hasNext(); ) {
            String str = itr.next();
            char_str = str.toCharArray();
            for (int i = 0; i < str.length(); i++) {
                if (char_str[i] >= 'a' && char_str[i] <= 'z') {
                    char_str[i] = (char) ('A' + (str.charAt(i) - 'a'));
                }
            }
        }
        String str = String.valueOf(char_str);
        return BasicAutomata.makeString(str);
    }

    /**
     * Changes the labels
     * @param a - An Automata object
     * @return Automata Object with changed trans labels changed to uppercase
     */
    public static Automaton label_toUpper(Automaton a) {
        Map<Character, Set<Character>> letters = new HashMap();
        Set<String> str_set = a.getFiniteStrings();

        for(Iterator<String> itr = str_set.iterator(); itr.hasNext() ;) {
            String str = itr.next();
            for(int i = 0; i < str.length(); i++) {
                if(!letters.containsKey(str.charAt(i))) {
                    if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                        char convert = (char) ('A' + (str.charAt(i) - 'a'));
                        Set<Character> upper = new HashSet<>();
                        upper.add(convert);
                        letters.put(str.charAt(i), upper);
                    }
                }
            }
        }
        return SpecialOperations.subst(a, letters);
    }

    /**
     * NOTE: Returns a new Automaton object. It does not do change in place.
     */
    public static Automaton singleton_toLower(Automaton a) {
        String str = a.getCommonPrefix();
        char[] char_str = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (char_str[i] >= 'A' && char_str[i] <= 'Z') {
                char_str[i] = (char) ('a' + (char_str[i] - 'A'));
            }
        }
        str = String.valueOf(char_str);
        return BasicAutomata.makeString(str);
    }

    public static Automaton toLower(Automaton a) {
        Set<String> str_set = a.getFiniteStrings();
        char[] char_str = {};

        for(Iterator<String> itr = str_set.iterator(); itr.hasNext();) {
            String str = itr.next();
            char_str = str.toCharArray();
            for (int i = 0; i < str.length(); i++) {
                if (char_str[i] >= 'A' && char_str[i] <= 'Z') {
                    char_str[i] = (char) ('a' + (str.charAt(i) - 'A'));
                }
            }
        }
        String str = String.valueOf(char_str);
        return BasicAutomata.makeString(str);
    }
}

