package dyskretna;

import javafx.util.Pair;

/**
 * Created by s396451 on 26.03.2017.
 */
public class Edge {

    private Vertex u;

    private Vertex v;

    public Edge(Vertex u, Vertex v) {
        this.u = u;
        this.v = v;
    }

    public Vertex getU() {
        return u;
    }

    public Vertex getV() {
        return v;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            final Edge edge = (Edge) obj;
            return this.u.equals(edge.getU()) && this.v.equals(edge.getV());
        }
        return false;
    }

    public boolean isOneOfEnds(Pair<Double, Double> u) {
        return u.equals(this.u) || u.equals(this.v);
    }
}
