package graph.mst;

import graph.Graph;
import graph.mst.extractor.MSTExtractor;
import graph.mst.extractor.PrimMstWithPriorityQueueExtractor;
import queue.Pair;
import queue.PriorityMinQueue;

import java.util.Optional;

/**
 * Created by Bartosz Wesolowski on 25.03.2017.
 */
public class PrimMstWithPriorityQueue extends MST {

    public PrimMstWithPriorityQueue(final Graph source) {
        super(new PrimMstWithPriorityQueueExtractor(), source);
    }
}
