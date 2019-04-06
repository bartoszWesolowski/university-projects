package dyskretna.complexnumbers.impl;

import dyskretna.complexnumbers.Complex;
import dyskretna.complexnumbers.MathUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Implemenation of Complex number: a + bi
 * Created by bartosz.wesolowski on 10.04.2017.
 */
public class ComplexNumber implements Complex {

    public static final ComplexNumber ZERO = ComplexNumber.of(0.0, 0.0);

    public static final ComplexNumber ONE = ComplexNumber.of(1.0);
    
    public static final ComplexNumber I = ComplexNumber.im(1.0);

    public static String IM_PREFIX = "i";

    private final double real;

    private final double imaginary;

    public static ComplexNumber of(final double real) {
        return new ComplexNumber(real, 0.0);
    }

    public static ComplexNumber of(final double real, double im) {
        return new ComplexNumber(real, im);
    }

    public static ComplexNumber im(double im) {
        return new ComplexNumber(0.0, im);
    }

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public Complex add(Complex other) {
        return new ComplexNumber(this.real + other.getReal(), this.imaginary + other.getImaginary());
    }

    @Override
    public Complex subtract(Complex other) {
        final Complex opposite = new ComplexNumber(-1.0 * other.getReal(), -1.0 * other.getImaginary());
        return this.add(opposite);
    }

    @Override
    public Complex multiply(Complex other) {
        //(a,b) * (c,d) = (ac - bd, ad + bc)
        final double real = this.real * other.getReal() - this.imaginary * other.getImaginary();
        final double imaginary = this.real * other.getImaginary() + this.imaginary * other.getReal();
        return new ComplexNumber(real, imaginary);
    }

    @Override
    public Complex multiply(double scalar) {
        return new ComplexNumber(this.real * scalar, this.imaginary * scalar);
    }

    @Override
    public Complex divide(Complex other) {
        if (other.isZero()) {
            throw new IllegalArgumentException(String.format("Cant perform division operation: %s / %s. Can not divide by zero! ", this.toString(), other.toString()));
        }
        final double denominator = Math.pow(other.getReal(), 2) + Math.pow(other.getImaginary(), 2);
        final double real = (this.real * other.getReal() + this.imaginary * other.getImaginary()) / denominator;
        final double imaginary = (this.imaginary * other.getReal() - this.real * other.getImaginary()) / denominator;
        return new ComplexNumber(real, imaginary);
    }

    /**
     * Sprzężenie
     * @return conjugated number: it this is equal to a + bi it will return a - bi
     */
    @Override
    public Complex conjugate() {
        return new ComplexNumber(this.real, -1 * this.imaginary);
    }

    @Override
    public Complex inverse() {
        if (isZero()){
            throw new IllegalArgumentException(String.format("Cant perform inverse operation: 1 / %s. Can not divide by zero! ", this.toString()));
        }
        final Complex conjugate = this.conjugate();
        return conjugate.divide(this.multiply(conjugate));
    }

    @Override
    public Complex pow(final int n) {
        final double rn = Math.pow(this.module(), n);
        final double arg = n * this.argument();
        return new ComplexNumber(Math.cos(arg), Math.sin(arg)).multiply(rn);
    }

    /**
     * Return list of  n-th rooths of this complex number.
     *
     * @param n
     * @return
     */
    @Override
    public List<Complex> roots(final int n) {
        final List<Complex> result = new ArrayList<>();
        for (int k = 0; k < n; k++) {
            result.add(this.kthRoot(n, k));
        }
        return result;
    }

    /**
     * Return the k-th root of n-th root of this number. MUST: k = 0, 1, ..., n-1
     * @param n
     * @param k
     * @return
     */
    @Override
    public Complex kthRoot(final int n, final int k) {
        final double rNthRoot = new MathUtils().nthRoot(this.module(), n).doubleValue();
        final double arg = (this.argument() + 2 * k * Math.PI) / (double) n;
        return new ComplexNumber(Math.cos(arg), Math.sin(arg)).multiply(rNthRoot);
    }

    @Override
    public double module() {
        return Math.hypot(this.real, this.imaginary);
    }

    @Override
    public double getReal() {
        return this.real;
    }

    @Override
    public double getImaginary() {
        return this.imaginary;
    }

    /**
     * Return the argument angle of this number.
     * @return 0.0 if this number is zero eg (0, 0)
     */
    @Override
    public double argument() {
        double arg = 0.0;
        if(this.real == 0.0 && this.imaginary == 0.0) {
            return 0.0;
        }
        if (this.real > 0.0) {
            arg = Math.atan(this.imaginary / this.real);
        } else  if(this.real < 0.0 && this.imaginary >= 0.0) {
            arg = Math.atan(this.imaginary / this.real) + Math.PI;
        } else  if(this.real < 0.0 && this.imaginary < 0.0) {
            arg = Math.atan(this.imaginary / this.real) - Math.PI;
        } else  if(this.real == 0.0 && this.imaginary > 0.0) {
            arg = Math.PI / 2.0;
        } else  if(this.real == 0.0 && this.imaginary < 0.0) {
            arg = Math.PI / -2.0;
        }
        return arg;
    }

    @Override
    public double argInDegrees() {
        return Math.toDegrees(this.argument());
    }

    /**
     * TODO:Check double comparision!
     * @return
     */
    @Override
    public boolean isZero() {
        return this.real == 0.0 && this.imaginary == 0.0;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.real)
                .append(this.imaginary)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Complex)) {
            return false;
        }
        final Complex other = (Complex) obj;
        return new EqualsBuilder()
                .append(this.real, other.getReal())
                .append(this.imaginary, other.getImaginary())
                .build();
    }

    private static final DecimalFormat df = new DecimalFormat("0.0####");

    @Override
    public String toString() {
        final String sign = this.imaginary <= 0 ? " - " : " + ";
        return StringUtils.join(df.format(this.real), sign, df.format(Math.abs(this.imaginary)), ComplexNumber.IM_PREFIX);
    }
}
