package edu.oakland.stringabs;

import dk.brics.automaton.Automaton;

/**
 * Created by koby on 6/9/2016.
 */
public class AbstractString {
    private Automaton dfa;

    public AbstractString(String s){
        dfa = AbstractOperations.newStr(s);
    }

    public static AbstractString newEmptyAbstractString(){
        return new AbstractString("");
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

    // Abstract Operations

    public AbstractString concat(AbstractString b) {
        dfa = AbstractOperations.concat(dfa, b.dfa);
        return this;
    }

    public int length() {return dfa.getNumberOfStates();}

    public boolean startsWith(String s) { return true;}
    public boolean startsWith(AbstractString s) { return true;}
    public char charAt(int i) { return 0;}
    public AbstractString substring(int b, int e){return newEmptyAbstractString();}
    public AbstractString substring(int b){return newEmptyAbstractString();}
}
