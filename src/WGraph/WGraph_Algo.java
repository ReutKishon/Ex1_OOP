package WGraph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WGraph_Algo implements weighted_graph_algorithms {

    private weighted_graph graph;

    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    @Override
    public weighted_graph copy() {
        return null;
    }

    @Override
    public boolean isConnected() {

        if (graph == null) return true;
        if (graph.nodeSize() == 0) {
            return true;
        }
        Iterator<node_info> it = graph.getV().iterator();

        //start the BFS from arbitrary vertex
        BFS_Algo(it.next().getKey());

        //check if all the vertices are visited, if yes then graph is connected

        for (node_info nodeInfo : graph.getV()) {
            if (nodeInfo.getTag() != 1) {
                // reset the tags of all nodes back to 0 (unvisited) so it can be ready for the next method call.
                resetTags();
                return false;
            }

        }
        // reset the tags of all nodes back to 0 (unvisited) so it can be ready for the next method call.
        resetTags();
        return true;
    }


    public void resetTags() {
        for (node_info node : graph.getV()) {
            node.setTag(0);
        }
    }


    public void BFS_Algo(int node) {


        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        graph.getNode(node).setTag(1);
        queue.add(node);

        while (!queue.isEmpty()) {
            // Dequeue a vertex from queue and print it
            int tempNode = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            if (graph.getV(tempNode).size()!= 0) {
                for (node_info neighbor : graph.getV(tempNode)) {
                    if (neighbor.getTag() != 1) {
                        neighbor.setTag(1);

                        queue.add(neighbor.getKey());
                    }
                }
            }
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
