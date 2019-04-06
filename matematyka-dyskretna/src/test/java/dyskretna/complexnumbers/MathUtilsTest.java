package dyskretna.complexnumbers;

import junit.framework.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by Bartosz Wesolowski on 12.04.2017.
 */
public class MathUtilsTest {

    @Test
    public void simpleExamples() throws Exception {
        final MathUtils mathUtils = new MathUtils();
        this.test(16, 4);
        this.test(81, 4);
        this.test(-8.0, 3);
        this.test(-27.0, 3);
    }

    @Test
    public void positiveComplex() throws Exception {
        this.test(100, 4);
        this.test(100, 3);
        this.test(100, 2);
        this.test(1004, 10);
        this.test(1004000, 10);
        this.test(1004000, 100);
        this.test(100000, 100);
        this.test(999999.999999, 100);
    }

    @Test
    public void negative() throws Exception {
        this.test(-100, 3);
        this.test(-1004, 9);
        this.test(-1004000, 11);
    }
    @Test
    public void edgeCases() throws Exception {
        this.test(100, 1);
        this.test(-100, 1);
    }


    private void test(final double base, final int n) {
        final MathUtils mathUtils = new MathUtils();
        final BigDecimal d = mathUtils.nthRoot(base, n);
        System.out.println("d: " + n + "-th root of " + base + ": " + d.toEngineeringString());
        BigDecimal resultPow = d.pow(n);
        System.out.println(d + " ^ " + n + " = " + resultPow.toEngineeringString());
        System.out.println("diff between calculated value raised to the power of n and the actual value: " + resultPow.subtract(BigDecimal.valueOf(base)).abs());
        Assert.assertEquals(base, resultPow.doubleValue(), mathUtils.getErrorMarigin());
    }
}