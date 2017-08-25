package Players.MMERI;

import java.util.ArrayList;

import Interface.Coordinate;
import Interface.PlayerMove;

/**
 * This is a collection of functions to read and interact with the board for use
 * with Dijkstra's path finding algorithm.
 *
 *  @author Mike Canning
 */
public class GraphTools {
	
	private ArrayList<ArrayList<Integer>> board;
	private int player;
	private ArrayList<Node> graph = new ArrayList<>();
	private ArrayList<Node> queue = new ArrayList<>();
	private int endx;
	private int endy;
	private int truePlayerID;
	

	
	
	/**
     * Checks to see if a point is on the game board
     * @param row The row to check
     * @param col The col to check
     * @return True if the point is on the board, false otherwise
     */
	private boolean in_bounds(int row, int col) {
		if((col < board.size() && row < board.size()) && (col > -1 && row > -1)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
     * This sets up a call for finding the shortest path between two points. This is rarely supposed
     * to be called directly. It is also dependent on who this tool was initialized for.
     * @param startx The x coordinate to start the path from
     * @param starty The y coordinate to start the path from
     * @param endx The x coordinate to end the path at
     * @param endy The y coordinate to end the path at
     * @return An arraylist of coordinate pairs representing the moves that need to be made for the shortest path
     */
	public ArrayList<int[]> findPath(int startx, int starty, int endx, int endy) {
		this.endx = endx;
		this.endy = endy;
		resetNodes();
		queue.clear();
		Node currentNode = getNode(startx, starty);
		currentNode.setDistance(0);
		currentNode.setFrom(null);
		for(int i = 0; i < currentNode.getEdges().size(); i ++) {
			Edge currentEdge = currentNode.getEdges().get(i);
			for(int z = 0; z < currentEdge.getNodes().size(); z ++) {
				if(currentEdge.getLength() != Integer.MAX_VALUE && currentEdge.getNodes().get(z) != currentNode && currentEdge.getNodes().get(z) != null && currentEdge.getNodes().get(z).getType() == player) {
					Node temp = currentEdge.getNodes().get(z);
					temp.setDistance(1);
					temp.setFrom(currentEdge);
					queue.add(temp);
				}
			}
		}
		if(queue.size() != 0) {
			return(findPathHelper());
		}else {
			return null;
		}
	}
	
	/**
     * Starts processing the queue setup in the findPath() function
     * @return An arraylist of coordinate pairs representing the moves that need to be made for the shortest path
     */
	private ArrayList<int[]> findPathHelper() {
		if(queue.isEmpty()) {
			return null;
		}
		Node currentNode = getNextNode();
		currentNode.setDone(true);
		queue.remove(currentNode);
		if(currentNode.getCoords()[0] == endx && currentNode.getCoords()[1] == endy) {
			ArrayList<Edge> tempEdgeArray = new ArrayList<>();
			ArrayList<Node> tempNodeArray = new ArrayList<>();
			while(currentNode.getDistance() > 1) {
				tempEdgeArray.add(currentNode.getFrom());
				for(int i = 0; i < currentNode.getFrom().getNodes().size(); i ++) {
					
					if(currentNode.getFrom().getNodes().get(i) != currentNode && currentNode.getFrom().getNodes().get(i).getType() == player && !tempNodeArray.contains(currentNode.getFrom().getNodes().get(i))) {
						currentNode = currentNode.getFrom().getNodes().get(i);
						tempNodeArray.add(currentNode);
						break;
					}
				}
			}
			tempEdgeArray.add(currentNode.getFrom());
			ArrayList<int[]> tempEdgeCoordArray = new ArrayList<>();
			for(int i = tempEdgeArray.size() - 1; i >= 0; i --) {
				tempEdgeCoordArray.add(tempEdgeArray.get(i).getCoords());
			}
			return tempEdgeCoordArray;
		}else {
			for(int i = 0; i < currentNode.getEdges().size(); i ++) {
				Edge currentEdge = currentNode.getEdges().get(i);
				for(int z = 0; z < currentEdge.getNodes().size(); z ++) {
					if(currentEdge.getLength() != Integer.MAX_VALUE && currentEdge.getNodes().get(z) != currentNode && !currentEdge.getNodes().get(z).isDone() && !queue.contains(currentEdge.getNodes().get(z)) && currentEdge.getNodes().get(z).getType() == player) {
						Node temp = currentEdge.getNodes().get(z);
						temp.setDistance(currentNode.getDistance() + currentEdge.getLength());
						temp.setFrom(currentEdge);
						queue.add(temp);
					}
				}
			}
			return(findPathHelper());
			
		}
		
	}
	
	/**
     * Constructs a GraphTools object given a board and player
     * @param board The board being navigated
     * @param player The player number being helped
     */
	public GraphTools(ArrayList<ArrayList<Integer>> board, int player, int truePlayerID) {
		this.board = board;
		this.player = player;
		this.truePlayerID = truePlayerID;
		convert();
	}
	
	/**
     * Updates the GraphTools board when the MMERI board changes
     * @param The board 2d arraylist being changed to
     */
	public void updateBoard(ArrayList<ArrayList<Integer>> board) {
		this.board = board;
		convert();
	}
	
	/**
     * Converts the internal graph representation to a real graph
     */
	public void convert() {
		graph.clear();
		for(int y = 0; y < board.size(); y += 2) {
			for(int x = 1; x < board.size(); x += 2) {
				Node node = new Node(board.get(y).get(x), x, y);
				Edge newEdge;
				if((newEdge = getEdge(x + 1, y)) != null) {
					node.connect(newEdge);
				}
				if((newEdge = getEdge(x - 1, y)) != null) {
					node.connect(newEdge);
				}
				if((newEdge = getEdge(x, y + 1)) != null) {
					node.connect(newEdge);
				}
				if((newEdge = getEdge(x, y - 1)) != null) {
					node.connect(newEdge);
				}
				graph.add(node);
			}
		}
		
		for(int y = 1; y < board.size(); y += 2) {
			for(int x = 0; x < board.size(); x += 2) {
				Node node = new Node(board.get(y).get(x), x, y);
				Edge newEdge;
				if((newEdge = getEdge(x + 1, y)) != null) {
					node.connect(newEdge);
				}
				if((newEdge = getEdge(x - 1, y)) != null) {
					node.connect(newEdge);
				}
				if((newEdge = getEdge(x, y + 1)) != null) {
					node.connect(newEdge);
				}
				if((newEdge = getEdge(x, y - 1)) != null) {
					node.connect(newEdge);
				}
				graph.add(node);
			}
		}
	}
	
	/**
     * Returns the edge for a given point as long as it is valid. If it does not exist it will create a new edge
     * @param x The x coord of the edge
     * @param y The y coord of the edge
     * @return An edge at the requested coordinates
     */
	private Edge getEdge(int x, int y) {
		if(x == 0 || y == 0 || x == board.size() || y == board.size()) {
			return null;
		}
		if(!this.in_bounds(x, y)){
			return null;
		}else {
			for(int i = 0; i < graph.size(); i ++) {
				Node tempNode = graph.get(i);
				for(int z = 0; z < tempNode.getEdges().size(); z ++) {
					if(tempNode.getEdges().get(z).coords[0] == x && tempNode.getEdges().get(z).coords[1] == y) {
						return tempNode.getEdges().get(z);
					}
				}
			}
			int point = board.get(y).get(x);
			if(point != player && point != 0) {
				point = Integer.MAX_VALUE;
			} else if(point == player) {
				point = 0;
			}else {
				point = 1;
			}
			return new Edge(point, x, y);
		}
	}
	
	/**
     * Returns the node of a given point
     * @param x The x coord of the node
     * @param y The y coord of the node
     * @return The node at the given point
     */
	private Node getNode(int x, int y) {
		for(int i = 0; i < graph.size(); i ++) {
			if(graph.get(i).getCoords()[0] == x && graph.get(i).getCoords()[1] == y) {
				return graph.get(i);
			}
		}
		return null;
	}
	
	/**
     * Returns the node in the queue with the smallest distance
     * @param The node with the smallest distance
     */
	private Node getNextNode() {
		int currentMax = Integer.MAX_VALUE;
		Node currentMaxNode = null;
		
		for(int i = 0; i < queue.size(); i ++) {
			if(queue.get(i).getDistance() < currentMax) {
				currentMax = queue.get(i).getDistance();
				currentMaxNode = queue.get(i);
			}
		}
		return currentMaxNode;
	}
	
	/**
     * Returns an arraylist of coord pairs of the shortest path between the start and end of the board
     * @return The shortest path from start point to end point
     */
	private ArrayList<int[]> getShortestPath() {
		int minDist = Integer.MAX_VALUE;
		ArrayList<int[]> returnPath = null;
		
		ArrayList<int[]> startPoints = new ArrayList<>();
		ArrayList<int[]> endPoints = new ArrayList<>();
		
		if(player == 1) {
			for(int i = 1; i < board.size(); i += 2) {
				int[] tempCoord = new int[2];
				tempCoord[0] = 0;
				tempCoord[1] = i;
				startPoints.add(tempCoord);	
			}
			
			for(int i = 1; i < board.size(); i += 2) {
				int[] tempCoord = new int[2];
				tempCoord[0] = board.size() - 1;
				tempCoord[1] = i;
				endPoints.add(tempCoord);
			}
		}else {
			for(int i = 1; i < board.size(); i += 2) {
				int[] tempCoord = new int[2];
				tempCoord[0] = i;
				tempCoord[1] = 0;
				startPoints.add(tempCoord);	
			}
			
			for(int i = 1; i < board.size(); i += 2) {
				int[] tempCoord = new int[2];
				tempCoord[0] = i;
				tempCoord[1] = board.size() - 1;
				endPoints.add(tempCoord);
			}
		}
		
		for(int startCount = 0; startCount < startPoints.size(); startCount ++) {
			for(int endCount = 0; endCount < endPoints.size(); endCount ++) {
				ArrayList<int[]> tempPath = findPath(startPoints.get(startCount)[0], startPoints.get(startCount)[1], endPoints.get(endCount)[0], endPoints.get(endCount)[1]); 
				if(tempPath != null) {
					if(tempPath.size() < minDist) {
						returnPath = new ArrayList<>();
						returnPath.addAll(tempPath);
						minDist = tempPath.size();
					}else if(tempPath.size() == minDist) {
						returnPath = new ArrayList<>();
						returnPath.addAll(tempPath);
						minDist = tempPath.size();
					}
				}
			}
		}
		
		return returnPath;
        
	}
	
	/**
     * Returns the shortest path for the current player in the form of PlayerMove objects
     * @return The shortest path to victory
     */
	public ArrayList<PlayerMove> getMyShortestPath() {
		return convertToPlayerMove(player, getShortestPath());
	}
	
	/**
     * Converts a coordinate pair list to a list of PlayerMove objects
     * @param player The player that the playerMove object is being created for
     * @param moveList A list of coordinate pairs to create the playerMove objects from
     */
	public ArrayList<PlayerMove> convertToPlayerMove(int player, ArrayList<int[]> moveList) {
		
		ArrayList<PlayerMove> toReturn = new ArrayList<>();
		for(int i = 0; i < moveList.size(); i ++) {
			PlayerMove move = new PlayerMove(new Coordinate(moveList.get(i)[1], moveList.get(i)[0]), truePlayerID);
			toReturn.add(move);
		}
		
		return toReturn;
	}
	
	/**
     * Resets all nodes to not done before searching for a path.
     */
	private void resetNodes() {
		for(int i = 0; i < graph.size(); i ++) {
			graph.get(i).setDone(false);
			graph.get(i).setDistance(Integer.MAX_VALUE);
		}
	}
	
	
}
