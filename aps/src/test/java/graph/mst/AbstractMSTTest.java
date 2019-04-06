package graph.mst;

import graph.Graph;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Bartosz Wesolowski on 18.03.2017.
 */
public abstract class AbstractMSTTest {

    @Test
    public void mst1() {
        final Graph g = new Graph(new int[][] {
                {0, 2, 100, 6, 100},
                {2, 0, 3, 8, 5},
                {100, 3, 0, 100, 7},
                {6, 8, 100, 0, 9},
                {100, 5, 7, 9, 0},
        });
        this.testMstCreation(g, 16, 4);
    }

    @Test
    public void mst2() {
        final Graph g = new Graph(new int[][] {
                {0, 4, 100, 100, 100, 100, 100, 8, 100},
                {4, 0, 8, 100, 100, 100, 100, 11, 100},
                {100, 8, 0, 7, 100, 4, 100, 100, 2},
                {100, 100, 7, 0, 9, 14, 100, 100, 100},
                {100, 100, 100, 9, 0, 10, 100, 100, 100},
                {100, 100, 4, 14, 10, 0, 2, 100, 100},
                {100, 100, 100, 100, 100, 2, 0, 1, 6},
                {8, 11, 100, 100, 100, 100, 1, 0, 7},
                {100, 100, 2, 100, 100, 100, 6, 7, 0}
        });
        this.testMstCreation(g, 37, 8);
    }

    @Test
    public void mst3() throws Exception {
        final Graph g = new Graph(new int[][] {
                {0, 5, 100, 9, 100, 100, 3, 100},
                {5, 0, 9, 100, 8, 6, 100, 7},
                {100, 9, 0, 9, 4, 100, 5, 3},
                {9, 100, 9, 0, 100, 100, 8, 100},
                {100, 8, 4, 100, 0, 2, 1, 100},
                {100, 6, 100, 100, 2, 0, 6, 100},
                {3, 100, 5, 8, 1, 6, 0, 9},
                {100, 7, 3, 100, 100, 100, 9, 0}
        });

        this.testMstCreation(g, 26, 7);
    }

    private void testMstCreation(final Graph g, final int expectedSumOfEdgesWeightsInMst,
                                 final int expectedNumberOfEdgesInMSt) {
        final MST mst = this.getMst(g);
        Assert.assertEquals(expectedSumOfEdgesWeightsInMst, mst.getTotalWeight());
        Assert.assertEquals(expectedNumberOfEdgesInMSt, mst.getEdges().size());
        Assert.assertNotNull(mst.toString());
        Assert.assertNotNull(mst.rawMatrix());
    }

    protected abstract MST getMst(final Graph graph);

}
