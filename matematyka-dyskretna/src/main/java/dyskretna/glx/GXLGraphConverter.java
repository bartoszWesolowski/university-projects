package dyskretna.glx;

import dyskretna.Graph;
import dyskretna.Vertex;
import net.sourceforge.gxl.GXLDocument;
import net.sourceforge.gxl.GXLGraph;

/**
 * Created by Bartosz Wesolowski on 08.04.2017.
 */
public interface GXLGraphConverter {

    GXLGraph convertToGraph(Graph graph);

    GXLDocument convertToDocument(final Graph g);

    String getVertexName(Vertex vertex);
}
