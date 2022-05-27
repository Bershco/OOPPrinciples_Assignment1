package polynomials;

import numerics.*;
import numerics.Integer;

public class Monomial {

    private int exponent;
    private Scalar coefficient;

    public Monomial(int exp, Scalar coef) {
        exponent = exp;
        coefficient = coef;
    }

    /**
     * Used to get the sum of two monomials
     * @param m The monomial to be added
     * @return A new monomial representing the sum of this monomial and m
     */
    public Monomial add(Monomial m) {
        if (m == null) throw new IllegalArgumentException("Illegal monomial for addition");
        if (exponent == m.exponent) return new Monomial(exponent, coefficient.add(m.coefficient));
        return null;
    }

    /**
     *
     * @param m The monomial to be multiplied
     * @return The product of this monomial and m
     */
    public Monomial mul(Monomial m) {
        if (m == null) throw new IllegalArgumentException("Illegal monomial for multiplication");
        return new Monomial(exponent + m.exponent, coefficient.mul(m.coefficient));
    }

    /**
     *
     * @param s The scalar to place in the monomial
     * @return The value of the monomial with s in place of the variable
     */
    public Scalar evaluate(Scalar s) {
        if (s == null) throw new IllegalArgumentException("Illegal scalar for evaluation");
        return coefficient.mul(s.power(exponent));
    }

    /**
     *
     * @return A monomial representing the derivative of this one
     */
    public Monomial derivative() {
        if (exponent == 0) return new Monomial(0, new numerics.Integer(0));
        return new Monomial(exponent - 1, coefficient.mul(new numerics.Integer(exponent)));
    }

    /**
     *
     * @return  1 for a positive coefficient<br>
     * -1 for a negative one<br>
     * 0 for zero
     */
    public int sign() {
        return coefficient.sign();
    }

    public String toString() {
        if (exponent == 0 || coefficient.toString().equals("0")) return coefficient.toString();
        if (exponent == 1) {
            if (coefficient.toString().equals("1")) return "x";
            if (coefficient.toString().equals("-1")) return "-x";
            return coefficient.toString() + "x";
        }
        if (coefficient.toString().equals("1")) return "x^" + exponent;
        if (coefficient.toString().equals("-1")) return "-x^" + exponent;
        return coefficient.toString() + "x^" + exponent;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) throw new IllegalArgumentException("If you've reached this point, you're just trying to get me (no null pls)");
        if ((obj instanceof Monomial)) {
            if (coefficient.equals(((Monomial) obj).coefficient)) {
                return exponent == ((Monomial) obj).exponent || coefficient.equals(new Integer(0));
            }
        }
        return false;
    }

    /**
     * reduces the coefficient of the monomial
     * @return a reduced monomial
     */
    protected Monomial reduce() {
        Scalar newCoef = coefficient.getRational().reduce();
        return new Monomial(exponent,newCoef);
    }
    public int getExponent() { return exponent; }
}