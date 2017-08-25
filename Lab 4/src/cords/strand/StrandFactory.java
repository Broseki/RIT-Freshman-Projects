package cords.strand;

import cords.Cord;
import cords.CordMaker;
/**
 * Builds a strand based on whether a string is passed to the factory or not. This uses overloading to build a
 * TextStrand is a string is given, or a NullStrand if not string is given.
 * @author Michael Canning
 */
public class StrandFactory implements CordMaker {

	@Override
	/**
     * Creates a non-null TextStrand
     * @param source The string to build the strand from
     * @return The built TextStrand
     */
	public Cord from(String source) {
		return new TextStrand(source);
	}
	
	/**
     * Returns a NullStrand
     * @return A NullStrand
     */
	public Cord from() {
		return new NullStrand();
	}

}
