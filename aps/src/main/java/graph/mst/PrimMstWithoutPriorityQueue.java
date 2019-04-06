package graph.mst;

import graph.Graph;
import graph.mst.extractor.MSTExtractor;
import graph.mst.extractor.PrimMstWithoutPriorityQueueExtractor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bartosz Wesolowski on 12.03.2017.
 */
public class PrimMstWithoutPriorityQueue extends MST {


    public PrimMstWithoutPriorityQueue(Graph source) {
        super(new PrimMstWithoutPriorityQueueExtractor(), source);
    }
}
