package dyskretna.complexnumbers.impl;

import dyskretna.complexnumbers.Complex;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Bartosz Wesolowski on 12.04.2017.
 */
public class ComplexTest {

    private final Complex first = ComplexNumber.of(2, 3);
    private final Complex second = ComplexNumber.of(5, -1);

    private double delta = 0.00001;

    @Test
    public void additionTest() throws Exception {
        final Complex sum = first.add(second);
        Assert.assertEquals(first.add(second), second.add(first));
        Assert.assertEquals(sum, ComplexNumber.of(7, 2));
    }


    @Test
    public void subtract() throws Exception {
        final Complex sum = first.subtract(second);
        Assert.assertEquals(new ComplexNumber(-3, 4), sum);
    }

    @Test
    public void multiply() throws Exception {
        final Complex product = first.multiply(second);
        Assert.assertEquals(first.multiply(second), second.multiply(first));
        Assert.assertEquals(product, ComplexNumber.of(13, 13));
        Assert.assertEquals(first, first.multiply(ComplexNumber.ONE));
    }

    @Test
    public void multiplyByScalar() throws Exception {
        final Complex product = first.multiply(2.0);
        Assert.assertEquals(first.multiply(second), second.multiply(first));
        Assert.assertEquals(product, ComplexNumber.of(first.getReal() * 2, first.getImaginary() * 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void divideByZero() throws Exception {
        ComplexNumber.ONE.divide(ComplexNumber.ZERO);
    }

    @Test
    public void divide() {
        final Complex div = this.first.multiply(26).divide(second);
        Assert.assertEquals(new ComplexNumber(7, 17), div);
    }

    @Test
    public void pow() {
        final Complex pow = this.first.pow(3);
        Assert.assertEquals(-46, pow.getReal(), delta);
        Assert.assertEquals(9, pow.getImaginary(), delta);
    }

    /**
     * <a href="https://www.wolframalpha.com/input/?i=(1%2Bi)%5E(1%2F5)&lk=3">MATHEMATICA</a>
     * @throws Exception
     */
    @Test
    public void root() throws Exception {
        final Complex c = ComplexNumber.of(1, 1);
        System.out.println("5-th root of " + c.toString());
        List<Complex> roots = c.roots(5);
        roots.stream().forEach(cn -> System.out.println(cn.toString()));

        roots = ComplexNumber.I.roots(5);
        System.out.println("\n5-th root of " + ComplexNumber.I.toString());
        roots.stream().forEach(cn -> System.out.println(cn.toString()));
    }

    @Test
    public void argument() {
        Assert.assertEquals(56.3099, first.argInDegrees(), 0.0001);
        Assert.assertEquals(-11.3099, second.argInDegrees(), 0.0001);
        Assert.assertEquals(129.8055710, ComplexNumber.of(-5, 6).argInDegrees(), 0.0001);
        Assert.assertEquals(-129.80557109, ComplexNumber.of(-5, -6).argInDegrees(), 0.0001);
        Assert.assertEquals(-129.80557109, ComplexNumber.of(-5, -6).argInDegrees(), 0.0001);
        Assert.assertEquals(90, ComplexNumber.I.argInDegrees(), 0.0001);
        Assert.assertEquals(-90, ComplexNumber.I.multiply(-1).argInDegrees(), 0.0001);
    }

    @Test
    public void module() throws Exception {
        Assert.assertEquals(3.605551275463989, first.module(), delta);
        Assert.assertEquals(5.0990195135927845, second.module(), delta);
    }

    @Test
    public void inverse() throws Exception {
        final Complex c = ComplexNumber.of(3, 4);
        final Complex inverse = c.inverse();
        Assert.assertEquals(ComplexNumber.of(0.12, -0.16), inverse);
        Assert.assertEquals(ComplexNumber.ONE, c.multiply(inverse));
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroInverse() throws Exception {
        ComplexNumber.ZERO.inverse();
    }

    @Test
    public void examples() {
        System.out.println(this.first + " + " + this.second + " = " + this.first.add(this.second));
        System.out.println(this.first + " - " + this.second + " = " + this.first.subtract(this.second));
        System.out.println(this.first + " * " + this.second + " = " + this.first.multiply(this.second));
        System.out.println(this.first + " / " + this.second + " = " + this.first.divide(this.second));
        System.out.println("1 / " + this.first + " = " + this.first.inverse());
        System.out.println(this.first + " ^ " + 2 + " = " + this.first.pow(2));
        System.out.println("arg(" + this.first + ") =  " + this.first.argument() + "[rad]");
        System.out.println("arg(" + this.first + ") =  " + this.first.argInDegrees() + "[deg]");
        System.out.println("|" + this.first + "| =  " + this.first.module());
    }
}