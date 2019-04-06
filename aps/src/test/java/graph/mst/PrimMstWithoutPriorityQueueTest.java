package graph.mst;

import graph.Graph;
import org.junit.Test;

/**
 * Created by Bartosz Wesolowski on 25.03.2017.
 */
public class PrimMstWithoutPriorityQueueTest extends AbstractMSTTest {

    @Override
    protected MST getMst(final Graph graph) {
        return new PrimMstWithoutPriorityQueue(graph);
    }
}
