package cords.strand;

import java.io.PrintStream;
/**
 * This is the base of all Strands. This essentially wraps itself around a given string
 * and can be used to build other types of strands. It is very similar to OldStringFactory
 * @author Michael Canning
 */
class TextStrand extends Strand {
	private String text; // The raw string being encapsulated
	private int strand_size; // The size of the string being encapsulated
	
	/**
     * Initializes the strand using a given string
     * @param text The string to encapsulate into a Strand
     */
    public TextStrand(String text) {
    	this.text = text;
    	this.strand_size = text.length();
    }

    @Override
    /**
     * Returns the size of the encapsulated string
     * @return The size of the encapsulated string
     */
    public int getSize() {
        return strand_size;
    }

    @Override
    /**
     * Returns the character at a given index
     * @param index The index of the string being requested
     * @return The character at the given index
     */
    public char get( int index ) {
        return text.charAt(index);
    }

    @Override
    /**
     * Returns the string that is encapsulated
     * @return The raw string that is being encapsulated
     */
    public String toString() {
        return text;
    }

    @Override
    /**
     * Prints the string to a given PrintStream
     * @param w The PrintStream to print the string to
     */
    public void toOutput( PrintStream w ) {
    	w.print(text);
    }

    @Override
    /**
     * Prints a substring of the string being encapsulated to a given PrintStream
     * @param w The PrintStream being printed to
     * @param from Where the substring should start
     * @param to Where the substring should end
     */
    public void toOutput( PrintStream w, int from, int to ) {
    	if(from < 0){
    		throw new IndexOutOfBoundsException( Integer.toString( from ) );
    	}else if(to > text.length() - 1){
    		throw new IndexOutOfBoundsException( Integer.toString( to ) );
    	}
        w.print(text.substring(from, to));
    }

}
