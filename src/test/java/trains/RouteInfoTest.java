package trains;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RouteInfoTest {

    // Vertex constants to make this more readable
    public static final int A = 0;
    public static final int B = 1;
    public static final int C = 2;
    public static final int D = 3;
    public static final int E = 4;

    private Integer[][] testGraph;

    @Before
    public void setUp(){
        // Graph as defined in problem spec
        testGraph = new Integer[5][5];
        testGraph[A][B] = 5;
        testGraph[B][C] = 4;
        testGraph[C][D] = 8;
        testGraph[D][C] = 8;
        testGraph[D][E] = 6;
        testGraph[A][D] = 5;
        testGraph[C][E] = 2;
        testGraph[E][B] = 3;
        testGraph[A][E] = 7;
    }

    /**
     * 1. The distance of the route A-B-C.
     */
    @Test
    public void test1() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(B);
        route.add(C);
        String result = RouteInfo.distance(testGraph, route);
        assertEquals("Incorrect result for route A-B-C", "9", result);
    }

    /**
     * 2. The distance of the route A-D.
     */
    @Test
    public void test2() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(D);
        String result = RouteInfo.distance(testGraph, route);
        assertEquals("Incorrect result for route A-D", "5", result);
    }

    /**
     * 3. The distance of the route A-D-C.
     */
    @Test
    public void test3() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(D);
        route.add(C);
        String result = RouteInfo.distance(testGraph, route);
        assertEquals("Incorrect result for route A-D-C", "13", result);
    }

    /**
     * 4. The distance of the route A-E-B-C-D.
     */
    @Test
    public void test4() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(E);
        route.add(B);
        route.add(C);
        route.add(D);
        String result = RouteInfo.distance(testGraph, route);
        assertEquals("Incorrect result for route A-E-B-C-D", "22", result);
    }

    /**
     * 5. The distance of the route A-E-D.
     */
    @Test
    public void test5() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(E);
        route.add(D);
        String result = RouteInfo.distance(testGraph, route);
        assertEquals("Incorrect result for route A-E-D", "NO SUCH ROUTE", result);
    }

    /**
     * 6. The number of trips starting at C and ending at C with a maximum of 3 stops (and a minimum of 1).
     */
    @Test
    public void test6() {
        int result = RouteInfo.stopBoundedRoutes(testGraph, C, C, 1, 3);
        assertEquals("Incorrect result for no of routes C-C max length 3", 2, result);
    }

    /**
     * 7. The number of trips starting at A and ending at C with exactly 4 stops.
     */
    @Test
    public void test7() {
        int result = RouteInfo.stopBoundedRoutes(testGraph, A, C, 4, 4);
        assertEquals("Incorrect result for no of routes A-C length 4", 3, result);
    }

    /**
     * 8. The length of the shortest route (in terms of distance to travel) from A to C.
     */
    @Test
    public void test8() {
        int result = RouteInfo.shortestRouteLength(testGraph, A, C);
        assertEquals("Incorrect result for shortest route length A-C", 9, result);
    }

    /**
     * 9. The length of the shortest route (in terms of distance to travel) from B to B.
     */
    @Test
    public void test9() {
        int result = RouteInfo.shortestRouteLength(testGraph, B, B);
        assertEquals("Incorrect result for shortest route length A-C", 9, result);
    }

    /**
     * 10. The number of different routes from C to C with a distance of less than 30.
     */
    @Test
    public void test10() {
        int result = RouteInfo.distanceBoundedRoutes(testGraph, C, C, 1, 29, false);
        assertEquals("Incorrect result for no of routes C-C max distance 30", 7, result);
    }
}
