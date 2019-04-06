package graph.mst.extractor;

import graph.Graph;
import graph.mst.AbstractMST;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bartosz Wesolowski on 12.03.2017.
 */
public class PrimMstWithoutPriorityQueueExtractor implements MSTExtractor {

    @Override
    public Graph extractMST(final Graph source) {
        final Graph result = new Graph(source.n);
        //wartości krawędzi łączących wierzchołki z z dwóch zbiorów - wierzchołków już dodanych do mst
        //i tych które jeszcze nie zostały dodane
        final Integer[] keys = new Integer[source.n];
        final Set<Integer> mst = new HashSet<>();
        //tablica reprezentująca mst parent[i] jest ojcem węzła o indeksie i
        final Integer[] parent = new Integer[source.n];
        for (int i = 0; i < source.n; i++) {
            keys[i] = Graph.MAX_VALUE;
            parent[i] = null;
        }
        keys[0] = 0;
        for (int i = 1; i < source.n; i++) {
            int toVisit = this.getVertexWithMinimumEdgeWeightConnectingItToCurrentMstThatWasNotYetIncludedInMst(keys, mst);
            mst.add(toVisit);
            for (int v = 0; v < source.n; v++) {
                //iterujemy po wszystkich krawędziach wierzchołek toVisit z wierzchołkami, które nie są jeszcze w naszym mst
                //wybieramy te krawędzie które mają mniejszą wartość niż dotychczasowe połączenie zapisane w tablict keys (połączenie pomiędzy naszym mst a pozostałymi wierzchołkami w grafie)
                final int edgeWeight = source.getEdgeWeight(toVisit, v);
                if (!mst.contains(v) && source.hasEdge(toVisit, v) && edgeWeight < keys[v]) {
                    parent[v] = toVisit;
                    keys[v] = edgeWeight;
                }
            }

        }
        for (int i = 0; i < source.n; i++) {
            if (parent[i] != null) {
                result.addEdge(i, parent[i], source.getEdgeWeight(i, parent[i]));
            }
        }
        return result;
    }

    private Integer getVertexWithMinimumEdgeWeightConnectingItToCurrentMstThatWasNotYetIncludedInMst(final Integer[] keys, final Set<Integer> mst) {
        Integer minIndex = null;
        int min = Graph.MAX_VALUE;

        for (int i = 0; i < keys.length; i++) {
            if (!mst.contains(i) && keys[i] < min) {
                minIndex = i;
                min = keys[i];
            }
        }
        return minIndex;
    }
}
