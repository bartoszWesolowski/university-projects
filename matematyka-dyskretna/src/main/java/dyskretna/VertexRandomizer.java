package dyskretna;

import javafx.util.Pair;
import sun.text.resources.et.CollationData_et;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by s396451 on 26.03.2017.
 */
public class VertexRandomizer {

    private static final Random random = new Random(System.nanoTime());

    /**
     *
     * @param R
     * @return random pair with coordinates from a circle of radius R with a center in (0, 0)
     */
    public static Pair<Double, Double> randomPair(double R) {
        double angle = random.nextDouble() * Math.PI * 2;
        double radius = random.nextDouble() * R;
        double x = radius * Math.cos(angle);
        double y = radius * Math.sin(angle);
        assert Math.hypot(x, y) <= R;
        return new Pair<>(x, y);
    }

    /**
     * List with N elements containing random Pairs with coordinates from [-R, R]
     * @param R
     * @param N
     * @return
     */
    public static List<Vertex> randomPairs(double R, int N) {
        final List<Vertex> result = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            result.add(new Vertex(i, randomPair(R)));
        }
        return result;
    }
}
