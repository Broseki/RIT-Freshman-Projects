import cords.Cord;
import cords.CordMaker;

/**
 * A little fun test. Don't know where I first saw this
 * &quote;quote&quote;!
 * @author James Heliotis
 */
public class strandtest5 {

    /**
     * Exercise the slice method.
     */
    public static void main(  String args[]  ) {

        util.noArgs( args );

        CordMaker factory = CordMaker.strandFactory();

        String initiator = "xxx";
        Cord sin = factory.from( initiator );
        Cord xtr = factory.from( initiator );
        Cord soc = factory.from( "To be is to do" );
        Cord pla = factory.from( "To be is to do" );

        System.out.println( sin.toString() + sin + ' ' + sin );
        pla = pla.replace( 3, 5, pla.slice( 12, 14 ) );
        pla = pla.replace( 12, 14, soc.slice( 3, 5 ) );
        System.out.println( sin.toString() + sin + '5' + sin );
        sin = sin.replace( 0, 3, factory.from( "To be do be do" ) );
        Cord sin2 = sin.replace( 0, 1, factory.from( "D" ) );
        System.out.println( xtr.toString() + xtr + ' ' + xtr );
        System.out.println( soc.toString() + " -- Socrates" );
        System.out.println( pla.toString() + " -- Plato" );
        System.out.println( sin2.toString() + " -- Sinatra (" + sin + ")\n" );

    }
}
