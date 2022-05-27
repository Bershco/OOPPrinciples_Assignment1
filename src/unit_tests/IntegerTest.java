package unit_tests;

import numerics.Integer;
import numerics.Rational;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerTest {

    @Test
    public void addTest() {
        assertThrows(IllegalArgumentException.class, () -> {new Integer(42).add(null);});
        assertEquals(new Integer(1), new Integer(0).add(new Integer(1)));
        assertEquals(new Integer(1), new Integer(1).add(new Integer(0)));
        assertEquals(new Rational(3, 2), new Integer(1).add(new Rational(1, 2)));
    }

    @Test
    public void mulTest() {
        assertThrows(IllegalArgumentException.class, () -> {new Integer(42).mul(null);});
        assertEquals(new Integer(4), new Integer(1).mul(new Integer(4)));
        assertEquals(new Integer(4), new Integer(4).mul(new Integer(1)));
        assertEquals(new Integer(0), new Integer(0).mul(new Integer(1)));
        assertEquals(new Integer(0), new Integer(1).mul(new Integer(0)));
        assertEquals(new Rational(8, 5), new Integer(2).mul(new Rational(4, 5)));
    }

    @Test
    public void negTest() {
        assertEquals(new Integer(-1), new Integer(1).neg());
        assertEquals(new Integer(42), new Integer(-42).neg());
    }

    @Test
    public void powerTest() {
        assertEquals(new Integer(0), new Integer(0).power(1));
        assertEquals(new Integer(0), new Integer(0).power(42));
        assertEquals(new Integer(1), new Integer(0).power(0));
        assertEquals(new Integer(1), new Integer(42).power(0));
        assertEquals(new Integer(1), new Integer(-42).power(0));
        assertEquals(new Integer(2), new Integer(2).power(1));
        assertEquals(new Integer(4), new Integer(-2).power(2));
        assertEquals(new Integer(8), new Integer(2).power(3));
    }

    @Test
    public void signTest() {
        assertEquals(1, new Integer(1).sign());
        assertEquals(1, new Integer(42).sign());
        assertEquals(0, new Integer(0).sign());
        assertEquals(-1, new Integer(-1).sign());
        assertEquals(-1, new Integer(-42).sign());
    }

    @Test
    public void toStringTest() {
        assertEquals("0", new Integer(0).toString());
        assertEquals("1", new Integer(1).toString());
        assertEquals("-1", new Integer(-1).toString());
    }
}