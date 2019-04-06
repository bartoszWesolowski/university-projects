package graph;

import java.util.Scanner;

/**
 * Created by bartosz.wesolowski on 06.04.2017.
 */
public class GraphReader {

    private final Scanner in = new Scanner(System.in);

    public Graph readGraph() {
        System.out.println("Podaj liczbę wierzchołków: ");
        int v = in.nextInt();
        final Graph g = new Graph(v);
        this.readEdges(g);
        return g;
    }


    private void readEdges(final Graph graph, final int n) {
        for (int i = 0; i < n; i++) {
            System.out.println("Podaj 3 liczny postaci: wierzcholek wierzcholek waga: ");
            int v1 = in.nextInt();
            int v2 = in.nextInt();
            int w = in.nextInt();
            graph.addEdge(v1, v2, w);
        }
    }

    private void readEdges(final Graph graph) {
        System.out.println("Liczba krawedzi: ");
        int n = in.nextInt();
        this.readEdges(graph, n);
    }
}
