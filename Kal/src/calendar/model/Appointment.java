package calendar.model;


/**
 * This is the appointment object used to store information for the Calendar. This stores date, time, and information about
 * given appointments, and returns that information when queried.
 *
 * @author Mike Canning @ RIT CS
 */
public class Appointment implements Comparable<Appointment> {
	
	private Time appointmentTime;
	private int appointmentDate;
	private String appointmentInfo;
	
	/**
     * The constructor for the appointment object; this takes in values and loads them into an appointment object.
     * @param theDate The day on the calendar to set the appointment for.
     * @param timeString The string to build the time object for the appointment from.
     * @param what The extra information about the appointment
     */
	public Appointment(int theDate, Time timeString, String what) {
		appointmentDate = theDate;
		appointmentTime = timeString;
		appointmentInfo = what;
	}

	/**
     * This builds an appointment from a CSV string.
     * @param args The CSV string to build the appointment from.
     */
	public static Appointment fromString(String args) {
		int date = Integer.parseInt(args.split(",")[0]);
		Time time = Time.fromString(args.split(",")[1]);
		String desc = args.split(",")[2];
		return new Appointment(date, time, desc);
	}
	
	/**
     * This returns the time object from this appointment.
     * @return The time object from this appointment.
     */
	public Time getTime() {
		return appointmentTime;
	}
	
	/**
     * This returns the date of the appointment
     * @return The date of the appointment
     */
	public int getDate() {
		return appointmentDate;
	}
	
	/**
     * This returns the description of the appointment
     * @return The description of the appointment
     */
	public String getText() {
		return appointmentInfo;
	}
	
	/**
     * This returns the appointment in CSV format.
     * @return The appointment is CSV format
     */
	public String csvFormat() {
		return appointmentDate + "," + appointmentTime.toString() + "," + appointmentInfo;
	}
	
	/**
     * This returns the appointment in an easy to read format
     * @return The the appointment in an easy to read format.
     */
	public String toString() {
		return "Date: " + appointmentDate + " | Time: " + appointmentTime.toString() + " | Info: " + appointmentInfo;
	}
	
	/**
     * This is the rules regarding comparing this object to other appointments. The order is based on when the appointment occurs. The
     * earlier the appointment is the higher the appointment's value
     * @param comparable_Appointment The appointment to compare this appointment to.
     * @return -1 if this appointment is less than the other appointment 0 if they are equal, and 1 if this appointment is greater than
     */
	public int compareTo(Appointment comparable_Appointment) {
		if((comparable_Appointment.getTime().toString().equals(this.getTime().toString())) && (comparable_Appointment.getDate() == this.getDate())) {
			return 0;
		}else if(comparable_Appointment.getDate() > this.getDate()) {
			return -1;
		}else if(comparable_Appointment.getDate() < this.getDate()) {
			return 1;
		}else{
			return this.getTime().compareTo(comparable_Appointment.getTime());
		}
	}
	
	/**
     * This compares another object to this appointment, and if they are equal it returns true;
     * @return True if the appointments are the same, false otherwise.
     */
	public boolean equals (Object object) {
		if(object instanceof Appointment) {
			Appointment appointment = (Appointment) object;
			if(this.csvFormat() == appointment.csvFormat()) {
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}
	}

}
