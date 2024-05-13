package hw4;

//need to add ==> detect cycles, detect negative cycles, isTree (No cycles and |V| = |E| + 1)
//strongly connected components, Topological Sort (Kahn's algo), Minimum Spanning Tree
//longest path ==> negate all weights whne adding to distances but same apprach
//optimize shortestPath for graphs with negative cycles (add BellmanFord Approach)
//add Tree Algorithms (only if is tree) ==> Common Ancestor, Tree Height, Center(s)
import java.util.*;

import org.junit.Test.None;

public class Graph<T extends Comparable<T>, S extends Comparable<S>>{

    /**
     * Graph represents a directed Labeled multigraph
     */
    protected HashMap<T, Node<T>> mapNode;
    protected HashMap<Node<T>, SortedSet<Edge<T, S>>> mapEdges;
    protected int nodeCount;
    protected int edgeCount;

  /**
 * Abstraction Function:
 * - This graph contains a hashmap that maps node labels to node objects.
 * - It also has a hashmap that maps each node object to a set of edge objects.
 * - Each edge object is unique within the graph.
 * - It allows for edges to exist from node 'a' to node 'b' and also from node 'b' to node 'a'.
 */

/**
 * Representation Invariant:
 * - No node in the graph should be equivalent to Node.NaN, indicating invalid nodes.
 * - Every edge in the graph must have a valid start and end node.
 * - The parent node of each edge should contain the edge in its set of outgoing edges.
 * - All edges must be correctly associated with their start and end nodes.
 */


    /**
    * @param None
     * @requires None
     * @modifies None
     * @effects Creates Graph with a hashmap to find the nodes and a noher hashmap to find the edges
     * @throws None
     * @return None
     */
    public Graph(){
        this.mapNode = new HashMap<T, Node<T>>();
        this.mapEdges = new HashMap<Node<T>, SortedSet<Edge<T, S>>>();
        this.nodeCount = 0;
        this.edgeCount = 0;
    }
    /**
    * @param n, n is the node we are checking if it is in the Node
     * @requires None
     * @modifies None
     * @effects None
     * @throws None
     * @return true iff n is in Graph
     */
    public boolean findNode(Node<T> n){
        return this.mapEdges.containsKey(n);
    }
    /**
    * @param nodeLabel is the label of the node we are checking if it is in the Node
     * @requires None
     * @modifies None
     * @effects None
     * @throws None
     * @return true iff there exists a node with the label nodeLabel is in Graph
     */
    public boolean findNode(T nodeLabel){
        return this.mapNode.containsKey(nodeLabel);
    }
    /**
    * @param nodeLabel is the label of the node we are locating if it is in the Node
     * @requires there exists a node with label nodeLabel && 0 <= index < numNodes
     * @modifies None
     * @effects None
     * @throws None
     * @return Node with label nodeLabel
     */
    public Node<T> getNode (T nodeLabel){
        if(this.mapNode.containsKey(nodeLabel)){
            return this.mapNode.get(nodeLabel);
        }
        return new Node<T>();
    }
    /**
    * @param None
     * @modifies None
     * @effects None
     * @throws None
     * @return number of Nodes
     */
    public int numNodes(){
        return nodeCount;
    }
    /**
    * @param None
     * @modifies None
     * @effects None
     * @throws None
     * @return number of Edges
     */
    public int numEdges(){
        return edgeCount;
    }
/**
 * Adds a node to the graph if it is not already present and is a valid node.
 * @param n The node to add to the graph.
 * @requires n is not in the graph and is not NaN (not a number or not applicable node).
 * @modifies Nodes and Edges by adding the new node and initializing its edge set.
 * @effects Increases the number of nodes in the graph by one if the node is added.
 * @throws None
 * @return None
 */
public void addNode(Node<T> n) {
    // Check if the node is not already in the graph and is not a NaN-type node
    if (!this.mapNode.containsKey(n.getLabel()) && !n.isNaN()) {
        // Create a new TreeSet for holding edges of this node
        SortedSet<Edge<T, S>> edges = new TreeSet<>();

        // Add the node to the Nodes map
        this.mapNode.put(n.getLabel(), n);

        // Initialize its edges set in the Edges map
        this.mapEdges.put(n, edges);

        // Increment the total number of nodes in the graph
        this.nodeCount++;
    }
}

/**
 * Adds an edge between two nodes in the graph with a specified label and weight.
 * @param n1 The starting node.
 * @param n2 The ending node.
 * @param weight The weight of the edge.
 * @param label The label for the edge.
 * @requires n1 and n2 are valid nodes (not NaN) and edgeLabel is non-empty.
 * @modifies Adds an edge to the graph, creating nodes if they do not exist.
 * @effects Creates a new edge with the specified weight and label and adds it to the graph.
 * @throws None
 * @return None
 */
public void addEdge(Node<T> n1, Node<T> n2, Double weight, S label) {
    Node<T> NaN = new Node<>();  // Represents an invalid node
    
    // If either node is invalid, return without adding the edge
    if (n1.compareTo(NaN) == 0 || n2.compareTo(NaN) == 0) {
        return;
    }

    // Ensure nodes are in the graph
    this.addNode(n1);
    this.addNode(n2);

    // Create the new edge
    Edge<T, S> newEdge = new Edge<>(n1, n2, label);
    newEdge.setWeight(weight);

    // Get the edge set for the starting node, initializing if necessary
    SortedSet<Edge<T, S>> edgeSet = this.mapEdges.getOrDefault(n1, new TreeSet<>());
    edgeSet.add(newEdge);  // Add the new edge to the set

    // Update the graph and edge count
    this.mapEdges.put(n1, edgeSet);
    this.edgeCount++;  // Increment edge count
}

  /**
 * Gets the edge between two nodes in the graph.
 * @param Start The starting node.
 * @param Finish The ending node.
 * @requires Start and Finish are in the graph, and there's an edge connecting them.
 * @modifies None
 * @effects None
 * @throws None
 * @return The edge connecting Start and Finish, or a default edge if no such edge is found.
 */
public Edge<T, S> getEdge(Node<T> Start, Node<T> Finish) {
    if (!this.mapEdges.containsKey(Start)) {
        return new Edge<>();  // Return a default edge if the starting node has no edges
    }

    // Find the edge from the Start node to the Finish node
    for (Edge<T, S> edge : this.mapEdges.get(Start)) {
        if (edge.getEndNode().compareTo(Finish) == 0) {
            return edge;  // Return the edge if found
        }
    }

    // Return a default edge if no edge is found
    return new Edge<T, S>();
}


    /**
 * Checks if an edge exists between two nodes in the graph.
 * @param Start The starting node.
 * @param Finish The ending node.
 * @requires Start and Finish are in the graph, and there's a potential edge connecting them.
 * @modifies None
 * @effects None
 * @throws None
 * @return true if an edge exists between Start and Finish, false otherwise.
 */
public boolean findEdge(Node<T> Start, Node<T> Finish) {
    if (!this.mapEdges.containsKey(Start)) {
        return false;  // No edges from the Start node
    }

    // Check for an edge from Start to Finish
    for (Edge<T, S> edge : this.mapEdges.get(Start)) {
        if (edge.getEndNode().compareTo(Finish) == 0) {
            return true;  // Edge found
        }
    }

    return false;  // No edge found
}


     
    /**
     * Computes the shortest distance between two nodes in the graph using Dijkstra's Algorithm.
     *
     * @param start the starting node for the path
     * @param end the target node for the path
     * @return the shortest distance between the start and end nodes
     *          or Double.MAX_VALUE if no path exists
     * @throws IllegalArgumentException if the start or end node is not in the graph
     * @effects none
     * @modifies none
     */
public double getDistance(Node<T> start, Node<T> end) {
    // If start or end nodes are not in the graph, return the maximum value
    if (!this.mapEdges.containsKey(start) || !this.mapEdges.containsKey(end)) {
        return Double.MAX_VALUE;
    }

    // Queue for processing nodes and a map to track shortest distances
    LinkedList<Node<T>> queue = new LinkedList<>();
    HashMap<Node<T>, Double> distances = new HashMap<>();

    // Initialize distances to Double.MAX_VALUE, set the start node's distance to 0, and enqueue the start node
    for (Node<T> node : this.mapNode.values()) {
        distances.put(node, Double.MAX_VALUE);
    }

    distances.put(start, 0.0);
    queue.add(start);

    while (!queue.isEmpty()) {
        Node<T> curr = queue.poll();

        // Process each edge for the current node
        for (Edge<T, S> edge : this.mapEdges.get(curr)) {
            Node<T> neighbor = edge.getEndNode();
            double weight = edge.getWeight();
            double currentDistance = distances.get(curr) + weight;

            // If a shorter path is found, update the distance and add the neighbor to the queue
            if (currentDistance < distances.get(neighbor)) {
                distances.put(neighbor, currentDistance);
                queue.add(neighbor);
            }
        }

        // If the current node is the end node, return the shortest distance
        if (curr.equals(end)) {
            return distances.get(curr);
        }
    }

    // If no path exists, return Double.MAX_VALUE
    return Double.MAX_VALUE;
}

    /**
    * @param start, End, find a path between start and finish via Dijstra's Algorithm
     * @requires start and end are in graph, returns an emptry array list of edges if either start or end
     * are not in graph
     * @modifies None
     * @effects None
     * @throws None
          * @return an array of Strings formatted as NodeLabel + ( + weight from previous node to curr node + )
     */
    public List<Edge<T, S>> shortestPathDikjstra(Node<T> start, Node<T> end){
        if (this.mapEdges.containsKey(start) && this.mapEdges.containsKey(end)){
            if (start.equals(end)){
                return new ArrayList<Edge<T, S>>();
            }
            HashMap<Node<T>, Double> distances = new HashMap<Node<T>, Double>();
            HashMap<Node<T>, Node<T>> prevs = new HashMap<Node<T>, Node<T>>();
            LinkedList<Node<T>> queue = new LinkedList<>();
          // Initialize distances to maximum and predecessors to NaN
for (Node<T> node : this.mapNode.values()) {
    distances.put(node, Double.MAX_VALUE); // Set initial distance to max
    prevs.put(node, node.NaN()); // Set initial predecessor to NaN or a special invalid node
}

            Node<T> curr;
            distances.put(start, 0.0);
            queue.add(start);
            while(queue.size() > 0){
                curr = queue.poll();
                Iterator<Edge<T, S>> currEdge = this.mapEdges.get(curr).iterator();
               // Iterate through all edges from the current node
while (currEdge.hasNext()) {
    Edge<T, S> edge = currEdge.next(); // Retrieve the current edge
    Node<T> neighbor1 = edge.getEndNode(); // Get the neighboring node
    
    double currentDistance = distances.get(curr); // Distance to the current node
    double edgeWeight = edge.getWeight(); // Weight of the edge
    
    // Calculate the new distance to the neighboring node
    double newDistance = currentDistance + edgeWeight;
    
    // If the new distance is shorter, update distances and predecessors
    if (newDistance < distances.get(neighbor1)) {
        distances.put(neighbor1, newDistance); // Update the distance
        prevs.put(neighbor1, curr); // Update the predecessor
        queue.add(neighbor1); // Add the neighbor to the queue for further processing
    }
}

            }
            return generatePath(prevs, start, end);
        }
        return new ArrayList<Edge<T, S>>();
    }
   /**
 * Generates the path from a given start to end using the predecessors map.
 * @param predecessors A map indicating the predecessor of each node in the path.
 * @param start The starting node.
 * @param end The ending node.
 * @return An ArrayList of edges forming the path from start to end, or an empty list if no path is found.
 */
private List<Edge<T, S>> generatePath(HashMap<Node<T>, Node<T>> predecessors, Node<T> start, Node<T> end) {
    List<Edge<T, S>> path = new ArrayList<>();
    Node<T> current = end;

    // Traverse the predecessors map from end to start
    while (current != null && !current.equals(start)) {
        Node<T> predecessor = predecessors.get(current);
        if (predecessor == null) {
            return new ArrayList<>(); // Return an empty list if there's no valid predecessor
        }

        // Find the edge between predecessor and current
        Edge<T, S> edge = getEdge(predecessor, current);
        if (edge == null) {
            return new ArrayList<>(); // If no edge is found, return an empty list
        }

        path.add(0, edge); // Insert at the beginning to maintain correct order
        current = predecessor; // Move to the next predecessor
    }

    // Check if the start node is in the path
    if (!path.isEmpty() && path.get(0).getStartNode().equals(start)) {
        return path; // Return the valid path from start to end
    }

    return new ArrayList<>(); // Return empty if no valid path is found
}


     /**
    * @param start, End, find a path between start and finish via BFS
     * @requires start and end are in graph, returns an emptry array list of edges if either start or end
     * are not in graph
     * @modifies None
     * @effects None
     * @throws None
     * @return an array of Strings formatted as NodeLabel + ( + weight from previous node to curr node + )
     */
    public List<Edge<T, S>> shortestPathBFS(Node<T> start, Node<T> end){
        if(this.mapEdges.containsKey(start) && this.mapEdges.containsKey(end)){
            LinkedList<Node<T>> queue = new LinkedList<Node<T>>();
            HashMap<Node<T>, Node<T>> prevs = new HashMap<Node<T>, Node<T>>();
            HashSet<Node<T>> visited = new HashSet<Node<T>>();
            queue.add(start);
            visited.add(start);
            Node<T> NaN = new Node<T>();
            prevs.put(start, NaN);
            // Loop while the queue is not empty
while (!queue.isEmpty()) {
    Node<T> current = queue.poll(); // Get the next node from the queue

    // If the current node is the end, return the generated path
    if (current.equals(end)) {
        return generatePath(prevs, start, end); // Early return if the end node is found
    }

    // Iterate over the edges of the current node
    Iterator<Edge<T, S>> edgeIterator = this.mapEdges.get(current).iterator();

    // Process each edge and add unvisited nodes to the queue
    while (edgeIterator.hasNext()) {
        Node<T> neighbor1 = edgeIterator.next().getEndNode(); // Get the neighboring node

        if (!visited.contains(neighbor1)) { // If the neighbor hasn't been visited
            prevs.put(neighbor1, current); // Record the predecessor
            queue.add(neighbor1); // Add to the queue for further exploration
            visited.add(neighbor1); // Mark as visited
        }
    }
}

            return new ArrayList<Edge<T, S>>();
        }
        return new ArrayList<Edge<T, S>>();
    }
      /**
 * Returns an iterator over the parents of a specified node in the graph.
 * @param node The node whose parents are to be iterated over.
 * @return An iterator over the parents, or an empty iterator if the node has no parents.
 */
public Iterator<Node<T>> parentIterator(Node<T> node) {
    SortedSet<Node<T>> parentSet = new TreeSet<>();
    for (Map.Entry<Node<T>, SortedSet<Edge<T, S>>> entry : this.mapEdges.entrySet()) {
        for (Edge<T, S> edge : entry.getValue()) {
            if (edge.getEndNode().equals(node)) {
                parentSet.add(edge.getStartNode()); // Add parent node to the set
            }
        }
    }
    
    return parentSet.isEmpty() ? Collections.emptyIterator() : parentSet.iterator(); // Return empty if no parents found
}

   
    /**
 * Returns an iterator over the nodes in the graph.
 * @return An iterator for the nodes if the graph has nodes, or an empty iterator if the graph is empty.
 */
public Iterator<T> NodesIterator() {
    if (nodeCount > 0) {
        SortedSet<T> nodesSet = new TreeSet<>();
        for (T key : this.mapNode.keySet()) {
            nodesSet.add(key);
        }
        return nodesSet.iterator();
    }
    return Collections.emptyIterator(); // Return an empty iterator if the graph is empty
}

     /**
 * Returns an iterator over the children of a specified node in the graph.
 * @param node The node whose children are to be iterated over.
 * @return An iterator over the children, or an empty iterator if the node has no children.
 */
public Iterator<Node<T>> childIterator(Node<T> node) {
    if (this.mapEdges.containsKey(node)) {
        SortedSet<Node<T>> childrenSet = new TreeSet<>();
        for (Edge<T, S> edge : this.mapEdges.get(node)) {
            childrenSet.add(edge.getEndNode()); // Add the child node to the set
        }
        return childrenSet.iterator();
    }
    return Collections.emptyIterator(); // Return empty if no children are found
}

    /**
    * @param n (node in Graph)
     * @requires None
     * @modifies None
     * @effects creates sorted list of children of n (if in graph)
     * @throws None
     * @return Iterator for children is node is in Graph, empty Iterator if n doesnt have children
     */
    public Iterator<String> childIteratorLabels(Node<T> n) {
        if(this.mapEdges.containsKey(n)){
            SortedSet<String> children = new TreeSet<String>();
            Iterator<Edge<T, S>> it_edge = this.mapEdges.get(n).iterator();
            Edge<T, S> curr;
            while(it_edge.hasNext()){
                curr = it_edge.next();
                children.add((String)curr.getEndNode().getLabel() + "("  + (String)curr.getLabel() + ")");
            }
            return children.iterator();
        }
        return Collections.emptyIterator();
    }
  
}