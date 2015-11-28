/*
 * @author Manikeswaram Chaitanya
 * @created on: 27 November 2015 
 * @time:12:00 PM
 */
import java.util.Scanner;
 
public class BellmanFordAlgorithm{
    private int distances[];
    private int totalVertices;
    public static final int INF= 99999;// Code for finding negative cycle edge
 
    public BellmanFordAlgorithm(int vertices){
        this.totalVertices = vertices;
        distances = new int[vertices + 1];
    }
    
    public void maxValueNode() {
        for (int node = 1; node <= totalVertices; node=node+1){
            distances[node] = INF;
        }
    }
    
    public void negativeEdgeCycle(int[][] adjMatrix) {
        for (int srcNode = 1; srcNode <= totalVertices; srcNode=srcNode+1){
            for (int destNode = 1; destNode <= totalVertices; destNode++){
                if (adjMatrix[srcNode][destNode] != INF){
                    if (distances[destNode]>distances[srcNode]+adjMatrix[srcNode][destNode])
                        System.out.println("Found a negative edge cycle.");
                }
            }
        }
    }
    
    public void costMatrix(int src,int dest,int adjMatrix[][]){
        maxValueNode();
        distances[src] = 0;
        for (int node = 1; node <= totalVertices - 1; node=node+1){
            for (int srcNode = 1; srcNode <=totalVertices; srcNode=srcNode+1){
                for (int destNode = 1; destNode <= totalVertices; destNode=destNode+1){
                    if (adjMatrix[srcNode][destNode] != INF){
                        if (distances[destNode] > distances[srcNode]+ adjMatrix[srcNode][destNode])
                            distances[destNode] = distances[srcNode] + adjMatrix[srcNode][destNode];
                    }
                }
            }
        }
        negativeEdgeCycle(adjMatrix);// Code for finding negative cycle edge
        for (int vertex = 1; vertex <= totalVertices; vertex=vertex+1){
            if (vertex == dest)
                System.out.println("Path from " + src + " to "+ vertex + " is " + distances[vertex]);           
        }
    }
  
    public void shortestPath(int src, int adjMatrix[][]){
        maxValueNode();
        distances[src] = 0;
        for (int node = 1; node <= totalVertices - 1; node=node+1){
            for (int srcNode = 1; srcNode <= totalVertices; srcNode=srcNode+1){
                for (int destNode = 1; destNode <= totalVertices; destNode=destNode+1){
                    if (adjMatrix[srcNode][destNode] != INF){
                        if (distances[destNode] > distances[srcNode] + adjMatrix[srcNode][destNode])
                            distances[destNode] = distances[srcNode]+adjMatrix[srcNode][destNode];
                    }
                }
            }
        }
        negativeEdgeCycle(adjMatrix);// Code for finding negative cycle edge        
        for (int vertex = 1; vertex <= totalVertices; vertex=vertex+1){
            System.out.println("Path from " + src + " to "+ vertex + " is " + distances[vertex]);
        }
        System.out.println("=====================");
    }
 
    public static void main(String args[]){
        int vertices = 0;
        int src,dest;
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Input the total number of vertices in the graph:");
        System.out.println("================================================");
        vertices = scanner.nextInt(); 
        int adjMatrix[][] = new int[vertices + 1][vertices + 1];
        System.out.println("Input the adjacency matrix:");
        System.out.println("===========================");
        for (int srcnode = 1; srcnode <= vertices; srcnode++){
            for (int destnode = 1; destnode <= vertices; destnode++){
                adjMatrix[srcnode][destnode] = scanner.nextInt();
 	        if (srcnode == destnode){
                    adjMatrix[srcnode][destnode] = 0;
                    continue;
                }
                if (adjMatrix[srcnode][destnode] == 0){
                    adjMatrix[srcnode][destnode] = INF;
                }
            }
        } 
        System.out.println("Source vertex:");
        System.out.println("==============");
        src= scanner.nextInt();
        System.out.println("Destination vertex:");
        System.out.println("===================");
        dest = scanner.nextInt();
        BellmanFordAlgorithm bellmanford = new BellmanFordAlgorithm(vertices);
        bellmanford.shortestPath(src, adjMatrix);
        bellmanford.costMatrix(src, dest, adjMatrix);
        scanner.close();	
    }
}
