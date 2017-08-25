import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The Registrar class.
 * 
 * This acts as the main database, and information modification power house for the
 * CRI system.
 * 
 * The system database is stored in HashMaps and TreeMaps here, and there are accesser and mutator
 * methods here as well.
 * 
 * This class uses permissions defined in the Professor, Student, and LoggedOut Vocabulary classes to
 * determine if a user can use certain methods.
 * 
 * @author Mike Canning
 */

public class Registrar {
	
	private Vocabulary vocab = null;
	private User user = null;
	
	private TreeMap<Integer, Student> student_list = new TreeMap<Integer, Student>(); // The list of students by ID
	private TreeMap<String, User> user_list = new TreeMap<String, User>(); // The complete user list
	private TreeMap<String, Student> student_list_by_name = new TreeMap<String, Student>(); // The list of students by name
	private TreeMap<Integer, Professor> professor_list = new TreeMap<Integer, Professor>(); // The list of professors by ID
	private TreeMap<Integer, Course> class_list = new TreeMap<Integer, Course>(); // The class list by ID
	private Map<Integer, TreeSet<Integer>> professor_class_list = new HashMap<Integer, TreeSet<Integer>>(); // The classes professors are teaching by instructor ID
	private Map<Integer, TreeSet<Course>> student_class_list = new HashMap<Integer, TreeSet<Course>>(); // The classes students are taking by student ID
	 
	/**
     * The constructor for the Registrar class. Reads in designated files, after setting
     * up a logged out user, and new Vocabulary object.
     *
     * @param courseFile The course information file name
     * @param professorFile The professor information file name.
     * @param studentFile The student information file name
     */
	public Registrar(String courseFile, String professorFile, String studentFile) {
		vocab = new Vocabulary();
		user = LoggedOut.getInstance();
		
		read_course_file(courseFile);
		read_professor_file(professorFile);
		read_student_file(studentFile);
		
	}

	/**
     * Checks to see if a command is allowed given the current user object.
     *
     * @param command The command being checked for permissions
     * @return true or false depending on the current user's permission level
     */
	public boolean allowing(String command) {
		if(vocab.legal(command) && user.mayPerform(command,vocab)){
			return true;
		}
		return false;
	}
	
	/**
     * Attempts to enroll a student in a course. This is meant to be used by
     * a logged on student.
     *
     * @param args A list containing the course code the student wishes to enroll into.
     * @return The result of the attempt. Success if it worked, or an error message if it did not.
     */
	public String enrollStudent(String[] args){
		int course_code = Integer.valueOf(args[0]);
		if(!(class_list.containsKey(course_code))){
			return(StringConst.COURSE_DOES_NOT_EXIST);
		}else{
			student_class_list.get(user.getID()).add(class_list.get(course_code));
			class_list.get(course_code).addStudent(student_list.get(user.getID()));
			return(StringConst.SUCCESS);
		}
	}
	
	/**
     * Attempts to enroll a student in a course given a student name and course id. This
     * is meant to be used by logged in professors.
     *
     * @param args A list containing the student's name and the course id to enroll the student in
     * @return The result of the attempt. Success if it worked, or an error message if it did not.
     */
	public String enrollStudentByProf(String[] args){
		String student_name = args[0];
		int course_id = Integer.valueOf(args[1]);
		
		if(!(student_list_by_name.containsKey(student_name))) {
			return(StringConst.NO_SUCH_STUDENT);
		}else if(!(class_list.containsKey(course_id))) {
			return(StringConst.NO_SUCH_COURSE);
		}
		student_class_list.get(student_list_by_name.get(student_name).getID()).add(class_list.get(course_id));
		class_list.get(course_id).addStudent(student_list_by_name.get(student_name));
		return(StringConst.SUCCESS);
	}
	
	/**
     * This returns a list of all of the courses currently in the database if there are any.
     *
     * @param args This should be an empty list.
     * @return The result of the attempt. The course list if it worked, or an error message if it did not.
     */
	public String listAllCourses(String[] args){
		if(args.length != 0){
			return(StringConst.WRONG_NUM_ARGS);
		}else if (class_list.isEmpty()){
			return(StringConst.NO_COURSES_IN_DB);
		}else {
			String to_return = "";
			for(Entry<Integer, Course> entry : class_list.entrySet()) {
				to_return = to_return + entry.getValue().toString() + "\n";
			}
			return(to_return);
		}
	}
	
	/**
     * Returns a logged in student's class list if they are enrolled in any classes.
     *
     * @param args This should be an empty list.
     * @return The result of the attempt. The student's class list if it worked, or an error message if it did not.
     */
	public String listCoursesForStudent(String[] args){
		if(student_class_list.get(user.getID()).isEmpty()) {
			return(StringConst.NOT_ENROLLED_IN_ANY_COURSES);
		}else {
			String to_return = "";
			for (Course elem : student_class_list.get(user.getID())) {
					to_return = to_return + elem.toString() + "\n";
				}
			return(to_return);
		}
	}
	
	/**
     * Returns a list of all registered users to school faculty.
     *
     * @param args This should be an empty list.
     * @return The result of the attempt. The user list if it worked, or an error message if it did not.
     */
	public String listAllUsers(String[] args){
		if(args.length != 0){
			return(StringConst.WRONG_NUM_ARGS);
		}else {
			String to_return = "";
			for(Map.Entry<String, User> entry : user_list.entrySet()) {
				to_return = to_return + entry.getValue().toString() + "\n";
			}
			return(to_return);
		}
	}
	
	/**
     * Returns a list of all students in a given class if there are any.
     *
     * @param args A list containing the course to be checked.
     * @return The result of the attempt. The course roster if it worked, or an error message if it did not.
     */
	public String listCourseRoster(String[] args){
		int course_id;
		if(args.length != 1){
			return(StringConst.WRONG_NUM_ARGS);
		}else{
			course_id = Integer.valueOf(args[0]);
		}
		if(!(professor_class_list.get(user.getID()).contains(course_id))){
			return(StringConst.NOT_YOUR_COURSE);
		}else if(class_list.get(course_id).getStudents().isEmpty()){
			return(StringConst.NO_STUDENTS_IN_COURSE);
		}else{
			String to_return = "";
			for (Student elem : class_list.get(course_id).getStudents()) {
				  	to_return = to_return + elem.toString() + "\n";
				}
			return(to_return);
		}
	}
	
	/**
     * Returns a list of courses being taught to a logged in professor if there are any.
     *
     * @param args This should be an empty list.
     * @return The result of the attempt. The courses being taught be the current professor if it worked,
     * or an error message if it did not.
     */
	public String listCoursesForProf(String[] args){
		if(professor_class_list.get(user.getID()).isEmpty()) {
			return(StringConst.NOT_TEACHING_ANY_COURSES);
		}else if(args.length != 0){
			return(StringConst.WRONG_NUM_ARGS);
		} else {
			String to_return = "";
			for (Integer elem : professor_class_list.get(user.getID())) {
				  int class_id = elem;
				  to_return = to_return + class_list.get(class_id).toString() + "\n"; 
				}
			return(to_return);
		}
	}
	
	/**
     * Logs the current user out. This sets the user to a LoggedOut user object.
     *
     * @param args This should be a blank list
     * @return The result of the attempt; success if it worked, an error message if it did not.
     */
	public String logout(String[] args){
		if(user.getID() == -1){
			return(StringConst.NOT_LOGGED_IN);
		}else{
			user = LoggedOut.getInstance();
			return(StringConst.SUCCESS);
		}
	}
	
	/**
     * Attempts to log a user in given a username. Sets the user variable to the applicable user
     * object the supplied username exists in the database.
     *
     * @param args A list containing the user's username.
     * @return The result of the attempt. Success if it worked, or an error message if it did not.
     */
	public String login(String[] args){
		if(user.getID() != -1) {
			return(StringConst.ALREADY_LOGGED_IN);
		}else if(user_list.containsKey(args[0])) {
			user = user_list.get(args[0]);
			return(StringConst.SUCCESS);
		}else{
			return(StringConst.UNKNOWN_USER);
		}
	}
	
	/**
     * Reads a given course file into the database
     *
     * @param filename The name of the course information file.
     */
	private void read_course_file(String filename) {
		Scanner scan = null;
		try {
			scan = new Scanner (new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("The course file was not found!");
		}
		while(scan.hasNextLine()){
			String[] currentLine = scan.nextLine().split(",");
			class_list.put(Integer.valueOf(currentLine[0]), new Course(Integer.valueOf(currentLine[0]), currentLine[1]));
		}
	}
	
	/**
     * Reads a given professor file into the database
     *
     * @param filename The name of the professor information file.
     */
	private void read_professor_file(String filename) {
		Scanner scan = null;
		try {
			scan = new Scanner (new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("The professor file was not found!");
		}
		while(scan.hasNextLine()){
			String[] currentLine = scan.nextLine().split(",");
			int user_id = Integer.valueOf(currentLine[0]);
			String username = currentLine[1];
			Professor professor = new Professor(user_id, username);
			user_list.put(username, professor);
			professor_list.put(user_id, professor);
			professor_class_list.put(user_id, new TreeSet<Integer>());
			for(int i = 2; i < currentLine.length; i++){
				professor_class_list.get(user_id).add(Integer.valueOf(currentLine[i]));
				class_list.get(Integer.valueOf(currentLine[i])).addProfessor(professor);
			}
		}
	}
	
	/**
     * Reads a given student file into the database
     *
     * @param filename The name of the student information file.
     */
	private void read_student_file(String filename) {
		Scanner scan = null;
		try {
			scan = new Scanner (new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("The student file was not found!");
		}
		
		Comparator<Course> sort_by_course_name = new Comparator<Course>() {
			@Override
			public int compare(Course o1, Course o2) {
				return o1.getName().compareTo(o2.getName());
			}
        };
        
		while(scan.hasNextLine()){
			String[] currentLine = scan.nextLine().split(",");
			int user_id = Integer.valueOf(currentLine[0]);
			String username = currentLine[1];
			Student student = new Student(user_id, username);
			user_list.put(username, student);
			student_list.put(user_id, student);
			student_list_by_name.put(username, student);
			student_class_list.put(user_id, new TreeSet<Course>(sort_by_course_name));
			for(int i = 2; i < currentLine.length; i++){
				student_class_list.get(user_id).add(class_list.get(Integer.valueOf(currentLine[i])));
				class_list.get(Integer.valueOf(currentLine[i])).addStudent(student);
			}
		}
		
	}
}
