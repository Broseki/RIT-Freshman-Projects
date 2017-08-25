import java.util.Random;

/**
 * A woolie is what actually battles. The hit points and battle related events occur and are stored here.
 *
 * @author Mike Canning @ RIT CS
 */
public class Woolie {
	
	public static String INDENT = "   ";
	
	private String name;
	private int minAtk;
	private int maxAtk;
	private int hitTime;
	private int initialHitPoints;
	private int currentHitPoints;
	private Random rand;
	
	/**
	 * Creates a new woolie object. This formats a call to the other constructor and calls it.
	 * 
	 * @param rand The random number generator use for the battle
	 * @param A list of variables in the order String name, int minAtk, int maxAtk, int hitTime, int initialHitPoints
	 */
	public Woolie (Random rand, String[] params) {
		this(rand, params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), Integer.parseInt(params[4]));
	}
	
	/**
	 * This actually creates all woolie objects.
	 * 
	 * @param rand The random number generator
	 * @param name The woolie name
	 * @param minAtk The minimum attack amount
	 * @param maxAtk The max attack amount
	 * @param hitTime The time between each hit for this woolie
	 * @param initialHitPoints The initial health of the woolie
	 */
	public Woolie (Random rand, String name, int minAtk, int maxAtk, int hitTime, int initialHitPoints) {
		this.rand = rand;
		this.name = name;
		this.minAtk = minAtk;
		this.maxAtk = maxAtk;
		this.hitTime = hitTime;
		this.initialHitPoints = initialHitPoints;
		this.currentHitPoints = initialHitPoints;
	}
	
	/**
	 * Returns the amount to attack with
	 * 
	 * @return The amount to hit with
	 */
	public int getAttackAmount() {
		return rand.nextInt(maxAtk - minAtk + 1) + minAtk;
	}
	/**
	 * Returns the name of the woolie
	 * 
	 * @returns The name of the woolie
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Removes an amount from the currentHitPoints variable
	 * 
	 * @param damage The amount of damage done
	 */
	public void takeDamage(int damage) {
		currentHitPoints -= damage;
	}
	
	/**
	 * Returns true if the woolie is alive, false otherwise.
	 * 
	 * @return True if the woolie is alive, false otherwise.
	 */
	public boolean isOK() {
		if (currentHitPoints <= 0) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * Returns the cooldown time between each hit this woolie makes
	 * 
	 * @return The time between hits.
	 */
	public int getHitTime() {
		return hitTime;
	}
	
	/**
	 * Resets the woolie to full health
	 */
	public void reset() {
		currentHitPoints = initialHitPoints;
	}
	
	/**
	 * Returns a nicely formatted string showing the woolie's status
	 * 
	 * @param A string showing the woolie's status
	 */
	public String info() {
		return INDENT + this.toString() + "[  " + minAtk + "-" + maxAtk + " (" + hitTime + ") *" + currentHitPoints + "/" + initialHitPoints + "*  ]";
	}
	
}
