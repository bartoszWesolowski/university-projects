package field;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigInteger;

/**
 * Created by Bartosz Wesolowski on 09.06.2017.
 */
public class EllipticalCurve {

    private static RandomGenerator randomGenerator = new RandomGenerator();

    private BigInteger p;

    private BigInteger A;

    private BigInteger B;

    public EllipticalCurve(final long p, final long a, final long b) {
        this(BigInteger.valueOf(p), BigInteger.valueOf(a), BigInteger.valueOf(b));
    }

    public EllipticalCurve(final BigInteger p, final BigInteger a, final BigInteger b) {
        if (!p.isProbablePrime(100)) {
            throw new IllegalArgumentException("Can not build Eliptic Curve with p that is not prime. P = " + p);
        }
        this.p = p;
        A = a;
        B = b;
    }

    public static EllipticalCurve random(BigInteger p) {
        Mod delta = Mod.of(BigInteger.ZERO, p);
        Mod x = null;
        Mod y = null;
        Mod a = null;
        Mod b = null;
        while (delta.isZero()) {
            y = Mod.of(randomGenerator.random(p), p);
            x = Mod.of(randomGenerator.random(p), p);
            a = Mod.of(randomGenerator.random(p), p);
            b = y.square().minus(x.pow(3)).minus(x.multiply(a));
            delta = a.pow(3).multiply(4).plus(b.square().multiply(27));
        }
        final EllipticalCurve e = new EllipticalCurve(p, a.value, b.value);
        Point point = new Point(x, y);
        return e;
    }


    public Mod delta() {
        return getMod(A).pow(3).multiply(4).plus(getMod(B).square().multiply(27));
    }

    public boolean isFromCurve(Point point) {
        if (point == null) {
            return false;
        }
        boolean isFromCurve = false;
        if(point.x.p.equals(p) && point.y.p.equals(p)) {
            //sprawdzamy czy rownanie krzywej jest spelnione
            Mod res = point.x.pow(3).plus(point.x.multiply(A)).plus(getMod(B)).minus(point.y.square());
            isFromCurve = res.isZero();
        }
        return isFromCurve;
    }

    public Point add(Point first, Point second) {
        if(!first.x.equals(second.x)) {
            Mod m = second.y.minus(first.y).divide(second.x.minus(first.x));
            Mod x = m.square().minus(first.x).minus(second.x);
            Mod y = m.multiply(first.x.minus(x)).minus(first.y);
            return new Point(x, y);
        }

        if (first.x.equals(second.x) && !first.y.equals(second.y)) {
            return Point.INFINITY;
        }

        if (first.equals(second) && !first.y.value.equals(BigInteger.ZERO)) {
            Mod m = first.x.square().multiply(3).plus(getMod(A)).divide(first.y.multiply(2));
            Mod x = m.square().minus(first.x).minus(second.x);
            Mod y = m.multiply(first.x.minus(x)).minus(first.y);
            return new Point(x, y);
        }

        return Point.INFINITY;
    }

    public Point add(long n, Point p) {
        Point result = p;
        for (int i = 0; i < n; i++) {
            result = this.add(result, p);
        }
        return p;
    }

    private Mod getMod(BigInteger value) {
        return Mod.of(value, p);
    }

    public Point randomPoint(){
        Point result = null;
        if (!p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
            System.out.println("cant calculate point for this field: F" + p);
            return null;
        }
        while (!this.isFromCurve(result)) {
            final Mod x = Mod.of(randomGenerator.random(p), p);
            //y^2 = x^2 + A*x + B
            //jest b^2 = a (mod p) to b = a ^( (p + 1) /  4)
            Mod ySquere = x.pow(3).plus(x.multiply(A)).plus(getMod(B));
            if (ySquere.isQuadraticResidue()) {
                Mod y = ySquere.root();
                if (y != null) {
                    result = new Point(x, y);
                }
            }
        }
        return result;
    }
    @Override
    public String toString() {
        return "ElipticCurve{" +
                "p=" + p +
                ", A=" + A +
                ", B=" + B +
                '}';
    }

    public static class Point {
        final Mod x;

        final Mod y;

        public Point(final Mod x, final Mod y) {
            this.x = x;
            this.y = y;
        }

        public static final Point INFINITY = new Point(null, null);

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final Point point = (Point) o;

            return new EqualsBuilder()
                    .append(x, point.x)
                    .append(y, point.y)
                    .build();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(x)
                    .append(y)
                    .build();
        }
    }
}
