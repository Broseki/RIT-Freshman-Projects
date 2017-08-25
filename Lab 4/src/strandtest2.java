import cords.Cord;
import cords.CordMaker;

/**
 * Do some basic slice and replace tests.
 * @author James Heliotis
 */
class strandtest2 {

    /**
     * Exercise slice, replace, and concatenate.
     */
    public static void main( String args[] ) {

        CordMaker factory = CordMaker.strandFactory();

        Cord alphabet = factory.from( "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Cord a = alphabet.slice( 0, 1 );
        Cord e = alphabet.slice( 4, 5 );
        Cord i = alphabet.slice( 8, 9 );
        Cord o = alphabet.slice( 14, 15 );
        Cord u = alphabet.slice( 20, 21 );
        Cord y = alphabet.slice( 23, 25 );

        Cord sometimesY =
            y.replace( 0, 1, factory.from( " and sometimes " ) );

        Cord vowels = a.concatenate( e ).
                           concatenate( i ).
                            concatenate( o ).
                             concatenate( u ).
                              concatenate( sometimesY );

        System.out.println( vowels );
    }
}
