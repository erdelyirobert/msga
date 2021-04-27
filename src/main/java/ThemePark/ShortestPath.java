package ThemePark;

import java.util.ArrayList;

public class ShortestPath {
    public int V;

    public int minDistance(int dist[], Boolean sptSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        return min_index;
    }

    public void printSolution(int dist[], int prev[]) {
        System.out.println("Vertex \t\t Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + " \t\t dist: " + dist[i] +" prev: "+ prev[i]);
    }

    public ArrayList<Integer> reverseArrayList(ArrayList<Integer> alist)
    {
        // Arraylist for storing reversed elements
        ArrayList<Integer> revArrayList = new ArrayList<Integer>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }

    public ArrayList<Integer> dijkstra(int graph[][], int src, int sizeOfPeaks) {
        ArrayList<Integer> shortestRoadPeaks = new ArrayList<>();
        V = sizeOfPeaks;
        int dist[] = new int[V];
        Boolean sptSet[] = new Boolean[V];
        int prevIndex[] = new int[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;

            for (int v = 0; v < V; v++) {

                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] <= dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    prevIndex[v] = u;
                }
            }
        }
        printSolution(dist,prevIndex);
        int targetIndex = V-1;
        shortestRoadPeaks.add(targetIndex);
        do{
            targetIndex = prevIndex[targetIndex];
            shortestRoadPeaks.add(targetIndex);
        } while (targetIndex != 0);

        return (reverseArrayList(shortestRoadPeaks));
    }
}
