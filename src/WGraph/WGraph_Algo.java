package WGraph;

import org.testng.internal.collections.Pair;

import java.util.*;

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
        HashMap<Integer, Boolean> visited = new HashMap<>();
        //start the BFS from arbitrary vertex
        BFS_Algo(it.next().getKey(), visited);

        //check if all the vertices are visited, if yes then graph is connected

        return visited.size() == graph.nodeSize();
    }


    public void BFS_Algo(int node, HashMap<Integer, Boolean> visited) {


        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<>();

        // Mark the current node as visited and enqueue it
        visited.put(node, true);
        queue.add(node);

        while (!queue.isEmpty()) {
            // Dequeue a vertex from queue and print it
            int tempNode = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            if (graph.getV(tempNode).size() != 0) {
                for (node_info neighbor : graph.getV(tempNode)) {
                    if (!visited.containsKey(neighbor.getKey())) {
                        visited.put(neighbor.getKey(), true);

                        queue.add(neighbor.getKey());
                    }
                }
            }
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        PriorityQueue<Pair<Integer,Double>> priorityQueue = new PriorityQueue<>();
        HashMap<Integer, Double> distances = new HashMap<>();
        HashSet<Integer> visited = new HashSet<>();

        // Add source node to the priority queue
        priorityQueue.add(new Pair<>(src,0.0));

        // Distance to the source is 0
        distances.put(src,0.0);



        while (visited.size() != graph.nodeSize()) {

            // remove the minimum distance node
            // from the priority queue
            int u = priorityQueue.remove().getKey();

            // adding the node whose distance is
            // finalized
            visited.add(u);

            e_Neighbours(u,distances,visited,priorityQueue);
        }
        return distances.get(dest);
    }


    // Function to process all the neighbours
    // of the passed node
    private void e_Neighbours(int u, HashMap<Integer, Double> dist, HashSet<Integer> visited, PriorityQueue<node_info> pq) {


        // All the neighbors of v
        for (node_info neighbor : graph.getV(u)) {

            // If current node hasn't already been processed
            if (!visited.contains(neighbor.getKey())) {
                double edgeDistance = graph.getEdge(u, neighbor.getKey());
                double newDistance = dist.get(u) + edgeDistance;

                // If new distance is cheaper in cost
                if (!dist.containsKey(neighbor.getKey()) || newDistance < dist.get(neighbor.getKey()))
                    dist.put(neighbor.getKey(), newDistance);

                // Add the current node to the queue
                pq.add(neighbor);
            }
        }
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
