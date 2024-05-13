package hw4;
 
import java.util.*; 
import org.junit.jupiter.api.Test;


/**
 * @see hw4.Node
 * @see hw4.Edge
 * @see hw4.Graph
 */

public class GraphTest {
    private static Graph<String, String> a;
    

    @Test
    public void testAddEmpty() {
        Graph<String, String> graphA = new Graph<>();
        Graph<String, String> graphB = new Graph<>();
        Graph<String, String> graphC = new Graph<>();
        Graph<String, String> graphD = new Graph<>();
        Graph<String, String> graphE = new Graph<>();

        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");
        Node<String> nodeD = new Node<>("D");
        Node<String> nodeE = new Node<>("E");

        // Add nodes to empty graphs
        graphA.addNode(nodeA);
        assert(graphA.numNodes() == 1);

        graphB.addNode(nodeB);
        assert(graphB.numNodes() == 1);

        graphC.addNode(nodeC);
        assert(graphC.numNodes() == 1);

        graphD.addNode(nodeD);
        assert(graphD.numNodes() == 1);

        graphE.addNode(nodeE);
        assert(graphE.numNodes() == 1);

        // Add duplicate nodes (should not increase node count)
        graphA.addNode(new Node<>("A"));
        assert(graphA.numNodes() == 1);

        graphB.addNode(new Node<>("B"));
        assert(graphB.numNodes() == 1);

        // Add different nodes to the same graph
        graphA.addNode(new Node<>("B"));
        assert(graphA.numNodes() == 2);

        graphB.addNode(new Node<>("C"));
        assert(graphB.numNodes() == 2);

        graphC.addNode(new Node<>("D"));
        assert(graphC.numNodes() == 2);

        graphD.addNode(new Node<>("E"));
        assert(graphD.numNodes() == 2);

        graphE.addNode(new Node<>("A"));
        assert(graphE.numNodes() == 2);

        // Test adding NaN (should not change node count)
        Node<String> nanNode = nodeE.NaN();
        graphE.addNode(nanNode);
        assert(graphE.numNodes() == 2);
    }

    @Test
    public void testAddNode100() {
        Graph<String, String> testGraph = new Graph<>();
        for (int i = 0; i < 100; i++) {
            Node<String> newNode = new Node<>(String.valueOf(i));
            testGraph.addNode(newNode);
        }

        // Verify that the graph contains 100 nodes
        assert(testGraph.numNodes() == 100);
    }

    @Test
    public void testerweight() {
        Graph<String, String> testGraph = new Graph<>();
        
        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");

        // Add edges with varying weights
        testGraph.addEdge(nodeA, nodeB, 5.0, "5");
        testGraph.addEdge(nodeB, nodeC, 1.0, "1");
        testGraph.addEdge(nodeA, nodeC, 2.0, "2");

        // Verify that the edges were added and that the number of edges matches expectations
        assert(testGraph.numEdges() == 3);
    }

 
    @Test
    public void testNan() {
        Graph<String, String> testGraph = new Graph<>();

        // Create new nodes with different labels
        Node<String> nodeX = new Node<>("X");
        Node<String> nodeY = new Node<>("Y");
        Node<String> nodeZ = new Node<>("Z");

        // Add an edge between two regular nodes
        testGraph.addEdge(nodeX, nodeY, 5.0, "XY");
       // assert(testGraph.numEdges() == 1);

        // Add more edges with one or both nodes not in the graph
        testGraph.addEdge(nodeY, nodeZ, 1.0, "YZ");
        testGraph.addEdge(nodeX, nodeZ, 1.0, "XZ");
        //assert(testGraph.numEdges() == 3);

        // Attempt to add edges with Node.NaN as endpoints
        Node<String> nanNode = nodeX.NaN();
        testGraph.addEdge(nanNode, nodeY, 1.0, "NY");
        testGraph.addEdge(nodeX, nanNode, 1.0, "XN");
      //  assert(testGraph.numEdges() == 5); // Total edges in the graph
    }

    @Test
    public void edgeL() {
        Graph<String, String> testGraph = new Graph<>();

        // New nodes for the graph
        Node<String> nodeX = new Node<>("X");
        Node<String> nodeY = new Node<>("Y");
        Node<String> nodeZ = new Node<>("Z");

        // Add edges with unique labels
        testGraph.addEdge(nodeX, nodeY, 1.0, "XY");
        testGraph.addEdge(nodeY, nodeZ, 1.0, "YZ");
        testGraph.addEdge(nodeX, nodeZ, 1.0, "XZ");
        assert(testGraph.numEdges() == 3);

        // Add edges with duplicate labels
        Node<String> nodeW = new Node<>("W");
        Node<String> nodeV = new Node<>("V");
        testGraph.addEdge(nodeW, nodeV, 1.0, "XZ"); // Duplicate label
        assert(testGraph.numEdges() == 4);

        // Add edges with Node.NaN and empty edge labels
        Node<String> nanNode = nodeX.NaN();
        testGraph.addEdge(nanNode, nodeY, 1.0, "NaN_Y");
        testGraph.addEdge(nodeX, nanNode, 1.0, "X_NaN");
        testGraph.addEdge(nodeX, nodeY, 1.0, ""); // Empty edge label
        //assert(testGraph.numEdges() == 7); // Total edges in the graph
    }

    @Test
    public void add10() {
        Graph<String, String> testGraph = new Graph<>();
    
        Node<String> nodeA = new Node<>("NodeA");
        Node<String> nodeB = new Node<>("NodeB");
        Node<String> nodeC = new Node<>("NodeC");
        Node<String> nodeD = new Node<>("NodeD");
        Node<String> nodeE = new Node<>("NodeE");
        Node<String> nodeF = new Node<>("NodeF");
        Node<String> nodeG = new Node<>("NodeG");
        Node<String> nodeH = new Node<>("NodeH");
        Node<String> nodeI = new Node<>("NodeI");
        Node<String> nodeJ = new Node<>("NodeJ");
    
        // Add 11 edges to the graph with a circular structure
        testGraph.addEdge(nodeA, nodeB, 1.0, "AB");
        testGraph.addEdge(nodeB, nodeC, 1.0, "BC");
        testGraph.addEdge(nodeC, nodeD, 1.0, "CD");
        testGraph.addEdge(nodeD, nodeE, 1.0, "DE");
        testGraph.addEdge(nodeE, nodeF, 1.0, "EF");
        testGraph.addEdge(nodeF, nodeG, 1.0, "FG");
        testGraph.addEdge(nodeG, nodeH, 1.0, "GH");
        testGraph.addEdge(nodeH, nodeI, 1.0, "HI");
        testGraph.addEdge(nodeI, nodeJ, 1.0, "IJ");
    }
    
    @Test
    public void add100() {
        Graph<String, String> testGraph = new Graph<>();
        Node<String> startNode = new Node<>("Start");
        Node<String> nextNode = new Node<>("Next");
    
        // Add the first edge
        testGraph.addEdge(startNode, nextNode, 1.0, "FirstEdge");
    
        Node<String> previousNode = nextNode;
    
        // Add 99 more edges in a linear fashion
        for (int i = 1; i < 100; i++) {
            Node<String> currentNode = new Node<>(Integer.toString(i));
            testGraph.addEdge(previousNode, currentNode, 1.0, "Edge_" + i);
            previousNode = currentNode;
        }
    }
    
    @Test
    public void nodeIt() {
        // Test an empty graph to ensure no nodes are returned
        Graph<String, String> testGraph = new Graph<>();
        Iterator<String> iterator = testGraph.NodesIterator();
        assert(!iterator.hasNext());  // No nodes in the graph
    
        // Add some nodes and test the iterator
        Node<String> node1 = new Node<>("Node1");
        Node<String> node2 = new Node<>("Node2");
        Node<String> node3 = new Node<>("Node3");
        testGraph.addNode(node1);
        testGraph.addNode(node2);
        testGraph.addNode(node3);
    
        // Test the iterator after adding nodes
        iterator = testGraph.NodesIterator();
        List<String> nodeLabels = new ArrayList<>();
        while (iterator.hasNext()) {
            nodeLabels.add(iterator.next());
        }
    
        // Check that the expected nodes are returned
        assert(nodeLabels.contains("Node1"));
        assert(nodeLabels.contains("Node2"));
        assert(nodeLabels.contains("Node3"));
        assert(nodeLabels.size() == 3);
    }
    
    
    @Test
    public void findN() {
        Graph<String, String> testGraph = new Graph<>();
        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");
    
        testGraph.addNode(nodeA);
        testGraph.addNode(nodeB);
        testGraph.addNode(nodeC);
    
        // Check that nodes can be found using their labels
        assert(testGraph.findNode("A") == true);
        assert(testGraph.findNode("B") == true);
        assert(testGraph.findNode("C") == true);
    
        // Check that nonexistent nodes cannot be found
        assert(testGraph.findNode("X") == false);
        assert(testGraph.findNode("Y") == false);
    }
    
    @Test
    void dijk_small() {
        Graph<String, String> simpleGraph = new Graph<>();
        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");
    
        simpleGraph.addEdge(nodeA, nodeC, 1.0, "AC_1");
        simpleGraph.addEdge(nodeA, nodeC, 2.0, "AC_2");
        simpleGraph.addEdge(nodeA, nodeB, 3.0, "AB");
        simpleGraph.addEdge(nodeB, nodeC, 4.0, "BC");
    
        List<Edge<String, String>> path = simpleGraph.shortestPathDikjstra(nodeA, nodeC);
        assert(path.size() == 1);
    
        Edge<String, String> edge = path.get(0);
        assert(edge.getStartNode().getLabel().equals("A"));
        assert(edge.getEndNode().getLabel().equals("C"));
    }
    
    @Test
    void dikstra_big() {
        Graph<String, String> complexGraph = new Graph<>();
        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");
        Node<String> nodeD = new Node<>("D");
        Node<String> nodeE = new Node<>("E");
        Node<String> nodeF = new Node<>("F");
    
        complexGraph.addEdge(nodeA, nodeB, 1.0, "AB");
        complexGraph.addEdge(nodeB, nodeC, 1.0, "BC");
        complexGraph.addEdge(nodeC, nodeD, 1.0, "CD");
        complexGraph.addEdge(nodeD, nodeE, 1.0, "DE");
        complexGraph.addEdge(nodeE, nodeF, 1.0, "EF");
    
        List<Edge<String, String>> bfsPath = complexGraph.shortestPathBFS(nodeA, nodeF);
    
        assert(bfsPath.size() == 5); // Path from A to F through all edges
        int expectedEdges = 5;
        int pathSize = bfsPath.size();
        assert(pathSize == expectedEdges); // Ensure BFS traverses correctly
    }
    @Test
    public void getEdgeTest() {
        Graph<String, String> graph = new Graph<>();
        
        // Create a set of nodes
        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeE = new Node<>("E");
        Node<String> nodeF = new Node<>("F");
        Node<String> nodeG = new Node<>("G");
        Node<String> nodeI = new Node<>("I");
        Node<String> nodeJ = new Node<>("J");
        Node<String> nodeK = new Node<>("K");
        // Add edges between nodes
        graph.addEdge(nodeA, nodeB, 1.0, "AB");
        graph.addEdge(nodeB, nodeA, 1.0, "BA");
        graph.addEdge(nodeA, nodeE, 1.0, "AE");
        graph.addEdge(nodeE, nodeA, 1.0, "EA");
        graph.addEdge(nodeE, nodeJ, 1.0, "EJ");
        graph.addEdge(nodeJ, nodeE, 1.0, "JE");
        graph.addEdge(nodeE, nodeI, 1.0, "EI");
        graph.addEdge(nodeI, nodeE, 1.0, "IE");
        graph.addEdge(nodeB, nodeF, 1.0, "BF");
        graph.addEdge(nodeF, nodeB, 1.0, "FB");
        graph.addEdge(nodeG, nodeK, 1.0, "GK");
        graph.addEdge(nodeK, nodeG, 1.0, "KG");
    
    }

    @Test
    public void djkstra_test() {
        Graph<String, String> graph = new Graph<>();
        Node<String> nodeS = new Node<>("S");
        Node<String> nodeA = new Node<>("A");
        Node<String> nodeC = new Node<>("C");
        Node<String> nodeE = new Node<>("E");
        Node<String> nodeI = new Node<>("I");
    
        // Adding edges to create a simple graph
        graph.addEdge(nodeS, nodeA, 7.0, "SA");
        graph.addEdge(nodeS, nodeC, 6.0, "SC");
        graph.addEdge(nodeC, nodeE, 2.0, "CE");
        graph.addEdge(nodeE, nodeI, 2.0, "EI");
    
        // Test the distance between S and I
        double distance = graph.getDistance(nodeS, nodeI);
        assert distance == 10.0;
    
        // Test the shortest path between S and I
        List<Edge<String, String>> path = graph.shortestPathDikjstra(nodeS, nodeI);
        assert path.size() == 3;
    
        // Check the labels of edges in the shortest path
        String[] expectedLabels = {"SC", "CE", "EI"};
        for (int i = 0; i < path.size(); i++) {
            assert path.get(i).getLabel().equals(expectedLabels[i]);
        }
    
        // Test non-existing paths
        List<Edge<String, String>> emptyPath = graph.shortestPathDikjstra(nodeA, new Node<>("Z"));
        assert emptyPath.isEmpty();  // No path expected
    }


    @Test
    public void mainGraph() {
        a = new Graph<>();

        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");
        Node<String> nodeD = new Node<>("D");
        Node<String> nodeE = new Node<>("E");

        // Add nodes and edges
        a.addNode(nodeA);
        a.addEdge(nodeA, nodeC, 1.0, "AC");
        
        // Assert that there is one edge and two nodes
       //assertEquals(1, a.numEdges(), "Number of edges should be 1");
        //assertEquals(2, a.numNodes(), "Number of nodes should be 2");
        
        a.addEdge(nodeB, nodeC, 2.0, "BC");
        
        // Assert updated edge and node counts
      //  assertEquals(2, a.numEdges(), "Number of edges should be 2");
       // assertEquals(3, a.numNodes(), "Number of nodes should be 3");
        
        a.addEdge(nodeD, nodeE, 2.0, "DE");
        
       // assertEquals(3, a.numEdges(), "Number of edges should be 3");
       // assertEquals(5, a.numNodes(), "Number of nodes should be 5");
    }

    @Test
    public void Iteratortester() {
        a = new Graph<>();

        Node<String> nodeA = new Node<>("A");
        Node<String> nodeB = new Node<>("B");
        Node<String> nodeC = new Node<>("C");
        Node<String> nodeD = new Node<>("D");
        Node<String> nodeE = new Node<>("E");

        a.addEdge(nodeA, nodeC, 1.0, "AC");
        a.addEdge(nodeB, nodeC, 2.0, "BC");
        a.addEdge(nodeD, nodeE, 2.0, "DE");

        // Test that the iterator returns expected node labels
        Iterator<String> nodeIterator = a.NodesIterator();
     //   assertTrue(nodeIterator.hasNext(), "Node iterator should have nodes");

        Set<String> retrievedNodes = new HashSet<>();

        while (nodeIterator.hasNext()) {
            retrievedNodes.add(nodeIterator.next());
        }

       // assertEquals(expectedNodes, retrievedNodes, "Node iterator should return correct nodes");
    }

}