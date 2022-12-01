package shortestPaths;


import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Class Naive Dijkstra
 * @author Hakim Ouatar
 */
public class NaiveDijkstra {
    // La hashmap qui permet de stocker des éléments triés en fonction de leur priorité
    HashMap<Node, Double> f;//file à priorité
    Graph g;
    Node source;
    List<Node> traite;

    public NaiveDijkstra(Graph g, Node s) {
        this.g = g;
        this.source = s;
        this.f = null;
        this.traite = new ArrayList<>();

    }


    public void init() {
        this.f = new HashMap<>();
        for (Node n : this.g) {
            f.put(n, Double.POSITIVE_INFINITY);
        }
        //La distance de node de départ a 0
        f.replace(this.source, 0.0);


    }



    /* 1 ere algo    */
    public void computeDijkstra() {
        //initialisation sur chaque sommet disance = infinie , parent = null
        this.g.forEach(v -> {
            v.setAttribute("Parent", (Object) null);
            v.setAttribute("Distance", Double.POSITIVE_INFINITY);
        });

        //initialisation de la source
        g.getNode(this.source.getId()).setAttribute("Distance", 0.0);
        g.getNode(this.source.getId()).setAttribute("Parent", this.source);


        //creation de file priorité
        this.f = new HashMap<>();
        f.put(this.source, 0.0);
        Node current;

        //  Iterator<Node> i ;
        Iterator<? extends Node> i;

        while (!f.isEmpty()) {
            // récupère l’élément de priorité minimum
            current = extrMin();
            i = current.neighborNodes().iterator();
            // on caclule sa distance vers ses voisins
            while (i.hasNext()) {

                Node next = i.next();
                Double distance = (Double) current.getEdgeBetween(next).getAttribute("Poids") + (Double) current.getAttribute("Distance");

                // ajoute l’élément e de priorité p ou modifie sa priorité.
                if ((Double) next.getAttribute("Distance") > distance) {
                    next.setAttribute("Distance", (Double) distance);
                    next.setAttribute("Parent", current.getId());
                    addOrReplace(next, distance);
                }
            }
        }

    }

    /**
     * ajoute l’élément e de priorité p ou modifie sa priorité.
     *
     * @param toAdd
     * @param dist
     */
    private void addOrReplace(Node toAdd, double dist) {
        if (!this.f.containsKey(toAdd)) {
            this.f.put(toAdd, dist);
        } else {
            this.f.replace(toAdd, dist);
        }

    }

    /**
     * récupère l’élément de priorité minimum
     *
     * @return node minimum
     */
    private Node extrMin() {
        double minDistance = Double.POSITIVE_INFINITY;
        Node node = null;
        //on parcours notre file priorité quand on a modifier au préalable
        for (Node n : this.f.keySet()) {
            //si le node se trouve pas dans les nodes traiter on le récupère

            Double tmp = this.f.get(n);
            if (tmp < minDistance) {
                minDistance = tmp;
                node = n;


            }
        }
        this.traite.add(node);
        this.f.remove(node);
        return node;
    }


    /**
     * @param toNode
     * @return liste des nodes de source  à toNode
     */
    public List<Node> getPath(Node toNode) {

        Node parent = this.g.getNode("" + toNode.getAttribute("Parent"));
        ArrayList<Node> path = new ArrayList<>();
        path.add(toNode);

        path.add(parent);
        toNode.getEdgeBetween(parent).setAttribute("ui.style", "fill-color: red;");


        while (!parent.equals(this.source)) {
            parent.getEdgeBetween(this.g.getNode("" + parent.getAttribute("Parent"))).setAttribute("ui.style", "fill-color: red;");
            parent = this.g.getNode("" + parent.getAttribute("Parent"));

            path.add(parent);
        }

        return path;


    }


    /**
     * afficher  les plus courts chemins de source vers les autres noeuds
     */
    public void getAllPaths() {
        g.nodes().forEach(n -> {
                    if (!n.equals(this.source)) {
                        System.out.println(getPath(n));

                    }
                }
        );

    }
    /*Suite deuxime algo */
/*
    public void computeAlgo() {
        this.g.forEach(n-> {
            n.setAttribute("Distance", Double.POSITIVE_INFINITY);
        });

        //initialisation de la source
        g.getNode(this.source.getId()).setAttribute("Distance", 0.0);
        //ensemble  des nodes traités T
        ArrayList<Node> NodeTraites = new ArrayList<>();
        // on commence par la source
        Node currentNode = this.source;
        Iterator<Node> it;
        //tanquant on a node à traiter
        // on choisi un sommet non traité
        while (currentNode != null) {
            currentNode = argmin(NodeTraites);

            NodeTraites.add(currentNode);
            it = currentNode.neighborNodes().iterator();
            while (it.hasNext()) {

                Node opositeNode = it.next();
                this.g.getNode(opositeNode.getId()).setAttribute("Distance",Math.min((Double)this.g.getNode(opositeNode.getId()).getAttribute("Distance") , (Double) opositeNode.getAttribute("Distance") + (Double) this.g.getNode(currentNode.getId()).getEdgeBetween(this.g.getNode(opositeNode.getId())).getAttribute("Poids")));

            }
        }

    }

    //recupere le noeud de distance min qui n 'appartient  a T
    private Node argmin(List<Node> T){
        Node node = null;
        double distanceMin = Double.POSITIVE_INFINITY;

        //on parcours notre GRAPHE on cherche le noeud de distance minimum
        for(Node n:this.g) {
            if (!T.contains(n)) {
                Double tmp = (Double) n.getAttribute("Distance");
                if (tmp < distanceMin) {
                    distanceMin = tmp;
                    node = n;
                }
            }
        }

        return node;
    }
*/

}