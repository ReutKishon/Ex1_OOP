package Tests;

import WGraph.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    @Test
    void hasEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(1, 2, 4);
        g.connect(2, 0, 2);
        assertTrue(g.hasEdge(1, 2));
        assertTrue(g.hasEdge(0, 2));
        assertFalse(g.hasEdge(1, 0));


    }

    @Test
    void addNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        assertEquals(1, g.nodeSize());
        g.addNode(1);
        assertEquals(2, g.nodeSize());
        g.addNode(2);
        assertEquals(3, g.nodeSize());


    }

    @Test
    void connect() {

        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(1, 2, 4);
        g.connect(2, 0, 2);
        assertEquals( 4,g.getEdge(1, 2));
        assertEquals( 2,g.getEdge(0, 2));
        g.connect(1,2,8);
        assertEquals(8,g.getEdge(1,2));
        assertEquals(-1,g.getEdge(1,0));



    }

    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(1, 2, 4);
        g.connect(1, 0, 7);
        assertEquals(3,g.nodeSize());
        g.removeNode(1);
        assertEquals(2,g.nodeSize());
        assertEquals(0,g.edgeSize());
        assertNull(g.getNode(1));
        assertEquals(8,g.getMC());
    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(1, 2, 4);
        g.connect(3, 0, 7);
        assertEquals(2,g.edgeSize());
        g.removeEdge(1,2);
        assertEquals(1,g.edgeSize());
        assertEquals(7,g.getMC());
    }

    @Test
    void getNode() {
    }

    @Test
    void getEdge() {
    }

    @Test
    void getV() {
    }

    @Test
    void testGetV() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }

    @Test
    void testEquals() {
    }
}