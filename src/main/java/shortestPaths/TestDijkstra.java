package shortestPaths;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;

public class TestDijkstra {
    public static void main(String[] args){
        // test Dijkstra naive
        Graph graph1 = new SingleGraph("test2");
        graph1.addNode("A").setAttribute("xy", 0, 1);
        graph1.addNode("B").setAttribute("xy", 1, 2);
        graph1.addNode("C").setAttribute("xy", 1, 0);
        graph1.addNode("D").setAttribute("xy", 2, 2);
        graph1.addNode("E").setAttribute("xy", 2, 0);

        graph1.addEdge("AB", "A", "B",true ).setAttribute("Poids", 4.0);
        graph1.addEdge("AC", "A", "C",true).setAttribute("Poids", 3.0);
        graph1.addEdge("BD", "B", "D",true).setAttribute("Poids", 2.0);
        graph1.addEdge("BE", "B", "E",true).setAttribute("Poids", 3.0);
        graph1.addEdge("BC", "B", "C",true).setAttribute("Poids", 3.0);
        graph1.addEdge("CD", "C", "D",true).setAttribute("Poids", 4.0);
        graph1.addEdge("CE", "C", "E",true).setAttribute("Poids", 5.0);
        graph1.addEdge("CB", "C", "B",true).setAttribute("Poids", 1.0);
        graph1.addEdge("ED", "E", "D",true).setAttribute("Poids", 1.0);




        System.setProperty("org.graphstream.ui", "swing");
        graph1.setAttribute("ui.quality");
        graph1.setAttribute("ui.antialias");
        graph1.setAttribute("ui.stylesheet" ,"url('data/style.css')");
        graph1.nodes().forEach(n -> n.setAttribute("ui.label", n.getId()));
        graph1.edges().forEach(e -> e.setAttribute("ui.label", " " + (Double) e.getNumber("Poids")));

        System.out.println("Dijkstra NAIVE : ");

         long temps = System.currentTimeMillis();
        NaiveDijkstra naiveDijkstra1 = new NaiveDijkstra(graph1,graph1.getNode("A"));

        naiveDijkstra1.computeDijkstra();
        graph1.nodes().forEach(n -> n.setAttribute("ui.label", n.getId() +"  ; "+n.getAttribute("Parent")+", " +n.getAttribute("Distance")));


        System.out.println(System.currentTimeMillis() - temps + " ms pour " + graph1.getNodeCount() + " sommet(s)");
        List<Node> AD  = naiveDijkstra1.getPath(graph1.getNode("D"));
        System.out.println(AD);
        naiveDijkstra1.getAllPaths();
        graph1.display(false);



    }
}






















