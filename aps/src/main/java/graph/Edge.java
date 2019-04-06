package graph;

/**
 * Created by Bartosz Wesolowski on 12.03.2017.
 */
public class Edge {

    private final int v1;

    private final int v2;

    private final int weight;

    public Edge(final int v1, final int v2, final int w) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = w;
    }

    public int getV1() {
        return v1;
    }

    public int getV2() {
        return v2;
    }

    public int getWeight() {
        return weight;
    }

    /**
     * Checks if one of the vertices creating this edge is equal to u
     * @param u
     * @return
     */
    public boolean hasEndIn(int u) {
        return this.v1 == u || this.v2 == u;
    }

    /**
     * Return other end of the edge or null if edge has no vertex with value u
     * @param u
     * @return
     */
    public Integer getOtherEnd(final int u) {
        if (v1 == u) {
            return v2;
        } else if (v2 == u) {
            return v1;
        }
        return null;
    }
}
