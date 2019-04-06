package graph.mst;

import graph.Graph;

/**
 * Created by Bartosz Wesolowski on 25.03.2017.
 */
public class NaiveMstTest extends AbstractMSTTest {

    @Override
    protected MST getMst(final Graph graph) {
        return new NaiveMst(graph);
    }
}
