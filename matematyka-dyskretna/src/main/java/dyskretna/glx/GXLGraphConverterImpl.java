package dyskretna.glx;

import dyskretna.Graph;
import dyskretna.Vertex;
import javafx.util.Pair;
import net.sourceforge.gxl.GXLDocument;
import net.sourceforge.gxl.GXLEdge;
import net.sourceforge.gxl.GXLFloat;
import net.sourceforge.gxl.GXLGraph;
import net.sourceforge.gxl.GXLNode;

/**
 * Created by Bartosz Wesolowski on 08.04.2017.
 */
public class GXLGraphConverterImpl implements GXLGraphConverter {

    @Override
    public GXLGraph convertToGraph(final Graph graph) {
        final GXLGraph result = new GXLGraph(graph.getName());
        graph.getVertices().stream()
            .forEach(
                v -> {
                    final GXLNode vertexNode = new GXLNode(this.getVertexName(v));
                    final Pair<Double, Double> coordinates = v.getCoordinates();
                    vertexNode.setAttr("x", new GXLFloat(coordinates.getKey().floatValue()));
                    vertexNode.setAttr("y", new GXLFloat(coordinates.getValue().floatValue()));
                    result.add(vertexNode);
                }
        );

        graph.getEdges().stream()
            .forEach(
                e -> {
                    final String uName = this.getVertexName(e.getU());
                    final String vName = this.getVertexName(e.getV());
                    final GXLEdge edge = new GXLEdge(uName, vName);
                    result.add(edge);
                }
            );

        return result;
    }

    @Override
    public GXLDocument convertToDocument(final Graph g) {
        final GXLDocument gxlDocument = new GXLDocument();
        gxlDocument.getDocumentElement().add(this.convertToGraph(g));
        return gxlDocument;
    }

    @Override
    public String getVertexName(final Vertex vertex) {
        return String.valueOf(vertex.getId());
    }
}
