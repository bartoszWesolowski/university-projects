package graph.mst;

import graph.Edge;
import graph.Graph;
import graph.mst.extractor.MSTExtractor;

import java.util.List;

/**
 * Created by bartosz.wesolowski on 06.04.2017.
 */
public class MST {

    private Graph mst;

    public MST(final MSTExtractor mstExtractor, final Graph source) {
        mst = mstExtractor.extractMST(source);
    }

    @Override
    public String toString() {
        return mst.toString();
    }

    public List<Edge> getEdges() {
        return mst.getEdges();
    }

    public int getTotalWeight() {
        return mst.getTotalWeight();
    }

    public String rawMatrix() {
        return mst.rawMatrix();
    }
}
