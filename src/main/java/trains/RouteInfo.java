package trains;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Provides methods for calculating information about paths in graphs.
 */
public class RouteInfo {

    /**
     * Calculates the distance covered in the specified directed weighted <code>graph</code> by following the specified
     * <code>route</code>.
     *
     * @param graph an adjacency matrix representing the input graph
     * @param route the route to follow
     * @return      the sum of the weights of all the edges in <code>route</code>, or "NO SUCH ROUTE" if the route does
     *              not exist
     */
    public static String distance(Integer[][] graph, List<Integer> route) {
        Integer routeLength = pathWeight(graph, route);
        if(routeLength == null) {
            return "NO SUCH ROUTE";
        } else {
            return routeLength.toString();
        }
    }

    /**
     * Calculates the weight of <code>route</code> in the specified directed weighted <code>graph</code>.
     *
     * @param graph an adjacency matrix representing the input graph
     * @param route the route to follow
     * @return      the sum of the weights of all the edges in <code>route</code>, or null if the route does not exist
     */
    public static Integer pathWeight(Integer[][] graph, List<Integer> route) {
        int routeLength = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            // Get next edge weight
            int u = route.get(i);
            int v = route.get(i + 1);
            Integer edge = graph[u][v];

            // If there's no edge, fail
            if (edge == null) {
                return null;
            }

            routeLength += graph[u][v];
        }
        return routeLength;
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
    public static Integer shortestRouteLength(Integer[][] graph, int source, int destination) {
        Integer[] estimate = dijkstra(graph, source);
        return estimate[destination];
    }

    /**
     * Implementation of Dijkstra's algorithm for calculating single-source shortest-path distances.  Note that the
     * predecessor tree is not recorded by this method, as it is not currently needed by this class.
     *
     * @param graph  adjacency matrix of input graph
     * @param source source vertex
     */
    private static Integer[] dijkstra(Integer[][] graph, int source) {
        final Integer[] estimate = new Integer[graph.length];

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
                        toDo.add(v);
                    } else if (estimate[v] > estimate[u] + graph[u][v]) {
                        // "Relax" the shortest path estimate
                        estimate[v] = estimate[u] + graph[u][v];
                        toDo.add(v);
                    }
                }
            }
        }
        return estimate;
    }

    /**
     * Calculates the number of paths in the specified directed weighted <code>graph</code> from the <code>start</code>
     * vertex to the <code>destination</code> vertex that are at least <code>minStops</code> and at most
     * <code>maxStops</code>.  This uses a depth-first approach.
     *
     * @param graph         an adjacency matrix representing the input graph
     * @param start         the start vertex
     * @param destination   the destination vertex
     * @param minStops      the shortest length of path to consider
     * @param maxStops      the longest length of path to consider
     * @return              the number of paths from the start to the destination vertices covering at least minStops
     *                      and at most maxStops edges
     */
    public static int stopBoundedRoutes(Integer[][] graph, int start, int destination, int minStops, int maxStops) {
        // Sanity
        if(minStops > maxStops) {
            return 0;
        }

        int count = 0;

        // If this is the destination and we've reached the minimum path length, count it
        if (start == destination && minStops <= 0) {
            count++;
        }

        // If we've hit the max path length, retreat
        if (maxStops == 0) {
            return count;
        }

        // Examine adjacent nodes
        for (int v = 0; v < graph[start].length; v++) {
            if (graph[start][v] != null) {
                count += stopBoundedRoutes(graph, v, destination, minStops - 1, maxStops - 1);
            }
        }

        // Retreat
        return count;
    }

    /**
     * Calculates the number of paths in the specified directed weighted <code>graph</code> from the <code>start</code>
     * vertex to the <code>destination</code> vertex that are at least <code>minStops</code> and at most
     * <code>maxStops</code>.  This uses a depth-first approach.
     *
     * @param graph         an adjacency matrix representing the input graph
     * @param start         the start vertex
     * @param destination   the destination vertex
     * @param minDistance   the minimum path weight to consider
     * @param maxDistance   the maximum path weight to consider
     * @return              the number of paths from the start to the destination vertices where the sum of the edges
     *                      traversed is at least minDistance and at most maxDistance
     */
    public static int distanceBoundedRoutes(Integer[][] graph, int start, int destination, int minDistance, int maxDistance) {

        // If we've hit the max path weight, retreat
        if (maxDistance <= 0) {
            return 0;
        }

        int count = 0;

        // If this is the destination, count it
        if (start == destination && minDistance <= 0) {
            count ++;
        }

        // Examine adjacent nodes
        for (int v = 0; v < graph[start].length; v++) {
            if (graph[start][v] != null) {
                count += distanceBoundedRoutes(
                        graph, v, destination, minDistance - graph[start][v], maxDistance - graph[start][v]);
            }
        }

        // Retreat
        return count;
    }
}