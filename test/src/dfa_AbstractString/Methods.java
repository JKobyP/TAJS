package edu.oakland.stringabs;

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
        AbstractString uInt = AbstractString.uIntString();

        assertEquals(true, uInt.run("1234"));
        assertEquals(true, uInt.run("4294967294"));
        assertEquals(true, uInt.run("28446744073709551615"));
        assertEquals(false, uInt.run("-14635677"));
        assertEquals(true, uInt.run("0000000"));
        assertEquals(false, uInt.run("4.5"));
        assertEquals(false, uInt.run("run!"));
    }

    @Test
    public void getIdentifierString_test() {
        AbstractString iStr = AbstractString.getIdentifierString();
        Automaton iStr_dfa = iStr.getDfa();

       // System.out.println(iStr_dfa.getStrings(5));
        assertEquals(true, iStr_dfa.run("Hello"));
        assertEquals(true, iStr_dfa.run("H4i"));
        assertEquals(false, iStr_dfa.run("45"));
    }

    @Test
    public void otherNumStr_test() {
        AbstractString oNum = AbstractString.otherNumString();

        assertEquals(false, oNum.run("1234"));
        assertEquals(true, oNum.run("-14635677"));
        assertEquals(false, oNum.run("0000000"));
        assertEquals(true, oNum.run("4.6"));
        assertEquals(true, oNum.run("-4.6"));
        assertEquals(false, oNum.run("run!"));
    }

    @Test
    public void getAnyNumberString_test() {
        AbstractString nStr = AbstractString.getAnyNumberString();

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
    public void isSubset_test() {
        AbstractString a = new AbstractString("kappa");
        AbstractString b = new AbstractString("phi");
        AbstractString c = new AbstractString("kappo");
        AbstractString bc =  b.leastUpperBound(c);
        AbstractString abc = a.leastUpperBound(bc);
        AbstractString empty = AbstractString.newEmptyAbstractString();

        assertEquals(false, a.isSubset(b));      // {"kappa"} < {"phi"}
        assertEquals(false, b.isSubset(a));      // {"phi"} < {"kappa"}
        assertEquals(false, a.isSubset(c));      // {"kappa"} < {"kappo"}
        assertEquals(false, a.isSubset(bc));     // {"kappa"} < {"phi", "kappo"}
        assertEquals(true, b.isSubset(bc));      // {"phi"} < {"phi", "kappo"}
        assertEquals(true, c.isSubset(bc));      // {"kappo"} < {"phi", "kappo"}
        assertEquals(true, bc.isSubset(abc));    // {"phi", "kappo"} < {"kappa", "phi", "kappo"}
        assertEquals(true, empty.isSubset(a));   // {} < {"kappa"}
    }

    @Test
    public void isMaybeAnyStr_test() {
        AbstractString a = new AbstractString("yes");
        AbstractString any = AbstractString.anyString();

        assertEquals(true, any.equals(AbstractString.anyString()));
    }

    @Test
    public void stringValue_test() {
        AbstractString empty = AbstractString.newEmptyAbstractString();

        assertEquals(null, empty.stringValue());
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("edu.oakland.stringabs.Methods");
    }
}