
/**
 * Methods used by test programs
 * @author James Heliotis
 */
public class util {

    /**
     * Make sure there are no command line arguments.
     * @param args the command line argument array
     * @throws RunTimeException if there are any arguments
     */
    public static void noArgs( String[] args ) {
        if ( args.length > 0 ) {
            throw new RuntimeException( "This program expects no arguments." );
        }
    }

    /**
     * Make sure there are the correct number of command line arguments.
     * @param args the command line argument array
     * @param count the desired number of command line arguments
     * @throws RunTimeException if the number of arguments is incorrect
     */
    public static void expectArgs( String[] args, int count ) {
        if ( args.length != count ) {
            throw new RuntimeException(
                "This program expects " + count + " arguments."
            );
        }
    }

}
