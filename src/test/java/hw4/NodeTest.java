package hw4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NodeTest {

    @Test
    public void testComp() {
        Node<String> a = new Node<>("A");
        Node<String> b = new Node<>("B");
        Node<String> c = new Node<>("C");

        assertTrue(a.hashCode() < b.hashCode());
        assertTrue(b.hashCode() < c.hashCode());
        assertTrue(a.hashCode() < c.hashCode());

        assertFalse(a.equals(b));
        assertFalse(b.equals(c));
        assertFalse(a.equals(c));
        assertTrue(a.equals(a));

        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, a.compareTo(a));
    }

    @Test
    public void nanTest() {
        Node<String> d = new Node<>();
        Node<String> e = d.NaN();

        assertTrue(d.isNaN());
        assertTrue(e.isNaN());
        assertFalse(e.equals(d));
        assertFalse(d.equals(e));
    }

    @Test
    public void labelTest() {
        Node<String> a = new Node<>("A");
        assertEquals("A", a.getLabel());
    }
}
