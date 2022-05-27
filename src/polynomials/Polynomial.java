package polynomials;

import java.util.ArrayList;
import numerics.*;
import numerics.Integer;

public class Polynomial {

    private ArrayList<Monomial> monomials;

    public Polynomial(ArrayList<Monomial> monomials) {
        this.monomials = monomials;
    }

    /**
     * Builds a new polynomial from a legal string
     * @param s The building string
     * @return A new polynomial built from the string
     */
    public static Polynomial build(String s) {
        ArrayList<Monomial> mList = new ArrayList<>();
        String[] split = s.split(" "); // gets rid of spaces and separates the coefficients
        int j = 0;
        for (int i = 0; i < split.length; i++) { // the loop will add each number
            if (!split[i].equals("")) { // handle extra spaces
                String[] rationalSplit = split[i].split("/"); // determine if the number is an integer or a rational
                if (rationalSplit.length > 1) {
                    mList.add(new Monomial(i-j,
                                    new Rational(java.lang.Integer.parseInt(rationalSplit[0]),
                                            java.lang.Integer.parseInt(rationalSplit[1]))
                            )
                    );
                }
                else {
                    mList.add(new Monomial(i-j,
                                    new numerics.Integer(java.lang.Integer.parseInt(rationalSplit[0]))
                            )
                    );
                }
            }
            else j++;
        }
        return new Polynomial(mList);
    }

    /**
     *
     * @param p The polynomial to be added
     * @return A new polynomial representing the sum of p and this one.
     */
    public Polynomial add(Polynomial p) {
        if (p == null) throw new IllegalArgumentException("Illegal polynomial for addition");
        polyFix();
        p.polyFix();
        int maxSize = Math.max(p.monomials.size(), monomials.size());
        int minSize = Math.min(p.monomials.size(), monomials.size());
        ArrayList<Monomial> mList = new ArrayList<>();
        for (int i = 0; i < minSize; i++) {
            Monomial m1 = monomials.get(i);
            Monomial m2 = p.monomials.get(i);
            mList.add(m1.add(m2));
        }

        if (monomials.size() == maxSize) {
            for (int i = minSize; i < maxSize; i++) {
                mList.add(monomials.get(i));
            }
        }
        else {
            for (int i = minSize; i < maxSize; i++) {
                mList.add(p.monomials.get(i));
            }
        }
        return new Polynomial(mList);
    }

    /**
     * Performs polynomial multiplication between two polynomials
     * @param p The polynomial to be multiplied
     * @return A new polynomial that represents p multiplied by this
     */
    public Polynomial mul(Polynomial p) {
        if (p == null) throw new IllegalArgumentException("Illegal polynomial for multiplication");
        ArrayList<Polynomial> multipliedList = new ArrayList<>();
        for (Monomial m : p.monomials) { // populates the list with this polynomial multiplied by each monomial from p
            multipliedList.add(mul(m));
        }
        Polynomial output = build("0");
        for (Polynomial current : multipliedList) {
            output = output.add(current);
        }
        return output;
    }

    /**
     *
     * @param m The monomial to multiply by
     * @return A new polynomial that represents this one multiplied by m
     */
    private Polynomial mul(Monomial m) {
        if (m == null) throw new IllegalArgumentException("Illegal monomial for polynomial multiplication");
        ArrayList<Monomial> mList = new ArrayList<>();
        for (Monomial current : monomials) {
            mList.add(current.mul(m));
        }

        return new Polynomial(mList);
    }

    /**
     *
     * @param s The scalar to place in the polynomial
     * @return A new scalar representing the value of the polynomial with s in place of the variable
     */
    public Scalar evaluate(Scalar s) {
        if (s == null) throw new IllegalArgumentException("Illegal scalar for evaluation");
        Scalar sum = new Rational(0, 1);
        for (Monomial m : monomials) {
            sum = sum.add(m.evaluate(s));
        }

        return sum;
    }

    /**
     *
     * @return A new polynomial representing the derivative of this one
     */
    public Polynomial derivative() {
        ArrayList<Monomial> mList = new ArrayList<>();
        for (Monomial m : monomials) {
            mList.add(m.derivative());
        }
        return new Polynomial(mList);
    }

    public String toString() {
        polyRed();
        StringBuilder output = new StringBuilder();
        for (Monomial m : monomials) {
            output.append("+").append(m.toString());
        }
        for (int i = 0; i < output.length(); i++) {
            if (output.charAt(i) == '+' && output.charAt(i + 1) == '-')
                output = new StringBuilder(output.substring(0, i) + output.substring(i + 1));
        }
        if (output.isEmpty()) return "0";
        if (output.charAt(0)=='+') {return output.substring(1);}
        if (output.toString().length() == 0 || (output.toString().length() == 1 && output.charAt(0) == '0')) return "0";
        return output.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null) throw new IllegalArgumentException("If you've reached this point, you're just trying to get me (no null pls)");
        if ((obj instanceof Polynomial)) {
            ((Polynomial) obj).polyRed();
            polyRed();
            if (monomials.size() != ((Polynomial) obj).monomials.size()) return false;
            for (int i = 0; i < monomials.size(); i++) {
                Monomial m1 = monomials.get(i).reduce();
                Monomial m2 = ((Polynomial) obj).monomials.get(i).reduce();
                if (!(m1.equals(m2))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reduces the polynomial to its most reduced form (i.e turns 0+0+x^2+5x^3+0+0+6x^6 to x^2+5x^3+6x^6)
     */
    private void polyRed() {
        Rational z0 = new Rational(0,1);
        Integer z1 = new Integer(0);
        int currSize = monomials.size();
        int i=0;
        while (i<currSize) {
            Monomial curr = monomials.get(i);
            if (curr.equals(new Monomial(i,z0))) {monomials.remove(curr); currSize--;}
            else if (curr.equals(new Monomial(i,z1))) {monomials.remove(curr); currSize--;}
            else i++;
        }
    }

    /**
     *  Fixes the polynomial to a standard form, in order to help the 'add' method (i.e turns x^2+5x^3+6x^6 to 0+0+x^2+5x^3+0+0+6x^6)
     */
    private void polyFix() {
        int finSize = monomials.get(monomials.size()-1).getExponent()+1;
        for (int j = 0; j < finSize; j++) {
            if (monomials.get(j).getExponent() != j) monomials.add(j,new Monomial(j,new Integer(0)));
        }
    }
}