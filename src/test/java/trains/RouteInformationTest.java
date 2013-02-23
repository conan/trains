package trains;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static trains.RouteInformation.*;


public class RouteInformationTest {

    // Vertex constants to make this more readable
    public static final int A = 1;
    public static final int B = 2;
    public static final int C = 3;
    public static final int D = 4;
    public static final int E = 5;

    private int[][] testGraph;

    private RouteInformation routeInformation;

    @BeforeClass
    public void setUp(){
        // Graph as defined in problem spec
        testGraph = new int[5][5];
        testGraph[A][B] = 5;
        testGraph[B][C] = 4;
        testGraph[C][D] = 8;
        testGraph[D][C] = 8;
        testGraph[D][E] = 6;
        testGraph[A][D] = 5;
        testGraph[C][E] = 2;
        testGraph[E][B] = 3;
        testGraph[A][E] = 7;

        // Class under test
        //routeInformation = new BFSRouteInformation();
    }

    /**
     * 1. The distance of the route A-B-C.
     */
    @Test
    public void testOne() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(B);
        route.add(C);
        String result = routeInformation.distance(route);
        assertEquals("Incorrect result for test #1 (route A-B-C)", "9", result);
    }

    /**
     * 2. The distance of the route A-D.
     */
    @Test
    public void testTwo() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(D);
        String result = routeInformation.distance(route);
        assertEquals("Incorrect result for test #2 (route A-D)", "5", result);
    }

    /**
     * 3. The distance of the route A-D-C.
     */
    @Test
    public void testThree() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(D);
        route.add(C);
        String result = routeInformation.distance(route);
        assertEquals("Incorrect result for test #3 (route A-D-C)", "13", result);
    }

    /**
     * 4. The distance of the route A-E-B-C-D.
     */
    @Test
    public void testFour() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(E);
        route.add(B);
        route.add(C);
        route.add(D);
        String result = routeInformation.distance(route);
        assertEquals("Incorrect result for test #4 (route A-E-B-C-D)", "22", result);
    }

    /**
     * 5. The distance of the route A-E-D.
     */
    @Test
    public void testFive() {
        List<Integer> route = new ArrayList<>();
        route.add(A);
        route.add(E);
        route.add(D);
        String result = routeInformation.distance(route);
        assertEquals("Incorrect result for test #2 (route A-D)", "NO SUCH ROUTE", result);
    }

    /**
     * 6. The number of trips starting at C and ending at C with a maximum of 3 stops.
     */
    @Test
    public void testSix() {

    }

    /**
     * 7. The number of trips starting at A and ending at C with exactly 4 stops.
     */
    @Test
    public void testSeven() {

    }

    /**
     *
     */
}
