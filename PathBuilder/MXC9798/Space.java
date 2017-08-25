package Players.MXC9798;

/**
 * This represents the spaces on the internal board in my bot player.
 *
 * @author Mike Canning @ RIT CS
 */

public class Space {
	private int space_type; // 0 = None | 1 = p1 | 2 = p2 | 3 = connector 1 | 4 = connector 2
	
	/**
     * Sets up a space with value 0 if no value is provided.
     */
	public Space() {
		this.space_type = 0;
	}
	
	/**
     * Initializes a space with a given value.
     * @param place The value of the space on creation
     */
	public Space(int place) {
		this.space_type = place;
	}
	
	/**
     * Returns the value of the space
     * @return The value of the space
     */
	public int get_space_type() {
		return space_type;
	}
	
	/**
     * Changes the value of the already created space object
     * @param place The new value of the space
     */
	public void set_space_type(int place) {
		this.space_type = place;
	}
}
