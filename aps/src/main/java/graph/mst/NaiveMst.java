package graph.mst;

import graph.Graph;
import graph.mst.extractor.MSTExtractor;
import graph.mst.extractor.NaiveMstExtractor;

/**
 * Created by Bartosz Wesolowski on 25.03.2017.
 */
public class NaiveMst extends MST {

    public NaiveMst(Graph source) {
        super(new NaiveMstExtractor(), source);
    }
}
