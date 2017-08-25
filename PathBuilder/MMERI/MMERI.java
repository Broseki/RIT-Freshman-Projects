package Players.MMERI;

import Interface.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * A player for the game PathBuilder.
 *
 *  @author Brandon Goodhue
 *  @author Mike Canning
 */
public class MMERI implements PlayerModule{

    private boolean isWinner;
    private int playID;
    private int dim;
    private ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>();
    private GraphTools graphToolsMe;
    private GraphTools graphToolsThem;
    private boolean firstMove = true;



    @Override
    public void initPlayer(int dim, int playerID) {
        this.dim = dim;
        ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i<(2*dim + 1); i++) {
            ArrayList<Integer> row = new ArrayList<Integer>(2*dim +1);
            if (i%2 == 0) {
                for (int j = 0; j<2*dim+1; j++){
                    if (j%2 == 1) {
                        row.add(2);
                    }
                    else {
                        row.add(0);
                    }
                }
            }
            if (i%2 == 1) {
                for (int j = 0; j<2*dim+1; j++) { //board.size() is index 1
                    if (j%2 == 0) {
                        row.add(1);
                    }
                    else {
                        row.add(0);
                    }
                }
            }
            board.add(row);
        }
        this.board = board;
        this.playID = playerID;
        graphToolsMe = new GraphTools(board, playerID, playerID);
    	if(playID == 1) {
    		graphToolsThem = new GraphTools(board, 2, playerID);
    	}else {
    		graphToolsThem = new GraphTools(board, 1, playerID);
    	}
    }

    @Override
    public void lastMove(PlayerMove playerMove) {
        int row = playerMove.getCoordinate().getRow();
        int col = playerMove.getCoordinate().getCol();
        int id = playerMove.getPlayerId();
        board.get(row).set(col, id);
        graphToolsMe.updateBoard(board);
        graphToolsThem.updateBoard(board);
    }

    @Override
    public void otherPlayerInvalidated() {
        move();
    }

    @Override
    public PlayerMove move() {
        List<PlayerMove> pMoves = allLegalMoves();
    	if (firstMove == true) {
    		firstMove = false;
    		
    		if(playID == 1) {
    			if(dim%2 == 1) {
                    return new PlayerMove(new Coordinate(dim, dim), playID);
                }
                else {
                    return new PlayerMove(new Coordinate(dim-1, dim-1), playID);
                }
    		}
    		if (playID == 2) {
                if (dim%2 == 1) {
                    PlayerMove pm = new PlayerMove(new Coordinate(dim, dim), playID);
                    if (pMoves.contains(pm) == true) {
                        return pm;
                    }
                }
                else {
                    PlayerMove pm = new PlayerMove(new Coordinate(dim-1, dim-1), playID);
                    if (pMoves.contains(pm) == true) {
                        return pm;
                    }
                }
            }
    	}

        ArrayList<PlayerMove> temp = graphToolsMe.getMyShortestPath();
        temp = getValidMoves(temp);
        ArrayList<PlayerMove> temp2 = graphToolsThem.getMyShortestPath();
        temp2 = getValidMoves(temp2);
        if (temp.size() <= 1 && !temp.isEmpty()) {
            return temp.get(0);
        }
        if (temp2.size() <= 1 && !temp2.isEmpty()) {
            return temp2.get(0);
        }
        if(temp.size() <= temp2.size() -5&& !temp.isEmpty()) {
        	return temp.get(0);
        }
        if(!temp2.isEmpty()) {
        	return temp2.get(0);
        }else{
        	return temp.get(0);
        }
        /*
        ArrayList<Cluster> opponClusters = new ArrayList<>();
        ArrayList<Cluster> myClusters = new ArrayList<>();
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
        for ( int i = 1; i<board.size()-1; i++) {//Iterates over the board ignoring the borders
            for (int j = 1; j<board.size()-1; j++) {
                if(i%2 == 1 && j%2 == 1) {//if both indexes are odd (ie: if they don't start on a node)
                    int id = board.get(i).get(j);
                    if (id == 1 || id == 2) {
                        Coordinate coord = new Coordinate(i,j);
                        if (coords.contains(coord) == false) {
                            ArrayList<Coordinate> cluster = new ArrayList<>();
                            Cluster size = clusterSize(id, coord, cluster, coords);
                            if (id == playID) {
                                myClusters.add(size);
                            }
                            else {
                                opponClusters.add(size);
                            }
                        }
                    }
                }
            }
        }
        coords = null; // doesn't reset so to avoid duplicate clusters

        TreeMap<Integer, Cluster[]> combine = combineCluster(opponClusters, myClusters);

        //Note: potential if statement for minimum key size limitation.
        while (combine.size() > 0) { //While there are still pairings in combine.
            Map.Entry<Integer, Cluster[]> entry = combine.lastEntry();
            combine.remove(entry.getKey(), entry.getValue()); //Removes the entry just gotten from the Map
            Cluster[] pair = entry.getValue();
            Cluster c1 = pair[0];
            Cluster c2 = pair[1];
            Coordinate[] coords1 = c1.getCoords();
            Coordinate[] coords2 = c2.getCoords();
            for (int i = 0; i<coords1.length; i++) { //2d loop iterates over both clusters in the array to test for matching coordinates.
                for (int j = 0; j<coords2.length; j++) {
                    Adjacent adjacent = isAdjacent(coords1[i], coords2[j]);
                    if (adjacent.getAdjacency() == true) {
                        return (new PlayerMove(adjacent.getCoordinate(), playID));
                    }
                }
            }
        }

        ArrayList<PlayerMove> blockMoves = blockMoves();
        if (blockMoves.size() > 0) { //If blockmoves exists
            int index = (int) (Math.random()*(blockMoves.size()-1)); //Pick one at random
            return blockMoves.get(index);
        }

        return pMoves.get(0); //Is temporary
        */
    }

    /**
     * A method that returns a list of moves to block your opponents end points.
     * @return An arraylist that contains nodes bordering your opponents victory points.
     */
    private ArrayList<PlayerMove> blockMoves() {
        List<PlayerMove> pMoves = allLegalMoves();
        ArrayList<PlayerMove> blockMoves = new ArrayList<>();
        if (playID == 1) { //If you are player1
            ArrayList<Integer> row = board.get(1);
            for (int i = 1; i<row.size(); i+=2) {
                PlayerMove pm = new PlayerMove(new Coordinate(1,i), playID);
                if (pMoves.contains(pm) == true) {
                    blockMoves.add(pm);
                }
            }
            ArrayList<Integer> botRow = board.get(board.size()-2);
            for (int i = 1; i<board.size(); i+=2) {
                PlayerMove pm = new PlayerMove(new Coordinate(1,board.size()-2), playID);
                if (pMoves.contains(pm) == true) {
                    blockMoves.add(pm);
                }
            }
        }
        else if (playID == 2) { //if you are player2
            for(int i = 1; i<board.size(); i+=2) {
                PlayerMove pm = new PlayerMove(new Coordinate(i,1), playID);
                if (pMoves.contains(pm) == true) {
                    blockMoves.add(pm);
                }
            }
            for (int i = 1; i<board.size(); i+=2) {
                PlayerMove pm = new PlayerMove(new Coordinate(i,board.size()-2), playID);
                if (pMoves.contains(pm) == true) {
                    blockMoves.add(pm);
                }
            }
        }
        return blockMoves;
    }

    /**
     * A method that tests the adjacency of two coordinates.
     * @param c1 - A coordinate that's altered to test for adjacency.
     * @param c2 - A coordinate that is compared to to test for adjacency.
     * @return An object of type adjacent, containing a boolean and coordinate (if adjacent).
     */
    private Adjacent isAdjacent(Coordinate c1, Coordinate c2) {
        Coordinate up = new Coordinate(c1.getRow()-2, c1.getCol()); //creates adjacent coordinates
        Coordinate down = new Coordinate(c1.getRow()+2, c1.getCol());
        Coordinate right = new Coordinate(c1.getRow(), c1.getCol()+2);
        Coordinate left = new Coordinate(c1.getRow(), c1.getCol()-2);
        if (up.equals(c2) == true) {
            Coordinate reCoord = new Coordinate(c1.getRow()-1, c1.getCol()); //the coordinate between adjeacent spaces on the board.
            List<PlayerMove> pm = allLegalMoves();
            PlayerMove actual = new PlayerMove(reCoord, playID);
            if (pm.contains(actual) == true) {
                return new Adjacent(true, reCoord); //Send this coordinate back as the inBetween coordinate.
            }
        }
        if (down.equals(c2) == true) {
            Coordinate reCoord = new Coordinate(c1.getRow()+1, c1.getCol());
            List<PlayerMove> pm = allLegalMoves();
            PlayerMove actual = new PlayerMove(reCoord, playID);
            if (pm.contains(actual) == true) {
                return new Adjacent(true, reCoord); //Send this coordinate back as the inBetween coordinate.
            }
        }
        if (right.equals(c2) == true) {
            Coordinate reCoord = new Coordinate(c1.getRow(), c1.getCol()+1);
            List<PlayerMove> pm = allLegalMoves();
            PlayerMove actual = new PlayerMove(reCoord, playID);
            if (pm.contains(actual) == true) {
                return new Adjacent(true, reCoord); //Send this coordinate back as the inBetween coordinate.
            }
        }
        if (left.equals(c2) == true) {
            Coordinate reCoord = new Coordinate(c1.getRow(), c1.getCol()-1);
            List<PlayerMove> pm = allLegalMoves();
            PlayerMove actual = new PlayerMove(reCoord, playID);
            if (pm.contains(actual) == true) {
                return new Adjacent(true, reCoord); //Send this coordinate back as the inBetween coordinate.
            }
        }
        return new Adjacent(false, null);
    }

    /**
     * A method that combines two sets of clusters into a tree map to
     *      find the largest combined size of 2 of the clusters.
     * @param opponClusters - The opponents clusters.
     * @param myClusters - My clusters.
     * @return A treemap with size as the key and an array of two clusters as a value.
     */
    private TreeMap<Integer, Cluster[]> combineCluster(ArrayList<Cluster> opponClusters, ArrayList<Cluster> myClusters) {
        TreeMap<Integer, Cluster[]> combine = new TreeMap<>();
        for (Cluster clust : opponClusters) { //Iterate through opponents clusters
            int size = clust.getSize();
            for (Cluster compare : opponClusters) {
                if (compare.equals(clust) == false) { //Compare for equality between the 2 clusters.
                    size = size + compare.getSize();
                    Cluster[] add = {clust, compare};
                    combine.put(size, add); //If the clusters are not equal combine their size and add them to the map.
                }
            }
        }
        for (Cluster clust : myClusters) { //Iterate through opponents clusters
            int size = clust.getSize();
            for (Cluster compare : myClusters) {
                if (compare.equals(clust) == false) { //Compare for equality between the 2 clusters.
                    size = size + compare.getSize();
                    Cluster[] add = {clust, compare};
                    combine.put(size, add); //If the clusters are not equal combine their size and add them to the map.
                }
            }
        }
        return combine;
    }

    /**
     * A method intended to get the size of clusters so that they can
     *      be connected.
     * @param id A player id
     * @return The size of a cluster for the given player id.
     */
    private Cluster clusterSize(int id, Coordinate coord, ArrayList<Coordinate> cluster, ArrayList<Coordinate> coords) {
        coords.add(coord);
        cluster.add(coord);
        Coordinate up = new Coordinate(coord.getRow()-1, coord.getCol()); //creates adjacent coordinates
        Coordinate down = new Coordinate(coord.getRow()+1, coord.getCol());
        Coordinate right = new Coordinate(coord.getRow(), coord.getCol()+1);
        Coordinate left = new Coordinate(coord.getRow(), coord.getCol()-1);
        if (coords.contains(up) == false) { //If coordinate hasn't already been checked.
            if (in_bounds(up.getRow(), up.getCol()) == true) {
                if (board.get(up.getRow()).get(up.getCol()) == id) { //And it belongs to the passed in id.
                    clusterSize(id, up, cluster, coords); //Recursive call centered on this coordinate.
                }
            }
        }
        if (coords.contains(down) == false) {
            if (in_bounds(down.getRow(), down.getCol()) == true) {
                if (board.get(down.getRow()).get(down.getCol()) == id) {
                    clusterSize(id, down, cluster, coords);
                }
            }
        }
        if (coords.contains(right) == false) {
            if (in_bounds(right.getRow(), right.getCol()) == true) {
                if (board.get(right.getRow()).get(right.getCol()) == id) {
                    clusterSize(id, right, cluster, coords);
                }
            }
        }
        if (coords.contains(left) == false) {
            if (in_bounds(left.getRow(), left.getCol()) == true) {
                if (board.get(left.getRow()).get(left.getCol()) == id) {
                    clusterSize(id, left, cluster, coords);
                }
            }
        }
        Coordinate[] contains = new Coordinate[cluster.size()];
        for(int i = 0; i<cluster.size(); i++) {
            contains[i] = cluster.get(i);
        }
        return new Cluster(contains.length, contains); //Creates new Cluster
    }


    public List<PlayerMove> allLegalMoves() {
        ArrayList<PlayerMove> legalMoves = new ArrayList<PlayerMove>();
        for (int i = 1; i<board.size()-1; i++) {
            for (int j = 1; j<board.get(i).size()-1; j++) {
                if (board.get(i).get(j) != 1 && board.get(i).get(j) != 2) {
                    Coordinate coord = new Coordinate(i, j);
                    legalMoves.add(new PlayerMove(coord, playID));
                }
            }
        }
        return legalMoves;
    }


    public int fewestSegmentsToVictory(int playId) {
    	return graphToolsMe.getMyShortestPath().size();
    }

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
     * Returns valid moves based on a list of moves
     * 
     * @param unfilteredMoves A list of moves that may be valid
     * @return A list of moves that are valid
     */
	private ArrayList<PlayerMove> getValidMoves(ArrayList<PlayerMove> unfilteredMoves) {
		ArrayList<PlayerMove> validMoves = new ArrayList<>();
		for(int i = 0; i < unfilteredMoves.size(); i ++) {
			int moveX = unfilteredMoves.get(i).getCoordinate().getCol();
			int moveY = unfilteredMoves.get(i).getCoordinate().getRow();
			if(board.get(moveY).get(moveX) == 0) {
				validMoves.add(unfilteredMoves.get(i));
			}
		}
		return validMoves;
	}
}
