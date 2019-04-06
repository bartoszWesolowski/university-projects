package graph;

import graph.mst.MST;
import graph.mst.NaiveMst;

/**
 * Created by bartosz.wesolowski on 07.04.2017.
 */
public class MstTest {

    public static void main(String[] args) {
        final GraphReader graphReader = new GraphReader();
        final Graph source = graphReader.readGraph();
        System.out.println(source.toString());
        System.out.println(source.rawMatrix());
        final MST mst = new NaiveMst(source);
        //final MST mst = new NaiveMst(source);
        System.out.println(mst);
    }
}
