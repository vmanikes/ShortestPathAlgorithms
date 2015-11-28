/*
 * @author Manikeswaram Chaitanya
 * @created on: 24November 2015 
 * @time:3:00 PM
 */
package shortestpath;
import java.util.*;
public class DijkstraSet{
    private int dist[];
    private Set<Integer> set;
    private Set<Integer> unset;
    private int totalNodes;
    private int adjMatrix[][];
 
    public DijkstraSet(int totalNodes){
        this.totalNodes = totalNodes;
        dist = new int[totalNodes + 1];
        set = new HashSet<Integer>();
        unset = new HashSet<Integer>();
        adjMatrix = new int[totalNodes + 1][totalNodes + 1];
    }
 
    public void dijkstraAlgorithm(int adjacencyMatrix[][], int src){
        int evalNode;
        for (int i = 1; i <= totalNodes; i=i+1)
            for (int j = 1; j <= totalNodes; j=j+1)
                adjMatrix[i][j] = adjacencyMatrix[i][j];
        for (int i = 1; i <= totalNodes; i=i+1){
            dist[i] = Integer.MAX_VALUE;
        }
        unset.add(src);
        dist[src] = 0;		
        while (!unset.isEmpty()){
            evalNode = minDistance();
            unset.remove(evalNode);
            set.add(evalNode);
            checkNodes(evalNode);
        } 
    }
 
    private int minDistance(){
        int minimum,node = 0;
        Iterator<Integer> iterator = unset.iterator();
        node = iterator.next();
        minimum = dist[node];
        for (int i = 1; i <= dist.length; i=i+1){
            if (unset.contains(i)){
                if (dist[i] <= minimum){
                    minimum = dist[i];
                    node = i;			
                }
            }
        }
        return node;
    }
 
    private void checkNodes(int evalNode){
        int edgeDist = -1;
        int newDist = -1;
 
        for (int destNode = 1; destNode <= totalNodes; destNode=destNode+1){
            if (!set.contains(destNode)){
                if (adjMatrix[evalNode][destNode] != Integer.MAX_VALUE){
                    edgeDist = adjMatrix[evalNode][destNode];
                    newDist = dist[evalNode] + edgeDist;
                    if (newDist < dist[destNode]){
                        dist[destNode] = newDist;
                    }
                    unset.add(destNode);
                }
            }
        }
    }
 
    public static void main(String args[]){
        int adjMatrix[][];
        int vertices;
        int src = 0,dest=0;
        Scanner scan = new Scanner(System.in);
        try{
            System.out.println("Input the number of vertices in the graph:");
            System.out.println("==========================================");
            vertices = scan.nextInt();
            adjMatrix = new int[vertices + 1][vertices + 1]; 
            System.out.println("Input the matrix for the graph:");
            System.out.println("==============================");
            for (int i = 1; i <= vertices; i=i+1){
                for (int j = 1; j <= vertices; j=j+1){
                    adjMatrix[i][j] = scan.nextInt();
                    if (i == j){
                        adjMatrix[i][j] = 0;
                        continue;
                    }
                    if (adjMatrix[i][j] == 0){
                        adjMatrix[i][j] =  Integer.MAX_VALUE;
                    }
                } 
            }  
            System.out.println("Input the source:");
            System.out.println("=================");
            src = scan.nextInt();
            System.out.println("Enter the destination:");
            System.out.println("======================");
            dest = scan.nextInt();
            DijkstraSet DS = new DijkstraSet(vertices);
            DS.dijkstraAlgorithm(adjMatrix, src); 
            System.out.println("The Shorted Path to all nodes are:");
            System.out.println("==================================");
            for (int i = 1; i <= DS.dist.length - 1; i=i+1){
                System.out.println(src + " to " + i + " is "+ DS.dist[i]);
            }
            System.out.println("================================");
            System.out.println("The Shorted Path from " + src + " to " + dest + " is: ");
            System.out.println("================================");
            for (int i = 1; i <= DS.dist.length - 1; i=i+1){
                if (i == dest)
                    System.out.println(src + " to " + i + " is " + DS.dist[i]);
            }
        } catch (InputMismatchException wrongInput){
            System.out.println("Unexpected Input from the user detected!!!!");
        }
        scan.close();
    }
}
