package hw4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @see hw4.Node
 * @see hw4.Edge
 */
public class EdgeTest {
    private static Node<String> a;
    private static Node<String> b;
    private static String Label1;

    private static Node<String> c;
    private static String Label2;

    @BeforeAll
    public static void setBefore() {
        a = new Node<String>("A");
        b = new Node<String>("B");
        Label1 = new String("Edge1");

        c = new Node<String>("C");
        Label2 = new String("Edge2");
    }

    @Test
    public void testCons(){
        Edge<String, String> e1, e2, e3;

        //regular constrcutor
        e1 = new Edge<String, String>(a, b, Label1);

        //connecting a vertex to itself
        e2 = new Edge<String, String>(c, c, Label2);
        assert(!e1.equals(e2));

        e3 = new Edge<String, String>();
        assert(e3.isNAN());
    }

    @Test
    public void accTest(){
        Edge<String, String> e1, e2;
        e1 = new Edge<String, String>(a, b, Label1);
        e2 = new Edge<String, String>(c, c,  Label2);
        assert(e1.compareTo(e2) != 0);
        assert(e1.compareTo(e1) == 0);
        e1.setWeight(0.1);
        assert(e1.getWeight() == 0.1);

        assert(e1.getStartNode().equals(a));
        assert(e1.getEndNode().equals(b));
        assert(e1.getLabel().compareTo(Label1) == 0);
        assert(e2.hashCode() != e1.hashCode());
        
    }
}
