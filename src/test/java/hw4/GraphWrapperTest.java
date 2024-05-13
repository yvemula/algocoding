package hw4;

import java.util.Iterator;
import org.junit.jupiter.api.Test;

/**
 * @see hw4.Node
 * @see hw4.Edge
 * @see hw4.Graph
 * @see hw4.GraphWrapper
 */
public class GraphWrapperTest {

    @Test
    public void addNode() {
        GraphWrapper graph = new GraphWrapper();
        assert(graph.getNodes() == 0);

        graph.addNode("A");
        assert(graph.getNodes() == 1);

        graph.addNode("A"); // Adding the same node again
        assert(graph.getNodes() == 1);

        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        assert(graph.getNodes() == 5);
    }

    @Test
    public void addEdge() {
        GraphWrapper graph = new GraphWrapper();
        assert(graph.getNodes() == 0);
        assert(graph.getEdges() == 0);

        graph.addEdge("A", "B", "7");
        assert(graph.getNodes() == 2);
        assert(graph.getEdges() == 1);

        graph.addEdge("C", "D", "EdgeLabel1");
        assert(graph.getNodes() == 4);
        assert(graph.getEdges() == 2);

        graph.addEdge("A", "B", "EdgeLabel1");
        assert(graph.getEdges() == 3);

        graph.addEdge("A", "B", "EdgeLabel2");
        assert(graph.getEdges() == 4);

        graph.addEdge("A", "B", "7"); // Reusing the same label
        assert(graph.getEdges() == 5);
    }

    @Test
    public void noList() {
        GraphWrapper graph = new GraphWrapper();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        Iterator<String> nodeIterator = graph.listNodes();
        assert(nodeIterator.hasNext());

        String[] expectedNodes = {"A", "B", "C", "D"};
        int i = 0;

        while (nodeIterator.hasNext()) {
            assert(expectedNodes[i].equals(nodeIterator.next()));
            i++;
        }

        assert(i == 4); // Ensures we've iterated over all expected nodes
    }

    @Test
    public void emptryGraph() {
        GraphWrapper graph = new GraphWrapper();
        Iterator<String> nodeIterator = graph.listNodes();

        assert(!nodeIterator.hasNext()); // Iterator should be empty
    }

    @Test
    public void childIt() {
        GraphWrapper graph = new GraphWrapper();

        graph.addEdge("a", "b", "4");
        graph.addEdge("a", "c", "4");
        graph.addEdge("a", "c", "7");
        graph.addEdge("b", "c", "5");
        graph.addEdge("c", "b", "2");

        Iterator<String> childIteratorB = graph.listChildren("b");
        assert(childIteratorB.hasNext());
        assert("c(5)".equals(childIteratorB.next()));

        Iterator<String> childIteratorC = graph.listChildren("c");
        assert(childIteratorC.hasNext());
        assert("b(2)".equals(childIteratorC.next()));

        Iterator<String> childIteratorD = graph.listChildren("d");
        assert(!childIteratorD.hasNext()); // No children for 'd'
    }
}
