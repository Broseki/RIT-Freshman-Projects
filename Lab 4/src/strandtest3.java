import cords.CordMaker;
import cords.Cord;

/**
 * Try some replace operations, and make sure that the Cord
 * classes are doing bounds checking.
 * @author James Heliotis
 */
class strandtest3 {

    /**
     * Test concatenation, getSize, and get.
     * Make sure that illegal indices throw IndexOutOfBounds.
     */
    public static void main( String args[] ) {

        util.noArgs( args );

        CordMaker factory = CordMaker.strandFactory();

        Cord s = factory.from( "Jack " );
        Cord t = factory.from( "& Jill" );
        System.out.println( "5:  " + s.getSize() );
        System.out.println( "6:  " + t.getSize() );
        System.out.println( "s.get( 1 ) = " + s.get( 1 ) );
        System.out.println( "t.get( 0 ) = " + t.get( 0 ) );
        System.out.println( "t.get( 5 ) = " + t.get( 5 ) );
        System.out.println( "s = <<" + s + ">>" );
        System.out.println( "t = <<" + t + ">>" );
        Cord u = s.concatenate( t );
        System.out.println( "11:  " + u.getSize() );
        System.out.println( "u.get( 0 ) = " + u.get( 0 ) );
        System.out.println( "u.get( 3 ) = " + u.get( 3 ) );
        System.out.println( "u.get( 5 ) = " + u.get( 5 ) );
        System.out.println( "u.get( 10 ) = " + u.get( 10 ) );

        try {
            System.out.println( "u.get( -1 )?" );
            System.out.println( u.get( -1 ) );
            System.out.println( "ERROR -- shouldn't have worked" );
        }
        catch( IndexOutOfBoundsException b ) {
            System.out.println( "Good; we got an exception:" );
            System.out.println( "    " + b );
        }

        try {
            System.out.println( "u.get( 11 )?" );
            System.out.println( u.get( 11 ) );
            System.out.println( "ERROR -- shouldn't have worked" );
        }
        catch( IndexOutOfBoundsException b ) {
            System.out.println( "Good; we got an exception:" );
            System.out.println( "    " + b );
        }

        Cord bye = factory.from( "Good-bye" );
        Cord a = factory.from( "hello, " );
        System.out.println( "a = " + a );
        Cord b = factory.from( "woild?" );
        System.out.println( "b = " + b );
        Cord c = a.concatenate( b );
        System.out.println( "c = " + c );
        System.out.println( "a.slice( 1, 5 ) = " + a.slice( 1, 5 ) );
        System.out.println( "c.slice( 4, 10 ) = " + c.slice( 4, 10 ) );
        Cord d = c.replace( 9, 10, factory.from( "r" ) );
        System.out.println( "d = " + d );
        Cord e = d.replace( 0, 1, factory.from( "H" ) );
        System.out.println( "e = " + e );
        Cord f = e.replace( 12, 13, factory.from( "!" ) );
        System.out.println( "f = " + f );
        Cord g = f.replace( 0, 5, bye );
        System.out.println( "g = " + g );

    }
}
