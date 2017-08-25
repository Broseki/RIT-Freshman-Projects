package Players.MMERI;

import Interface.Coordinate;

import java.util.ArrayList;

/**
 * A cluster of pieces for one player in the pathbuilder game.
 * @author Brandon Goodhue
 */
public class Cluster {

    /**
     * The number of Coordinates in this cluster.
     */
    private int size;

    /**
     * An array of coordinates in this cluster.
     * Note: coords.length should equal size
     */
    private Coordinate[] coords;

    /**
     * The constructer for clusters
     * @param size The number of items in the cluster.
     * @param coords The locations of the items in this cluster.
     */
    public Cluster(int size, Coordinate[] coords) {
        this.size = size;
        this.coords = coords;
    }

    /**
     * A getter method for the size of this cluster.
     * @return The number of items in this cluster.
     */
    public int getSize() {
        return size;
    }

    /**
     * A getter method for the locations of coords in this cluster.
     * @return An array of coordinates representing the location of
     *              a players pieces.
     */
    public Coordinate[] getCoords() {
        return coords;
    }
}
