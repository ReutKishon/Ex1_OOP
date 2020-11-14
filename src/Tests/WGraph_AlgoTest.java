package Tests;

import WGraph.*;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        double dist4 = wga.shortestPathDist(8,4);
        assertEquals(-1,dist4);
        double distToMyself = wga.shortestPathDist(1,1);
        assertEquals(0,distToMyself);
        List<node_info>  path = wga.shortestPath(7,3);
        for (int i = 0; i <path.size() ; i++) {
            System.out.print(path.get(i).getKey() + ",");
        }
        double dist2 = wga.shortestPathDist(6,1);
        assertEquals(7,dist2);
        double dist3 = wga.shortestPathDist(4,6);
        assertEquals(12,dist3);
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