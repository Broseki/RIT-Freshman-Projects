/**
 * The Professor class.
 * 
 * This is used to build a Professor user object, and interpret the permissions of that user. This
 * also stores the default professor "TBD" information.
 * 
 * @author Mike Canning
 */
public class Professor extends User{

	/**
     * The constructor for the Professor class. This uses a given id and username to create a
     * superclass user object.
     * 
     * @param id The student's ID
     * @param username The student's username
     */
	public Professor(int id, String username) {
		super(id, username);
	}
	
	public static final Professor TBD = new Professor(-1, "TBD"); // This is a default object which is used to define the professor for classes without one

	/**
     * This checks to see if a Professor user can perform a certain action.
     *
     * @param cmd The command being performed
     * @param vocabulary The Vocabulary object where permissions are defined
     * @return True if the user can perform that action; otherwise this returns false.
     */
	@Override
	public boolean mayPerform(String cmd, Vocabulary vocabulary) {
		if(vocabulary.lookup(cmd).role == Vocabulary.Role.PROFESSOR || vocabulary.lookup(cmd).role == Vocabulary.Role.OPEN){
			return true;
		}else{
			return false;
		}
	}

}
