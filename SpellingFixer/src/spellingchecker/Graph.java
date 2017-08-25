package spellingchecker;

import java.util.Set;

/**
 * Interface for a directed graph. Nodes are connected by edges, but
 * there is no concept of an edge here, e.g., no edge "weights".
 * The Node type is passed in because nodes contain no other internal data.
 * Node needs a legitimate equals() method, and likely a legitimate
 * hash() method as well, depending on the implementing class
 *
 * This is a simplified version where the Node type is simply the
 * type passed in as the class's generic argument.
 *
 * @param <Node> the class for the graph's nodes
 * @author James E Heliotis
 */
public interface Graph< Node > {

    /**
     * Check if a given node is in the graph.
     * The equals method in the Node type must work properly.
     *
     * @param node sought node
     * @return true iff the graph contains a node with that name
     */
    public boolean hasNode( Node node );

    /**
     * Hook up two more nodes with an edge. Edges are directional, so if
     * you desire a two-way connection, addNeighbor must be called twice,
     * the second time with the arguments reversed.
     * @param node the source node for the edge
     * @param neighbor the destination node for the edge
     */
    public void addNeighbor( Node node, Node neighbor );

    /**
     * What are all the neighbors of a node?
     * The equals method in the Node type must work properly.
     * @param node the node whose neighbors are sought
     * @return a set of {@link Node}s where for each node v in the set,
     *         addNeighbor had previously been called with node as the
     *         first argument and v as the second
     */
    public Set< Node > getNeighbors( Node node );

    /**
     * Create a new node for this graph.
     * @rit.post the new node has no neighbors.
     * @param node the name of the new node
     */
    public void makeNode( Node node );

}
