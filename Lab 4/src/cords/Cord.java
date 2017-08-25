package cords;

import java.io.PrintStream;

/**
 * This interface represents a set of character string operations
 * that could be implemented in a Java class. The operations are not
 * the same as those in java.lang.String. This set is much smaller.
 */
public interface Cord {

    /**
     * Return a new Cord based on a subrange of this one's characters.
     * @param from the starting index of the slice
     * @param to the index <em>one past</em> the end of the slice
     * @return the new Cord object
     */
    public abstract Cord slice( int from, int to );

    /**
     * Return a new Cord created by concatenating this one with the
     * argument.
     * @param s the second part of the new Cord
     * @return the new Cord object
     */
    public abstract Cord concatenate( Cord s );

    /**
     * Return a new Cord created by (potentially symbolically)
     * replacing this one's characters in the given range with the
     * argument.
     * @param from the starting index of the part to be replaced
     * @param to the index <em>one past</em> the end of the part to be replaced
     * @param s the new &quot;middle&quot; part of the result
     * @return the new Cord object
     */
    public abstract Cord replace( int from, int to, Cord s );

    /**
     * Return a new Cord created by (potentially symbolically)
     * removing this one's characters in the given range. It is
     * essentially equivalent to calling
     * <code>replace(from,to,"")</code>, but likely more efficient.
     * @param from the starting index of the part to be removed
     * @param to the index <em>one past</em> the end of the part to be removed
     * @return the new Cord object
     */
    public abstract Cord remove( int from, int to );

    /**
     * How big is this object?
     * @return the number of characters
     */
    public abstract int getSize();

    /**
     * What character is stored at a location?
     * @param index the location in question
     * @return the character get the given position.
     */
    public abstract char get( int index ) ;


    /**
     * &quot;Print&quot; this Cord object's characters on a
     * PrintStream. <em>No new-line is appended</em>.
     * The reason this is here is that if you used
     * <code>pw.print( strOpsObj.toString() )</code> instead,
     * this framework would be forced to first build a java.lang.String
     * instance out of the characters, which could be inefficient.
     * @param pw the output stream (System.out works.)
     */
    public abstract void toOutput( PrintStream pw );

    /**
     * &quot;Print&quot; portion of this Cord object's characters
     * on a PrintStream. <em>No new-line is appended</em>.
     * The reason this is here is complicated. In the case of Strands,
     * to avoid copying strings or sending characters one-by-one to
     * the PrintStream, one must be able to specify a subrange, and
     * let that get filtered down to the base Strings.
     * This method ought not be public and in this interface, but
     * if it is not, the subclasses cannot assume that everyone
     * implements it.
     * @param pw the output stream (System.out works.)
     * @param from the starting index of the range to output
     * @param to the index <em>one past</em> the end of the range to output
     */
    public abstract void toOutput( PrintStream pw, int from, int to );

    /**
     * Build a regular instance of java.lang.String from the characters
     * of this Cord object.
     * @return a printable version of the characters
     */
    public abstract String toString();

}
