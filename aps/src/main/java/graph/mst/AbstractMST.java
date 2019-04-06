package graph.mst;

import graph.Edge;
import graph.Graph;
import graph.GraphReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Bartosz Wesolowski on 25.03.2017.
 */
public abstract class AbstractMST {

    protected Graph mst;

    protected Graph source;

    /**
     * Creates mst from the <code>source</code>
     * @param source
     */
    public AbstractMST(final Graph source) {
        this.source = source;
        this.mst = this.extractMST(source);
    }

    protected abstract Graph extractMST(final Graph source);

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
