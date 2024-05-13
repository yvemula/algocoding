package hw7;

import hw4.Graph;
import hw4.Node;
import hw4.Edge;
import java.util.*;

import java.io.*;

public class CampusPaths{
    private Graph<String, String> graph;
    private HashMap<String, Integer> buildingL;
    private HashMap<Integer, String> labelB;
    private ArrayList<String> instruct;
    private HashMap<Integer, ArrayList<Integer>> coor;

        /**
     * @param None
     * @requires None
     * @modifies building_to_label, label_to_building, g, instructions, coordinates
     * @effects creates a Campus Path object 
     * @throws None
     * @return None
     **/
    public CampusPaths(){
        this.graph = new Graph<String, String>();
        this.buildingL = new HashMap<String, Integer>();
        this.labelB = new HashMap<Integer, String>();
        this.instruct = new ArrayList<String>();
        this.instruct.add("b");
        this.instruct.add("r");
        this.instruct.add("q");
        this.instruct.add("m");
        this.coor = new HashMap<Integer, ArrayList<Integer>>();
    }
/**
 * Retrieves a formatted string listing all buildings on the RPI campus,
 * excluding intersections based on a specific range of building IDs.
 * 
 * @param None
 * @requires None
 * @modifies None
 * @effects None
 * @throws None
 * @return A string of all the buildings on the RPI campus, excluding intersections,
 *         formatted as "BuildingName,BuildingID\n".
 */
public String allBuildings() {
    // Create a sorted set of building names
    SortedSet<String> buildingsSorted = new TreeSet<>();

    // Populate the set with building names from the map
    for (Map.Entry<String, Integer> entry : buildingL.entrySet()) {
        buildingsSorted.add(entry.getKey());
    }

    // Initialize a StringBuilder for efficient string concatenation
    StringBuilder buildings = new StringBuilder();

    // Iterate over sorted building names and filter based on the specified ID range
    for (String building : buildingsSorted) {
        Integer buildingID = buildingL.get(building);
        // Exclude building IDs that are within the intersection range
        if (buildingID < 93 || buildingID > 143) {
            buildings.append(building)
                     .append(",")
                     .append(buildingID)
                     .append("\n");
        }
    }

    return buildings.toString();
}
/**
 * Returns a string containing all valid instructions for the program.
 *
 * @param None
 * @requires None
 * @modifies None
 * @effects None
 * @throws None
 * @return A string listing all the instructions for the program.
 */
public String printMenu() {
    StringBuilder menu = new StringBuilder();

    // List of instructions with their corresponding descriptions
    String[] instructions = {
        "lists all buildings",
        "prints directions for the shortest route between any two buildings",
        "quits the program",
        "prints a menu of all commands"
    };

    // Iterate through the instruction descriptions and format them into the menu string
    for (int i = 0; i < instructions.length; i++) {
        menu.append(this.instruct.get(i)).append(" ").append(instructions[i]);
        if (i < instructions.length - 1) {
            menu.append("\n"); // Add newline except for the last instruction
        }
    }

    return menu.toString();
}

            /**
     * @param Start ==> the node at the start of the edge
     * @param End ==> the node at the start of the edge
     * @requires None
     * @modifies None
     * @effects None
     * @throws None
     * @return The angle of the Edge from Start to Finish
     **/
    private double getAngle(Node<String> Start, Node<String> Finish) {
        ArrayList<Integer> startCoords = coor.get(buildingL.get(Start.getLabel()));
        ArrayList<Integer> endCoords = coor.get(buildingL.get(Finish.getLabel()));
        double num = endCoords.get(1) - startCoords.get(1);
        double dem = endCoords.get(0) - startCoords.get(0);
        if (dem == 0) {
            return num >= 0 ? 90.0 : 270.0;
        }
        double ret = Math.toDegrees(Math.atan(num / dem)) + 90;
        if (num < 0 && dem < 0) {
            ret += 180;
        } else if (num > 0 && dem < 0) {
            ret += 180;
        } else if (num < 0 && dem > 0) {
            ret += 360;
        }
        ret %= 360;
        if (ret < 0) {
            ret += 360;
        }
        return ret;
    }
       /**
 * Returns a string that represents the direction (from start to finish) of the edge based on its angle.
 *
 * @param currEdge The edge for which the direction is determined.
 * @requires None.
 * @modifies None.
 * @effects None.
 * @throws None.
 * @return A string representing the direction of the edge.
 */
private String getDirection(Edge<String, String> currEdge) {
    double angle = getAngle(currEdge.getStartNode(), currEdge.getEndNode());

    // Determine the direction based on angle ranges
    if (angle >= 337.5 || angle < 22.5) {
        return "North";
    } else if (angle >= 22.5 && angle < 67.5) {
        return "NorthEast";
    } else if (angle >= 67.5 && angle < 112.5) {
        return "East";
    } else if (angle >= 112.5 && angle < 157.5) {
        return "SouthEast";
    } else if (angle >= 157.5 && angle < 202.5) {
        return "South";
    } else if (angle >= 202.5 && angle < 247.5) {
        return "SouthWest";
    } else if (angle >= 247.5 && angle < 292.5) {
        return "West";
    } else if (angle >= 292.5 && angle < 337.5) {
        return "NorthWest";
    }

    return ""; // Default return in case of an unexpected angle
}
/**
 * Retrieves a string representation of the shortest path from building1 to building2.
 *
 * @param building1 The starting building for the path.
 * @param building2 The ending building for the path.
 * @requires Both buildings are on the RPI campus.
 * @modifies None.
 * @effects None.
 * @throws None.
 * @return A string representing the shortest path from building1 to building2.
 *         If no path exists, it returns a message indicating no path.
 *         If one or both buildings are unknown, it indicates which building(s) are unknown.
 */
private String getPath(String building1, String building2) {
    // Check if both buildings are on the RPI campus
    boolean building1Exists = buildingL.containsKey(building1);
    boolean building2Exists = buildingL.containsKey(building2);

    // Handle unknown buildings
    if (!building1Exists || !building2Exists) {
        StringBuilder response = new StringBuilder();

        if (!building1Exists) {
            response.append("Unknown building: [").append(building1).append("]");
        }

        if (!building2Exists && !building1.equals(building2)) {
            if (response.length() > 0) {
                response.append("\n");
            }
            response.append("Unknown building: [").append(building2).append("]");
        }

        return response.toString();
    }

    // Start constructing the path description
    StringBuilder path = new StringBuilder("Path from ")
            .append(building1).append(" to ").append(building2).append(":\n");

    if (building1.equals(building2)) {
        path.append("Total distance: 0.000 pixel units.");
        return path.toString(); // Return immediately if the buildings are the same
    }

    // Retrieve the nodes for the buildings
    Node<String> startNode = this.graph.getNode(building1);
    Node<String> endNode = this.graph.getNode(building2);

    // Get the shortest path using Dijkstra's algorithm
    List<Edge<String, String>> shortestPath = this.graph.shortestPathDikjstra(startNode, endNode);

    if (shortestPath.isEmpty()) {
        return "There is no path from " + building1 + " to " + building2 + ".";
    }

    // Calculate the total distance and build the path description
    double totalWeight = 0.0;

    for (Edge<String, String> edge : shortestPath) {
        totalWeight += edge.getWeight();
        path.append("\tWalk ")
            .append(getDirection(edge))
            .append(" to (")
            .append(edge.getEndNode().getLabel())
            .append(")\n");
    }

    path.append("Total distance: ")
        .append(String.format("%.3f", totalWeight))
        .append(" pixel units.");

    return path.toString();
}



          /**
 * Checks whether a given string represents a valid integer.
 *
 * @param str The string to check.
 * @requires None.
 * @modifies None.
 * @effects None.
 * @throws None.
 * @return True if the string can be parsed as an integer, false otherwise.
 */
private static boolean isInteger(String str) {
    try {
        Integer.parseInt(str); // Attempt to parse the string as an integer
        return true; // If successful, return true
    } catch (NumberFormatException e) {
        return false; // If parsing fails, return false
    }
}

           /**
 * Determines the shortest path between two buildings, given either their names or building codes.
 *
 * @param input1 The first building's name or code.
 * @param input2 The second building's name or code.
 * @requires Both inputs are valid building names or codes on the RPI campus.
 * @modifies None.
 * @effects None.
 * @throws None.
 * @return A string representing the path with the shortest distance from one building to another.
 *         If buildings are unknown, it returns an appropriate message.
 */
public String pathCommand(String input1, String input2) {
    StringBuilder response = new StringBuilder();

    boolean input1IsInt = isInteger(input1);
    boolean input2IsInt = isInteger(input2);

    if (input1IsInt && input2IsInt) {
        // Both inputs are building codes
        int code1 = Integer.parseInt(input1);
        int code2 = Integer.parseInt(input2);

        boolean validCode1 = (code1 < 93 || code1 > 143) && labelB.containsKey(code1);
        boolean validCode2 = (code2 < 93 || code2 > 143) && labelB.containsKey(code2);

        if (validCode1 && validCode2) {
            return getPath(labelB.get(code1), labelB.get(code2));
        }

        if (!validCode1) {
            response.append("Unknown building: [").append(code1).append("]");
        }

        if (!validCode2 && code1 != code2) {
            if (response.length() > 0) {
                response.append("\n");
            }
            response.append("Unknown building: [").append(code2).append("]");
        }
    } else if (input1IsInt) {
        // Input1 is a building code, Input2 is a building name
        int code1 = Integer.parseInt(input1);

        if ((code1 < 93 || code1 > 143) && labelB.containsKey(code1) && buildingL.containsKey(input2)) {
            return getPath(labelB.get(code1), input2);
        }

        if (!labelB.containsKey(code1) || (code1 > 93 && code1 < 143)) {
            response.append("Unknown building: [").append(code1).append("]");
        }

        if (!buildingL.containsKey(input2)) {
            if (response.length() > 0) {
                response.append("\n");
            }
            response.append("Unknown building: [").append(input2).append("]");
        }
    } else if (input2IsInt) {
        // Input1 is a building name, Input2 is a building code
        int code2 = Integer.parseInt(input2);

        if ((code2 < 93 || code2 > 143) && buildingL.containsKey(input1) && labelB.containsKey(code2)) {
            return getPath(input1, labelB.get(code2));
        }

        if (!buildingL.containsKey(input1)) {
            response.append("Unknown building: [").append(input1).append("]");
        }

        if (!labelB.containsKey(code2) || (code2 > 93 && code2 < 143)) {
            if (response.length() > 0) {
                response.append("\n");
            }
            response.append("Unknown building: [").append(code2).append("]");
        }
    } else {
        // Both inputs are building names
        return getPath(input1, input2);
    }

    return response.toString();
}
/**
 * Calculates the Euclidean distance between two points given their x and y coordinates.
 *
 * @param x_1 The x-coordinate of the start node.
 * @param y_1 The y-coordinate of the start node.
 * @param x_2 The x-coordinate of the end node.
 * @param y_2 The y-coordinate of the end node.
 * @requires None.
 * @modifies None.
 * @effects None.
 * @throws None.
 * @return The Euclidean distance between the points (x_1, y_1) and (x_2, y_2).
 */
private double getDistance(int x_1, int y_1, int x_2, int y_2) {
    int deltaX = x_1 - x_2; // Difference in x-coordinates
    int deltaY = y_1 - y_2; // Difference in y-coordinates

    return Math.sqrt(deltaX * deltaX + deltaY * deltaY); // Calculate the Euclidean distance
}

                 /**
 * Reads node data from a specified file and populates building and coordinate mappings.
 *
 * @param filename The name of the file containing node data for the RPI campus.
 * @requires None.
 * @modifies buildingL, labelB, coor, graph.
 * @effects Adds nodes to the graph and stores their coordinates in this.coor.
 *         Records each label and building in the corresponding hashmaps.
 * @throws IOException If the file isn't formatted correctly.
 * @return None.
 */
private void readNodeData(String filename) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;

        while ((line = reader.readLine()) != null) {
            String[] lineSeparated = line.split(",", 4);

            // Validate the CSV format
            if (lineSeparated.length != 4) {
                throw new IOException("Invalid format in file " + filename + 
                    ". Expected format: \"BUILDING_NAME\",\"BUILDING_ID\",\"X_COORDINATE\",\"Y_COORDINATE\".");
            }

            String buildingName = lineSeparated[0].isEmpty() 
                ? "Intersection " + lineSeparated[1] 
                : lineSeparated[0];

            int buildingId = Integer.parseInt(lineSeparated[1]);
            int xCoordinate = Integer.parseInt(lineSeparated[2]);
            int yCoordinate = Integer.parseInt(lineSeparated[3]);

            // Add building to the mappings and graph
            this.buildingL.put(buildingName, buildingId);
            this.labelB.put(buildingId, buildingName);

            Node<String> currNode = new Node<>(buildingName);
            this.graph.addNode(currNode);

            // Store coordinates if not already present
            if (!this.coor.containsKey(buildingId)) {
                ArrayList<Integer> coordinates = new ArrayList<>();
                coordinates.add(xCoordinate);
                coordinates.add(yCoordinate);
                this.coor.put(buildingId, coordinates);
            }
        }
    }
}
                       /**
 * Reads edge data from a specified file and adds edges to the graph.
 *
 * @param filename The name of the file containing edge data for the RPI campus.
 * @requires None.
 * @modifies Graph by adding edges and weights using the Euclidean distance formula.
 * @effects Adds edges to the graph based on the distance formula.
 * @throws IOException If the file isn't formatted correctly.
 * @return None.
 */
private void readEdgeData(String filename) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        int edgeCount = 1;

        while ((line = reader.readLine()) != null) {
            String[] lineSeparated = line.split(",", 2);

            // Ensure proper CSV format
            if (lineSeparated.length != 2) {
                throw new IOException("Invalid format in file " + filename + 
                    ". Expected format: \"ID_1\",\"ID_2\".");
            }

            int id1 = Integer.parseInt(lineSeparated[0]);
            int id2 = Integer.parseInt(lineSeparated[1]);

            Node<String> nodeA = graph.getNode(labelB.get(id1));
            Node<String> nodeB = graph.getNode(labelB.get(id2));

            ArrayList<Integer> coordinatesA = coor.get(id1);
            ArrayList<Integer> coordinatesB = coor.get(id2);

            // Calculate the distance using the Euclidean formula
            double distance = getDistance(
                coordinatesA.get(0), coordinatesA.get(1),
                coordinatesB.get(0), coordinatesB.get(1)
            );

            String edgeLabel = "Edge " + edgeCount;

            // Add the edge to the graph
            graph.addEdge(nodeA, nodeB, distance, edgeLabel);
            graph.addEdge(nodeB, nodeA, distance, edgeLabel);

            edgeCount++; // Increment the edge count
        }
    }
}
/**
     * @param name of file with node data for RPI campus
     * @param name of file with edge data for RPI campus
     * @requires None
     * @modifies buildingL, labelB, coor, graph
     * @effects adds nodes to graph and stores their coordinates in this.coordinates and records each label and buiulding in correspinding hashmaps and adds edges to graph
     * @throws IOException if either file isnt formatted correctly
     * @return None
     **/
    public void createNewGraph(String node_filename, String edge_filename){
        try{
            readNodeData(node_filename);
            readEdgeData(edge_filename);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
                 /**
 * Main method for the CampusPaths program.
 *
 * @param args Any command-line arguments for the program.
 * @requires None.
 * @modifies None.
 * @effects Processes user input to interact with the CampusPaths application.
 * @throws IOException If the required data files are not formatted correctly.
 * @return None.
 */
public static void main(String[] args) {
    CampusPaths cc = new CampusPaths();
    cc.createNewGraph("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");

    Scanner scanner = new Scanner(System.in);
    String instruction;

    while (!(instruction = scanner.nextLine()).equals("q")) {
        switch (instruction) {
            case "b":
                System.out.print(cc.allBuildings());
                break;

            case "m":
                System.out.println(cc.printMenu());
                break;

            case "r":
                System.out.print("First building id/name, followed by Enter: ");
                String building1 = scanner.nextLine();

                System.out.print("Second building id/name, followed by Enter: ");
                String building2 = scanner.nextLine();

                System.out.print(cc.pathCommand(building1, building2));
                break;

            default:
                System.out.println("Unknown option");
                break;
        }
    }
}
    
}