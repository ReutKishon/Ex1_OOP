package WGraph;

import org.testng.internal.collections.Pair;

import java.io.*;
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
        if (graph == null) {
            return null;
        }
        weighted_graph copyGraph = new WGraph_DS();

        Queue<node_info> q = new LinkedList<>();

        Iterator<node_info> iterator = graph.getV().iterator();
        if (!iterator.hasNext()) return copyGraph;
        // get arbitrary node
        node_info src = iterator.next();
        q.add(src);
// adding a copy of this node to the copyGraph
        copyGraph.addNode(src.getKey());
        copyGraph.getNode(src.getKey()).setInfo(src.getInfo());
        copyGraph.getNode(src.getKey()).setTag(src.getTag());

        while (!q.isEmpty()) {
            // Get the front node from the queue
            // and then visit all its neighbours
            node_info u = q.poll();


            if (graph.getV(u.getKey()) != null) {
                //for all neighbor of u:
                for (node_info neighbor : graph.getV(u.getKey())) {


                    // Check if this node has not already been created
                    if (copyGraph.getNode(neighbor.getKey()) == null) {
                        q.add(neighbor);

                        // If not then create a new Node and add it to the copyGraph

                        copyGraph.addNode(neighbor.getKey());
                        copyGraph.getNode(neighbor.getKey()).setTag(neighbor.getTag());
                        copyGraph.getNode(neighbor.getKey()).setInfo(neighbor.getInfo());

                    }
                    // add the 'neighbor' to neighbors of u
                    if (!copyGraph.hasEdge(u.getKey(), neighbor.getKey())) {
                        double weight = graph.getEdge(u.getKey(), neighbor.getKey());
                        copyGraph.connect(u.getKey(), neighbor.getKey(), weight);
                    }
                }
            }
        }

        // Return the copyGraph
        return copyGraph;

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

 // helper method to isConnected
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
        int settled = ++WGraph_DS.Node.count;
        if (graph == null) return -1;
        HashMap<Integer, Double> distances = new HashMap<>();
        HashMap<Integer, Integer> parents = new HashMap<>();
        // start dijkstra
        Dijkstra(src, distances, parents, settled);
// there is no path between src to dest
        if (!distances.containsKey(dest)) {
            return -1;
        }

        return distances.get(dest);
    }


    public void Dijkstra(int src, HashMap<Integer, Double> distances, HashMap<Integer, Integer> parents, int settled) {
        PriorityQueue<Pair<Integer, Double>> priorityQueue = new PriorityQueue<>(Comparator.comparing(Pair::second));
//        HashSet<Integer> settled = new HashSet<>();

        // Add source node to the priority queue. A distance from src to itself is zero.
        priorityQueue.add(new Pair<>(src, 0.0));

        // Distance to the source is 0
        distances.put(src, 0.0);
        // checking for all the possible shortest path to every node from src , hench src does not have parent.
        parents.put(src, -1);

        int i = 0;
        while (i != graph.nodeSize() && !priorityQueue.isEmpty()) {

            // remove the minimum distance node
            // from the priority queue
            int u = priorityQueue.remove().first();

            // adding the node whose distance is
            // finalized
//            settled.add(u);
            graph.getNode(u).setTag(settled);
            i++;

            NeighboursProcess(u, distances, settled, priorityQueue, parents);
        }
    }

    // Function to process all the neighbours
    // of the passed node
    private void NeighboursProcess(int u, HashMap<Integer, Double> dist, int settled, PriorityQueue<Pair<Integer, Double>> pq, HashMap<Integer, Integer> parents) {


        // All the neighbors of v
        for (node_info neighbor : graph.getV(u)) {

            // If current node hasn't already been processed
            if (neighbor.getTag() != settled) {
                double edgeDistance = graph.getEdge(u, neighbor.getKey());
                double newDistance = dist.get(u) + edgeDistance;

                // If new distance is cheaper in cost
                if (!dist.containsKey(neighbor.getKey()) || newDistance < dist.get(neighbor.getKey())) {
                    dist.put(neighbor.getKey(), newDistance);
                    //updating the parent of neighbor to u in order to restore the path later
                    parents.put(neighbor.getKey(), u);

                }

                // Add the current node to the queue
                pq.add(new Pair<>(neighbor.getKey(), dist.get(neighbor.getKey())));
            }
        }
    }


    @Override
    public List<node_info> shortestPath(int src, int dest) {
        int settled = ++WGraph_DS.Node.count;
        if (graph == null) return null;

        HashMap<Integer, Double> distances = new HashMap<>();
        HashMap<Integer, Integer> parents = new HashMap<>();
        Dijkstra(src, distances, parents , settled);
        if (distances.size() == 1 || !distances.containsKey(dest)) {
            return null;
        }
        List<node_info> path = new LinkedList<>();
        int tail = dest;
        path.add(graph.getNode(tail));
        while (parents.get(tail) != -1) {
            path.add(graph.getNode(parents.get(tail)));
            tail = parents.get(tail);
        }
        Collections.reverse(path);
        return path;

    }

    @Override
    public boolean save(String file) {
        System.out.println("start Serialize to" + file);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(fileOutputStream);
            o.writeObject(graph);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("end of Serialize");
        return true;
    }

    @Override
    public boolean load(String file) {

        try {
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);


            // Read objects
            weighted_graph gr = (weighted_graph) oi.readObject();

            System.out.println(gr.toString());

            oi.close();
            fi.close();
            graph = gr;
            return true;

        } catch (
                FileNotFoundException e) {
            System.out.println("File not found");
        } catch (
                IOException e) {
            System.out.println("Error initializing stream");
        } catch (
                ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}

