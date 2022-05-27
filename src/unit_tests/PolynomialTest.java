package unit_tests;
import numerics.Integer;
import numerics.Rational;

import org.junit.Test;
import polynomials.Monomial;
import polynomials.Polynomial;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PolynomialTest {

    //Polynomial poly1 =

    @Test
    public void buildTest() {
        ArrayList<Monomial> xPlus3Sqr = new ArrayList<>();
        xPlus3Sqr.add(new Monomial(0,new Integer(9)));
        xPlus3Sqr.add(new Monomial(1,new Integer(6)));
        xPlus3Sqr.add(new Monomial(2,new Integer(1)));
        assertEquals("Wrong polynomial",new Polynomial(xPlus3Sqr),Polynomial.build("9 6 1"));
        assertEquals("Wrong polynomial",new Polynomial(xPlus3Sqr),Polynomial.build("9   6 1"));
        assertEquals("Wrong polynomial",new Polynomial(xPlus3Sqr),Polynomial.build("9 6   1   "));

    }

    @Test
    public void addTest() {
        assertThrows(IllegalArgumentException.class, () -> {Polynomial.build("0 1").add(null);});
        assertEquals(Polynomial.build("1 1 1 1 1 1"),Polynomial.build("1 0 1 0 1 0").add(Polynomial.build("0 1 0 1 0 1")));
        assertEquals(Polynomial.build("1 0 0 0 0 0 0 0 1"),Polynomial.build("1").add(Polynomial.build("0 0 0 0 0 0 0 0 1")));
        assertEquals(Polynomial.build("1 0 0 0 0 0 0 0 1"),Polynomial.build("0 0 0 0 0 0 0 0 1").add(Polynomial.build("1")));
        assertEquals(Polynomial.build("13 5 7 3 71 17"),Polynomial.build("13 3 2 8 1231 1").add(Polynomial.build("0 2 5 -5 -1160 16")));
        assertEquals(Polynomial.build("0 0 17"),Polynomial.build("5 5").add(Polynomial.build("-5 -5 17")));
        assertEquals(Polynomial.build("6/41 1"),Polynomial.build("1 1").add(Polynomial.build("-35/41")));
        assertEquals(Polynomial.build("5 6 -7"),Polynomial.build("-18 4 0 0 0 0 7").add(Polynomial.build("23 2 -7 0 0 0 -7")));

    }

    @Test
    public void mulTest() {
        assertThrows(IllegalArgumentException.class, () -> {Polynomial.build("0 1").mul(null);});
        assertEquals(Polynomial.build("0 1 0 2 0 3 0 2 0 1"), Polynomial.build("1 0 1 0 1").mul(Polynomial.build("0 1 0 1 0 1")));
        assertEquals(Polynomial.build("0 0 4 10/9 -40/9 -31/9 -3"), Polynomial.build("0 2 1 1").mul(Polynomial.build("0 2 -4/9 -3")));
        assertEquals(Polynomial.build("1 2 1 1 2 1"), Polynomial.build("1 2 1").mul(Polynomial.build("1 0 0 1")));
        assertEquals(Polynomial.build("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 17 8 4 9"), Polynomial.build("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1").mul(Polynomial.build("17 8 4 9")));
    }

    @Test
    public void evaluateTest() {
        assertEquals(new Rational(1,27),Polynomial.build("0 0 0 0 3").evaluate(new Rational(1,3)));
        assertEquals(new Rational(37,4),Polynomial.build("6 6 1").evaluate(new Rational(1,2)));
        assertEquals(new Rational(-416945,88482),Polynomial.build("1/2 -1/4 6/7 -8/3 6/123").evaluate(new Rational(7,6)));
        assertEquals(new Rational(250563,1),Polynomial.build("0 0 0 0 3").evaluate(new Integer(17)));

    }

    @Test
    public void derivativeTest() {
        assertEquals(Polynomial.build("234"),Polynomial.build("17 234").derivative());
        assertEquals(Polynomial.build("5 13 42 74"),Polynomial.build("123123 5 26/4 84/6 37/2").derivative());
        assertEquals(Polynomial.build("0 0 0 0 5"),Polynomial.build("42 0 0 0 0 1").derivative());
        assertEquals(Polynomial.build("0"),Polynomial.build("42").derivative());
        Polynomial poly = Polynomial.build("0 0 0 0 0 0 0 0 1");
        for (int i = 0; i < 8; i++) {
            poly = poly.derivative();
        }
        assertEquals(Polynomial.build("40320"),poly);
    }

    @Test
    public void toStringTest() {
        assertEquals("5x^2",Polynomial.build("0 0 5").toString());
        assertEquals("6+5x^2",Polynomial.build("6 0 5").toString());
        assertEquals("3+5x+17/2x^2",Polynomial.build("3 5 17/2").toString());
        assertEquals("-x",Polynomial.build("0 -1").toString());
        assertEquals("-9-6x^4+4x^8",Polynomial.build("-9 0 0 0 -6 0 0 0 4").toString());
        assertEquals("-3/2x+5/8x^3",Polynomial.build("0 -3/2 0 5/8").toString());
    }
}