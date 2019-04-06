package graph.mst.extractor;

import graph.Graph;

/**
 * Created by bartosz.wesolowski on 06.04.2017.
 */
public interface MSTExtractor {

    Graph extractMST(final Graph source);
}
