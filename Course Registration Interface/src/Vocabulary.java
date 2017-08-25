import java.util.TreeMap;
import java.util.Map;

/**
 * This class is a collection of information about CRI commands.
 * <ol>
 *     <li>What strings are legal as the first item in a command line?</li>
 *     <li>For what type of user is each command appropriate?</li>
 *     <li>What is a good description of each command if a user
 *         requests it?</li>
 * </ol>
 * @author James Heliotis
 */
public class Vocabulary {

    /**
     * The roles currently associated with commands.
     * Note that students have some commands that professors don't.
     */
    public enum Role {
        /** a command with an OPEN role can be executed at any time. */
        OPEN,
        /**
         * A command with a STUDENT role can be executed when
         * a student is logged in.
         */
        STUDENT,
        /**
         * A command with a PROFESSOR role can be executed when
         * a professor is logged in.
         */
        PROFESSOR
    }

    /**
     * Because the data in Vocabulary is set up as a map keyed by a command
     * string, the rest of the data is put into a class whose instances
     * are the values of said map.
     */
    public static class CmdInfo {
        /**
         * The role associated with this command
         */
        public final Role role;
        /**
         * The help message associated with this command
         */
        public final String helpMsg;

        /**
         * Construct a CmdInfo instance for some command
         * @param role the role associated with this command
         * @param helpMsg the help message associated with this command
         */
        private CmdInfo( Role role, String helpMsg ) {
            this.role = role;
            this.helpMsg = helpMsg;
        }
    }

    /*
     * List of user command strings
     */
    public static final String ALL_COURSES = "all_courses";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String ADD = "add";
    public static final String TAKING = "taking";
    public static final String ALL_USERS = "all_users";
    public static final String ROSTER = "roster";
    public static final String TEACHING = "teaching";
    public static final String ENROLL = "enroll";

    /*
     * These last two commands are not linked to registrar methods.
     */
    public static final String HELP = "help";
    public static final String QUIT = "quit";

    /**
     * Maps string command names to information about those commands
     * @see CmdInfo
     */
    private Map< String, CmdInfo > commands;

    {
        commands = new TreeMap<>(); // chosen to alphabetize list of commands

        commands.put( ALL_COURSES, new CmdInfo(
                      Role.OPEN,
                      StringConst.NEW_LINE + '\t' +
                      "List all courses being offered" )
        );
        commands.put( LOGIN, new CmdInfo(
                      Role.OPEN,
                      "<professor name>" + StringConst.NEW_LINE + '\t' +
                      "Log in as student or professor" )
        );
        commands.put( LOGOUT, new CmdInfo(
                      Role.OPEN,
                      StringConst.NEW_LINE + '\t' +
                      "Go back to not-logged-in mode" )
        );
        commands.put( QUIT, new CmdInfo(
                      Role.OPEN,
                      StringConst.NEW_LINE + "\tQuit this program" ) );
        commands.put( HELP, new CmdInfo(
                      Role.OPEN,
                      StringConst.NEW_LINE + "\tDisplay commands" ) );

        commands.put( ADD, new CmdInfo(
                      Role.STUDENT,
                      "<course ID>" + StringConst.NEW_LINE + '\t' +
                      "Add a course to your schedule"
        ) );
        commands.put( TAKING, new CmdInfo(
                      Role.STUDENT,
                      StringConst.NEW_LINE + '\t' +
                      "List courses in which you are enrolled"
        ) );

        commands.put( ALL_USERS, new CmdInfo(
                      Role.PROFESSOR,
                      StringConst.NEW_LINE + '\t' +
                      "List all the users of this system."
        ) );
        commands.put( ROSTER, new CmdInfo(
                      Role.PROFESSOR,
                      "<course ID>" + StringConst.NEW_LINE + '\t' +
                      "List students enrolled in a course you are teaching"
        ) );
        commands.put( TEACHING, new CmdInfo(
                      Role.PROFESSOR,
                      StringConst.NEW_LINE + '\t' +
                      "List courses you are teaching"
        ) );
        commands.put( ENROLL, new CmdInfo(
                      Role.PROFESSOR,
                      "<student name> <course ID>" + StringConst.NEW_LINE + '\t' +
                      "Add a student to your course"
        ) );
    }

    /**
     * Is the command the user entered legal?
     * @param name the command string
     * @return true iff name is in the vocabulary
     */
    public boolean legal( String name ) {
        return commands.containsKey( name ) && !name.equals( HELP );
    }

    /**
     * Get the entire set of legal command strings
     * @return some kind of collection of the command strings
     */
    public Iterable< String > names() { return commands.keySet(); }

    /**
     * Get a command's related information
     * @param name the command string
     * @return the CmdInfo object associated with name
     */
    public CmdInfo lookup( String name ) { return commands.get( name ); }
}
