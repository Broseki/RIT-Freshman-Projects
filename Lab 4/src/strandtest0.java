import cords.Cord;
import cords.CordMaker;

/**
 * Try a few basic concatenation operations.
 * @author James Heliotis
 */
public class strandtest0 {
    public static void main( String args[] ) {

    	CordMaker factory = CordMaker.strandFactory();

        Cord I = factory.from( "I" );
        Cord can = factory.from( " can" );
        Cord save = factory.from( " save" );
        Cord the_world = factory.from( " the world" );

        Cord Ican = I.concatenate( can );
        Cord savetheworld = save.concatenate( the_world );
        Cord s = factory.from( "oops" );
        s = Ican.concatenate( savetheworld );

        System.out.println( s );
    }
}
