/**
 * An enum for the "locations" of the four quadrants of a block in an image
 * to be compressed to a DeQuattorEnum QTree. Here are some sample uses.
 * <pre>
 *     node.getChild( Quadrant.UR );
 *
 *     Coordinate upperLeftCorner = ...;
 *     int ulRow = upperLeftCorner.getRow();
 *     int ulCol = upperLeftCorner.getCol();
 *     Coordinate lowerLeftCorner =
 *                   new Coordinate(
 *                           ulRow + Quadrant.LL.rowDelta( size / 2 ),
 *                           ulCol + Quadrant.LL.colDelta( size / 2 )
 *                   );
 *
 *     for ( Quadrant quadrant: Quadrant.values() ) { . . . }
 * </pre>
 *
 * @author James Heliotis
 */
public enum Quadrant {
    /**
     * quadrant II
     */
    UL( 0, 0 ),
    /**
     * quadrant I
     */
    UR( 0, 1 ),
    /**
     * quadrant III
     */
    LL( 1, 0 ),
    /**
     * quadrant IV
     */
    LR( 1, 1 );

    private final int r_off;
    private final int c_off;

    Quadrant( int r_off, int c_off ) {
        this.r_off = r_off;
        this.c_off = c_off;
    }

    public int rowDelta( int dimension ) {
        return this.r_off * dimension;
    }

    public int colDelta( int dimension ) {
        return this.c_off * dimension;
    }
}
