package hw4;

import java.util.*;

import org.junit.Test.None;
public class GraphWrapper {
    private Graph<String, String> graph;
    private int nodeCount;
    private int edgeCount;

    /**
    * @param None
     * @requires None
     * @modifies None
     * @effects Creates GrapWrapper that represents a graph g
     * @throws None
     * @return None
     */
    public GraphWrapper(){
        this.graph = new Graph<String, String>();
        this.nodeCount = 0;
        this.edgeCount = 0;
    }
    /**
 * Adds a node with the specified label to the graph.
 * @param nodeData The label of the node to be added.
 * @modifies g by adding the node if it does not already exist.
 * @effects Increases the number of nodes in the graph by one if a new node is added.
 * @throws None
 * @return None
 */
public void addNode(String nodeData) {
    // Check if the node already exists
    if (!graph.findNode(nodeData)) {
        // Create and add the new node to the graph
        Node<String> newNode = new Node<>(nodeData);
        graph.addNode(newNode);

        // Increment the node count
        nodeCount++;
    }
}


        /**
    * @param None
     * @requires None
     * @modifies None 
     * @effects None
     * @throws None
     * @return this.numNodes
     */

     public int getNodes(){
        return nodeCount;
     }

  /**
 * Adds an edge between two nodes in the graph.
 * @param parentNode The label of the parent node.
 * @param childNode The label of the child node.
 * @param edgeLabel The label for the edge.
 * @modifies g by adding the edge.
 * @effects Increases the number of edges in the graph by one (if an edge is added).
 * @throws None
 * @return None
 */
public void addEdge(String parentNode, String childNode, String edgeLabel) {
    Node<String> parent = graph.findNode(parentNode) ? graph.getNode(parentNode) : new Node<>(parentNode);
    Node<String> child = graph.findNode(childNode) ? graph.getNode(childNode) : new Node<>(childNode);

    // Add the edge to the graph
    graph.addEdge(parent, child, 1.0, edgeLabel);

    // Update the node and edge counts
    nodeCount = graph.numNodes();
    edgeCount = graph.numEdges();
}


    /**
    * @param None
     * @requires None
     * @modifies None 
     * @effects None
     * @throws None
     * @return this.numNodes
     */

     public int getEdges(){
        return edgeCount;
     }
    /**
    * @param None
     * @requires g.numNodes >= 0
     * @modifies None
     * @effects None
     * @throws None
     * @return returns g.NodeIterator
     */
    public Iterator<String> listNodes(){
        return graph.NodesIterator();
    }
   /**
 * Lists the children of a specified node.
 * @param parentNode The label of the parent node.
 * @requires The node with label parentNode must exist in the graph.
 * @modifies None
 * @effects None
 * @throws None
 * @return An iterator over the children of the specified node if it exists in the graph, or an empty iterator if it does not.
 */
public Iterator<String> listChildren(String parentNode) {
    if (graph.findNode(parentNode)) {
        return graph.childIteratorLabels(graph.getNode(parentNode));
    } else {
        return Collections.emptyIterator();
    }
}

}