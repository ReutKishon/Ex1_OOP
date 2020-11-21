package src;

import java.util.*;

public class WGraph_DS implements weighted_graph, java.io.Serializable {
    HashMap<Integer, node_info> nodes;
    HashMap<Integer, HashMap<node_info, Double>> edges;
    private int edgesSize;
    int modifyCount;

    public WGraph_DS() {
        edgesSize = 0;
        modifyCount = 0;
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

    @Override
    public node_info getNode(int key) {
        // if the node with the given key doesn't exist in the nodes map return null
        if (!nodes.containsKey(key)) { // O(1)
            return null;
        }
        //return the node associated with the given key in nodes map
        return nodes.get(key); // O(1)
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        // if the given nodes don't exist in nodes map
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) return false;  //O(1)
        //check if node2 is contained in node1's neighbors.
        return edges.get(node1).containsKey(getNode(node2)); // O(1)
    }

    @Override
    public double getEdge(int node1, int node2) {
        // if node1 and node2 are not neighbors.
        if (!edges.get(node1).containsKey(getNode(node2))) return -1; // O(1)

        return edges.get(node1).get(getNode(node2)); //O(1)
    }

    @Override
    public void addNode(int key) {
        // if there is already a node with the given key
        if (nodes.containsKey(key)) return; //O(1)
        node_info newNode = new Node(key);
        // update the nodes map
        nodes.put(key, newNode); // O(1)
        HashMap<node_info, Double> neighbors = new HashMap<>();
        //update the edges map with new empty map that represents his neighbors.
        edges.put(key, neighbors); //O(1)
        // adding a new node to the graph - changes the graph
        modifyCount++;
    }

    @Override
    public void connect(int node1, int node2, double w) {
        //A node connected to itself is forbidden.
        if (node1 == node2) return;
        // if node1 and node2 are not connected, then increase the edgesSize
        if (!edges.get(node1).containsKey(getNode(node2))) { // O(1)
            edgesSize++;
        }
//      put new edge if there is no edge between node1 and node2 , else update the weight. O(1)
        edges.get(node1).put(getNode(node2), w);
        edges.get(node2).put(getNode(node1), w);

//      adding an edge or updating a weight of an edge changes the graph.
        modifyCount++;

    }

    //return pointer to the nodes collection O(1)
    @Override
    public Collection<node_info> getV() {
        return nodes.values();
    }

    @Override

    //return pointer to the node_id's neighbors collection O(1)
    public Collection<node_info> getV(int node_id) {

        return edges.get(node_id).keySet();

    }

    @Override
    public node_info removeNode(int key) {
        // try to remove node that doesn't exist
        if (!nodes.containsKey(key)) { // O(1)
            return null;
        }
// removing node from the graph changes the graph
        modifyCount++;
        //save the node to delete
        node_info deletedNode = getNode(key);
        if (edges.get(key).size() != 0) {
            // remove this node from all his neighbors lists and decrease the edgeSize respectively
            for (node_info neighbor : edges.get(key).keySet()) { // O(k) when k is the deletedNode's degree.
                edges.get(neighbor.getKey()).remove(deletedNode);
                edgesSize--;
                // removing edge from the graph changes the graph
                modifyCount++;

            }
        }
        //remove the node with the given key from nodes map O(1)
        nodes.remove(key);
        //remove the node with the given key from edges map O(1)
        edges.remove(key);
        return deletedNode;

    }

    @Override
    public void removeEdge(int node1, int node2) {

        //  try to remove edge that doesn't exist O(1)
        if (!edges.get(node1).containsKey(getNode(node2))) return;

        // remove node1 and node2 from each other's neighbors list and decrease the edgeSize in 1
        edges.get(node1).remove(getNode(node2));
        edges.get(node2).remove(getNode(node1));
        //decrease edgeSize
        edgesSize--;
        // removing edge from the graph changes the graph
        modifyCount++;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgesSize;
    }

    @Override
    public int getMC() {
        return modifyCount;
    }


    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        weighted_graph anotherGraph = (weighted_graph) obj;

        if (this.nodeSize() != anotherGraph.nodeSize() || this.edgesSize != anotherGraph.edgeSize()) {
            return false;
        }

        for (node_info n : this.getV()) {

            // if anotherGraph does not contain n
            if (anotherGraph.getNode(n.getKey()) == null) {

                return false;
            }

            for (node_info neighbor : this.getV(n.getKey())) {

                if (anotherGraph.getNode(neighbor.getKey()) == null) return false;

                //if anotherGraph does not contain the edge {n,neighbor}
                if (!anotherGraph.hasEdge(n.getKey(), neighbor.getKey())) {
                    return false;
                }
            }


        }

        return true;
    }

    // Node class
    public static class Node implements node_info, java.io.Serializable {
        private final int key;
        private String info;
        private double tag;
        public static int count = 0;


        public Node(int key) {
            this.key = key;
            this.tag = count;
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }


    }


}
