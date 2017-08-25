package Players.MMERI;

import java.util.ArrayList;

/**
 * Represents an edge on the game board. This is a connection piece
 * 
 * @author Mike Canning
 */
public class Edge {
	int length;
	int[] coords;
	
	ArrayList<Node> connections = new ArrayList<>();
	
	/**
     * Creates an edge object with a given length and coordinate location
     * 
     * @param length The length of the edge
     * @param x The x coord of the edge
     * @param y The y coord of the edge
     */
	public Edge(int length, int x, int y) {
		this.length = length;
		coords = new int[2];
		coords[0] = x;
		coords[1] = y;
	}
	
	/**
     * Connects this edge to a node
     * 
     * @param node The node to connect this edge to
     */
	public void connect(Node node) {
		connections.add(node);
	}
	
	/**
     * Returns the nodes this edge is connected to
     * 
     * @return The nodes that this edge is connected to
     */
	public ArrayList<Node> getNodes() {
		return connections;
	}
	
	/**
     * Removes a node that this edge is currently connected to
     * 
     * @param node The node to disconnect
     */
	public void disconnect(Node node) {
		this.connections.remove(node);
	}
	
	/**
     * Returns the length of this edge
     * 
     * @return The length of the edge
     */
	public int getLength() {
		return length;
	}
	
	/**
     * Returns a list of the x and y coordinates of this edge
     * 
     * @param The x and y coordinates of this edge in a list
     */
	public int[] getCoords() {
		return coords;
	}
}
