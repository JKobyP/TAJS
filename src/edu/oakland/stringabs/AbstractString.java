package edu.oakland.stringabs;

import dk.brics.automaton.Automaton;

/**
 * Created by reu-5 on 6/9/2016.
 */
public class AbstractString {
    private Automaton dfa;

    public AbstractString(String s){
        dfa = AbstractOperations.newStr(s);
    }

    public boolean isEmpty(){
        return dfa.isEmpty();
    }

    public String stringValue(){
        return dfa.getSingleton();
    }
    public boolean equals(String s) {
        return s.equals(stringValue());
    }
}
