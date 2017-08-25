/**
 * The LoggedOut class.
 * 
 * This is used to create a singleton type object which is used to define a logged
 * out user. It is similar to a Professor and Student object, but it has super limited
 * permissions.
 * 
 * @author Mike Canning
 */
public class LoggedOut extends User {
	
	public static LoggedOut instance = null;
	
	/**
     * The constructor for the LoggedOut class. This is here strictly for internal use to create
     * a User object.
     *
     * @param id This is always -1
     * @param username This is always NOBODIES_NAME
     */
	protected LoggedOut(int id, String username) {
		super(id, username);
	}
	
	/**
     * This is the real LoggedOut constructor. If LoggedOut is null it builds a new object using
     * the protected constructor LoggedOut. Otherwise it returns the LoggedOut object which was
     * previously created.
     *
     * @return The LoggedOut instance.
     */
	public static LoggedOut getInstance() {
		if(instance == null){
			instance = new LoggedOut(-1, "NOBODIES_NAME");
		}
		return instance;
	}
	
	/**
     * This checks to see if a LoggedOut user can perform a certain action.
     *
     * @param cmd The command being performed
     * @param vocabulary The Vocabulary object where permissions are defined
     * @return True if the user can perform that action; otherwise this returns false.
     */
	@Override
	public boolean mayPerform(String cmd, Vocabulary vocabulary) {
		if(vocabulary.lookup(cmd).role == Vocabulary.Role.OPEN){
			return true;
		}else{
			return false;
		}
	}
	
	

}
