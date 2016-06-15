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
    public void AbstractString_string_test() {
        AbstractString accept_ab = new AbstractString("ab");

        assertEquals(true, accept_ab.run("ab"));
        assertEquals(false, accept_ab.run("ba"));
        assertEquals(false, accept_ab.run("aab"));
        assertEquals(false, accept_ab.run("abb"));
        assertEquals(false, accept_ab.run("abab"));
        assertEquals(false, accept_ab.run("aaa"));
    }

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

        assertEquals(true, uInt.run("1234"));
        assertEquals(true, uInt.run("4294967294"));
        assertEquals(false, uInt.run("4294967295"));
        assertEquals(false, uInt.run("-14635677"));
        assertEquals(true, uInt.run("0000000"));
        assertEquals(false, uInt.run("4.5"));
        assertEquals(false, uInt.run("run!"));
    }

    @Test
    public void otherNumStr_test() {
        AbstractString oNum = new AbstractString("").otherNumString();

        assertEquals(false, oNum.run("1234"));
        assertEquals(true, oNum.run("-14635677"));
        assertEquals(false, oNum.run("0000000"));
        assertEquals(true, oNum.run("4.6"));
        assertEquals(true, oNum.run("-4.6"));
        assertEquals(false, oNum.run("run!"));
    }

    @Test
    public void getAnyNumberString_test() {
        AbstractString nStr = new AbstractString("").getAnyNumberString();

        assertEquals(true, nStr.run("1234"));
        assertEquals(true, nStr.run("12.34"));
        assertEquals(true, nStr.run("12.34e7"));
        assertEquals(true, nStr.run("12.34e-7"));
        assertEquals(true, nStr.run("12.34e+7"));
        assertEquals(true, nStr.run("-14635677"));
        assertEquals(false, nStr.run("-run"));
        assertEquals(false, nStr.run("run!"));
        assertEquals(true, nStr.run("-Infinity"));
        assertEquals(true, nStr.run("Infinity"));
        assertEquals(true, nStr.run("NaN"));
    }

    @Test
    public void isLessThan_test() {
        AbstractString a = new AbstractString("kappa");
        AbstractString b = new AbstractString("kappag");

        assertEquals(false, a.isLessThan(b));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("edu.oakland.stringabs.Methods");
    }
}