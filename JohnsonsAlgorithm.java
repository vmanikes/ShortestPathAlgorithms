/*
 * @author Manikeswaram Chaitanya
 * @created on: 21 November 2015 
 * @time:9:00 AM
 */
package shortestpath;

import java.util.InputMismatchException;
import java.util.Scanner;
public class JohnsonsAlgorithm{
    private int srcNode, totalNodes;
    private int augMatrix[][];
    private int temp[];
    private BellmanFord bellman;
    private DijkstraShortesPath dijkstra;
    private int[][] allPair;
    public static final int MAX_VALUE = 999;
    public JohnsonsAlgorithm(int totalNodes){
        this.totalNodes = totalNodes;
        augMatrix = new int[totalNodes + 2][totalNodes + 2];
        srcNode = totalNodes + 1;
        temp = new int[totalNodes + 2];
        bellman = new BellmanFord(totalNodes + 1);
        dijkstra = new DijkstraShortesPath(totalNodes);
        allPair= new int[totalNodes + 1][totalNodes + 1];
    }
 
    public void johnsonsAlgorithm(int adjMatrix[][]){
        computeGraph(adjMatrix);
        bellman.bellmanFordPath(srcNode, augMatrix);
        temp = bellman.getDistance(); 
        int reweightedGraph[][] = reweightGraph(adjMatrix);
        for (int i = 1; i <= totalNodes; i=i+1){
            for (int j = 1; j <= totalNodes; j=j+1){
                System.out.print(reweightedGraph[i][j] + "\t");
            }
            System.out.println();
        }
        for (int src = 1; src <= totalNodes; src=src+1){
            dijkstra.dijkstraShortest(src, reweightedGraph);
            int[] result = dijkstra.getDistances();
            for (int dest= 1; dest<= totalNodes; dest=dest+1){
                allPair[src][dest]=result[dest]+temp[dest]-temp[src];
            }
        }
        System.out.println();
        for (int i = 1; i<= totalNodes; i=i+1){
            System.out.print("\t"+i);
        }
        System.out.println();
        for (int src = 1; src <= totalNodes; src=src+1){
            System.out.print( src +"\t" );
            for (int dest= 1; dest<= totalNodes; dest=dest+1){
                System.out.print(allPair[src][dest]+ "\t");
            }
            System.out.println();
        }
    }
 
    private void computeGraph(int adjMatrix[][]){
        for (int src = 1; src <= totalNodes; src=src+1){
            for (int dest= 1; dest<= totalNodes; dest=dest+1){ 
                augMatrix[src][dest] = adjMatrix[src][dest];
            }
        }
        for (int dest = 1; dest <= totalNodes; dest=dest+1){
            augMatrix[srcNode][dest] = 0;
        }
    }
 
    private int[][] reweightGraph(int adjMatrix[][]){
        int[][] result = new int[totalNodes + 1][totalNodes + 1];
        for (int src = 1; src <= totalNodes; src=src+1){
            for (int dest = 1; dest <= totalNodes; dest=dest+1){
                result[src][dest]=adjMatrix[src][dest]+temp[src]-temp[dest];
            }
        }
        return result;
    }
 
    public static void main(String args[]){
        int adjMatrix[][];
        int vertices;
        Scanner scan = new Scanner(System.in);
        try{
            System.out.println("Input the number of vertices:");
            System.out.println("=============================");
            vertices = scan.nextInt();
            adjMatrix = new int[vertices + 1][vertices + 1];
            System.out.println("Input the matrix for the graph:");
            System.out.println("===============================");
            for (int i = 1; i <= vertices; i=i+1){
                for (int j = 1; j <= vertices; j=j+1){
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
 
            JohnsonsAlgorithm JA = new JohnsonsAlgorithm(vertices);
            JA.johnsonsAlgorithm(adjMatrix);
        } catch (InputMismatchException wrongInput)
        {
            System.out.println("User has entered an incorrect input format");
        }
        scan.close();
    }
}
 
class BellmanFord{
    private int dist[];
    private int totalVertices;
    public static final int MAX_VALUE = 9999;
    public BellmanFord(int totalVertices){
        this.totalVertices = totalVertices;
        dist = new int[totalVertices + 1];
    }
 
    public void bellmanFordPath(int src, int adjMatrix[][]){
        for (int node = 1; node <= totalVertices; node=node+1){
            dist[node] = MAX_VALUE;
        }
        dist[src] = 0;
        for (int node = 1; node <= totalVertices - 1; node=node+1){
            for (int srcNode = 1; srcNode <= totalVertices; srcNode=srcNode+1){
                for (int destNode = 1; destNode <= totalVertices; destNode=destNode+1){
                    if (adjMatrix[srcNode][destNode] != MAX_VALUE){
                        if (dist[destNode] > dist[srcNode]+ adjMatrix[srcNode][destNode]){
                            dist[destNode] = dist[srcNode]+adjMatrix[srcNode][destNode];
                        }
                    }
                }
            }
        }
        for (int srcNode = 1; srcNode <= totalVertices; srcNode=srcNode+1){
            for (int destNode = 1; destNode <= totalVertices; destNode=destNode+1){
                if (adjMatrix[srcNode][destNode] != MAX_VALUE){
                    if (dist[destNode] > dist[srcNode]+ adjMatrix[srcNode][destNode])
                        System.out.println("Graph has negative egde cycle");
	        }
            }
        }
    }
 
    public int[] getDistance(){
        return dist;
    }
}
 
class DijkstraShortesPath{
    private boolean set[];
    private boolean unset[];
    private int dist[];
    private int adjMatrix[][];
    private int totalVertices;
    public static final int MAX_VALUE = 999;
 
    public DijkstraShortesPath(int totalVertices){
        this.totalVertices = totalVertices;
    }
 
    public void dijkstraShortest(int src, int adjMatrix[][]){
        this.set = new boolean[totalVertices + 1];
        this.unset = new boolean[totalVertices + 1];
        this.dist = new int[totalVertices + 1];
        this.adjMatrix = new int[totalVertices + 1][totalVertices + 1];
        int evalNode;
        for (int vertex = 1; vertex <= totalVertices; vertex=vertex+1){
            dist[vertex] = MAX_VALUE;
        }
 
        for (int srcVertex = 1; srcVertex <= totalVertices; srcVertex=srcVertex+1){
            for (int destVertex = 1; destVertex <= totalVertices; destVertex=srcVertex+1){
                this.adjMatrix[srcVertex][destVertex]= adjMatrix[srcVertex][destVertex];
            }
        }
        unset[src] = true;
        dist[src] = 0;
        while (getUnsetCount(unset)!= 0){
            evalNode = minimumDistance(unset);
            unset[evalNode] = false;
            set[evalNode] = true;
            checkNeighbours(evalNode);
        }
    } 
 
    public int getUnsetCount(boolean unset[]){
        int count = 0;
        for (int vertex = 1; vertex <= totalVertices; vertex=vertex+1){
            if (unset[vertex] == true){
                count++;
            }
        }
        return count;
    }
 
    public int minimumDistance(boolean unset[]){
        int min = MAX_VALUE;
        int node = 0;
        for (int vertex = 1; vertex <= totalVertices; vertex=vertex+1){
            if (unset[vertex] == true && dist[vertex] < min){
                node = vertex;
                min = dist[vertex];
            }
        }
        return node;
    }
 
    public void checkNeighbours(int evalNode){
        int edgeDist = -1;
        int newDist = -1; 
        for (int destNode = 1; destNode <= totalVertices; destNode=destNode+1){
            if (set[destNode] == false){
                if (adjMatrix[evalNode][destNode] != MAX_VALUE){
                    edgeDist = adjMatrix[evalNode][destNode];
                    newDist = dist[evalNode] + edgeDist;
                    if (newDist < dist[destNode]){
                        dist[destNode] = newDist;
                    }
                    unset[destNode] = true;
                }
            }
        }
    }
 
    public int[] getDistances(){
        return dist;
    }
}
