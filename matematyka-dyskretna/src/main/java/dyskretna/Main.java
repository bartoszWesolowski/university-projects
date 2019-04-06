package dyskretna;

import dyskretna.glx.GXLGraphConverter;
import dyskretna.glx.GXLGraphConverterImpl;
import net.sourceforge.gxl.GXLDocument;
import net.sourceforge.gxl.GXLGraph;

import java.io.File;
import java.io.IOException;

/**
 * Created by s396451 on 26.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        final dyskretna.Graph g = dyskretna.GraphRandomizer.randomGraph(100, 50, 10);
        System.out.println(g.getVertices().size());
        final GXLGraphConverter glxConverter = new GXLGraphConverterImpl();
        final GXLDocument gxlDocument = glxConverter.convertToDocument(g);
        try {
            gxlDocument.write(new File("GXLDocument.gxl"));
        } catch (IOException ioe) {
            System.out.println("Error while writing to file: " + ioe);
        }
    }
}
