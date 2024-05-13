package hw6;

import java.util.*;

import org.junit.Test.None;

import java.io.*;

import hw4.Graph;

import hw4.Edge; 
import hw4.Node;

public class LegoPaths{
    private Graph<String, Double> graph;
    /**
    * @param None
     * @requires Node
     * @modifies Node
     * @effects Initializes a new Professor Paths object and the graph implemetation behind it
     * @throws None
     * @return None
     *      */
    public LegoPaths(){
        this.graph = new Graph<String, Double>();
    }
/**
 * Reads data from a CSV file and populates a map with <lego_piece, Set-of-lego-sets> pairs.
 * @param filename The name of the CSV file to read.
 * @param pieces_to_sets A map that maps each Lego piece to a set of all Lego sets containing that piece.
 * @modifies pieces_to_sets
 * @throws IOException If the file cannot be read or does not follow the expected CSV format.
 */
private static void readData(String filename, Map<String, Set<String>> pieces_to_sets) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;

        while ((line = reader.readLine()) != null) {
            if (!isValidCSV(line)) {
                throw new IOException("File " + filename + " is not a valid CSV (\"PIECE\",\"SET\") file.");
            }

            int separatorIndex = line.indexOf("\",\"");
            String lego_piece = line.substring(1, separatorIndex);
            String lego_set = line.substring(separatorIndex + 3, line.length() - 1);

            // Update or create the set of Lego sets for this piece
            pieces_to_sets.computeIfAbsent(lego_piece, k -> new TreeSet<>()).add(lego_set);
        }
    }
}

/**
 * Checks if a given CSV line has the correct format ("PIECE","SET").
 * @param line The CSV line to check.
 * @return true if the format is correct, false otherwise.
 */
private static boolean isValidCSV(String line) {
    return line.startsWith("\"") && line.endsWith("\"") && line.contains("\",\"");
}

/**
 * Creates a new graph based on the nodes and edges documented in the specified file.
 * @param filename The name of the file to read nodes and edges from.
 * @modifies Updates the graph with nodes and edges from the file.
 * @effects Modifies the graph with the data from the file.
 * @throws IOException If there's an error reading the file.
 */
public void createNewGraph(String filename) {
    graph = new Graph<String, Double>();
    Map<String, Set<String>> nodeConnections = new HashMap<>();

    try {
        readData(filename, nodeConnections);

        // Add all nodes to the graph
        for (String nodeKey : nodeConnections.keySet()) {
            graph.addNode(new Node<>(nodeKey));
        }

        // Add edges based on common connections
        List<String> nodeKeys = new ArrayList<>(nodeConnections.keySet());
        for (int i = 0; i < nodeKeys.size(); i++) {
            for (int j = i + 1; j < nodeKeys.size(); j++) {
                String key1 = nodeKeys.get(i);
                String key2 = nodeKeys.get(j);

                Set<String> commonConnections = new TreeSet<>(nodeConnections.get(key1));
                commonConnections.retainAll(nodeConnections.get(key2));

                if (!commonConnections.isEmpty()) {
                    double weight = 1.0 / commonConnections.size();

                    Node<String> node1 = graph.getNode(key1);
                    Node<String> node2 = graph.getNode(key2);

                    graph.addEdge(node1, node2, weight, weight);
                    graph.addEdge(node2, node1, weight, weight);
                }
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
}

        /**
    * @param node1 (string that the path starts from)
    @param node2 (string that the path ends from)
     * @requires Node
     * @modifies Node
     * @effects None
     * @throws None
     * @return A String representing the path between node1 and node2
     * "No path found" if no path exists or states if either of the nodes
     * arent in the graph
     *      */

   /**
 * Finds the shortest path between two nodes and returns a string describing it.
 * @param node1 The start node.
 * @param node2 The end node.
 * @return A string describing the path or an appropriate error message if no path exists.
 */
public String findPath(String node1, String node2) {
    boolean node1Exists = graph.findNode(node1);
    boolean node2Exists = graph.findNode(node2);

    String path = "Path from " + node1 + " to " + node2 + ":\n";

    // Check if either node doesn't exist in the graph
    if (!node1Exists || !node2Exists) {
        if (!node1Exists) {
            return "Unknown part: " + node1 + "\n";
        } else {
            return "Unknown part: " + node2 + "\n";
        }
    }

    // Check for cycles (node1 and node2 are the same)
    if (node1.equals(node2)) {
        path += "Total cost: 0.000\n";
        return path;
    }

    // Find the shortest path between node1 and node2
    Iterator<Edge<String, Double>> edgeIterator = graph.shortestPathDikjstra(graph.getNode(node1), graph.getNode(node2)).iterator();

    // Check if a path was found
    if (!edgeIterator.hasNext()) {
        return "No path found\n";
    }

    // Construct the path details and calculate the total weight
    Double totalWeight = 0.0;
    while (edgeIterator.hasNext()) {
        Edge<String, Double> edge = edgeIterator.next();
        path += edge.getStartNode().getLabel() + " to " + edge.getEndNode().getLabel() + " with weight " + String.format("%.3f", edge.getLabel()) + "\n";
        totalWeight += edge.getLabel();
    }

    path += "Total cost: " + String.format("%.3f", totalWeight) + "\n";
    return path;
}

}