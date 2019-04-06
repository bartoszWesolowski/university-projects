package field;

import junit.framework.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by Bartosz Wesolowski on 10.06.2017.
 */
public class RandomPrimeGeneratorTest {

    private final RandomGenerator model = new RandomGenerator();

    @Test
    public void getPrime() throws Exception {
        BigInteger prime = model.randomPrime();
        System.out.println(prime + " " + Long.toBinaryString(prime.longValueExact()).length());
        Assert.assertTrue(prime.isProbablePrime(1000));
    }
}