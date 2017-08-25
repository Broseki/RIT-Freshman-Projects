package edu.rit.cs;

import java.util.Collection;

/**
 * The representation of a single configuration for the backtracking algorithm.
 * {@link Backtracker} depends on these routines being implemented in a
 * subclass in order to solve a problem.
 *
 * @author sps
 */
public interface Configuration {
    /**
     * Get the collection of successors from the current one.
     *
     * @return All successors, valid and invalid
     */
    public abstract Collection< Configuration > getSuccessors();

    /**
     * Is the current configuration valid or not?
     *
     * @return true if valid; false otherwise
     */
    public abstract boolean isValid();

    /**
     * Is the current configuration a goal?
     * @return true if goal; false otherwise
     */
    public abstract boolean isGoal();
}
