package calendar.view_controller.text;

import calendar.model.Appointment;
import calendar.model.Calendar;
import edu.rit.cs.ConsoleApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by jeh on 3/7/17.
 */
public class KalPTUI extends ConsoleApplication implements Observer {

    private Calendar model;

    private boolean busted = false;

    @Override
    public void init() {
        try {
            List< String > cmdLineArgs = this.getArguments();
            if ( cmdLineArgs.isEmpty() ) {
                this.model = new Calendar( 28 );
            }
            else {
                this.model = Calendar.fromFile( cmdLineArgs.get( 0 ) );
            }
            this.model.addObserver( this );
        }
        catch( IOException e ) {
            System.err.println( e.getMessage() );
            this.busted = true;
        }
    }

    @Override
    public void go( Scanner consoleIn, PrintWriter consoleOut ) {
        if ( !this.busted ) {
            consoleOut.println( "Hi, this is Kal!" );
            Controller commandProcessor = new Controller( model, consoleIn,
                                                              consoleOut );
            commandProcessor.mainLoop();
        }
        consoleOut.println( "Calendar shutting down." );
    }

    @Override
    public void stop() {

    }

    @Override
    public void update( Observable o, Object arg ) {
        Calendar model = (Calendar)o;
        int date = (Integer)arg;
        List< Appointment > appts = model.appointmentsOn( date );
        appts.forEach( System.out::println );
    }

    public static void main( String[] args ) {
        ConsoleApplication.launch( KalPTUI.class, args );
    }

}
