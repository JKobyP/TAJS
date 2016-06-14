package edu.oakland.stringabs;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;
import dk.brics.automaton.BasicOperations;

/**
 * Created by koby on 6/9/2016.
 */
public class AbstractString {
    private Automaton dfa;
    private static AbstractString anyString;
    private static AbstractString uIntString;

    public AbstractString(String s){
        dfa = AbstractOperations.newStr(s);
    }
    private AbstractString(Automaton a) {
        dfa = a;
    }

    public static AbstractString newEmptyAbstractString(){
        return new AbstractString("");
    }

    public static AbstractString anyString(){
        if (anyString == null) {
            anyString = new AbstractString(BasicAutomata.makeAnyString());
        }
        return anyString;
    }

    public static AbstractString uIntString(){
        if (uIntString == null) {
            String uintsize = String.valueOf((long)Integer.MAX_VALUE * 2);
            uIntString = new AbstractString(BasicAutomata.makeMinInteger("0")
                    .intersection(BasicAutomata.makeMaxInteger(uintsize)));
        }
        return uIntString;
    }

    /**
     * Corresponds to the partial ordering on our abstract domain
     * Our notion of less-than corresponds to language subset, ie.
     * A <= B iff L(A) subseteq L(B)
     * @return true if this is less than or equal to @other, false if it is greater, or if there is no ordering.
     */
    public boolean isLessThan(AbstractString other) {
        return dfa.equals(dfa.intersection(other.dfa));
    }

    public boolean hasIntersection(AbstractString other){
        return !BasicOperations.isEmpty(dfa.intersection(other.dfa));
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

    @Override
    public boolean equals(Object o) {
        if(o instanceof AbstractString) {
            return this.equals((AbstractString)o);
        } else {
            return false;
        }
    }

    public boolean equals(AbstractString s){
        return dfa.equals(s.dfa);
    }
    // Abstract Operations

    public AbstractString concat(AbstractString b) {
        dfa = AbstractOperations.concat(dfa, b.dfa);
        return this;
    }

    public AbstractString getComplement() {
        return new AbstractString(BasicOperations.complement(dfa));
    }

    public int length() {return dfa.getNumberOfStates();}

    public boolean startsWith(String s) { return true;}
    public boolean startsWith(AbstractString s) { return true;}
    public char charAt(int i) { return 0;}
    public AbstractString substring(int b, int e){return newEmptyAbstractString();}
    public AbstractString substring(int b){return newEmptyAbstractString();}
}
