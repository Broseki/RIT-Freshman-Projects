package cords;

import cords.strand.StrandFactory;
import cords.oldstring.OldStringFactory;

/**
 * The abstract factory that can also provide factory classes for the
 * known implementers of Cord.
 * Note that in practice more factory methods could be added without
 * affecting existing code.
 *
 * @author James Heliotis & Michael Canning
 */
public interface CordMaker {

    /**
     * Build a Cord-compliant object from a provided ordinary String.
     * @param source the ordinary String to be stored in this object
     * @return the new object built
     */
    public abstract Cord from( String source );

    /**
     * Make a factory that just wraps an adapter for the Cord
     * interface around any String it's given.
     * @return an OldStringFactory instance
     */
    public static CordMaker oldStringFactory() {
        return new OldStringFactory();
    }

    /**
     * Make a factory that builds Strand trees from any String it's given.
     * @return a StrandFactory instance
     */
    public static CordMaker strandFactory() {
        return new StrandFactory();
    }

}

