package trains;


import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class RouteInformation {

    // Calculated predecessor tree
    private Integer[] predecessor;
    // Shortest path estimates from the source vertex
    private Integer[] estimate;

    /**
     * Implementation of Dijkstra's algorithm for calculating single-source shortest-path distances.
     *
     * @param graph  adjacency matrix of input graph
     * @param source source vertex
     */
    private void dijkstra(Integer[][] graph, int source) {
        predecessor = new Integer[graph.length];
        estimate = new Integer[graph.length];

        // Set initial estimates to maximum
        for (int v = 0; v < graph.length; v++) {
            estimate[v] = Integer.MAX_VALUE;
        }

        // Min-priority queue keyed on estimate values.
        // This is a binary heap; a Fibonacci heap would be better, but there isn't currently one in java.util
        Queue<Integer> toDo = new PriorityQueue<>(graph.length,
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer u, Integer v) {
                        return estimate[u].compareTo(estimate[v]);
                    }
                }
        );

        // Start with source vertex
        toDo.add(source);

        while (!toDo.isEmpty()) {
            // Get lowest-estimate vertex from queue
            int u = toDo.remove();

            // Relax all adjacent vertices
            for (int v = 0; v < graph[u].length; v++) {
                if (graph[u][v] != null) {
                    if (u == source) {
                        // This is a hack to accomodate that the shortest estimate from one vertex to itself cannot be 0
                        estimate[v] = graph[u][v];
                        predecessor[v] = u;
                        toDo.add(v);
                    } else if (estimate[v] > estimate[u] + graph[u][v]) {
                        estimate[v] = estimate[u] + graph[u][v];
                        predecessor[v] = u;
                        toDo.add(v);
                    }
                }
            }
        }
    }

    /**
     * "Relaxes" the shortest path estimate of edge (u, v). (see CLRS 2001, MIT, ch. 24)
     *
     * @param u      the start vertex
     * @param v      the end vertex
     * @param weight the weight of the edge (u, v)
     */
    private void relax(int u, int v, int weight, Queue<Integer> queue) {
        if (estimate[v] > estimate[u] + weight) {
            estimate[v] = estimate[u] + weight;
            predecessor[v] = u;
        }
    }

    public String distance(Integer[][] graph, List<Integer> route) {
        int routeLength = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            // Get next edge weight
            int u = route.get(i);
            int v = route.get(i + 1);
            Integer edge = graph[u][v];

            // If there's no edge, fail
            if (edge == null) {
                return "NO SUCH ROUTE";
            }

            routeLength += graph[u][v];
        }
        return String.valueOf(routeLength);
    }

    /**
     * Calculates the length of the shortest path from the <code>source</code> vertex to the <code>destination</code>
     * vertex in the specified directed weighted <code>graph</code>.
     *
     * @param graph         an adjacency matrix representing the input graph
     * @param source        the source vertex
     * @param destination   the destination vertex
     * @return              the weight of the shortest path from <code>source</code> to <code>vertex</code> in
     *                      <code>graph</code>
     */
    public Integer shortestRouteLength(Integer[][] graph, int source, int destination) {
        dijkstra(graph, source);
        return estimate[destination];
    }

    public int numberOfRoutesWithMaxLength(Integer[][] graph, int source, int destination, int maxStops) {
        // -1 because we exclude the 0-length route which will always be found
        return countRoutes(graph, source, destination, maxStops) - 1;
    }

    public int numberOfRoutesWithExactLength(Integer[][] graph, int source, int destination, int stopCount) {
        return 0;
    }

    private int countRoutes(Integer[][] graph, int u, int destination, int maxStops) {
        int count = 0;

        // If it's the destination then count it
        if (u == destination) {
            count++;
        }

        // If we've hit the max path length, retreat
        if (maxStops == 0) {
            return count;
        }

        // Examine adjacent nodes
        for (int v = 0; v < graph[u].length; v++) {
            if (graph[u][v] != null) {
                count += countRoutes(graph, v, destination, maxStops - 1);
            }
        }

        // Retreat
        return count;
    }
}
