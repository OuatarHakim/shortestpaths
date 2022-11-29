# TP 3 Plus court chemain

### Introduction :
Dans ce tp , on  doit impléementer une version naiver de l'algorithme Dijkstra
,afin de tester son efficacité on doit procéder à une campagne de test sur cet algorithme et le comparer à des tests effectués sur l’algorithme Dijkstra de GraphStream .
Au premier temps on générera un graphe à l’aide de générateur de GraphStream , on lance les deux algorithmes et on mesure leur temps d'exécution sur des déférents taille  et degré de graphe .Et ensuite on répète les comparaison sur des graphes différents..
### Génération des graphes simples en utilisant random graph genrator
![Generation de graphes simples  ](data/g2.png)   

![Generation de graphes simples  ](data/g3.png)
###  Générateur GraphStream  :
### Générateur de graphe aléatoire :

GraphStream propose un générateur de graphe aléatoire , On crée un simple graphe et on rajoute  à l’aide de ce générateur
des sommets en respectant le degré moyen passé en paramètre .
```java
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
```

On a crée un RandomGenerator en lui passant un degré moyen , on lui rajoutant une attribut poids sur les
arretes et on on attribute des valeurs sur ces poids .

### Algorithme Dijkstra 

```java
Procédure Dijkstra(G,s)
v.dist ← ∞
v.parent ← null pour v ∈ V
s.dist ← 0
f.init()
f.add(s, 0)
TantQue non f.empty() Faire
u ← f.extractMin()
Pour (u, v) ∈ E Faire
Si v.dist > u.dist + wuv Alors
v.dist ← u.dist + wuv
v.parent ← u
f.add(v, v.dist)
FinSi
FinPour
FinTantQue
FinProcédure
```




### Test 
![Test algorithme DijkstraNaive ](data/2graph.png)   


