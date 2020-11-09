package Tests;

import WGraph.WGraph_Algo;
import WGraph.WGraph_DS;
import WGraph.weighted_graph;
import WGraph.weighted_graph_algorithms;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
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
    void BFS_Algo() {
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}