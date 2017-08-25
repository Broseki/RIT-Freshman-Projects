/**
 * DeQuattorUnum uncompressor. This program takes a DQU-compressed file,
 * uncompresses it, and then displays the image using the provided GrayPicViewer.
 *
 * @author Sean Strout, James Heliotis
 */
public class DQUUncompress {

    /**
     * The main routine.
     *
     * @param args an array with a single string holding the file name
     */
    public static void main( String[] args ) {
        if ( args.length != 1 ) {
            System.err.println( "Usage: DQUUncompress filename" );
            return;
        }

        try {
            // Initialize with the compressed image file
            QTree tree = QTree.compressedFromFile( args[ 0 ] );

            // uncompress the tree
            tree.uncompress();

            // print the tree in preorder
            System.out.println( tree );

            // create a separate viewer and pass it the raw image data
            GrayPicViewer view =
                    new GrayPicViewer( tree.getRawImage(), tree.getSideDim() );

            // finally display the image
            view.display( args[ 0 ] );
        }
        catch( Exception e ) {
            System.err.println( e.getMessage() );
        }
    }
}
