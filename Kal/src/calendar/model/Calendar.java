package calendar.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * This is the model of the calendar. It includes the calendar's information, as well as including functions for changing the data inside the
 * model and alerting observers according to the observer pattern.
 *
 * @author Mike Canning @ RIT CS
 */
public class Calendar extends Observable {
	
	private ArrayList<TreeSet<Appointment>> cal = new ArrayList<TreeSet<Appointment>>();
	private int numOfDays;
	private String filename = "calendar.txt";
	private int dateChanged;
	
	/**
	 * This is the constructor for the calendar object. It initializes a calendar based on a given month size
	 * @param monthSize The number of days in a given month.
	 */
	public Calendar(int monthSize) {
		numOfDays = monthSize;
		for(int i = 0; i < monthSize; i ++) {
			cal.add(new TreeSet<Appointment>());
		}
	}
	
	/**
	 * This removes an appointment from the model.
	 * @param appt The appointment to remove.
	 */
	public void remove(Appointment appt) {
		for(int a = 0; a < cal.size(); a ++) {
			ArrayList<Appointment> temp = new ArrayList<Appointment>(cal.get(a));
			for(int b = 0; b < cal.get(a).size(); b ++) {
				if(temp.get(b).getDate() == appt.getDate() && temp.get(b).getTime().toString().equals(appt.getTime().toString())) {
					dateChanged = appt.getDate();
					cal.get(a).remove(appt);
					this.announceChange();
					return;
				}
			}
		}
	}
	
	/**
	 * This saves the model calendar to a file. The default filename is calendar.txt, but it will save to
	 * any calendar file that is passed in at the start.
	 */
	public void toFile() throws FileNotFoundException {
		File file = new File(filename);
		PrintWriter writer = new PrintWriter(file);
	    writer.println(numOfDays);
	    for(int a = 0; a < cal.size(); a ++) {
	    	ArrayList<Appointment> temp = new ArrayList<Appointment>(cal.get(a));
			for(int b = 0; b < cal.get(a).size(); b ++) {
					writer.println(temp.get(b).csvFormat());
				}
			}
	    writer.close();
	}

	/**
	 * This returns the number of days in the calendar
	 * @return The number of days in the calendar.
	 */
	public int numDays() {
		return numOfDays;
	}

	/**
	 * This returns the appointments for a given day as a list.
	 * @param date The day to fetch the appointments for.
	 * @return A list of appointments from the given day.
	 */
	public List<Appointment> appointmentsOn(int date) {
		return new ArrayList<Appointment>(cal.get(date - 1));
	}

	/**
	 * This adds an appointment based on supplied information.
	 * 
	 * @param date The day to add the appointment to
	 * @param time The time object to set the appointment to
	 * @param what A description of the appointment
	 */
	public void add(int date, Time time, String what) {
		cal.get(date - 1).add(new Appointment(date, time, what));
		dateChanged = date;
		this.announceChange();
	}
	
	/**
	 * This loads a calendar file and sets up a calendar object based off of that file
	 * 
	 * @param string The location of the file to load
	 * @return The calendar object that was created from the given file.
	 */
	public static Calendar fromFile(String string) throws IOException {
		File file = new File(string);
		Scanner scanner = new Scanner(file);
		int number_of_days = Integer.parseInt(scanner.nextLine());
		Calendar temp = new Calendar(number_of_days);
		while (scanner.hasNext()) {
			String thisLine = scanner.nextLine();
			Time time = Time.fromString((thisLine.split(",")[1]));
			temp.add(Integer.parseInt(thisLine.split(",")[0]), time, thisLine.split(",")[2]);
		}
		scanner.close();
		temp.filename = string;
		return temp;
	}
	
	/**
	 * This is for the observer pattern. This combines setChanged() and notifyObservers()
	 */
	private void announceChange() {
		super.setChanged();
		super.notifyObservers(dateChanged);
    }
}
