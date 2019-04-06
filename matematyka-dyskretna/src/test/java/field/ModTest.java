package field;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by Bartosz Wesolowski on 07.06.2017.
 */
public class ModTest {

    @Test
    public void testModulo() {
        System.out.println(125 % 7);
        System.out.println(BigInteger.valueOf(-10).mod(BigInteger.valueOf(3)));
    }

    @Test
    public void inverse() throws Exception {
        testInverse(12767, 256);
        testInverse(12767222, 13);
        testInverse(1273672222, 5651);
        testInverse(BigInteger.valueOf(1273672222).pow(2120), BigInteger.valueOf(5651));
    }

    @Test
    public void isQuadraticResidue() {
        Mod mod = Mod.of(5, 7);
        System.out.println(mod + " is quadratic residue: " + mod.isQuadraticResidue());
    }

    private void testInverse(long value, long p) {
        final Mod x = new Mod(value, p);
        Mod inverse = x.inverse();
        if (inverse != null) {
            BigInteger expectedInverseValue = BigInteger.valueOf(value).modInverse(BigInteger.valueOf(p));
            System.out.println("Inverse of " + x.toString() + " = " + inverse + " Test: x * x.inverse() =  " + x.multiply(inverse));
            System.out.println("Expected:" + expectedInverseValue + "\n");
            Assert.assertEquals(expectedInverseValue, inverse.value);
        } else {
            System.out.println("NO inverse found for: " + x + "\n");
            try {
                BigInteger expectedInverseValue = BigInteger.valueOf(value).modInverse(BigInteger.valueOf(p));
                Assert.fail();
            } catch (ArithmeticException e) { }
        }
    }

    private void testInverse(BigInteger value, BigInteger p) {
        final Mod x = new Mod(value, p);
        testInverse(x);
    }

    private void testInverse(Mod x) {
        Mod inverse = x.inverse();
        if (inverse != null) {
            BigInteger expectedInverseValue = x.value.modInverse(x.p);
            System.out.println("Inverse of " + x.toString() + " = " + inverse + " Test: x * x.inverse() =  " + x.multiply(inverse));
            System.out.println("Expected:" + expectedInverseValue + "\n");
            Assert.assertEquals(expectedInverseValue, inverse.value);
        } else {
            System.out.println("NO inverse found for: " + x);
            try {
                BigInteger expectedInverseValue = x.value.modInverse(x.p);
                Assert.fail();
            } catch (ArithmeticException e) { }
        }
    }
}