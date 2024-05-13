package hw5;

import java.util.*;


import java.io.*;

import hw4.Graph;

import hw4.Edge; 
import hw4.Node; 



public class ProfessorPaths{
    private Graph<String, String> graph;
    /**
     * @requires Node
     * @modifies Node
     * @effects Initializes a new Professor Paths object and the graph implemetation behind it
     *      */
    public ProfessorPaths(){
        this.graph = new Graph<String, String>();
    }
    /**
     * @requires Node
     * @modifies Node
     * @return number of nodes of g/2 (since this is an undiected graph)
     *      */
    public int numNodes(){
        return this.graph.numNodes();
    }
    /**
     * @requires Node
     * @modifies Node
     * @return number of edges of g/2 (since this is an undiected graph)
     *      */
    public int numEdges(){
        return (this.graph.numEdges()/2);
    }
   /**
 * Initializes a new graph from a given file.
 * 
 * @param filename The name of the file containing node and edge data.
 * @requires The nodes and edges in the file are properly formatted.
 * @modifies Populates the graph with nodes and edges from the specified file.
 * @effects Initializes a new graph with nodes and edges as documented in the given file.
 */
public void createNewGraph(String filename) {
    try {
        graph = new Graph<>(); // Create a new empty graph
        Map<String, Set<String>> profsTeaching = new HashMap<>(); // Map of professors to their subjects
        Set<String> profs = new HashSet<>(); // Set of unique professors

        // Parse data from the specified file
        ProfessorParser.readData(filename, profsTeaching, profs);

        // Add nodes to the graph for each unique professor
        for (String profName : profs) {
            graph.addNode(new Node<>(profName));
        }

        // Add edges to the graph based on the teaching map
        for (Map.Entry<String, Set<String>> entry : profsTeaching.entrySet()) {
            String course = entry.getKey(); // The course label for the edges

            // Iterate through each professor in the set
            for (String professor1Name : entry.getValue()) {
                Node<String> prof1 = graph.getNode(professor1Name);

                // Connect to all other professors in the same course
                for (String professor2Name : entry.getValue()) {
                    if (!professor1Name.equals(professor2Name)) {
                        Node<String> prof2 = graph.getNode(professor2Name);

                        // Add bidirectional edges between the professors with the course as the label
                        graph.addEdge(prof1, prof2, 1.0, course);
                        graph.addEdge(prof2, prof1, 1.0, course);
                    }
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace(); // Print the stack trace if there's an I/O exception
    }
}
       /**
 * Finds the path between two nodes in the graph and returns a string representation of the path.
 * 
 * @param node1 The name of the starting node for the path.
 * @param node2 The name of the ending node for the path.
 * @requires Nodes exist in the graph.
 * @modifies None.
 * @effects None.
 * @throws None.
 * @return A string representing the path between node1 and node2. If no path exists, returns "no path found".
 *         If either of the nodes are not in the graph, indicates which node is missing.
 */
public String findPath(String node1, String node2) {
    // Check if both nodes are in the graph; return appropriate message if not
    boolean n1Exists = graph.findNode(node1);
    boolean n2Exists = graph.findNode(node2);

    if (!n1Exists || !n2Exists) {
        StringBuilder errorMessage = new StringBuilder();

        if (!n1Exists) {
            errorMessage.append("unknown professor ").append(node1).append("\n");
        }

        if (!n2Exists) {
            errorMessage.append("unknown professor ").append(node2).append("\n");
        }

        return errorMessage.toString(); // Return early if either node doesn't exist
    }

    // Check if the nodes are the same, indicating a cycle or no path needed
    if (node1.equals(node2)) {
        return "path from " + node1 + " to " + node2 + ":\n"; // No additional path needed
    }

    // Attempt to find the path between the two nodes
    Node<String> startNode = graph.getNode(node1);
    Node<String> endNode = graph.getNode(node2);
    List<Edge<String, String>> path = graph.shortestPathBFS(startNode, endNode);

    // If no path was found, return an appropriate message
    if (path.isEmpty()) {
        return "path from " + node1 + " to " + node2 + ":\nno path found\n";
    }

    // Construct the path string from the list of edges
    StringBuilder pathBuilder = new StringBuilder();
    pathBuilder.append("path from ").append(node1).append(" to ").append(node2).append(":\n");

    for (Edge<String, String> edge : path) {
        pathBuilder.append(edge.getStartNode().getLabel())
                   .append(" to ")
                   .append(edge.getEndNode().getLabel())
                   .append(" via ")
                   .append(edge.getLabel())
                   .append("\n");
    }

    return pathBuilder.toString(); // Return the constructed path
}

}