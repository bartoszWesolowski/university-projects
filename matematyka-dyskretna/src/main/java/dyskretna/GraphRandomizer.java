package dyskretna;

import java.util.List;

/**
 * Created by s396451 on 26.03.2017.
 */
public class GraphRandomizer {

    /**
     *
     * @param R - Promień koła z którego z kórego szukamy wierzchołki
     * @param N - Liczba szukanych wierzchołków
     * @param r -
     * @return
     */
    public static Graph randomGraph(double R, int N, double r) {
        final Graph g = new Graph();
        final List<Vertex> vertices = VertexRandomizer.randomPairs(R, N);
        for(int i = 0; i < N; i++) {
            for(int j = i + 1; j < N; j++) {
                final Vertex x = vertices.get(i); //(x1, y1)
                final Vertex y = vertices.get(j); //(x2, y2)
                final double distance = x.distance(y);
                if (distance < 2 * r) {
                    g.addEdge(new Edge(x, y));
                    System.out.println(x + ", " + y + " = " + distance);
                } else {
                    g.addVertex(x);
                    g.addVertex(y);
                }
            }
        }
        return g;
    }
}
