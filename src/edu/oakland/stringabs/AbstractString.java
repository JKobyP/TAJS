package edu.oakland.stringabs;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by koby on 6/9/2016.
 */
public class AbstractString {
    private Automaton dfa;
    private static AbstractString anyString;
    private static AbstractString uIntString;
    private static AbstractString otherNumString;
    private static AbstractString anyNumberString;
    private static AbstractString empty;

    public static AbstractString newEmptyAbstractString(){
        if (empty == null) {
            empty = new AbstractString(BasicAutomata.makeEmpty());
        }
        return empty;
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

    public static AbstractString otherNumString() {
        if(otherNumString == null) {
            otherNumString = getAnyNumberString().intersect(uIntString().getComplement());
        }
        return otherNumString;
    }

    public static AbstractString getAnyNumberString() {
        if(anyNumberString == null) {
            anyNumberString = new AbstractString(new RegExp("\\-?(([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+][0-9]+)?|Infinity)|NaN").toAutomaton());
        }
        return anyNumberString;
    }

    public AbstractString(String s){
        dfa = BasicAutomata.makeString(s);
    }

    private AbstractString(Automaton a) {
        dfa = a;
    }

    public AbstractString intersect(AbstractString a){
        return new AbstractString(this.dfa.intersection(a.dfa));
    }

    public boolean run(String s) {
        return dfa.run(s);
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
        dfa = BasicOperations.concatenate(dfa, b.dfa);
        return this;
    }

    public AbstractString contains(char c) {
        return new AbstractString(
                BasicOperations.intersection(dfa,
                BasicAutomata.makeAnyString()
                             .concatenate(BasicAutomata.makeChar(c)
                             .concatenate(BasicAutomata.makeAnyString()))));
    }

    public AbstractString getComplement() {
        return new AbstractString(BasicOperations.complement(dfa));
    }

    public int length() {return dfa.getNumberOfStates();}

    public boolean startsWith(AbstractString s) {
        throw new NotImplementedException();
    }
    public char charAt(int i) { return 0;}
    public AbstractString substring(int b, int e){
        throw new NotImplementedException();
    }
    public AbstractString substring(int b){
        throw new NotImplementedException();
    }
}
