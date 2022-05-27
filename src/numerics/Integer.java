package numerics;

public class Integer implements Scalar{

    private int number;

    public Integer(int val) {
        number = val;
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
        if (s.toString().contains("/")) {
            return new Rational(number * sFix.getDenominator() + sFix.getNumerator(), sFix.getDenominator());
        }
        else {
            return new Integer(number + s.getRational().getNumerator());
        }
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
            int num = number * sFix.getNumerator();
            int denom = sFix.getDenominator();
            return new Rational(num,denom);
        }
        else {
            return new Integer(number * sFix.getNumerator());
        }
    }

    /**
     *
     * @return A new integer representing the opposite number to this
     */
    @Override
    public Scalar neg() {
        return new Integer(-1 * number);
    }

    /**
     *
     * @param exponent The power to raise by
     * @return A new integer representing this raised to the power of exponent
     */
    @Override
    public Scalar power(int exponent) {
        return new Integer((int)Math.pow(number, exponent));
    }

    /**
     * @return 1 if this is positive<br>
     * -1 if this is negative<br>
     * 0 if this is 0
     */
    @Override
    public int sign() {
        return java.lang.Integer.signum(number);
    }

    /**
     *
     * @return This number as a rational number
     */
    @Override
    public Rational getRational() {
        return new Rational(number, 1);
    }

    public String toString() {
        return "" + number;
    }

    public boolean equals(Object obj) {
        if (obj == null) throw new IllegalArgumentException("If you've reached this point, you're just trying to get me (no null pls)");
        if (obj instanceof Integer) {
            return number == ((Integer) obj).number;
        }
        else if ((obj instanceof Rational)) {
            Rational objFix = ((Rational) obj).reduce();
            if (objFix.getDenominator() == 1 || objFix.getNumerator() == 0) return number == ((Rational) obj).getNumerator();
        }
        return false;
    }
}