package shortestPaths;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;

import java.io.IOException;
public class Comparaison {
    public static void main(String[] args)  {
        //Essayez-le avec des exemples simples en prenant soin de générer des poids aléatoires sur les arêtes.

        //createSimpleGraphe(5,2.0);

        //Naive dijkstra

        Graph g = genereRandomGraph(500, 2.0);
        System.out.println("Dijkstra NAIVE : ");

        long temps = System.currentTimeMillis();
        NaiveDijkstra naiveDijkstra = new NaiveDijkstra(g,g.getNode(0));
        naiveDijkstra.computeDijkstra();
        System.out.println(System.currentTimeMillis() - temps + " ms pour " + g.getNodeCount() + " sommet(s)");

        System.out.println("Dijkstra graphStream : ");
        temps = System.currentTimeMillis();
        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");

        // Compute the shortest paths in g from A to all nodes
        dijkstra.init(g);
        dijkstra.setSource(g.getNode(0));
        dijkstra.compute();
        System.out.println(System.currentTimeMillis() - temps + " ms pour " + g.getNodeCount() + " sommet(s)");








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

    private static void createSimpleGraphe(int nombreNodes , double averageDegree){
        Graph g = genereRandomGraph(nombreNodes, averageDegree);

        System.setProperty("org.graphstream.ui", "swing");
        g.setAttribute("ui.quality");
        g.setAttribute("ui.antialias");
        g.setAttribute("ui.stylesheet" ,"url('data/style.css')");
        g.nodes().forEach( n -> n.setAttribute("ui.label", n.getId()));
        g.edges().forEach(e -> e.setAttribute("ui.label", " " + (Double) e.getNumber("Poids")));
        g.display(true);
    }

    private static void compaison(){
        
    }
}
