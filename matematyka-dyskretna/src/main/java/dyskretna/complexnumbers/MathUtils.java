package dyskretna.complexnumbers;

import java.math.BigDecimal;

/**
 * Created by Bartosz Wesolowski on 12.04.2017.
 */
public class MathUtils {

    private double errorMarigin = 0.0000000000000001;

    private int maxNumberOfIterations = 10000;

    public BigDecimal nthRoot(final double d, final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Cant calculate 0 root of a number. n can not be equal to zero.");
        }
        if (d == 0) {
            return BigDecimal.ZERO;
        }
        if (d <= 0) {
            if (n % 2 == 0) {
                throw new IllegalArgumentException("Cant calculate even root of negative number");
            }
            return this.nthRoot2(new BigDecimal(d), n, new BigDecimal(d), BigDecimal.ZERO);
        }

        BigDecimal root = this.nthRoot2(new BigDecimal(d), n,BigDecimal.ZERO, new BigDecimal(d));
        long casted = (long) root.doubleValue();
        if (Math.pow(casted, n) == d) {
            return BigDecimal.valueOf(casted);
        }
        return root;
    }

    private double nthRoot(final double d, int n, double left, double right) {
        double middle = (left + right) / 2.0;
        double middleValuePow = Math.pow(middle, n);

        while(!this.equals(middleValuePow, d)) {
            System.out.println(left + " " + middle + " " + right + " middValue: " + middleValuePow);
            if (middleValuePow < d) {
                left = middle;
            } else if (middleValuePow > d) {
                right = middle;
            }
            middle = (left + right) / 2.0;
            middleValuePow = Math.pow(middle, n);
        }
        return middle;
    }

    private BigDecimal nthRoot2(final BigDecimal d, int n, BigDecimal left, BigDecimal right) {
        BigDecimal middle = (left.add(right)).divide(BigDecimal.valueOf(2.0));
        BigDecimal middleValuePow = middle.pow(n);

        while(!this.equals(middleValuePow, d)) {
            //System.out.println(left + " " + middle + " " + right + " middValue: " + middleValuePow);
            if (middleValuePow.compareTo(d) == -1) {
                left = middle;
            } else if (middleValuePow.compareTo(d) == 1) {
                right = middle;
            }
            middle = (left.add(right)).divide(BigDecimal.valueOf(2.0));
            middleValuePow = middle.pow(n);
        }
        return middle;
    }
    private boolean equals(final double d1, final double d2) {
        return Math.abs(d1 - d2) <= this.errorMarigin;
    }

    private boolean equals(final BigDecimal d1, final BigDecimal d2) {
        BigDecimal abs = d1.subtract(d2).abs();
        return abs.compareTo(BigDecimal.valueOf(this.errorMarigin)) == -1;
    }

    public double getErrorMarigin() {
        return errorMarigin;
    }
}
