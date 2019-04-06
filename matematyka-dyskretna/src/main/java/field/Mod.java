package field;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;

/**
 * Created by Bartosz Wesolowski on 07.06.2017.
 */
public class Mod {

    public final BigInteger value;

    public final BigInteger p;

    public static Mod of(final BigInteger value, final BigInteger p) {
        return new Mod(value, p);
    }

    public static Mod of(final long value, final long p) {
        return new Mod(value, p);
    }

    public Mod(final BigInteger value, final BigInteger p) {
        this.value = value.mod(p);
        this.p = p;
    }

    public Mod(final long value, final long p) {
        this.value = BigInteger.valueOf(value % p);
        this.p = BigInteger.valueOf(p);
    }

    public Mod plus(Mod other) {
        checkIfIsInSameField(other);
        final BigInteger value = add(this.value, other.value);
        return new Mod(value, p);
    }

    private BigInteger add(BigInteger first, BigInteger second) {
        return first.add(second.mod(p));
    }

    public Mod minus(Mod other) {
        checkIfIsInSameField(other);
        return new Mod(this.value.subtract(other.value).mod(p), p);
    }

    public Mod multiply(Mod other) {
        checkIfIsInSameField(other);
        return new Mod(this.value.multiply(other.value).mod(p), p);
    }


    public Mod multiply(long other) {
        return this.multiply(BigInteger.valueOf(other));
    }


    public Mod multiply(BigInteger other) {
        return new Mod(this.value.multiply(other).mod(p), p);
    }

    /**
     * @param other
     * @return
     */
    public Mod divide(Mod other) {
        checkIfIsInSameField(other);
        Mod inverse = other.inverse();
        if (inverse == null) {
            throw new ArithmeticException("Can not calculate inverse of while performing division: " + other);
        }
        return this.multiply(inverse);
    }

    public Mod pow(BigInteger n) {
        return Mod.of(this.value.modPow(n, p), p);
    }

    public Mod pow(long n) {
        return this.pow(BigInteger.valueOf(n));
    }

    public Mod square() {
        return this.pow(BigInteger.valueOf(2));
    }

    public Mod inverse() {
        //y = a^-1 mod b
        //http://eduinf.waw.pl/inf/alg/001_search/0009.php
        BigInteger u = BigInteger.ONE;
        BigInteger w = this.value;
        BigInteger x = BigInteger.ZERO;
        BigInteger z = this.p;
        BigInteger q;
        while (!w.equals(BigInteger.ZERO)) {
            if (w.compareTo(z) == -1) {
                //u ↔ x;   w ↔ z
                Pair<BigInteger, BigInteger> toSwap = Pair.of(u, x);
                u = toSwap.getRight();
                x = toSwap.getLeft();
                toSwap = Pair.of(w, z);
                w = toSwap.getRight();
                z = toSwap.getLeft();
            }
            q = w.divide(z);
            u = u.subtract(q.multiply(x));
            w = w.subtract(q.multiply(z));
        }
        if (!z.equals(BigInteger.ONE)) {
            return null;
        }
        if (x.compareTo(BigInteger.ZERO) == -1) {
            x = x.add(this.p);
        }
        return new Mod(x, p);
    }

    public boolean isZero() {
        return this.value.mod(p).equals(BigInteger.ZERO);
    }

    /**
     * @return true jestli jest resztą kwadratową modulo p
     */
    public boolean isQuadraticResidue() {
        BigInteger pow = this.p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
        final Mod mod = this.pow(pow);
        return mod.value.equals(BigInteger.ONE);
    }

    /**
     * Returns root if or null if is not existing. Supports only object that has p = 3 (mod 4)
     *
     * @return
     */
    public Mod root() {
        final BigInteger FOUR = BigInteger.valueOf(4);
        if (!isQuadraticResidue() || !p.mod(FOUR).equals(BigInteger.valueOf(3))) {
            return null;
        }
        return this.pow(p.add(BigInteger.ONE).divide(FOUR));
    }

    private void checkIfIsInSameField(final Mod other) {
        if (!p.equals(other.p)) {
            throw new IllegalArgumentException("Can not perform operation on element from different field");
        }
    }

    @Override
    public String toString() {
        return "Mod{" +
                "value=" + value +
                ", p=" + p +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Mod mod = (Mod) o;

        return new EqualsBuilder()
                .append(value, mod.value)
                .append(p, mod.p)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(value)
                .append(p)
                .build();
    }
}
