package john;

import java.util.*;
public class JohnsonsAlgorithm 
{
    private int SOURCE_NODE;
    private int totalNodes;
    private int augMatrix[][];
    private int temp[];
    private BellmanFord bell;
    private DijkstraShortesPath dijkstra;
    private int[][] allPair;
 
    public static final int MAX_VALUE = 99999;
 
    public JohnsonsAlgorithm(int totalNodes){
        this.totalNodes = totalNodes;
        augMatrix = new int[totalNodes + 2][totalNodes + 2];
        SOURCE_NODE = totalNodes + 1;
        temp = new int[totalNodes + 2];
        bell = new BellmanFord(totalNodes + 1);
        dijkstra = new DijkstraShortesPath(totalNodes);
        allPair = new int[totalNodes + 1][totalNodes + 1];
    }
 
    public void johnsonsAlgorithms(int adjMatrix[][]){
        augGraph(adjMatrix);
        bell.BellmanFordEvaluation(SOURCE_NODE, augMatrix);
        temp = bell.getDist();
        int reGraph[][] = reweightGraph(adjMatrix);
        findPath(reGraph);
        System.out.println("All pair shortest path is");
        System.out.println();
        for (int i = 1; i<= totalNodes; i=i+1){
            System.out.print("\t"+i);
        }
        System.out.println();
        //Find the shortest path
        for (int src = 1; src <= totalNodes; src=src+1){
            System.out.print( src +"\t" );
            for (int dest = 1; dest <= totalNodes; dest=dest+1){
                System.out.print(allPair[src][dest]+ "\t");
            }
            System.out.println();
        }
    }

    public void findPath(int[][] reGraph) {
        for (int src = 1; src <= totalNodes; src=src+1){
            dijkstra.dijkstraShortestPath(src, reGraph);
            int[] result = dijkstra.getDist();
            for (int dest = 1; dest <= totalNodes; dest=dest+1){
                allPair[src][dest] = result[dest]
                        + temp[dest] - temp[src];
            }
        }
    }
 
    private void augGraph(int adjMatrix[][]){
        for (int src = 1; src <= totalNodes; src=src+1){
            for (int dest = 1; dest <= totalNodes; dest=dest+1){ 
                augMatrix[src][dest] = adjMatrix[src][dest];
            }
        }
        for (int dest = 1; dest <= totalNodes; dest=dest+1){
            augMatrix[SOURCE_NODE][dest] = 0;
        }
    }
	// Nodes with value 0 will be turning out to infinity
    private int[][] reweightGraph(int adjMatrix[][]){
        int[][] result = new int[totalNodes + 1][totalNodes + 1];
        for (int src = 1; src <= totalNodes; src=src+1){
            for (int dest = 1; dest <= totalNodes; dest=dest+1){
                result[src][dest] = adjMatrix[src][dest]
                       + temp[src] - temp[dest];
            }
        }
        return result;
    }
 
    public static void main(String args[]){
        int adjMatrix[][];
        int totalVertices;
        Scanner scan = new Scanner(System.in);
            System.out.println("Enter the number of vertices:");
            System.out.println("=============================");
            totalVertices = scan.nextInt();
            adjMatrix = new int[totalVertices + 1][totalVertices + 1];
            System.out.println("Enter the Matrix for the graph:");
            System.out.println("===============================");
            for (int i = 1; i <= totalVertices; i=i+1){
                for (int j = 1; j <= totalVertices; j=j+1){
                    adjMatrix[i][j] = scan.nextInt();
                    if (i == j){
                        adjMatrix[i][j] = 0;
                        continue;
                    }
                    if (adjMatrix[i][j] == 0){
                        adjMatrix[i][j] = MAX_VALUE;
                    }
                }
            }
            System.out.println("=============================");
            JohnsonsAlgorithm johnsonsAlgo = new JohnsonsAlgorithm(totalVertices);
            long previous = System.nanoTime();
            johnsonsAlgo.johnsonsAlgorithms(adjMatrix);
            long currentTime = System.nanoTime();
            System.out.println("=================");
            System.out.println("Execution time is");
            System.out.println("=================");
            long elapsed=(currentTime-previous)/1000;
            System.out.println(elapsed);
        scan.close();
    }
}
 
class BellmanFord 
{
    private int dist[];
    private int vertices;
    public static final int MAX_VALUE = 999;
    public BellmanFord(int vertices)  
    {
        this.vertices = vertices;
        dist = new int[vertices + 1];
    }
 
    public void BellmanFordEvaluation(int src, int adjMatrix[][]){
        for (int node = 1; node <= vertices; node=node+1){
            dist[node] = MAX_VALUE;
        }
        dist[src] = 0;
        for (int node = 1; node <= vertices - 1; node=node+1){
            for (int srcnode = 1; srcnode <= vertices; srcnode=srcnode+1){
                for (int destnode = 1; destnode <= vertices; destnode=destnode+1){
                    if (adjMatrix[srcnode][destnode] != MAX_VALUE){
                        if (dist[destnode] > dist[srcnode]+ adjMatrix[srcnode][destnode]){
                            dist[destnode] = dist[srcnode]+ adjMatrix[srcnode][destnode];
                        }
                    }
                }
            }
        }
        negativeEdge(adjMatrix);
    }
	//calculating the negative edges
    public void negativeEdge(int[][] adjMatrix) {
        for (int srcnode = 1; srcnode <= vertices; srcnode=srcnode+1){
            for (int destnode = 1; destnode <= vertices; destnode=destnode+1){
                if (adjMatrix[srcnode][destnode] != MAX_VALUE){
                    if (dist[destnode] > dist[srcnode]+ adjMatrix[srcnode][destnode])
                        System.out.println("The Graph contains negative egde cycle");
                }
            }
        }
    }
     public int[] getDist(){
        return dist;
    }
}
 
class DijkstraShortesPath{
    private boolean set[];
    private boolean unset[];
    private int dist[];
    private int adjMatrix[][];
    private int vertices;
 
    public static final int MAX_VALUE = 999;
 
    public DijkstraShortesPath(int vertices){
        this.vertices = vertices;
    }
 
    public void dijkstraShortestPath(int src, int adjMatrix[][]){
        this.set = new boolean[vertices + 1];
        this.unset = new boolean[vertices + 1];
        this.dist = new int[vertices + 1];
        this.adjMatrix = new int[vertices + 1][vertices + 1];
        int evalNode;
        for (int vertex = 1; vertex <= vertices; vertex=vertex+1){
            dist[vertex] = MAX_VALUE;
        }
        for (int srcvertex = 1; srcvertex <= vertices; srcvertex=srcvertex+1){
            for (int destvertex = 1; destvertex <= vertices; destvertex=destvertex+1){
                this.adjMatrix[srcvertex][destvertex] 
                     = adjMatrix[srcvertex][destvertex];
            }
        }
        unset[src] = true;
        dist[src] = 0;
        while (getUnsetCount(unset) != 0){
            evalNode = minDistance(unset);
            unset[evalNode] = false;
            set[evalNode] = true;
            evalNeighbours(evalNode);
        }
    } 
 
    public int getUnsetCount(boolean unset[]){
        int count = 0;
        for (int vertex = 1; vertex <= vertices; vertex=vertex+1){
            if (unset[vertex] == true){
                count++;
            }
        }
        return count;
    }
 
    public int minDistance(boolean unset[]){
        int min = MAX_VALUE;
        int node = 0;
        for (int vertex = 1; vertex <= vertices; vertex=vertex+1){
            if (unset[vertex] == true && dist[vertex] < min){
                node = vertex;
                min = dist[vertex];
            }
        }
        return node;
    }
 
    public void evalNeighbours(int evaluationNode){
        int edgeDistance = -1;
        int newDistance = -1;
 
        for (int destNode = 1; destNode <= vertices; destNode++){
            if (set[destNode] == false){
                if (adjMatrix[evaluationNode][destNode] != MAX_VALUE){
                    edgeDistance = adjMatrix[evaluationNode][destNode];
                    newDistance = dist[evaluationNode] + edgeDistance;
                    if (newDistance < dist[destNode]){
                        dist[destNode] = newDistance;
                    }
                    unset[destNode] = true;
                }
            }
        }
    }
    public int[] getDist(){
        return dist;
    }
}
