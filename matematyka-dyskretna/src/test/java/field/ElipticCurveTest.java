package field;


import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by Bartosz Wesolowski on 10.06.2017.
 */
public class ElipticCurveTest {

    final RandomGenerator generator = new RandomGenerator();

    @Test
    public void example() throws Exception {
        final EllipticalCurve e = new EllipticalCurve(5, 1, 1);
        EllipticalCurve.Point P1 = new EllipticalCurve.Point(Mod.of(4, 5), Mod.of(2, 5));
        EllipticalCurve.Point P2 = new EllipticalCurve.Point(Mod.of(2, 5), Mod.of(-1, 5));
        System.out.println(e.isFromCurve(P1));
        System.out.println(e.isFromCurve(P2));
        System.out.println(e.add(P1, P2));

        EllipticalCurve.Point add = e.add(2, P1);
        System.out.println("2*P1: " + add);
        System.out.println(e.isFromCurve(add));


        add = e.add(3, P1);
        System.out.println("3*P1: " + add);
        System.out.println(e.isFromCurve(add));
    }

    @Test
    public void randomWithBigPrime() throws Exception {
        final BigInteger prime = generator.randomPrimeEqualTo3Mod4(256);
        System.out.println(prime + " \n\tbits: " + prime.bitCount());
        final EllipticalCurve e = EllipticalCurve.random(prime);
        System.out.println(e);
        System.out.println("Delta: " + e.delta());

        EllipticalCurve.Point point1 = e.randomPoint();
        System.out.println("point1: " + point1);
        EllipticalCurve.Point point2 = e.randomPoint();
        System.out.println("point2: " + point2);

        if (point1 != null && point2 != null) {
            EllipticalCurve.Point sum = e.add(point1, point2);
            System.out.println("Sum: " + sum);
            System.out.println(e.isFromCurve(sum));
            EllipticalCurve.Point nTimes = e.add(100, point1);
            System.out.println("100 * point 1" + nTimes);
            System.out.println("In curve: " + e.isFromCurve(nTimes));
        }
    }

    @Test
    public void random() {
        final BigInteger prime = generator.randomPrime(5);
        final EllipticalCurve e = EllipticalCurve.random(prime);
        System.out.println(e);
        System.out.println("Delta: " + e.delta());
    }
}