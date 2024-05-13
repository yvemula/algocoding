package hw4;

import java.util.*;

import org.junit.Test.None;

//add comparing and shit for sets
public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
    /**
 * Represents a mutable node in a graph.
 * This class has a string label, a map tracking outgoing edges to other nodes, 
 * and a map tracking incoming edges from other nodes.
 * There is also a special "NaN" node, representing "Not-a-Node," 
 * which is used when a node is created with an empty string as its label.
 */

    private T l;


    /**
 * Abstraction function:
 * A Node `n` is considered NaN (Not-a-Node) if its label is an empty string ("").
 * If the label is not empty, the node is not NaN.
 */

/**
 * Representation invariant:
 * A valid node must meet one of the following conditions:
 * - The node's label is not empty, and it's not the special NaN node.
 * - The node's label is empty, and it is the NaN node.
 */



    /**
     * @param Label
     * @requires None
     * @modifies 
     * @effects Constructs new Node with label = Label and Initializes in_degree and out_degree
     * @throws RunTimeEx
     * @return None
     */
    public Node(T Label){
        this.l = Label;
    }

    public Node(){
        this.l = null;
    }

    public Node<T> NaN() {
        return new Node<T>();
    }
    /** Returns true if this is NaN
     * @param None
     * @requires None
     * @modifies None
     * @effects None
     * @throws None
     * @return true iff this is NaN 
    */
    public boolean isNaN(){
        return l == null;
    }
    
    /** Returns Node's label
     * @param None
     * @requires None
     * @modifies None
     * @effects None
     * @throws None
     * @return this.label
    */
    public T getLabel(){
        return this.l;
    }
   /**
 * Compares this object with another to determine equality.
 * 
 * @param other The object to compare with.
 * @requires None
 * @modifies None
 * @effects None
 * @throws None
 * @return An integer indicating the result of the comparison:
 *         - `0`: The objects are equal.
 *         - `1`: The current object has more outgoing edges, more incoming edges, 
 *               or its label is lexicographically greater than the other object's label.
 *         - `-1`: The current object has fewer outgoing edges, fewer incoming edges, 
 *                or its label is lexicographically smaller than the other object's label.
 */

 @Override
 public int compareTo(Node<T> other) {
     boolean thisIsNaN = this.isNaN();
     boolean otherIsNaN = other.isNaN();
 
     if (thisIsNaN && otherIsNaN) {
         return 0; // Both nodes are NaN, so they are equal
     }
 
     if (thisIsNaN) {
         return 1; // This node is NaN, so it's greater (comes later)
     }
 
     if (otherIsNaN) {
         return -1; // Other node is NaN, so this node is smaller (comes earlier)
     }
 
     // If neither node is NaN, compare their labels
     return this.l.compareTo(other.l); // Return the result of comparing labels
 }
 

    @Override
    public int hashCode() {
        return Objects.hash(l);
    }
}