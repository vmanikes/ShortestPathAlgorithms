/*
 * @author Manikeswaram Chaitanya
 * @created on: 24November 2015 
 * @time:3:00 PM
 */
package shortestpath;

import java.util.*;
public class DijkstraQueue{
    private int dist[];
    private Queue<Integer> queue;
    private Set<Integer> set;
    private int totalNodes;
    private int adjMatrix[][];
 
    public DijkstraQueue(int totalNodes){
        this.totalNodes = totalNodes;
        dist = new int[totalNodes + 1];
        set = new HashSet<Integer>();
        queue = new LinkedList<Integer>();
        adjMatrix = new int[totalNodes + 1][totalNodes + 1];
    }
 
    public void dijkstra_algorithm(int adjacencyMatrix[][], int src){
        int evalNode;
        for (int i = 1; i <= totalNodes; i=i+1)
            for (int j = 1; j <= totalNodes; j=j+1)
                adjMatrix[i][j] = adjacencyMatrix[i][j];
        for (int i = 1; i <= totalNodes; i=i+1){
            dist[i] = Integer.MAX_VALUE;
        }
        queue.add(src);
        dist[src] = 0;
        while (!queue.isEmpty()){
            evalNode = minimumDistanceFromQueue();
            set.add(evalNode);
            checkNeighbours(evalNode);
        }
    }
 
    private int minimumDistanceFromQueue(){
        int min,node = 0;
        Iterator<Integer> iterator = queue.iterator();
        node = iterator.next();
        min = dist[node];
        for (int i = 1; i <= dist.length; i=i+1){
            if (queue.contains(i)){
                if (dist[i] <= min){
                    min = dist[i];
                    node = i;			
                }
            }
        }
        queue.remove(node);
        return node;
    }
 
    private void checkNeighbours(int evalNode){
        int edgeDist = -1;
        int newDist= -1; 
        for (int destNode = 1; destNode <= totalNodes; destNode=destNode+1){
            if (!set.contains(destNode)){
                if (adjMatrix[evalNode][destNode] != Integer.MAX_VALUE){
                    edgeDist = adjMatrix[evalNode][destNode];
                    newDist = dist[evalNode] + edgeDist;
                    if (newDist < dist[destNode]){
                        dist[destNode] = newDist;
                    }
                    queue.add(destNode);
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
            System.out.println("Input the number of vertices:");
            System.out.println("=============================");
            vertices = scan.nextInt();
            adjMatrix = new int[vertices + 1][vertices + 1];
            System.out.println("Input the Weighted Matrix for the graph:");
            System.out.println("========================================");
            for (int i = 1; i <= vertices; i=i+1){
                for (int j = 1; j <= vertices; j=j+1){
                    adjMatrix[i][j] = scan.nextInt();
                    if (i == j){
                        adjMatrix[i][j] = 0;
                        continue;
                    }
                    if (adjMatrix[i][j] == 0){
                        adjMatrix[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
            System.out.println("Input the source:");
            System.out.println("=================");
            src = scan.nextInt();
            System.out.println("Input the destination:");
            System.out.println("======================");
            dest = scan.nextInt();
            DijkstraQueue DQ = new DijkstraQueue(vertices);
            DQ.dijkstra_algorithm(adjMatrix, src); 
            System.out.println("The Shortest Path to all nodes are:");
            System.out.println("==================================");
            for (int i = 1; i <= DQ.dist.length - 1; i=i+1){
                System.out.println(src + " to " + i + " is " + DQ.dist[i]);
            }
            System.out.println("=================================");
            System.out.println("The Shortest Path from " + src + " to " + dest + " is:");
            System.out.println("=================================");
            for (int i = 1; i <= DQ.dist.length - 1; i=i+1){
                if (i == dest)
                    System.out.println(src + " to " + i + " is " + DQ.dist[i]);
            }
        } catch (InputMismatchException wrongInput){
            System.out.println("Unexpected Input from the user detected!!!!");
        }
        scan.close();
    }
}
