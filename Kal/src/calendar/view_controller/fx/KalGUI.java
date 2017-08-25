package calendar.view_controller.fx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import calendar.model.Appointment;
import calendar.model.Calendar;
import calendar.model.Time;

/**
 * This is the graphical part of the Kal application. All window layout and calls to
 * modify the model are placed here. This is both the view and the controller based on
 * the MVC design pattern. Note: The controller parts do not modify the view at all, and
 * thus this is truly MVC, I just put them in one file for readability.
 *
 * @author Mike Canning @ RIT CS
 */
public class KalGUI extends Application implements Observer {
	
	private GridPane grid = new GridPane();
	private Calendar model;
	private static String[] arguments;
	private Appointment selectedAppt = null;
	
	/**
     * The main method for the gui; this sets the aruments variable, and calls launch for the
     * JavaFX application.
     * @param args Contains the location of the calendar file to load
     */
	public static void main(String[] args) {
		arguments = args;
		Application.launch(args);
	}
	
	/**
     * Initializes the calendar based on either a calendar file, or a default setting of calendar size
     * 28.
     * @param args A list containing the location of the calendar file to load
     */
	public void init(String[] args) {
		try {
            if ( args.length == 0 ) {
                this.model = new Calendar( 28 );
            }
            else {
                this.model = Calendar.fromFile( args[0] );
            }
            this.model.addObserver( this );
        }
        catch( IOException e ) {
            System.err.println( e.getMessage() );
        }
	}

	/**
     * This is called by the observable model whenever it is updated to update the calendar in the GUI.
     * @param o The observable object
     * @param arg The changed object
     */
	@Override
	public void update(Observable o, Object arg) {
		assert o == this.model: "Unexpected subject of observation";
		updateCalendar();
	}
	
	/**
     * This is called by update and refreshes the calendar showed to the user on screen.
     */
	private void updateCalendar() {
		int current_row = 0;
		int current_col = 0;
		grid.getChildren().clear();
		for(int i = 0; i < model.numDays(); i ++) {
			VBox vbox = new VBox();
			vbox.setStyle("-fx-border-color:black;");
			Label dayLabel = new Label(Integer.toString(i + 1));
			Label apptAlertLabel;
			
			if(model.appointmentsOn(i + 1).isEmpty()) {
				apptAlertLabel = new Label(" ");
			}else {
				apptAlertLabel = new Label("!!!");
			}
			
			vbox.getChildren().add(dayLabel);
			vbox.getChildren().add(apptAlertLabel);
			
			vbox.setOnMouseClicked(event -> {
				try {
					showOptionButtons(Integer.parseInt(dayLabel.getText()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			grid.add(vbox, current_col, current_row);
			
			if(current_col == 6) {
				current_col = 0;
				current_row += 1;
			}else {
				current_col += 1;
			}
		}
	}
	
	/**
     * This contains the initial stage with the calendar, and the initial fetch of the model data.
     * @param primaryStage The stage to load the calendar to
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.init(arguments);
		
		VBox mainBox = new VBox();
		updateCalendar();
		
		// Start save button
		Button saveButton = new Button( "Save" );
		
		saveButton.setOnAction( event -> {
			try {
				model.toFile();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} );
		// End Save Button
		
		mainBox.setAlignment(Pos.CENTER);
		
		mainBox.getChildren().add(grid);
		mainBox.getChildren().add(saveButton);
		
		primaryStage.setScene( new Scene( mainBox ) );
        primaryStage.setTitle( "KalGUI" );
        primaryStage.show();
		
	}
	
	/**
     * This displays the screen with a list of appointments for a given day, and buttons to go
     * to the data modification menu.
     * @param day The calendar day being viewed
     */
	public void showOptionButtons(int day) throws Exception {
		VBox mainVbox = new VBox();
		VBox apptVbox = new VBox();
		HBox buttonBox = new HBox();
		
		selectedAppt = null;
		
		List<Appointment> apptList = model.appointmentsOn(day);
		
		Button addApptButton = new Button();
		addApptButton.setText("Add");
		addApptButton.setOnAction(event -> {
			try {
				editAppointment(day, null);
				Stage stage = (Stage) addApptButton.getScene().getWindow();
			    stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		Button delApptButton = new Button();
		delApptButton.setText("Delete");
		delApptButton.setOnAction(event -> {
			if(selectedAppt != null) {
				model.remove(selectedAppt);
				Stage stage = (Stage) delApptButton.getScene().getWindow();
			    stage.close();
			}
		});
		
		Button editApptButton = new Button();
		editApptButton.setText("Edit");
		editApptButton.setOnAction(event -> {
			try {
				editAppointment(day, selectedAppt);
				Stage stage = (Stage) editApptButton.getScene().getWindow();
			    stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		buttonBox.getChildren().add(addApptButton);
		
		if(apptList.isEmpty()) {
			Label apptText = new Label("No Appointments");
			apptVbox.getChildren().add(apptText);
		}else {
			buttonBox.getChildren().add(delApptButton);
			buttonBox.getChildren().add(editApptButton);
			for(int i = 0; i < apptList.size(); i ++) {
				Label apptText = new Label(apptList.get(i).toString());
				apptText.setOnMouseClicked(event -> {
					ObservableList<Node> list = apptVbox.getChildren();
					for(Node currentNode : list) {
					    currentNode.setStyle(null);
					}
					apptText.setStyle("-fx-border-color:red;");
					selectedAppt = findAppt(apptText.getText());
					
				});
				apptVbox.getChildren().add(apptText);
			}
		}
		
		mainVbox.getChildren().add(apptVbox);
		mainVbox.getChildren().add(buttonBox);
		Stage stage = new Stage();
		stage.setTitle("KalGUI");
        stage.setScene(new Scene(mainVbox));
        stage.show();
		
	}
	
	/**
     * This displays text fields and buttons used to edit/add events.
     * @param date The calendar day being edited
     * @param appt The appointment being edited, set to null if adding a new appointment
     */
	public void editAppointment(int date, Appointment appt) throws Exception {
		VBox mainBox = new VBox();
		VBox textBoxes = new VBox();
		HBox buttonBox = new HBox();
		
		TextField dateField = new TextField();
		TextField timeField = new TextField();
		TextField descField = new TextField();
		
		if(appt != null) {
			dateField.setText(Integer.toString(appt.getDate()));
			timeField.setText(appt.getTime().toString());
			descField.setText(appt.getText());
		}else{
			dateField.setText(Integer.toString(date));
		}
		
		textBoxes.getChildren().add(new Label("Date"));
		textBoxes.getChildren().add(dateField);
		textBoxes.getChildren().add(new Label("Time"));
		textBoxes.getChildren().add(timeField);
		textBoxes.getChildren().add(new Label("Info"));
		textBoxes.getChildren().add(descField);
		
		Button createButton = new Button();
		createButton.setText("Save");
		createButton.setOnAction(event -> {
			if(appt != null) {
				model.remove(appt);
			}
			model.add(Integer.parseInt(dateField.getText()), Time.fromString(timeField.getText()), descField.getText());
			Stage stage = (Stage) createButton.getScene().getWindow();
			stage.close();
		}
		);
		
		Button cancelButton = new Button();
		cancelButton.setText("Cancel");
		cancelButton.setOnAction(event -> {
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		});
		
		buttonBox.getChildren().add(createButton);
		buttonBox.getChildren().add(cancelButton);
		
		mainBox.getChildren().add(textBoxes);
		mainBox.getChildren().add(buttonBox);
		
		Stage stage = new Stage();
		
		stage.setScene( new Scene( mainBox ) );
        stage.setTitle( "KalGUI" );
        stage.show();
		
		
	}
	
	/**
     * Returns the appointment object of a given Appointment object toString()
     * @param search The toString() to match to an appointment object
     */
	private Appointment findAppt(String search) {
		for(int x = 0; x < model.numDays(); x ++) {
			List<Appointment> apptList = model.appointmentsOn(x + 1);
			for(int i = 0; i < apptList.size(); i ++) {
				if(apptList.get(i).toString().equals(search)) {
					return apptList.get(i);
				}
			}
		}
		return null;
	}
	
}
