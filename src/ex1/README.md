this project is about a weighted undirected graph.

#weighted graph definition:
 A graph having a weight, or number, associated with each edge. in this project we require all weights to be nonnegative.
#undirected graph definition: 
An undirected graph is graph, i.e., a set of objects (called vertices or nodes) that are connected together, where all the edges are bidirectional.

#implementation:
the graph is represented by set of nodes and edges. 
nodes  - map that holds the nodes of the graph according to the node's key.
edges - map that holds the edges of the graph.  given a key , returns his neighbors, and the weight corresponding to that neighbor. 
the neighbors are represented as hashmap. the key is the neighbor node, and the value is the weight.

#Algorithms:
I used a static variable called count that that marks that specific node was visited or settled that will be updated every time we run an algorithm.
the idea behind it is to avoid resetting all the nodes' tags that takes O(n) time.

##copy - 
make a deep copy of the graph. traverse over the nodes and create a new node to the copyGraph with the same key,tag and info.
In addition, traverse over the neighbors  of all the nodes (all the edges) and connect the corresponding nodes in the copyGraph.
O(n*m) when n is the number of nodes in the graph and m is the degree of the node.

##isConnected:
To check connectivity of a graph, we will try to traverse all nodes using BFS algorithm. After completing the traversal, if there is any node, which is not visited, then the graph is not connected.
Time Complexity of BFS = O(V+E) where V is vertices and E is edges.  time complexity of isConnected is also O(V+E).
 
 
##dijkstra:
The method finds the shortest path between a starting node and the rest of the graph. We will be using a heap data structure to always take the smallest current distance.
 I used a priority_queue that helps achieve the goal. 
For every node, we check if the distance of our node + the distance to his neighbor is smaller than the current distance of the neighbor.
 If this is true, we update the distance and add the node to our min-heap  and update the parent of the node.
 I used Pair<key,weight> in order to store the key of the node, and the updated weight from src to that node, because we want the PriorityQueue to pop the key with the minimum weight.
time complexity of dijkstra : O(ElogV), where E is the number of edges and V, the number of vertices. This is because queries on the heap takes logN time and we go through every node.

##shortestPathDist:
run dijkstra algorithm that finds the shortest path between a starting node and the rest of the graph. 
simply return the distance of dest. if distance map doesn't contain dest so return -1 (there is no path between src and dest).
time complexity: O(ElogV), where E is the number of edges and V, the number of vertices. This is because queries on the heap takes logN time and we go through every node.

##shortestPath:
run dijkstra algorithm that finds the shortest path between a starting node and the rest of the graph. 
 if distance map doesn't contain dest then return null  (there is no path between src and dest).
 else, traverse over the parent list of the nodes until we reached the src node. (O(n) when n is the number of nodes in the path).
time complexity : O(ElogV) + O(n)
