package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Bartosz Wesolowski on 12.03.2017.
 */
public class Graph {

    public static final int MAX_VALUE = 100;

    /**
     * Number of vertices in graph
     */
    public final int n;

    private int w[][];

    /**
     * Constructor - init empty graph with n vertices
     * @param n
     */
    public Graph(final int n) {
        this.n = n;
        this.w = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    w[i][j] = 0;
                } else {
                    w[i][j] = MAX_VALUE;
                }
            }
        }
    }

    public Graph(final int[][] w) {
        this.w = w;
        this.n = w.length;
    }

    /**
     * Getter for weight of a endge connecting vertices
     * @param u vertex
     * @param v vertex
     * @return 0 if u ==v, £MAX_MAX_VALUE if there is no edge or weight of edge connecting two vertices
     */
    public int getEdgeWeight(int u, int v) {
        return this.w[u][v];
    }

    /**
     * @return true if there is a edge connecting these two vertices, false otherwise
     */
    public boolean hasEdge(int u, int v) {
        return this.w[u][v] > 0 && this.w[u][v] < MAX_VALUE;
    }

    public void addEdge(int first, int second, int value) {
        this.w[first][second] = value;
        this.w[second][first] = value;

    }

    /**
     * @return matrix representation of graph
     */
    public String rawMatrix() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(Arrays.toString(w[i])).append("\n");
        }
        return sb.toString();
    }

    /**
     * Printing pairs of vertices with the weight of the edge connecting them
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        int counter = 1;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (w[i][j] != MAX_VALUE && w[i][j] != 0) {
                    sb.append(counter + ". (" + i + ", " + j + ") = " + w[i][j]).append("\n");
                    counter ++;
                }
            }
        }
        return sb.append("Total weight: ")
                .append(this.getTotalWeight())
                .toString();
    }

    /**
     * Creates a list of edges in this graph
     * @return
     */
    public List<Edge> getEdges() {
        final List<Edge> edges = new ArrayList<>();

        for (int u = 0; u < n; u++) {
            for (int v = u; v < n; v++) {
                if (this.hasEdge(u, v)) {
                    edges.add(new Edge(u, v, this.getEdgeWeight(u, v)));
                }
            }
        }
        return edges;
    }

    public List<Integer> getAdjectiveVertices(final int u) {
        return this.getEdges().stream()
                .filter(edge -> edge.hasEndIn(u))
                .map(edge -> edge.getOtherEnd(u))
                .collect(Collectors.toList());
    }
    /**
     * @return summed weight of all edges.
     */
    public int getTotalWeight() {
        return this.getEdges().stream().mapToInt(e -> e.getWeight()).sum();
    }
}
