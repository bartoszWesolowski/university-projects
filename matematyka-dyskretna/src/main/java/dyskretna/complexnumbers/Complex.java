package dyskretna.complexnumbers;

import java.util.List;

/**
 * Created by bartosz.wesolowski on 10.04.2017.
 */
public interface Complex {

    Complex add(final Complex other);

    Complex subtract(final Complex other);

    Complex multiply(final Complex other);

    Complex multiply(final double scalar);

    Complex divide(final Complex other);

    Complex conjugate();

    /**
     *
     * @return 1/ this
     */
    Complex inverse();

    Complex pow(final int n);

    List<Complex> roots(final int n);

    Complex kthRoot(final int n, final int k);

    double module();

    double getReal();

    double getImaginary();

    double argument();

    double argInDegrees();

    boolean isZero();

}
