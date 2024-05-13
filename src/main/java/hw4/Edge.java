package hw4;
import java.util.Objects;

public class Edge<T extends Comparable<T>, S extends Comparable<S>> implements Comparable<Edge<T, S>>{
   /**
 * An edge in a graph representing a mutable connection between two nodes.
 * It contains:
 * - A string that represents the edge's label.
 * - A double or integer that represents the edge's weight.
 * - Two nodes: one representing the start node and the other representing the end node.
 * 
 * This class models a directed edge in a graph.
 */

    protected Node<T> node1;
    protected Node<T> node2;
    private Double w;
    private S Label;

  /**
 * Abstraction function:
 * An edge `e` is valid if:
 * - `e.Label` is not an empty string, and
 * - `e.startNode` is a valid Node (not NaN), and
 * - `e.endNode` is a valid Node (not NaN).
 * 
 * An edge `e` is invalid if:
 * - `e.Label` is an empty string, or
 * - `e.startNode` is NaN, or
 * - `e.endNode` is NaN.
 */


  /**
 * Representation invariant:
 * - An edge `e` is valid if:
 *   - `e.Label` is not an empty string, and
 *   - `e.startNode` is not NaN, and
 *   - `e.endNode` is not NaN.
 * 
 * - An edge `e` can also be invalid, represented by `Edge.NaN`.
 */

   /**
 * Creates an edge object that starts at one node, ends at another, and has a specific weight and label.
 * 
 * @param n1 The starting node of the edge.
 * @param n2 The ending node of the edge.
 * @param w The weight of the edge.
 * @param l The label for the edge.
 * @requires n1 must not be NaN, n2 must not be NaN, and l must not be an empty string.
 * @modifies n1's out-degree and n2's in-degree.
 * @effects Creates an edge from n1 to n2 with a specified weight and label.
 */

    public Edge(Node<T> n1, Node<T> n2, S label){
        this.node1 = n1;
        this.node2 = n2;
        this.Label = label;
    }



    /**
     * @param n1 (Start Node), n2 (End Node), w (weight), l (label)
     * @requires n1 != Node.NaN, n2 != NaN, and l != ""
     * @modifies n1.out_degree, n2.in_degree
     * @effects creates an Edge object that starts from n1, ends at n2, has a weight of w and a label of l
    
     **/
    public Edge(){
        this.node1 = new Node<T>();
        this.node2 = new Node<T>();
        this.Label = null;
    }


    /**
 
     * @return returns Weight associated  with Edge
     **/
    public Double getWeight(){
        return this.w;
    }
    /**
     
     * @return returns label of Edge
     **/
    public S getLabel(){
        return this.Label;
    }
    /**
    
     * @return StartNode
     **/
    public Node<T> getStartNode(){
        return this.node1;
    }

        /**
    
     * @return endNode
     **/
    public Node<T> getEndNode(){
        return this.node2;
    }
    /**
   
     * @return ture if this is Edge.NaN, 
     **/
    public boolean isNAN(){
        return this.node1.isNaN() && this.node2.isNaN()  && this.Label == null;
    }

    public void setWeight(Double w){
        this.w = w;
    }
    /**
     * @param Other ==> another edge 
     * @return 0 if this and other are the same edge, -1 if any value is lower than the other
     **/

    @Override
    public int hashCode() {
        return Objects.hash(Label);
    }

    @Override
    public int compareTo(Edge<T, S> other) {
        if (this.isNAN() && !other.isNAN()) {
            return 1; // This edge is NaN while the other is not, so this is "greater"
        } else if (!this.isNAN() && other.isNAN()) {
            return -1; // This edge is not NaN while the other is, so this is "lesser"
        }
    
        int result = this.node1.compareTo(other.node1); // Compare the first nodes
        if (result != 0) {
            return result; // Return if the first nodes are different
        }
    
        result = this.node2.compareTo(other.node2); // Compare the second nodes
        if (result != 0) {
            return result; // Return if the second nodes are different
        }
    
        return this.Label.compareTo(other.Label); // Compare the edge labels if nodes are the same
    }
    



}