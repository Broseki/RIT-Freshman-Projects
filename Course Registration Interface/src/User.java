/**
 * Represents a user who is (in the current design) either a professor or
 * student. All users have a unique user name and ID.
 * Different kinds of users will be subclassed off of this class.
 */
public abstract class User implements Comparable< User > {
    /**
     * The username (unique)
     */
    private String username;

    /**
     * The user ID (unique)
     */
    private int id;

    /**
     * A reference to permission levels for the various commands
     */
    private Vocabulary vocabulary;

    /**
     * Construct a generic user. (Subclasses will call this to
     * do most of the construction of their objects.)
     * Note that it is assumed that all users share the same
     * Vocabulary object.
     *
     * @param id a unique id for this user
     * @param username a unique name for this user
     * @rit.pre id &gt; 0
     * @rit.post getUsername() = username
     * @rit.post getId() = id
     */
    public User( int id, String username ) {
        this.username = username;
        this.id = id;
    }

    /**
     * What is this user's name?
     *
     * @return this user's name
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * What is this user's ID?
     *
     * @return this user's ID
     */
    public int getID() { return this.id; }

    /**
     * Are two users equal?
     *
     * @param other the other course
     * @return true iff other is a User and they have the same ID
     */
    @Override
    public boolean equals( Object other ) {
        boolean result = false;
        if ( other instanceof User ) {
            User u = (User)other;
            result = this.username.equals( u.username );
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return the hash of the user name
     */
    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    /**
     * Get a string representation of the user.
     * The format is as follows. Italics represent items from the instance
     * <pre>
     *     <em>class-name</em>{<em>id</em>.<em>name</em>}
     * </pre>
     *
     * @return the formatted string
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
               this.id + '.' + this.username + '}';
    }

    /**
     * (Future enhancement)
     * @return a bigger string
     */
    public String fullInfo() {
        return this.toString() + ", courses: " +
               // get a list of the courses by course name
               "Blah Blah Blah" +
               '}';
    }

    /**
     * Cause users to be naturally ordered alphabetically by name.
     *
     * @param other the user to compare to
     * @return a value less than 0 if this username is less than other's
     * username, 0 if the user names are the same, a value greater than 0
     * if this username is greater than other's username.
     */
    @Override
    public int compareTo( User other ) {
        return this.username.compareTo( other.username );
    }

    /**
     * Is the given command legal for this kind of user?
     * (The vocabulary is consulted to help answer this question.)
     * @param cmd the command just entered by the user (first word)
     * @param vocabulary information about the legality and permissions
     *                   for CRI commands
     * @return whether or not the command named by <code>cmd</code>
     *         may be executed by a user of this type.
     * @see Vocabulary.CmdInfo
     */
    public abstract boolean mayPerform( String cmd, Vocabulary vocabulary );
}
