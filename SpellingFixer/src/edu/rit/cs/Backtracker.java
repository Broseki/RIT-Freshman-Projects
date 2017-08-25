package edu.rit.cs;

import java.util.List;
import java.util.Optional;

/**
 * This class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid configuration and return a
 * solution, if one exists.
 *
 * @author sps (Sean Strout @ RIT CS)
 * @author jeh (James Heliotis @ RIT CS)
 */
public class Backtracker {

    /**
     * Turn on this flag if you want to trace your algorithm's execution.
     */
    private static boolean DEBUG = false;

    /**
     * Try find a solution, if one exists, for a given configuration.
     *
     * @param config A valid configuration
     * @return A solution config, or null if no solution
     */
    public static Optional< Configuration > solve( Configuration config ) {
        if ( config.isGoal() ) {
            return Optional.of( config );
        }
        else {
            for ( Configuration child : config.getSuccessors() ) {
                if ( child.isValid() ) {
                    if ( DEBUG ) System.out.println( child + " VALID" );
                    Optional< Configuration > sol = solve( child );
                    if ( sol.isPresent() ) {
                        return sol;
                    }
                    else {
                        if ( DEBUG ) {
                            System.out.println(
                                    child + " did not lead to a solution" );
                        }
                    }
                }
                else {
                    if ( DEBUG ) System.out.println( child + " NOT VALID" );
                }
            }
            // implicit backtracking happens here
        }
        return Optional.empty();
    }

}
