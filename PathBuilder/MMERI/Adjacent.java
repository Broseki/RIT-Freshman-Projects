package Players.MMERI;

import Interface.Coordinate;

/**
 * Created by Bgood on 5/4/2017.
 */
public class Adjacent {

    private boolean adjacent;
    private Coordinate coordinate;

    public Adjacent (boolean adjacent, Coordinate coordinate) {
        if (adjacent == false) {
            this.adjacent = false;
            this.coordinate = null;
        }
        else {
            this.adjacent = true;
            this.coordinate = coordinate;
        }
    }


    public boolean getAdjacency() {
        return adjacent;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }


}
