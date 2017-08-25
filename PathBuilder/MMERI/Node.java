package Players.MMERI;

import java.util.ArrayList;

/**
 * This represents a circle on the board. It stores all information needed in a node for Dijkstra's path
 * finding algorithm.
 *
 *  @author Mike Canning
 */
public class Node {
	ArrayList<Edge> myEdges = new ArrayList<Edge>();
	int type;
	int[] coords;
	boolean done = false;
	Edge from;
	int distance = Integer.MAX_VALUE;
	
	/**
	 * Initializes a node object for a player at given coordinates
	 * 
	 * @param type The player the node is being created for
	 * @param x The x coordinate location of the node
	 * @param y The y coordinate location of the node
	 */
	public Node(int type, int x, int y) {
		this.type = type;
		coords = new int[2];
		coords[0] = x;
		coords[1] = y;
	}
	
	/**
	 * Adds an edge to the node.
	 * 
	 * @param edge The edge object to connect to this node.
	 */
	public void connect(Edge edge) {
		myEdges.add(edge);
		edge.connect(this);
	}
	
	/**
	 * Removes an edge from the node
	 * 
	 * @param edge The edge to disconnect
	 */
	public void disconnect(Edge edge) {
		myEdges.remove(edge);
		edge.disconnect(this);
	}
	
	/**
	 * Returns the node's edges
	 * 
	 * @return A list of edges connected to the node
	 */
	public ArrayList<Edge> getEdges() {
		return myEdges;
	}
	
	/**
	 * Returns the node type
	 * 
	 * @return The node type
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Returns the node's location as an integer list
	 * 
	 * @return The node's location
	 */
	public int[] getCoords() {
		return coords;
	}
	
	/**
	 * Returns if the nodes has been visited or not
	 * 
	 * @return True if the node has been visited, False if it has not
	 */
	public boolean isDone() {
		return done;
	}
	
	/**
	 * Sets whether the node has been visited or not.
	 * 
	 * @param done True/False The node had been visited
	 */
	public void setDone(boolean done) {
		this.done = done;
	}
	
	/**
	 * Returns the edge the node was accessed from
	 * 
	 * @return The edge the node was accessed from
	 */
	public Edge getFrom() {
		return from;
	}
	
	/**
	 * Sets the edge this node was accessed from
	 * 
	 * @param from The edge that this node was accessed from
	 */
	public void setFrom(Edge from) {
		this.from = from;
	}
	
	/**
	 * Returns the distance of this node from the starting node
	 * 
	 * @return the distance of this node from the starting node
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Sets this node's distance from the starting node
	 * 
	 * @param dist This node's distance from the starting node
	 */
	public void setDistance(int dist) {
		this.distance = dist;
	}
}
