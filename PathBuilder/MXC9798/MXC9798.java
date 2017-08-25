package Players.MXC9798;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Interface.Coordinate;
import Interface.PlayerModulePart1;
import Interface.PlayerModulePart2;
import Interface.PlayerMove;

/**
 * This is a player module for use with the game "Pathbuilder". It can find a winner using a recursive
 * operation, and can find all valid moves. The current state of the board is represented by an ArrayList
 * of custom Space objects which simply store the space type.
 *
 * @author Mike Canning @ RIT CS
 */

public class MXC9798 implements PlayerModulePart2{
	private List<Coordinate> already_checked;
	private Space[][] board;
	private int playerId;
	
	
	/**
     * Initializes the player with a board of a given size and loads in the playerId
     * @param dim The board's dimensions
     * @param playerId The player number, 1 or 2
     */
	public void initPlayer(int dim, int playerId) {
		this.board = new Space[(2 * dim)][(2 * dim)];
		this.playerId = playerId;
		int current_spot_integer = 1;
		for(int a=0; a < board.length; a++) {
			if(current_spot_integer == 1) {
				current_spot_integer = 2;
			}else {
				current_spot_integer = 1;
			}
			for(int b=0; b < board.length; b++) {
				if( ((a%2) == 0) ) {
					this.board[a][b] = new Space();
					board[a][b].set_space_type(0);
					b++;
					this.board[a][b] = new Space();
					board[a][b].set_space_type(current_spot_integer);
				}else{
					this.board[a][b] = new Space();
					board[a][b].set_space_type(current_spot_integer);
					b++;
					this.board[a][b] = new Space();
					board[a][b].set_space_type(0);
				}
			}
		}
	}

	
	/**
     * Loads the last move of the game into the internal game board.
     * @param move The move that was last made.
     */
	public void lastMove(PlayerMove move) {
		int col = move.getCoordinate().getCol();
		int row = move.getCoordinate().getRow();
		if(move.getPlayerId() == 1) {
			board[row][col].set_space_type(3);
		}else{
			board[row][col].set_space_type(4);
		}
	}
	
	/**
     * Returns a legal move that the player makes. This currently returns the first
     * available, valid move.
     * @return A valid move that will be made by the computer player.
     */
	public PlayerMove move() {
		return allLegalMoves().get(0);
	}

	
	/**
     * Sets the game state to that of the other player being invalidated. This does nothing in reality.
     */
	public void otherPlayerInvalidated() {
		// TODO Auto-generated method stub	
	}
	
	/**
     * This is an internal debug function to print the board as the bot sees it. This is
     * disabled by default and needs to be called explicitly.
     */
	private void printBoard() {
		for(int a=0; a < board.length; a ++) {
			System.out.println("");
			for(int b=0; b < board.length; b ++) {
				System.out.print(board[a][b].get_space_type() + "|");
			}
		}
	}
	
	/**
     * Initializes the player with a board of a given size and loads in the playerId
     * @param arg0 The player to check if they won the game.
     * @return True if the given player won, false if the did not.
     */
	public boolean hasWonGame(int arg0) {
		//printBoard();
		already_checked = new ArrayList<>();
		for(int b=0; b < board.length; b ++) {
			if(arg0 == 2 && has_path(0, b, arg0)) {
				return true;
			}
		}
		already_checked.clear();
		for(int a=0; a < board.length; a ++) {
			if(arg0 == 1 && has_path(a, 0, arg0)) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * The recursive search function to find a path from one end of the board to another.
     * @param start_row The current row to start this iteration of the search from.
     * @param start_col The current col to start this iteration of the search from.
     * @param looking_at_player The player that we are check for a path.
     * @return True if a path was found, false otherwise
     */
	private boolean has_path(int start_row, int start_col, int looking_at_player) {
		already_checked.add(new Coordinate(start_row, start_col));
		int checkPoint = ( looking_at_player == 1 ) ? 3 : 4;
		if(is_ending_move(start_row, start_col, looking_at_player)) {
			//System.out.println("Player: " + looking_at_player + " - " + start_row + "|" + start_col);
			return true;
		}
		if(!point_already_checked(start_row + 1, start_col) && in_bounds(start_row + 1, start_col) && (board[start_row + 1][start_col].get_space_type() == checkPoint || board[start_row + 1][start_col].get_space_type() == looking_at_player)) {
			if(has_path(start_row + 1, start_col, looking_at_player)) {
				return true;
			}
		}
		if(!point_already_checked(start_row - 1, start_col) && in_bounds(start_row - 1, start_col) && (board[start_row - 1][start_col].get_space_type() == checkPoint || board[start_row - 1][start_col].get_space_type() == looking_at_player)) {
			if(has_path(start_row - 1, start_col, looking_at_player)) {
				return true;
			}
		}
		if(!point_already_checked(start_row, start_col + 1) && in_bounds(start_row, start_col + 1) && (board[start_row][start_col + 1].get_space_type() == checkPoint || board[start_row][start_col + 1].get_space_type() == looking_at_player)) {
			if(has_path(start_row, start_col + 1, looking_at_player)) {
				return true;
			}
		}
		if(!point_already_checked(start_row, start_col - 1) && in_bounds(start_row, start_col - 1) && (board[start_row][start_col - 1].get_space_type() == checkPoint || board[start_row][start_col - 1].get_space_type() == looking_at_player)) {
			if(has_path(start_row, start_col - 1, looking_at_player)) {
				return true;
			}
		}
		return false;

	}
	
	/**
     * Checks to see if a given point is in the list of already checked spaces for the recursive function
     * @param row The row to check for
     * @param col The col to check for
     * @return true if the point is in the list, false otherwise
     */
	private boolean point_already_checked(int row, int col) {
		for(int i = 0; i < already_checked.size(); i ++) {
			if(already_checked.get(i).getCol() == col && already_checked.get(i).getRow() == row) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * Checks to see if a point is on the game board
     * @param row The row to check
     * @param col The col to check
     * @return True if the point is on the board, false otherwise
     */
	private boolean in_bounds(int row, int col) {
		if((col < board.length && row < board.length) && (col > -1 && row > -1)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
     * Checks to see if a move is a valid move based on game rules.
     * @param col The col to check for validity
     * @param row The row to check for validity
     * @return True if the move is valid, false otherwise.
     */
	private boolean is_valid_move(int col, int row) {
		if(!in_bounds(row, col)) return false;
		if(board[row][col].get_space_type() != 0) return false;
		if (row == 0 || col == 0) {
			return false;
		}else if(row == board.length || col == board.length){
			return false;
		}else if(row %2 == 0 && col %2 != 0) {
			return false;
		}else if(row %2 != 0 && col %2 == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
     * Checks to see if a position is in the end zone for a given player
     * @param row The row to check
     * @param col The col to check
     * @param looking_at_player The player to check if the point is an ending point for
     * @return True if the point is an ending move, false otherwise.
     */
	private boolean is_ending_move(int row, int col, int looking_at_player) {
		if(col == board.length - 1 && looking_at_player == 1) {
			return true;
		}else if(row == board.length - 1 && looking_at_player == 2){
			return true;
		}
		else{
			return false;
		}
	}

	/**
     * Returns a list of all available, valid moves.
     * @return A list of all available, valid moves.
     */
	public List<PlayerMove> allLegalMoves() {
		List<PlayerMove> legalMoves = new ArrayList<>();
		for(int a=0; a < board.length; a++) {
			for(int b=0; b < board.length; b++) {
				//System.out.print(board[a][b].get_space_type() + "|");
				if( is_valid_move(b, a)) {
					legalMoves.add(new PlayerMove(new Coordinate(a, b), playerId));
				}
			}
			//System.out.println("");
		}
		return legalMoves;
	}

	/**
     * Returns the length of the shortest path to victory for a given player. This was not implemented under
     * instruction from Professor Heliotis.
     * @param arg0 The player to check for the shortest path for.
     * @return The length of the shortest path
     */
	public int fewestSegmentsToVictory(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
