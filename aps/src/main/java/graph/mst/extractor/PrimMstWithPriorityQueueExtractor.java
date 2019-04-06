package graph.mst.extractor;

import graph.Graph;
import graph.mst.AbstractMST;
import queue.Pair;
import queue.PriorityMinQueue;

import java.util.Optional;

/**
 * Created by Bartosz Wesolowski on 25.03.2017.
 */
public class PrimMstWithPriorityQueueExtractor implements MSTExtractor {

    @Override
    public Graph extractMST(final Graph source) {
        final int n = source.n;
        final Graph mst = new Graph(n);
        final PriorityMinQueue q = new PriorityMinQueue();
        Integer[] p = new Integer[n];
        p[0] = null;
        q.insert(new Pair(0, 0));
        for (int i = 1; i < n; i++) {
            p[i] = null;
            q.insert(new Pair(i, Graph.MAX_VALUE));
        }
        while (!q.isEmpty()) {
            final Pair min = q.extractMin();
            final int u = min.getKey();
            for (int v : source.getAdjectiveVertices(u)) {
                final Optional<Pair> isInQueueOpt = q.getElements().stream().filter(pair -> pair.getKey() == v).findFirst();
                if (isInQueueOpt.isPresent()) {
                    final Pair pair = isInQueueOpt.get();
                    final int weight = source.getEdgeWeight(u, v);
                    if (weight < pair.getPriority()) {
                        p[v] = u;
                        q.decreaseKey(isInQueueOpt.get(), weight);
                    }
                }
            }
        }

        for (int i = 0; i < source.n; i++) {
            if (p[i] != null) {
                mst.addEdge(i, p[i], source.getEdgeWeight(i, p[i]));
            }
        }
        return mst;
    }
}
