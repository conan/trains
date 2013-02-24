package trains;

import java.util.*;

public class DijkstraRouteInformation implements RouteInformation {

    // Contains the calculated predecessor tree
    private Integer[] predecessor;
    // Records the shortest path estimates from the source town to each destination
    private Integer[] distance;

    private void breadthFirstSearch(Integer[][] graph, int source) {
        // Internal data structures
        Queue<Integer> queue = new LinkedList<>();
        Colour[] colour = new Colour[graph.length];

        // Output
        predecessor = new Integer[graph.length];
        distance = new Integer[graph.length];

        for (int i = 0; i < graph.length; i++) {
            colour[i] = Colour.WHITE;
        }
        colour[source] = Colour.GREY;
        distance[source] = 0;
        queue.add(source);
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int v = 0; v < graph[u].length; v++) {
                if (v != u) {
                    if (colour[v] == Colour.WHITE) {
                        colour[v] = Colour.GREY;
                        distance[v] = distance[u] + 1;
                        predecessor[v] = u;
                        queue.add(v);
                    }
                }
            }
            colour[u] = Colour.BLACK;
        }
    }

    private void dijkstra(Integer[][] graph, int source) {
        predecessor = new Integer[graph.length];
        distance = new Integer[graph.length];
        distance[source] = 0;

        // Min-priority queue keyed on distance values.  This is a binary heap; a Fibonacci heap would be better
        Queue<ShortestPathEstimate> toDo = new PriorityQueue<>(graph.length,
                new Comparator<ShortestPathEstimate>() {
                    @Override
                    public int compare(ShortestPathEstimate u, ShortestPathEstimate v) {
                        return u.weight.compareTo(v.weight);
                    }
                }
        );

        // Set of vertices whose shortest-path weights from source have been calculated
        Set<Integer> done = new HashSet<>();

        // Add all vertices to queue
        for (int v = 0; v < graph.length; v++) {
            distance[v] = Integer.MAX_VALUE;
            toDo.add(new ShortestPathEstimate(v, graph[source][v]));
        }

        while (!toDo.isEmpty()) {
            int u = toDo.remove().vertex;
            done.add(u);
            for (int v = 0; v < graph[u].length; v++) {
                if (v != u) {
                    relax(u, v, graph[u][v]);
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
    private void relax(int u, int v, int weight) {
        if (distance[v] > distance[u] + weight) {
            distance[v] = distance[u] + weight;
            predecessor[v] = u;
        }
    }

    @Override
    public String distance(Integer[][] graph, List<Integer> route) {
        int routeLength = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            // Get next edge weight
            int u = route.get(i);
            int v = route.get(i + 1);
            Integer distance = graph[u][v];

            // If there's no edge, fail
            if (distance == null) {
                return "NO SUCH ROUTE";
            }

            routeLength += graph[u][v];
        }
        return String.valueOf(routeLength);
    }

    @Override
    public int numberOfRoutes(Integer[][] graph, int source, int destination) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Integer> shortestRoute(Integer[][] graph, int source, int destination) {
        breadthFirstSearch(graph, source);
        List<Integer> shortestRoute = new ArrayList<>();
        return shortestRoute;
    }

    /**
     * Placeholder for marking vertices as the graph is explored:
     * <ul>
     * <li>WHITE: undiscovered</li>
     * <li>GREY: discovered, some adjacent vertices not explored</li>
     * <li>BLACK: discovered and all adjacent vertices explored</li>
     * </ul>
     */
    private enum Colour {
        WHITE, GREY, BLACK;
    }

    /**
     * Represents the shortest-path estimate from a source vertex to another reachable vertex in the same graph.
     */
    private class ShortestPathEstimate {
        public final Integer vertex;
        public final Integer weight;

        public ShortestPathEstimate(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }
}
