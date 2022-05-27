package numerics;

public interface Scalar {
    public Scalar add(Scalar s);
    public Scalar mul(Scalar s);
    public Scalar neg();
    public Scalar power(int exponent);
    public int sign();
    public Rational getRational();
}