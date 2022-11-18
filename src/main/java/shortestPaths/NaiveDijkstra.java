package shortestPaths;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Naive Dijkstra
 * @author Hakim Ouatar
 */
public class NaiveDijkstra {
    // La hashmap qui permet de stocker des éléments triés en fonction de leur priorité
    HashMap<Node, Double> f ;//file à priorité
    Graph g ;
    Node source;

    public NaiveDijkstra(Graph g  ,Node source) {
        this.g = g;
        this.source = source;

    }


    public void init() {
        f = new HashMap<>();
        for (Node  n : this.g ) {
            f.put(n,Double.POSITIVE_INFINITY);
        }

        //La distance de node de départ a 0
        f.replace(this.source,0.0);

    }


    private boolean emptyFile() {
       return f.isEmpty();
    }

    private void add(Node toAdd , double dist){
        this.f.put(toAdd,dist);
    }

    private Node extractMin(ArrayList<Node> traiter){
          double minDistance = Double.POSITIVE_INFINITY;
          Node node = null;

          for(Node n : this.f.keySet()){
              if(!traiter.contains(n)){
                  Double tmp = this.f.get(n);
                  if(tmp < minDistance){
                      minDistance = tmp;
                      node = n;

                  }
              }
          }
          return  node;
    }
    public HashMap<Node,Double> getF() {
        return this.f;
    }
    public HashMap<Node,Double> compute() {
        ArrayList<Node> NodeTraites = new ArrayList<>();
        Node currentNode  = this.source;
        Iterator<Node> it ;
        while (currentNode != null){
            NodeTraites.add(currentNode);
            it =currentNode.neighborNodes().iterator();
            while (it.hasNext()){
                Node opositeNode = it.next();
                this.f.replace(opositeNode,Math.min(this.f.get(opositeNode) , this.f.get(currentNode) + (Integer) currentNode.getEdgeBetween(opositeNode).getAttribute("Poids")));
            }
           currentNode  = extractMin(NodeTraites);
        }
        return this.f;
    }

}


