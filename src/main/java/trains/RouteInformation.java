package trains;


import java.util.*;

public class RouteInformation {

    // Calculated predecessor tree
    private Integer[] predecessor;

    // Shortest path estimates from the source vertex
    private Integer[] estimate;

    /**
     * Implementation of Dijkstra's algorithm for calculating single-source shortest-path distances.
     *
     * @param graph     adjacency matrix of input graph
     * @param source    source vertex
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
                if(graph[u][v] != null) {
                    if(u == source) {
                        // This is a hack to accomodate that the shortest estimate from one vertex to itself cannot be 0
                        estimate[v] = graph[u][v];
                        predecessor[v] = u;
                        toDo.add(v);
                    } else if(estimate[v] > estimate[u] + graph[u][v]){
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

    private void breadthFirstSearch(Integer[][] graph, int source, int destination) {
        // Internal data structures
        Queue<Integer> queue = new LinkedList<>();
        Colour[] colour = new Colour[graph.length];

        // Output
        predecessor = new Integer[graph.length];
        estimate = new Integer[graph.length];

        for (int i = 0; i < graph.length; i++) {
            colour[i] = Colour.WHITE;
        }
        colour[source] = Colour.GREY;
        estimate[source] = 0;
        queue.add(source);
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int v = 0; v < graph[u].length; v++) {
                if(graph[u][v] != null) {
                    if (v != u) {
                        if (colour[v] == Colour.WHITE) {
                            colour[v] = Colour.GREY;
                            estimate[v] = estimate[u] + 1;
                            predecessor[v] = u;
                            queue.add(v);
                        }
                    }
                    if (v == destination) {
                        int p = v;
                        Stack<Integer> path = new Stack<>();
                        while (predecessor[p] != source) {
                            path.push(p);
                            p = predecessor[p];
                        }
                        while(!path.isEmpty()) {
                            System.out.print(path.pop());
                        }
                        System.out.println("\n");
                    }
                }
            }
            colour[u] = Colour.BLACK;
        }
    }

    /**
     * Placeholder for marking vertices in BFS as the graph is explored:
     * <ul>
     * <li>WHITE: undiscovered</li>
     * <li>GREY: discovered, some adjacent vertices not explored</li>
     * <li>BLACK: discovered and all adjacent vertices explored</li>
     * </ul>
     */
    private enum Colour {
        WHITE, GREY, BLACK;
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

    public Integer shortestRouteLength(Integer[][] graph, int source, int destination) {
        dijkstra(graph, source);
        return estimate[destination];
    }

    public Integer numberOfRoutesWithMaxLength(Integer[][] graph, int source, int destination, int maxStops) {
        // -1 because we exclude the 0-length route which will always be found
        return countRoutes(graph, source, destination, maxStops) - 1;
    }

    private int countRoutes(Integer[][] graph, int u, int destination, int maxStops) {
        int count = 0;

        // If it's the destination then count it
        if(u == destination) {
            count++;
        }

        // If we've hit the max path length, retreat
        if (maxStops == 0) {
            return count;
        }

        // Examine adjacent nodes
        for(int v=0; v<graph[u].length; v++) {
            if(graph[u][v] != null) {
                count += countRoutes(graph, v, destination, maxStops - 1);
            }
        }

        // Retreat
        return count;
    }

}
