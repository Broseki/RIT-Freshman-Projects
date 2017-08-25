import edu.rit.cs.Backtracker;
import edu.rit.cs.Configuration;
import spellingchecker.KBGraph;
import spellingchecker.WordConfig;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Use backtracking to find a legal word that is spelled by changing zero or
 * more letters from provided words to letter found nearby on the
 * US keyboard. If more than one word meets the criteria, which one gets
 * returned is unspecified.
 *
 * @author James Heliotis
 */
public class SpellFixer {

    public static final String IDEA_EXIT = ".";
    private static final String PROMPT = "> ";

    public static void main( String[] args ) throws IOException {

        KBGraph kbGraph = KBGraph.undirectedFromFile( "data/US.txt" );

        Scanner userIn = new Scanner( System.in );

        System.out.println( "Please type in words and I will correct them." );
        System.out.println();
        System.out.print( PROMPT );
        System.out.flush();

        while ( userIn.hasNext() ) {
            String word = userIn.next();
            if ( word.equals( IDEA_EXIT ) ) break;
            Optional< Configuration > answer =
                    Backtracker.solve( new WordConfig( kbGraph, word ) );
            if ( answer.isPresent() ) {
                String fixed = answer.get().toString();
                if ( !fixed.equals( word ) ) {
                    System.out.println( "By \"" + word +
                                        "\" I think you meant \"" +
                                        answer.get() +
                                        "\"." );
                }
                else {
                    System.out.println( "That word is fabulous." );
                }
            }
            else {
                System.out.println( "\"" + word + "\"?" );
            }
            System.out.print( PROMPT );
            System.out.flush();
        }
    }
}
