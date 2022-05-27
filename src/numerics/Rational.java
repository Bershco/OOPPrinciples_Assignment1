package numerics;

public class Rational implements Scalar {
    private int numerator;
    private int denominator;

    public Rational(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * adds two scalars together, whether the added scalar would be integer or rational
     * @param s the scalar to be added
     * @return the added scalars
     */
    @Override
    public Scalar add(Scalar s) {
        if (s==null) throw new IllegalArgumentException("Can't add null, sir.");
        Rational sFix = s.getRational();
        int num = numerator * sFix.getDenominator() + denominator * sFix.getNumerator();
        int denom = denominator * sFix.getDenominator();
        return new Rational(num, denom);

    }

    /**
     * @param s the scalar to multiply by
     * @return the product of the two scalars
     */
    @Override
    public Scalar mul(Scalar s) {
        if (s==null) throw new IllegalArgumentException("Can't multiply by null, sir.");
        Rational sFix = s.getRational();
        if (s.toString().contains("/")) {
            int num = numerator * sFix.getNumerator();
            int denom = denominator * sFix.getDenominator();
            return new Rational(num,denom);
        }
        else {
            return new Rational(numerator * sFix.getNumerator(), denominator);
        }
    }

    /**
     * negation of the scalar
     * @return the negated scalar
     */
    @Override
    public Scalar neg() {
        if (sign()==0) return this;
        //else if (toString().contains("/")) {
        else {
            if (sign() == 1) {
                return new Rational(Math.abs(numerator)*-1,Math.abs(denominator));
            }
            else return new Rational(Math.abs(numerator),Math.abs(denominator));
        }
        //return null;
    }

    /**
     * @param exponent by which to raise the power of the scalar
     * @return 'this' by the power of exponent
     */
    @Override
    public Scalar power(int exponent) {
        return new Rational((int)Math.pow(numerator,exponent),(int)Math.pow(denominator,exponent));
    }

    /**
     * @return the sign of the scalar, -1 being any negative number, 0 being 0, and 1 being any positive number
     */
    @Override
    public int sign() {
        if ((numerator > 0 && denominator > 0) || (numerator < 0 && denominator < 0)) return 1;
        else if ((numerator > 0 && denominator < 0) || (numerator < 0 && denominator > 0)) return -1;
        return 0;
    }

    @Override
    public String toString() {
        Rational copy = reduce();
        if (copy.getDenominator() == 1) return ""+copy.getNumerator();
        else return ""+copy.getNumerator()+"/"+copy.getDenominator();
    }

    /**
     * reduces the rational number to its most basic form
     * @return the reduced rational number
     */
    public Rational reduce() {
        if (sign() == 0) return this;
        Rational copy = new Rational(numerator,denominator);
        if (sign() == 1 && numerator < 0) {
            copy.setNumerator(Math.abs(numerator));
            copy.setDenominator(Math.abs(denominator));
            copy.reducePositive();
        }
        else if (sign() == 1 && numerator > 0) {
            copy.reducePositive();
        }
        else if (sign() == -1 && numerator > 0) {
            copy.setDenominator(Math.abs(copy.getDenominator()));
            copy.reducePositive();
            copy.setNumerator(copy.getNumerator()*-1);
        }
        else {
            copy.setNumerator(Math.abs(copy.getNumerator()));
            copy.reducePositive();
            copy.setNumerator(copy.getNumerator()*-1);
        }
        return copy;
    }

    /**
     * helper method for reduce, made to remove any code duplication.
     */
    private void reducePositive() {
        int commonDivisor = 2;
        while (commonDivisor <= numerator && commonDivisor <= denominator) {
            while (numerator % commonDivisor == 0 && denominator % commonDivisor == 0) {
                numerator /= commonDivisor;
                denominator /= commonDivisor;
            }
            commonDivisor ++;
        }
    }

    /**
     * added to remove ambiguity of types
     * @return this as a Rational type
     */
    @Override
    public Rational getRational() {
        return this;
    }

    /**
     * @param num to be set as the numerator
     */
    protected void setNumerator(int num) {
        numerator=num;
    }

    /**
     * @param num to be set as the denominator
     */
    protected void setDenominator(int num) {
        denominator=num;
    }

    /**
     * @return the denominator
     */
    protected int getDenominator() {
        return denominator;
    }

    /**
     * @return the numerator
     */
    protected int getNumerator() {
        return numerator;
    }

    public boolean equals(Object obj) {
        if (obj == null) throw new IllegalArgumentException("If you've reached this point, you're just trying to get me (no null pls)");
        if (obj instanceof Rational) {
            Rational r1 = ((Rational) obj).reduce();
            Rational r2 = reduce();
            return r1.numerator == r2.numerator && r1.denominator == r2.denominator;
        }
        if (obj instanceof Integer) {
            Rational r1 = reduce();
            return ((Integer) obj).getRational().getNumerator() == r1.numerator && r1.denominator == 1;
        }
        return false;
    }
}
