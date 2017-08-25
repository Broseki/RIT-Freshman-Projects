/**
 * The Student class.
 * 
 * This is used to build a student user object, and interpret the permissions of that user.
 * 
 * @author Mike Canning
 */
public class Student extends User {
	/**
     * The constructor for the Student class. This uses a given id and username to create a
     * superclass user object.
     * 
     * @param id The student's ID
     * @param username The student's username
     */
	public Student(int id, String username) {
		super(id, username);
	}

	/**
     * This checks to see if a Student user can perform a certain action.
     *
     * @param cmd The command being performed
     * @param vocabulary The Vocabulary object where permissions are defined
     * @return True if the user can perform that action; otherwise this returns false.
     */
	@Override
	public boolean mayPerform(String cmd, Vocabulary vocabulary) {
		if(vocabulary.lookup(cmd).role == Vocabulary.Role.STUDENT || vocabulary.lookup(cmd).role == Vocabulary.Role.OPEN){
			return true;
		}else{
			return false;
		}
	}

}
