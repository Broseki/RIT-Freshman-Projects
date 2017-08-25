import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

/**
 * The Course Registration Interface class.
 * It is both the main program and "front end" for the Registrar.
 * It may be run on the command line in two ways.
 * <pre>
 * $ java CRI course-file professor-file student-file
 * $ java CRI course-file professor-file student-file input-file
 * </pre>
 * Its job is to provide a user interface to the Registrar object.
 * It also initializes that object by providing the "database" files
 * for the Registrar to open.
 *
 * It runs a loop reading input either from standard input (the first way),
 * or from a file of input commands (the second way). However, note that
 * if an input file is being used and the file text is exhausted, the program
 * does not terminate. Instead, it switches to reading from standard input.
 * This is useful for testing in that you can set some things up in a file and
 * then try new things at the end.
 *
 * The program understands comments and tolerates blank lines in the input.
 * @see CRI#COMMENT
 *
 * @author Sean Strout
 * @author James Heliotis
 */
public class CRI {

    /**
     * What gets displayed at the start of every line before
     * the user types a command
     */
    public static final String PROMPT = "> ";

    /**
     * Text at and beyond this character on any line is ignored.
     */
    public static final char COMMENT = '#';

    /** connection to back end */
    private Registrar registrar;

    /** the list of commands and their authorization levels */
    private Vocabulary vocabulary;

    /**
     * Initialize the backend with data from files. File formats are
     * as follows. No spaces are allowed.
     * <dl>
     *     <dt>course file lines</dt>
     *     <dd>course id<code>,</code>course name</dd>
     *     <dt>professor file lines</dt>
     *     <dd>professor user id<code>,</code>professor name[<code>,</code>course id]<sup>*</sup></dd>
     *     <dt>student file lines</dt>
     *     <dd>student user id<code>,</code>student name[<code>,</code>course id]<sup>*</sup></dd>
     * </dl>
     *
     * @param courseFile    the course file
     * @param professorFile the professor file
     * @param studentFile   the student file
     * @throws FileNotFoundException if any of the files cannot be found
     */
    public CRI( String courseFile, String professorFile, String studentFile )
            throws FileNotFoundException {
        this.registrar =
                new Registrar( courseFile, professorFile, studentFile );
        this.setUpCommandMenu();
        this.vocabulary = new Vocabulary();
    }



    private Map< String, Function< String[], String > > cmdImpls;

    private void setUpCommandMenu() {
        cmdImpls = new HashMap<>();
        cmdImpls.put( Vocabulary.ALL_COURSES, this.registrar::listAllCourses );
        cmdImpls.put( Vocabulary.LOGIN, this.registrar::login );
        cmdImpls.put( Vocabulary.LOGOUT, this.registrar::logout );
        cmdImpls.put( Vocabulary.ADD, this.registrar::enrollStudent );
        cmdImpls.put( Vocabulary.TAKING,
                                      this.registrar::listCoursesForStudent );
        cmdImpls.put( Vocabulary.ALL_USERS, this.registrar::listAllUsers );
        cmdImpls.put( Vocabulary.ROSTER, this.registrar::listCourseRoster );
        cmdImpls.put( Vocabulary.TEACHING, this.registrar::listCoursesForProf );
        cmdImpls.put( Vocabulary.ENROLL, this.registrar::enrollStudentByProf );
        cmdImpls = Collections.unmodifiableMap( cmdImpls );
    }
    
    

    /**
     * The main loop repeatedly reads commands from the input.
     * If the command is valid it calls the appropriate registrar method.
     * If the input is coming from a file and the end of the file
     * is reached, this method will switch to using standard input.
     * (The Scanner is closed and a new one opened.)
     *
     * @param in    a Scanner attached to the input (either stdin or an
     *              input file)
     * @param stdin tells whether the scanner is attached to stdin or not. If
     *              not, the prompt and input command are displayed to standard
     *              output just to make it easier to follow the output.
     * @see CRI#processCommand(Scanner, boolean)
     */
    public void mainLoop( Scanner in, boolean stdin ) {
        boolean timeToQuit = false;
        if ( stdin ) System.out.print( PROMPT );
        // continue looping until there is no more input
        while ( !timeToQuit ) {
            // Read the next command and then call the appropriate method to
            // process it.
            if ( in.hasNextLine() ) {
                timeToQuit = processCommand( in, stdin );
            }
            else { // end of current input
                if ( !stdin ) { // if reading from file so far
                    in.close();
                    in = new Scanner( System.in ); // switch to standard input
                    stdin = true;
                } // else it's Ilsa time
            }

            if ( stdin && !timeToQuit ) System.out.print( PROMPT );
        }
    }

    /**
     * Read and process one command line from the input.
     * If the command is valid it splits up the rest of the line into string
     * arguments for the command and calls the appropriate registrar method.
     * The registrar method is responsible for validating the arguments.
     * <p>
     *     If the first string in the command line is not in the Vocabulary,
     *     <code>Unrecognized command <em>command</em></code> is displayed.
     * </p>
     * <p>
     *     As specified in Vocabulary, all commands have an authorization
     *     level. The login and logout commands change who the current user
     *     is.
     *     If the current user object rejects the command due to authorization,
     *     <code>Command not allowed with current login status</code>
     *     is displayed.
     * </p>
     *
     *
     * @param in    a Scanner attached to the input (either stdin or an
     *              input file)
     * @param stdin tells whether the scanner is attached to stdin or not. If
     *              not, the prompt and input command are displayed to standard
     *              output just to make it easier to follow the output.
     */
    private boolean processCommand( Scanner in, boolean stdin ) {
        boolean timeToQuit = false;
        String line = in.nextLine();
        if ( !stdin ) { // If commands come from a file, display them in output.
            System.out.println( PROMPT + line );
        }

        // Strip comment part.
        int commentLoc = line.indexOf( COMMENT );
        if ( commentLoc != -1 ) line = line.substring( 0, commentLoc );

        // Split line into command + arguments.

        String fields[] = line.split( "\\W+" );
        if (
                fields.length != 0 &&
                !fields[ 0 ].isEmpty()
                ) {
            String command = fields[ 0 ].toLowerCase();

            if ( !vocabulary.legal( command ) ) {
                if ( !command.equals( Vocabulary.HELP ) ) {
                    System.out.println(
                            "Unrecognized command \"" + command + '"'
                    );
                }
                vocabulary.names().forEach(
                        cmd -> System.out.println(
                                cmd + ' ' +
                                vocabulary.lookup( cmd ).helpMsg + '.'
                        )
                );
            }
            else {
                if ( command.equals( Vocabulary.QUIT ) ) {
                    timeToQuit = true;
                }
                else if ( !this.registrar.allowing( command ) ) {
                    System.out.println(
                            "Command not allowed with current login " +
                            "status." );
                }
                else {
                    String[] args =
                            Arrays.copyOfRange( fields, 1, fields.length );
                    String result = cmdImpls.get( command ).apply( args );
                    if ( result != null && result.length() > 0 ) {
                        System.out.println( result );
                    }
                }
            }
        }
        return timeToQuit;
    }

    /**
     * The main method of the CRI program.
     * Command line arguments are validated and the Scanner (standard input
     * or file named by fourth argument) is opened.
     * Finally, the mainLoop method is called.
     *
     * @param args command line arguments (used - see class description)
     * @throws FileNotFoundException if a file is not found
     */
    public static void main( String[] args ) throws FileNotFoundException {
        // display a usage message if the number of command line arguments
        // is not correct
        if ( args.length < 3 || args.length > 4 ) {
            System.err.println(
                    "Usage: java CRI course-file professor-file student-file " +
                    "[input]" );
            return;
        }

        // Create the frontend and tie it to the registrar.
        CRI sis = new CRI( args[ 0 ], args[ 1 ], args[ 2 ] );

        // Create a scanner that is tied to either standard input or an input
        // file.
        Scanner in;
        boolean stdin = true;
        // If no extra argument, tie the scanner to standard input.
        if ( args.length == 3 ) {
            in = new Scanner( System.in );
        }
        else {
            // Otherwise tie scanner to a file using the last command
            // line argument as the filename.
            in = new Scanner( new File( args[ 3 ] ) );
            stdin = false;
        }

        // Enter the main loop.
        sis.mainLoop( in, stdin );
        in.close();
    }

}
