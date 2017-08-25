import java.util.Collection;
import java.util.TreeSet;

/**
 * The Course class.
 * 
 * This stores information about a course. This includes the course ID, name, roster, and teacher.
 * 
 * @author Mike Canning
 */
public class Course implements Comparable<Course>{
	private Professor professor;
	private TreeSet<Student> student_list = new TreeSet<Student>();
	private int course_id;
	private String course_name;
	
	/**
     * The constructor for the course class. Sets a default professor, and sets the name and ID.
     *
     * @param id The course ID
     * @param name The course name
     */
	public Course(int id, String name){
		this.course_id = id;
		this.course_name = name;
		this.professor = Professor.TBD;
	}
	
	/**
     * Returns the course_id
     * 
     * @return The course id
     */
	public int getID(){
		return course_id;
	}
	
	/**
     * Returns the course_name
     * 
     * @return The course name
     */
	public String getName() {
		return course_name;
	}
	
	/**
     * Sets the course professor
     * 
     * @param professor The professor of the course
     */
	public void addProfessor(Professor professor) {
		this.professor = professor;
	}
	
	/**
     * Returns the course roster
     * 
     * @return The course roster
     */
	public Collection<Student> getStudents() {
		return student_list;
	}
	
	/**
     * Adds a student to the course
     * 
     * @param student The to add to the course
     * 
     * @return True if the student was added False if the student was already enrolled
     */
	public boolean addStudent(Student student) {
		if(student_list.contains(student)) {
			return false;
		}else{
			student_list.add(student);
			return true;
		}
	}
	
	/**
     * Returns the course professor name
     * 
     * @return The the professor's name
     */
	public Professor getProfessor() {
		return professor;
	}
	
	/**
     * Checks to see if a student is enrolled in a course; returns true if they are, false if they are not.
     * 
     * @param user The user to check to see of they are enrolled.
     * @return True if the student is enrolled, false if they are not.
     */
	public boolean isEnrolled(User user) {
		if(!(user instanceof Student)) {
			return false;
		}else{
			Student student_to_check = (Student) user;
			if(student_list.contains(student_to_check)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
     * Compares this course to another
     * @param course_to_compare The course with which to compare this course
     * @return a value less than 0 if this course has an id less the other course, 0 if the two courses have the same id, or a value greater than 0 if this course has an id greater than the other course.
     */
	public int compareTo(Course course_to_compare){
		if(course_to_compare.course_id < this.course_id){
			return 1;
		}else if(course_to_compare.course_id == this.course_id){
			return 0;
		}else{
			return -1;
		}
	}
	
	/**
     * Checks to see if this course has the same code as another
     * @param course_to_compare The course being compared to
     * @return True if it is equal, false if it is not.
     */
	public boolean equals(Course course_to_compare) {
		if(course_to_compare.course_id == this.course_id) {
			return true;
		}else{
			return false;
		}
	}
	/**
     * Returns the course hashCode; this is just the course id.
     * 
     * @return The hashCode (course id)
     */
	public int hashCode() {
		return this.getID();
	}
	
	/**
     * Returns a summary of the course as a string.
     * 
     * @return A course summary as a string.
     */
	public String toString() {
		return(this.getID() + "{" + this.getName() + "}: " + getProfessor().toString());
	}
}
