package WGraph;

import java.util.*;

public class WGraph_DS implements weighted_graph, java.io.Serializable {
    private int edgesSize;
    int modifyCount;
    HashMap<Integer, node_info> nodes;
    HashMap<Integer, HashMap<node_info, Double>> edges;

    public WGraph_DS() {
        edgesSize = 0;
        modifyCount = 0;
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

    @Override
    public node_info getNode(int key) {
        if (!nodes.containsKey(key)) {
            return null;
        }
        return nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) return false;
        return edges.get(node1).containsKey(getNode(node2));
    }

    @Override
    public double getEdge(int node1, int node2) {

        if (!edges.get(node1).containsKey(getNode(node2))) return -1;
        return edges.get(node1).get(getNode(node2));
    }

    @Override
    public void addNode(int key) {
        if (nodes.containsKey(key)) return;
        node_info newNode = new Node(key);
        nodes.put(key, newNode);
        HashMap<node_info, Double> neighbors = new HashMap<>();
        edges.put(key, neighbors);
        modifyCount++;
    }

    @Override
    public void connect(int node1, int node2, double w) {
//        if (w < 0) throw new IllegalArgumentException("weight is less than zero!");

        if (node1 == node2) return;
        if (!edges.get(node1).containsKey(getNode(node2))) {
            edgesSize++;
        }
// put new edge if there is no edge between node1 and node2 , else update the weight.
        edges.get(node1).put(getNode(node2), w);
        edges.get(node2).put(getNode(node1), w);


        modifyCount++;

    }

    @Override
    public Collection<node_info> getV() {
        return nodes.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {

        return edges.get(node_id).keySet();

    }

    @Override
    public node_info removeNode(int key) {
        // try to remove node that doesn't exist
        if (!nodes.containsKey(key)) {
            return null;
        }

        modifyCount++;
        //get the node
        node_info deletedNode = getNode(key);
        if (edges.get(key).size() != 0) {
            // remove this node from all the neighbors lists of deleted_node and decrease the edgeSize respectively
            for (node_info neighbor : edges.get(key).keySet()) {
                edges.get(neighbor.getKey()).remove(deletedNode);
                edgesSize--;
                modifyCount++;

            }
        }
        nodes.remove(key);
        edges.remove(key);
        return deletedNode;

    }

    @Override
    public void removeEdge(int node1, int node2) {

        //  try to remove edge that doesn't exist
        if (!edges.get(node1).containsKey(getNode(node2))) return;

        // remove node1 and node2 from each other's neighbors list and decrease the edgeSize in 1
        edges.get(node1).remove(getNode(node2));
        edges.get(node2).remove(getNode(node1));
        edgesSize--;
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
        weighted_graph anotherGraph = (weighted_graph) obj;

        if (this.nodeSize() != anotherGraph.nodeSize() || this.edgesSize != anotherGraph.edgeSize() || this.modifyCount != anotherGraph.getMC()) {
            return false;
        }


        Queue<node_info> q = new LinkedList<>();

        Iterator<node_info> iterator = anotherGraph.getV().iterator();
        if (!iterator.hasNext()) return true;
        node_info src = iterator.next();

        q.add(src);
        src.setTag(1);

        while (!q.isEmpty()) {
            // Get the front node from the queue
            // and then visit all its neighbours
            node_info u = q.poll();

            //check the existence of the nodes of the anotherGraph in this graph
            if (!this.nodes.containsKey(u.getKey())) return false;
            // if u has neighbors
            if (anotherGraph.getV(u.getKey()) != null) {
                //for all neighbor of u:
                for (node_info neighbor : anotherGraph.getV(u.getKey())) {

                    // if neighbor is not visited
                    if (neighbor.getTag() != 1) {
                        //
                        if (!nodes.containsKey(neighbor.getKey()) || !edges.get(u.getKey()).containsKey(getNode(neighbor.getKey()))) {
                            return false;
                        }
                        q.add(neighbor);
                        neighbor.setTag(1);


                    }


                }
            }
        }
        return true;
    }


    public static class Node implements node_info, java.io.Serializable {
        private final int key;
        private String info;
        private double tag;


        public Node(int key) {
            this.key = key;
        }

        // constructor for copy the graph
        public Node(int key, double tag, String info) {
            this.key = key;
            this.tag = tag;
            this.info = info;
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

//    public static class Edge implements Comparator<Edge>, java.io.Serializable {
//
//        private int src;
//        private int dest;
//        private double weight;
//
//
//        public Edge(int src, int dest, double weight) {
//            this.src = src;
//            this.dest = dest;
//            this.weight = weight;
//        }
//
//        public int getSrc() {
//            return this.src;
//        }
//
//        public int getDest() {
//            return this.dest;
//        }
//
//        public double getWeight() {
//            return this.weight;
//        }
//
//        public void setDest(int dest) {
//            this.dest = dest;
//        }
//
//        public void setSrc(int src) {
//            this.src = src;
//        }
//
//        public void setWeight(double weight) {
//            this.weight = weight;
//        }
//
//
//        @Override
//        public int compare(Edge e1, Edge e2) {
//            if (e1.weight < e2.weight) {
//                return -1;
//            }
//            if (e1.weight > e2.weight) {
//                return 1;
//            }
//
//            return 0;
//        }
//    }


}
