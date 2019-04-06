package dyskretna;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by s396451 on 26.03.2017.
 */
public class Graph {

    private String name = "NO_NAME";

    private List<Edge> edges = new ArrayList<>();

    private Set<Vertex> vertices = new HashSet<>();

    public boolean addEdge(Edge edge) {
        this.vertices.add(edge.getU());
        this.vertices.add(edge.getV());
        return edges.add(edge);
    }

    public boolean addVertex(final Vertex vertex) {
        return this.vertices.add(vertex);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
