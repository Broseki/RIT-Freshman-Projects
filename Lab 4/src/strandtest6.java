import cords.CordMaker;
import cords.Cord;

/**
 * Try a few basic operations.
 * @author James Heliotis
 * @author Robert Bogart
 */
class strandtest6 {

    /**
     * Test concatenation, replace, and remove.
     */
    public static void main( String args[] ) {

        util.noArgs( args );

        CordMaker factory = CordMaker.strandFactory();

        String notext = "";

        Cord now = factory.from( "Now is the time " );
        Cord fora = factory.from( "for all good men " );
        Cord toc =
                factory.from( "to come to the aid of their country." );

        Cord nowforto = now.concatenate( fora ).concatenate( toc );
        nowforto.concatenate( factory.from( "\n" ) ).toOutput( System.out );
        Cord nownot = now.remove( 0, now.getSize() );
        Cord tonot = toc.remove( 0, toc.getSize() );
        Cord notnot = nownot.concatenate( tonot );
        System.out.print( "" + now );
        System.out.print( "" + notnot );
        System.out.println( "" + fora + notnot + toc );
        System.out.println( "" + tonot + notnot + nownot );
        System.out.println( "" + now + fora + toc );

        Cord x = now.replace( 0, now.getSize(), factory.from( notext ) );
        Cord y = factory.from( notext );

        now
                .concatenate( x )
                .concatenate( fora )
                .concatenate( y )
                .concatenate( toc )
                .concatenate( factory.from( "\n" ) )
            .toOutput( System.out );

    }
}

