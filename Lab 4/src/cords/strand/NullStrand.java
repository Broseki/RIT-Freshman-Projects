package cords.strand;

import java.io.PrintStream;

/**
 * A Strand that represents an empty string.
 * This class is here for two reasons.
 * <ol>
 *   <li>
 *     It is more efficient than having some other kind
 *     of Strand with a size equal to 0.
 *   </li>
 *   <li>
 *     As provided code, it is a fair illustration to the student
 *     about how a concrete subclass of Strand, and Cord,
 *     is built.
 *   </li>
 * </ol>
 */
class NullStrand extends Strand {

    /**
     * Build a new NullStrand.
     */
    public NullStrand() {
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public char get( int index ) {
        throw new IndexOutOfBoundsException( Integer.toString( index ) );
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void toOutput( PrintStream w ) {}

    @Override
    public void toOutput( PrintStream w, int from, int to ) {
        if ( from != 0 ) {
            throw new IndexOutOfBoundsException( Integer.toString( from ) );
        }
        if ( to != 0 ) {
            throw new IndexOutOfBoundsException( Integer.toString( to ) );
        }
    }

}
