package unit_tests;

import numerics.Integer;
import numerics.Rational;
import org.junit.Test;

import static org.junit.Assert.*;

public class RationalTest {

    @Test
    public void add() {
        assertThrows(IllegalArgumentException.class, () -> {new Rational(0, 1).add(null);});
        assertEquals(new Rational(3, 7), new Rational(1, 7).add(new Rational(2, 7)));
        assertEquals(new Rational(1, 2), new Rational(1, 2).add(new Rational(0, 1)));
        assertEquals(new Rational(1, 2), new Rational(0, 2).add(new Rational(1, 2)));
        assertEquals(new Rational(4, 5), new Rational(4, 5).add(new Integer(0)));
        assertEquals(new Rational(4, 5), new Rational(-1, 5).add(new Integer(1)));
        assertEquals(new Rational(7, 5), new Rational(2, 5).add(new Integer(1)));
        assertEquals(new Rational(7, 3), new Rational(1, 3).add(new Integer(2)));
        assertEquals(new Rational(7, 3), new Rational(1, 3).add(new Rational(2, 1)));
    }

    @Test
    public void mul() {
        assertThrows(IllegalArgumentException.class, () -> {new Rational(1, 2).mul(null);});
        //assertEquals(new Rational());
        assertEquals("3/5 times 7/8 should've been 21/40",new Rational(21,40),new Rational(3,5).mul(new Rational(7,8)));
        assertEquals("3/5 times 5 should've been 3",new Rational(3,1),new Rational(3,5).mul(new Integer(5)));
        assertEquals("Everything times 0 should be 0",new Integer(0),new Rational(6245,49123).mul(new Rational(0,2)));

    }

    @Test
    public void neg() {
        assertEquals("Negative 8 should be -8",new Rational(-8,1),new Rational(8,1).neg());
        assertEquals("Negative -3/5 should be 3/5",new Rational(3,5),new Rational(3,-5).neg());
        assertEquals("Negative 1623/4345 should be -1623/4345",new Rational(-1623,4345),new Rational(-1623,-4345).neg());
    }

    @Test
    public void power() {
        assertEquals("3 by the power of 3 is 27",new Rational(27,1),new Rational(3,1).power(3));
        assertEquals("3/7 by the power of 2 is 9/49",new Rational(9,49),new Rational(3,7).power(2));
        assertEquals("1/2 by the power of 5 is 1/32",new Rational(1,32),new Rational(1,2).power(5));

    }

    @Test
    public void sign() {
        assertEquals("0 should output 0",0,new Rational(0,27).sign());
        assertEquals("135 is positive",1,new Rational(135,1).sign());
        assertEquals("-7/2 is negative",-1,new Rational(-7,2).sign());
    }

    @Test
    public void testToString() {
        assertEquals("9/81 should print '1/9'","1/9",new Rational(9,81).toString());
        assertEquals("27/9 should print '3''","3",new Rational(27,9).toString());
        assertEquals("8/1 should print '8'","8",new Rational(8,1).toString());
        assertEquals("-13/-81 should print 13/81","13/81",new Rational(-13,-81).toString());
        assertEquals("25/-5 should print -5","-5",new Rational(25,-5).toString());


    }

    @Test
    public void reduce() {
        assertEquals("9/81 reduced should be 1/9",new Rational(1,9),new Rational(9,81).reduce());
        assertEquals("35 / -63 reduced should be -5/9",new Rational(-5,9),new Rational(35,-63).reduce());
        assertEquals("-2/1 reduced should be -2",new Rational(-2,1),new Rational(-2,1).reduce());
        assertEquals("-13/-169 reduced should be 1/13",new Rational(1,13),new Rational(-13,-169).reduce());

    }
}