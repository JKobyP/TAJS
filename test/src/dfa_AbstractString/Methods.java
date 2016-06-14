package edu.oakland.stringabs;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;
import dk.brics.automaton.BasicOperations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * created by christian 6/14/2016
 */

public class Methods {

    @Test
    public void isEmpty_test() {
        AbstractString empty = AbstractString.newEmptyAbstractString();
        AbstractString full = AbstractString.anyString();

        assertEquals(true, empty.isEmpty());
        assertEquals(false, full.isEmpty());
    }

    @Test
    public void uIntString_test() {
        AbstractString uInt = new AbstractString("").uIntString();
        Automaton ui_dfa = uInt.getDfa();

        assertEquals(true, BasicOperations.run(ui_dfa,"1234"));
        assertEquals(true, BasicOperations.run(ui_dfa,"4294967294"));
        assertEquals(false, BasicOperations.run(ui_dfa,"4294967295"));
        assertEquals(false,BasicOperations.run(ui_dfa,"-14635677"));
        assertEquals(true, BasicOperations.run(ui_dfa,"0000000"));
        assertEquals(false, BasicOperations.run(ui_dfa,"run!"));
    }

    @Test
    public void otherNumStr_test() {
        AbstractString oNum = new AbstractString("").otherNumString();
        Automaton oNum_dfa = oNum.getDfa();

        assertEquals(false, BasicOperations.run(oNum_dfa,"1234"));
        assertEquals(true,BasicOperations.run(oNum_dfa,"-14635677"));
        assertEquals(false, BasicOperations.run(oNum_dfa,"0000000"));
        assertEquals(false, BasicOperations.run(oNum_dfa,"run!"));
    }

    @Test
    public void getAnyNumberString_test() {
        AbstractString nStr = new AbstractString("").getAnyNumberString();
        Automaton nStr_dfa = nStr.getDfa();

        assertEquals(true, BasicOperations.run(nStr_dfa,"1234"));
        assertEquals(true,BasicOperations.run(nStr_dfa,"-14635677"));
        assertEquals(false, BasicOperations.run(nStr_dfa,"-run"));
        assertEquals(false, BasicOperations.run(nStr_dfa,"run!"));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("edu.oakland.stringabs.Methods");
    }
}