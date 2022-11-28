package shortestPaths;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;

import java.io.IOException;
public class Test {
    public static void main(String[] args) throws IOException, GraphParseException {
        //Naive dijkstra
        Graph g = genereRandomGraph(4, 2.0);



        System.out.println("Dijkstra NAIVE : ");

        long temps = System.currentTimeMillis();
        NaiveDijkstra naiveDijkstra = new NaiveDijkstra(g,g.getNode(0));

        naiveDijkstra.computeDijkstra();
        System.out.println(System.currentTimeMillis() - temps + " ms pour " + g.getNodeCount() + " sommet(s)");
        //dijkstra graphStream
        System.setProperty("org.graphstream.ui", "swing");
        g.setAttribute("ui.quality");
        g.setAttribute("ui.antialias");
        g.setAttribute("ui.stylesheet" ,"url('data/style.css')");

        g.nodes().forEach(n ->{n.setAttribute("ui.label", n.getId());

      });
        g.edges().forEach(e -> e.setAttribute("ui.label" ,e.getAttribute("Poids")));
        g.display();



    }

    private static Graph genereRandomGraph(int n, double averageDegree) {
        Graph graph = new SingleGraph("Random graphe");
        RandomGenerator gen = new RandomGenerator(averageDegree);
        gen.setDirectedEdges(true, true);
        gen.addSink(graph);
        gen.addEdgeAttribute("Poids");
        gen.begin();
        for (int i = 0; i < n; i++)
            gen.nextEvents();
        gen.end();

        graph.edges().forEach(e -> e.setAttribute("Poids", (double) Math.abs(Math.random() * 20) + 1));

        return graph;
    }
}
