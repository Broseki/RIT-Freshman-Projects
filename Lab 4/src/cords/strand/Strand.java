package cords.strand;

import cords.Cord;

/**
 * The base class for concrete Strand implementations. Some of the
 * higher level operations are implemented here in terms of the
 * basic ones.
 * @author James Heliotis & Michael Canning
 */
abstract class Strand implements Cord {

    /**
     * {@inheritDoc}
     * This is a facade method that just covers up a constructor call to
     * a concrete subclass.
     */
    @Override
    public Strand slice( int from, int to ) {
        return new SubStrand( this, from, to );
    }

    /**
     * {@inheritDoc}
     * This is a facade method that just covers up a constructor call to
     * a concrete subclass.
     */
    @Override
    public Strand concatenate( Cord s ) {
        return new ConcatStrand( this, s );
    }

    /**
     * {@inheritDoc}
     * This method works by making two subStrands out of the portions
     * before and after the portion to be eliminated, and then
     * concatenating them to the new portion.
     */
    @Override
    public Cord replace( int from, int to, Cord s ) {
        if (
                from < 0 || to > this.getSize() || from > to
                ) {
            throw new IndexOutOfBoundsException( from + "," + to );
        }
        Cord parts2and3;
        if ( to == this.getSize() ) {
            parts2and3 = s;
        }
        else {
            Strand part3 = slice( to, this.getSize() );
            parts2and3 = s.concatenate( part3 );
        }
        if ( from == 0 ) {
            return parts2and3;
        }
        else {
            return slice( 0, from ).concatenate( parts2and3 );
        }
    }

    /**
     * {@inheritDoc}
     * This method works by making two subStrands out of the portions
     * before and after the portion to be eliminated, and then
     * concatenating them together.
     */
    @Override
    public Strand remove( int from, int to ) {
        if (
                from < 0 || to > this.getSize() || from > to
                ) {
            throw new IndexOutOfBoundsException( from + "," + to );
        }
        if ( ( to == getSize() ) && ( from == 0 ) ) {
            return new NullStrand();
        }
        else if ( to == getSize() ) {
            return slice( 0, from );
        }
        else if ( from == 0 ) {
            return slice( to, getSize() );
        }
        else {
            return slice( 0, from ).concatenate(
                    slice( to, getSize() ) );
        }
    } // remove

    @Override
    abstract public int getSize();

    @Override
    abstract public char get( int index );

    /**
     * {@inheritDoc}
     * This is one case where the Strand is forced to manipulate
     * Strings.
     */
    @Override
    public abstract String toString();

    /**
     * Make sure a Cord index is in bounds.
     * @param index the index for this Strand
     */
    protected void checkBounds( int index ) {
        if ( index < 0 || index > this.getSize() ) {
            throw new IndexOutOfBoundsException( Integer.toString( index ) );
        }
    }

    /**
     * Make sure a pair of Cord indices is in bounds and properly
     * ordered.
     * @param from the first index for this Strand
     * @param to the second index for this Strand
     */
    protected void checkBounds( int from, int to ) {
        checkBounds( from );
        if ( to > this.getSize() || from > to ) {
            throw new IndexOutOfBoundsException( Integer.toString( to ) );
        }
    }

}
