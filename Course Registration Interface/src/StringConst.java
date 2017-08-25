/**
 * A collection of string constants that are used in this program as
 * messages to the user. The advantage of doing having this is that the
 * developer of the program does not have to worry about getting the
 * exact wording of the messages correct.
 */
public class StringConst {
    /**
     * This value is important to make the code portable across
     * operating systems with distinct line termination rules. It
     * is set to System.lineSeparator().
     */
    public static final String NEW_LINE = System.lineSeparator();

    /** A command that has no other output states that it ran successfully. */
    public static final String SUCCESS = "OK";

    /** The number of words in the command entered was incorrect. */
    public static final String WRONG_NUM_ARGS =
            "Incorrect number of arguments!";

    /** A message for ALL_COURSES when the database is empty */
    public static final String NO_COURSES_IN_DB =
            "There are no courses in the system at the current time.";

    /** A message for a command that has an invalid user argument */
    public static final String UNKNOWN_USER = "Unknown user";

    /** A message for logging in again without logging out first */
    public static final String ALREADY_LOGGED_IN = "You are already logged in!";

    /** A message for logging out when not logged in already */
    public static final String NOT_LOGGED_IN = "You are not logged in!";

    /** A message for a command that has an invalid course argument */
    public static final String COURSE_DOES_NOT_EXIST = "Course does not exist!";

    /** A message for TAKING for a student with no courses */
    public static final String NOT_ENROLLED_IN_ANY_COURSES =
            "You are not currently enrolled in any courses.";

    /** A message for TEACHING for a course with no students */
    public static final String NO_STUDENTS_IN_COURSE = "You have no students.";

    /**
     * A message for a professor who tries to do something with a course
     * s/he is not teaching
     */
    public static final String NOT_YOUR_COURSE =
            "You are not teaching this course!";

    /** A message for TEACHING for a professor who has no courses */
    public static final String NOT_TEACHING_ANY_COURSES =
            "You are not currently teaching any courses.";

    /** A message for any command that names a non-existent course */
    public static final String NO_SUCH_COURSE = "Course does not exist!";

    /** A message for any command that names a non-existent student */
    public static final String NO_SUCH_STUDENT = "Student does not exist!";
}
