/*
 * @author Manikeswaram Chaitanya
 * @created on: 26 November 2015 
 * @time:12:00 PM
 */
import java.util.Scanner;
 
public class FloydWarshall
{
    private int distanceMatrix[][];
    private int vertices;
    public static final int INF = 99999;
    
    public FloydWarshall(int vertices){
        distanceMatrix = new int[vertices + 1][vertices + 1];
        this.vertices = vertices;
    }
 
    public void floydWarshallAlgo(int adjMatrix[][]){
        for (int source = 1; source <= vertices; source=source+1){
            for (int destination = 1; destination <= vertices; destination++) {
                distanceMatrix[source][destination] = adjMatrix[source][destination];
            }
        }
 
        for (int mid = 1; mid <= vertices; mid=mid+1){
            for (int src = 1; src <= vertices; src=src+1){
                for (int dest= 1; dest <= vertices; dest=dest+1){
                    if (distanceMatrix[src][mid] + distanceMatrix[mid][dest]
                         < distanceMatrix[src][dest])
                        distanceMatrix[src][dest] = distanceMatrix[src][mid] + distanceMatrix[mid][dest];
                }
            }
        }
        finalGraph();
    }

    public void finalGraph() {
        for (int source = 1; source <= vertices; source=source+1)
            System.out.print("\t" + source);
 
        System.out.println();
        for (int source = 1; source <= vertices; source=source+1){
            System.out.print(source + "\t");
            for (int destination = 1; destination <= vertices; destination=destination+1){
                System.out.print(distanceMatrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }
 
    public static void main(String... arg)
    {
        int finalMatrix[][];
        int totalVertices;
        Scanner input = new Scanner(System.in);
        System.out.println("The number of vertices in the graph:");
        System.out.println("====================================");
        totalVertices = input.nextInt();
        finalMatrix = new int[totalVertices + 1][totalVertices + 1];
        System.out.println("\n");
        System.out.println("Edges for the graph are: ");
        System.out.println("========================");
        /*Please enter the matrix in n*n format*/
        /*Code to input elements*/
        for (int src = 1; src <= totalVertices; src=src+1){
            for (int dest = 1; dest <= totalVertices; dest=dest+1){
                finalMatrix[src][dest] = input.nextInt();
                if (src == dest){
                    finalMatrix[src][dest] = 0;
                    continue;
                }
                if (finalMatrix[src][dest] == 0){
                    finalMatrix[src][dest] = INF;
                }
            }
        }
        System.out.println("\n");
        System.out.println("Final all pair shortest path is:");
        System.out.println("================================");
        FloydWarshall fw = new FloydWarshall(totalVertices);
        fw.floydWarshallAlgo(finalMatrix);
        input.close();
    }
}
