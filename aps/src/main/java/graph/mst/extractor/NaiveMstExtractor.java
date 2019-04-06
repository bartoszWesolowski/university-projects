package graph.mst.extractor;

import graph.Graph;

/**
 * Created by Bartosz Wesolowski on 25.03.2017.
 */
public class NaiveMstExtractor implements MSTExtractor {

    @Override
    public Graph extractMST(final Graph source) {
        final int n = source.n;
        final Graph mst = new Graph(n);
        final int[] N = new int[n]; // numer wierzchoka y najbliższego wierzchokowi i
        final int[] D = new int[n]; // waga krawdzi laczacej wierzchoek i z wierzchokiem o numere N[i]

        for (int i = 1; i < n; i++) {
            N[i] = 0;
            D[i] = source.getEdgeWeight(0, i);
        }

        for (int z = 1; z < n; z++) {
            int min = Graph.MAX_VALUE;
            int k = 0;
            for (int i = 1; i < n; i++) {
                if (0 <= D[i] && D[i] < min) {
                    min = D[i];
                    k = i;
                }
            }
            mst.addEdge(k, N[k], source.getEdgeWeight(k, N[k]));
            D[k] = -1;

            for (int i = 1; i < n; i++) {
                if (source.getEdgeWeight(i, k) < D[i]) {
                    N[i] = k;
                    D[i] = source.getEdgeWeight(i, k);
                }
            }
        }
        return mst;
    }
}
