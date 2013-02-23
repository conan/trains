package trains;

import java.util.List;

public interface RouteInformation {

    public void setGraph(int[][] graph);

    public String distance(List<Integer> route);

    public int numberOfRoutes(int source, int destination);

    public List<Integer> shortestRoute(int source, int destination);

}
