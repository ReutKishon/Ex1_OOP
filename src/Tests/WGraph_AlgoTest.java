package Tests;

import WGraph.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    private static Random _rnd = null;




    @Test
    void oneVerticesGraph() {
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);
        assertEquals(-1, ga.shortestPathDist(1, 2));
        assertNull(ga.shortestPath(1, 2));
        double res = ga.shortestPathDist(1, 1);
        assertEquals(0, res);

    }


    @Test
    void nullGraph() {
        weighted_graph_algorithms ga = new WGraph_Algo();

        ga.init(null);
        assertEquals(-1, ga.shortestPathDist(1, 3));
        assertNull(ga.shortestPath(1, 3));
        assertNull(ga.copy());
        assertTrue(ga.isConnected()); // in an empty way

    }

    @Test
    void isConnected1() {
        weighted_graph g0 = graph_creator(0, 0, 1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = graph_creator(1, 0, 1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = graph_creator(2, 0, 1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());

        g0 = graph_creator(2, 1, 1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = graph_creator(10, 30, 1);
        ag0.init(g0);
        boolean b = ag0.isConnected();
        assertTrue(b);
    }


    @Test
    void isConnected2() {

        weighted_graph graph = new WGraph_DS();
        int i = 0;
        while (i < 10) {
            graph.addNode(i);
            i++;
        }
        graph.connect(0, 1, 4);
        graph.connect(0, 8, 1);
        graph.connect(1, 5, 10);
        graph.connect(1, 7, 32);
        graph.connect(2, 6, 7);
        graph.connect(3, 7, 4.2);
        graph.connect(3, 4, 6);
        graph.connect(4, 9, 12);
        graph.connect(5, 6, 41);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);
        assertTrue(ga.isConnected());
        graph.removeNode(9);
        assertTrue(ga.isConnected());
        graph.removeNode(7);
        assertFalse(ga.isConnected());
        graph.connect(3,5,1);
        assertTrue(ga.isConnected());
    }

    @Test
    void isConnected3() {
        weighted_graph g = new WGraph_DS();

        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.connect(0, 1, 4);
        g.connect(1, 2, 2);
        g.connect(1, 3, 1);
        g.connect(3, 4, 6);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        assertTrue(ga.isConnected());
        g.removeEdge(4, 3);
        assertFalse(ga.isConnected());
        g.connect(4, 0, 2);
        assertTrue(ga.isConnected());

    }
    @Test
    void isConnected4(){


        weighted_graph graph = graph_creator(100000,130000,1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);
        boolean res = ga.isConnected();
        assertFalse(res);


    }
    @Test
    void shortestPathDist1() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        double d = ag0.shortestPathDist(0, 10);
        assertEquals(d, 5.1);
    }

    @Test
    void shortestPathDist2(){
        weighted_graph graph = new WGraph_DS();
        int i = 0;
        while (i < 10) {
            graph.addNode(i);
            i++;
        }
        graph.connect(0, 1, 1);
        graph.connect(0, 8, 1);
        graph.connect(1, 5, 2);
        graph.connect(1, 7, 32);
        graph.connect(2, 6, 7);
        graph.connect(3, 7, 8);
        graph.connect(3, 4, 6);
        graph.connect(4, 9, 12);
        graph.connect(5, 6, 6.5);
        graph.connect(5, 3, 9);
        graph.connect(9, 8, 1.5);


        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(graph);
        double res1 = wga.shortestPathDist(9,1);
        assertEquals(3.5,res1);
        double res2 = wga.shortestPathDist(2,7);
        assertEquals(30.5,res2);
        double res3 = wga.shortestPathDist(4,1);
        assertEquals(15.5,res3);

    }


    @Test
    void shortestPathDist3() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);

        g.connect(0, 1, 4);
        g.connect(1, 2, 2);
        g.connect(1, 3, 1);
        g.connect(3, 4, 6);
        g.connect(2, 7, 9);
        g.connect(6, 7, 2);
        g.connect(6, 5, 2);
        g.connect(5, 3, 4);

        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(g);
//        double dist1 = wga.shortestPathDist(7,3);
//        assertEquals(8.0,dist1);
        double dist4 = wga.shortestPathDist(8, 4);
        assertEquals(-1, dist4);
        double distToMyself = wga.shortestPathDist(1, 1);
        assertEquals(0, distToMyself);
        List<node_info> path = wga.shortestPath(7, 3);
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getKey() + ",");
        }
        double dist2 = wga.shortestPathDist(6, 1);
        assertEquals(7, dist2);
        double dist3 = wga.shortestPathDist(4, 6);
        assertEquals(12, dist3);
    }

    @Test
    void shortestPath() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0, 10);
        double[] checkTag = {0.0, 1.0, 2.0, 3.1, 5.1};
        int[] checkKey = {0, 1, 5, 7, 10};
        int i = 0;
        for (node_info n : sp) {
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }
    }



    @Test
    void save_load() {
        weighted_graph g0 = graph_creator(10, 30, 1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = graph_creator(10, 30, 1);
        ag0.load(str);
        assertEquals(g0, g1);
        g0.removeNode(0);
        assertNotEquals(g0, g1);
    }

    private weighted_graph small_graph() {
        weighted_graph g0 = graph_creator(11, 0, 1);
        g0.connect(0, 1, 1);
        g0.connect(0, 2, 2);
        g0.connect(0, 3, 3);

        g0.connect(1, 4, 17);
        g0.connect(1, 5, 1);
        g0.connect(2, 4, 1);
        g0.connect(3, 5, 10);
        g0.connect(3, 6, 100);
        g0.connect(5, 7, 1.1);
        g0.connect(6, 7, 10);
        g0.connect(7, 10, 2);
        g0.connect(6, 8, 30);
        g0.connect(8, 10, 10);
        g0.connect(4, 10, 30);
        g0.connect(3, 9, 10);
        g0.connect(8, 10, 10);

        return g0;
    }


    //        ///////////////////////////////////
//        /**
//         * Generate a random graph with v_size nodes and e_size edges
//         * @param v_size
//         * @param e_size
//         * @param seed
//         * @return
//         */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for (int i = 0; i < v_size; i++) {
            g.addNode(i);
        }
//            // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while (g.edgeSize() < e_size) {
            int a = nextRnd(0, v_size);
            int b = nextRnd(0, v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i, j, w);
        }
        return g;
    }

    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }

    //        /**
//         * Simple method for returning an array with all the node_data of the graph,
//         * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
//         * @param g
//         * @return
//         */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = nodes[i].getKey();
        }
        Arrays.sort(ans);
        return ans;
    }


    @Test
    void copy() {

        weighted_graph graph = graph_creator(10, 5, 1);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(graph);
        weighted_graph copyGraph = ga.copy();
        assertEquals(graph.edgeSize(), copyGraph.edgeSize());
        assertEquals(graph.nodeSize(), copyGraph.nodeSize());

    }



//    @Test
//    void save2() {
//    }
//
//    @Test
//    void load2() {
//    }
}