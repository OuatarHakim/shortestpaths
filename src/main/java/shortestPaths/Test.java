package shortestPaths;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Test {
    public static void main(String[] args) {

        Graph g = genereRandomGraph(100, 4.0);
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

        graph.edges().forEach(e -> e.setAttribute("Poids", (double) (Math.random() * 20) + 1));
        return graph;
    }
}
