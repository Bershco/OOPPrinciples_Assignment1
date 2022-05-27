package unit_tests;

import numerics.Rational;
import numerics.Integer;
import org.junit.Before;
import org.junit.Test;
import polynomials.Monomial;


import static org.junit.Assert.*;

public class MonomialTest {
    public MonomialTest obj;
    public Monomial x;
    public Monomial xSqr;
    @Before
    public void beforeAny() {
        x = new Monomial(1,new Rational(1,1));
        xSqr = new Monomial(2,new Integer(1));
    }
    @Test
    public void addTest() {
        assertThrows(IllegalArgumentException.class, () -> {new Monomial(1,new Integer(1)).add(null);});
        assertNull("Addition using different exponents should've returned null", x.add(xSqr));
        assertEquals("x+x should be 2x",new Monomial(1,new Integer(2)),x.add(x));
        assertNotEquals("x+x shouldn't be x", new Monomial(1,new Integer(1)),x.add(x));
    }

    @Test
    public void mulTest() {
        assertThrows(IllegalArgumentException.class, () -> {new Monomial(1,new Integer(1)).mul(null);});
        assertEquals("x * x^2 should be x^3",new Monomial(3,new Integer(1)),x.mul(xSqr));
        assertEquals("x^2 times itself should be x^4",new Monomial(4,new Integer(1)),xSqr.mul(xSqr));
    }

    @Test
    public void evaluateTest() {
        assertEquals("16x^5 should be 243/2 when you replace x with 3/2",new Rational(243,2),(new Monomial(5,new Integer(16)).evaluate(new Rational(3,2))));
        assertEquals("2x^2 should be 72 when you replace x with 6",new Integer(72),(new Monomial(2,new Integer(2))).evaluate(new Integer(6)));
    }

    @Test
    public void derivativeTest() {
        assertEquals("The derivative of x^2 should be 2x",new Monomial(1,new Integer(2)),xSqr.derivative());
        assertEquals("The derivative of 1/4*x^5 should be 5/4*x^4",new Monomial(4,new Rational(5,4)),(new Monomial(5,new Rational(1,4)).derivative()));
    }

    @Test
    public void signTest() {
        assertEquals("-x should be negative (-1)",-1,new Monomial(1,new Integer(-1)).sign());
        assertEquals("7/4*x^5 should be positive(1)",1,new Monomial(5,new Rational(7,4)).sign());
        assertEquals("0 should be 0",new Monomial(0,new Integer(0)),new Monomial(16,new Rational(0,16)));
    }

    @Test
    public void toStringTest() {
        assertEquals("6x^0 should be 6","6",new Monomial(0,new Integer(6)).toString());
        assertEquals("0x^17 should be 0","0",new Monomial(17,new Integer(0)).toString());
        assertEquals("x should be presented as x and not 1x","x",new Monomial(1,new Integer(1)).toString());
        assertEquals("-x should be presented as -x and not -1x","-x",new Monomial(1,new Integer(-1)).toString());
        assertEquals("x^5 should be presented as x^5 and not 1x^5","x^5",new Monomial(5,new Integer(1)).toString());
        assertEquals("16x^5 should be presented as such","16x^5",new Monomial(5,new Integer(16)).toString());
        assertEquals("-135x^17 should be presented as such","-135x^17",new Monomial(17,new Integer(-135)).toString());
    }
}