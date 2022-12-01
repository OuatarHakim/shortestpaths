package shortestPaths;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class Comparaison {
    private  static final int NB_TESTS = 1;
    private static final int NB_NOEUDS  = 10000;
    private static final double DEGREE_MOYEN = 3.0;
    public static void main(String[] args)  {

      /*-------------------Exemple d'un graphe ---------------*/
        //Essayez-le avec des exemples simples en prenant soin de générer des poids aléatoires sur les arêtes.
        //createSimpleGraphe(5,2.0);


        /*-------------------------------------Graphe de même taille  -------------------------*/
        System.out.println("**************** Test sur des Graphes de même taille ************  \n");
        Graph g = genereRandomGraph(NB_NOEUDS, DEGREE_MOYEN);
        Node source  = g.getNode(0);
        System.out.println("    Execution Dijkstra NAIVE  ");

        //stokcer les temps d'execution sur des graphes de même  taille
        long[] tempsNaive = new long[NB_TESTS];
        long[] tempsGraphStream = new long[NB_TESTS];


        //Naive dijkstra
        //tester n fois
        NaiveDijkstra naiveDijkstra = new NaiveDijkstra(g, source);
        for (int i = 0 ; i < NB_TESTS ; i++) {

            long start = System.currentTimeMillis();
            naiveDijkstra.computeDijkstra();
            tempsNaive[i] = System.currentTimeMillis() - start;

        }

        System.out.println("     Execution Dijkstra graphStream  ");

        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");
        dijkstra.init(g);
        dijkstra.setSource(source);

        for (int i = 0 ; i < NB_TESTS ; i++){
            long start = System.currentTimeMillis();
            dijkstra.compute();
            tempsGraphStream[i] = System.currentTimeMillis() - start;
        }
        // afficher le resultat
        compaison(tempsNaive ,tempsGraphStream);


        /*-------------------------------------Graphe de taille déférente -------------------------*/
         System.out.println("******************** Test sur des Graphes de taille déférents ********************* \n");

        // stokcer les temps d'execution sur des graphes de déférents taille
        long[] tempsNaiveNbNoeuds = new long[NB_NOEUDS];
        long[] tempsGraphStreamNbNoeuds = new long[NB_NOEUDS];

        // et maintenant on cherche les temps execution sur nombre de noeuds déférent
        for (int i = 100 ; i < NB_NOEUDS ; i += 100){

            Graph g1 = genereRandomGraph(i, DEGREE_MOYEN);
            Node source1  = g1.getNode(0);
            NaiveDijkstra naiveDijkstra1 = new NaiveDijkstra(g1, source1);
            long start = System.currentTimeMillis();
            naiveDijkstra1.computeDijkstra();
            tempsNaiveNbNoeuds[i] = System.currentTimeMillis() - start;

            Dijkstra dijkstra1= new Dijkstra(Dijkstra.Element.EDGE, null, "length");
            dijkstra1.init(g1);
            dijkstra1.setSource(source1);
            start = System.currentTimeMillis();
            dijkstra1.compute();
            tempsGraphStreamNbNoeuds[i] = System.currentTimeMillis() - start;
        }



        //creer les données pour gnuPlot
        writeDataFile("./data/comparatif/naive.dat",tempsNaiveNbNoeuds);
        writeDataFile("./data/comparatif/graphStream.dat",tempsGraphStreamNbNoeuds);

        // creer le fichier gnuplot et execution
        writeGnuplotFile("./data/comparatif/comparatif.gnuplot","naive.dat","graphStream.dat" , "comparatif.png","Comparaison");
        ExcuteCommande("gnuplot", "comparatif.gnuplot", "./data/comparatif/");

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

    /*
    Afficher le graphe  afin de tester generator random graph et voir quelques exemples
     */
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

    private static void compaison(long[] tempsNaive , long[] tempsGraphStream ){

        System.out.println("Naive dijkstra :\n\ttemps :");
        System.out.println("\t\tMinimal: " + Arrays.stream(tempsNaive).min().getAsLong() + "ms.");
        System.out.println("\t\tMaximal: " + Arrays.stream(tempsNaive).max().getAsLong() + "ms.");
        System.out.println("\t\tMoyenne: " + Arrays.stream(tempsNaive).average().getAsDouble() + "ms.");

        System.out.println("GraphStream dijkstra :\n\ttemps :");
        System.out.println("\t\tMinimal: " + Arrays.stream(tempsGraphStream).min().getAsLong() + "ms.");
        System.out.println("\t\tMaximal: " + Arrays.stream(tempsGraphStream).max().getAsLong() + "ms.");
        System.out.println("\t\tMoyenne: " + Arrays.stream(tempsGraphStream).average().getAsDouble() + "ms.");
    }

    private static void writeDataFile(String filename, long[] data) {

        try {
            FileWriter fw = new FileWriter(filename);

            for (int i = 100; i < data.length; i+=100)
                 fw.write(String.format(Locale.US, "%6d%20.8f%n", i, (double) data[i]));

            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeGnuplotFile(String path,String nameData1,String nameData2 ,String GraphPngName ,String title){
        try {
            FileWriter fw = new FileWriter(path);
            fw.write("set terminal png  \n"

                    + "set title \""+title+"\"\n"
                    + "set xlabel 'n' \n"
                    + "set ylabel 'ms' \n"
                    + "set output '"+GraphPngName+"' \n"
                    +"set key top left\n"
                    +"plot \""+nameData2+"\" t \" Temps d'exécution  GraphStream\" with linespoints ls 1,\""+nameData1+"\" t\"Temps d'exécution  naive\" with linespoints ls 2 ;"

            );
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void ExcuteCommande(String commande, String name, String pathDir) {
        try {
            // Execute command
            ProcessBuilder p = new ProcessBuilder(commande, name);
            p.directory(new File(pathDir));
            p.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void test(){
        for(int i = 0 ;  i < NB_NOEUDS ; i += 100){
            genereRandomGraph(i,DEGREE_MOYEN);
            long start = System.currentTimeMillis();


        }
    }
}
