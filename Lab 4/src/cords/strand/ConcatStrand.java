package cords.strand;

import java.io.PrintStream;

import cords.Cord;
/**
 * This class is used to combine two given strands. It does not modify the underlying Strands,
 * but instead it has functions for reading the two strands as if they were one strand.
 * To the user it appears that the two strands were combined and stored as one string, but in reality
 * they are stored as two different cords.
 * @author Michael Canning
 */
class ConcatStrand extends Strand{

	private Cord cord_x; // The first cord in the concatenation
	private Cord cord_y; // The second cord in the concatenation
	
	private int size; // The size of the concatenated string
	
	/**
     * Initializes the concatenated strand object using two given cords
     * @param cord_x The first cord to combine with the second cord
     * @param cord_y The second cord to combine with the first
     */
	public ConcatStrand(Cord part_one, Cord part_two) {
		this.cord_x = part_one;
		this.cord_y = part_two;
		
		size = cord_x.getSize() + cord_y.getSize();
	}
	
	/**
     * Prints the two cords combined to the given PrintStream
     * @param pw The PrintStream to print to
     */
	@Override
	public void toOutput(PrintStream pw) {
		pw.print(cord_x.toString() + cord_y.toString());
	}

	@Override
	/**
     * Prints the concatenated cords starting at the from index and ending at the to index to a given PrintStream
     * @param pw The PrintStream to print to
     * @param from The index to start printing from
     * @param to The index t stop printing at
     */
	public void toOutput(PrintStream pw, int from, int to) {
		if(from > cord_x.getSize() - 1){
			pw.print((cord_y.slice(from - cord_x.getSize() - 1, to - cord_x.getSize() - 1).toString()));
		}else if(to < cord_x.getSize()){
			pw.print(cord_x.slice(from, to));
		}else{
			pw.print(cord_x.slice(from, cord_x.getSize() - 1).toString() + cord_y.slice(0, to - cord_x.getSize() - 1).toString());
		}
	}

	@Override
	/**
     * Returns the size of the two concatenated cords
     * @return The size of the two cords combined
     */
	public int getSize() {
		return size;
	}

	@Override
	/**
     * Returns a character from the concatenated strands at a given point
     * @param index The index of the character to return
     * @return The character at the given index
     */
	public char get(int index) {
		if(index > cord_x.getSize() - 1) {
			return cord_y.get(index - cord_x.getSize());
		}else{
			return cord_x.get(index);
		}
	}

	@Override
	/**
     * Returns the two cords in raw string form
     * @return The concatenated cords in string form
     */
	public String toString() {
		return cord_x.toString() + cord_y.toString();
	}
	
}
