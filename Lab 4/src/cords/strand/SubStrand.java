package cords.strand;

import java.io.PrintStream;

import cords.Cord;
/**
 * This stores a base cord and a start point and end point. These are then used to interact with a strand as if it were a
 * substring. To the user it looks like the original base cord was sliced and modified, but in reality it is
 * still intact; the interaction is restricted to the substring between a given start_pos and end_pos.
 * @author Michael Canning
 */
class SubStrand extends Strand {
	
	private Cord base; // The base cord to slice
	private int start_pos; // Where the slice starts
	private int end_pos; // Where the slice ends
	
	private int size; // The size of the sliced strand
	
	/**
     * Creates a new strand with a new start and end location used to return the correct slice
     * @param base The Cord that is being sliced
     * @param start_pos Where to start the slice
     * @param end_pos Where to end the slice
     */
    public SubStrand(Cord base, int start_pos, int end_pos) {
    	this.base = base;
    	this.start_pos = start_pos;
    	this.end_pos = end_pos;
    	this.size = end_pos - start_pos;
    }

    @Override
    /**
     * Returns the size of the number of characters in the newly sliced cord
     * @return The size of the newly sliced cord
     */
    public int getSize() {
        return size;
    }

    @Override
    /**
     * Returns a character at a given index in a sliced cord
     * @param index The index of the character being requested
     * @return The character in the slice at the given index
     */
    public char get( int index ) {
        return base.get(index + start_pos);
    }

    @Override
    /**
     * Returns the string starting at start_pos, and ending at end_pos
     * @return The sliced string
     */
    public String toString() {
        return base.toString().substring(start_pos, end_pos);
    }

    @Override
    /**
     * Prints the cord's content to the given print stream
     * @param w The PrintStream to print to
     */
    public void toOutput( PrintStream w ) {
    	w.print(base.toString().substring(start_pos, end_pos));
    }

    @Override
    /**
     * Prints part of the cord's content to the given PrintStream starting at the index from, and ending at the index to
     * @param w The PrintStream to print to
     * @param from The index to start printing from
     * @param to The index to stop printing at
     */
    public void toOutput( PrintStream w, int from, int to ) {
    	w.print(base.toString().substring(start_pos + from, start_pos + (to - from)));
    }

}
