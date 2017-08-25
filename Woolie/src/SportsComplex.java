/**
 * This acts as a tool to coordinate threads for printing and battling.
 *
 * @author Mike Canning @ RIT CS
 */
class SportsComplex {
	
    private int currentAvailableBattleNumber;
    private int totalBattleNumber;
    private Referee ref;
    
    /**
     * This constructs a SportsComplex object. It initializes the object with a totalNumber of battles,
     * a current number of battles available, and the referee for the game.
     * 
     * @param nArenas The number of available arenas
     * @param ref The referee in charge of the battles
     */
    public SportsComplex( int nArenas, Referee ref ) {
        totalBattleNumber = nArenas;
        currentAvailableBattleNumber = nArenas;
        this.ref = ref;
    }

    /**
     * This allows battles to occur in a synchronized way, only when there are battles available.
     */
    public synchronized void enterArena() {
    	while (currentAvailableBattleNumber == 0) {
    		try {
    			wait();
    		} catch (InterruptedException e) {}
    	currentAvailableBattleNumber -= 1;
    	notifyAll();
    	}
    }
    
    /**
     * This returns an arena to the available list when a battle is done.
     */
    public synchronized void leaveArena() {
    	currentAvailableBattleNumber += 1;
    	notifyAll();
    }
    
    /**
     * This prints in a synchronized way so that text does not get jumbled.
     * 
     * @param s The string to print
     */
    public synchronized void println(String s) {
    	System.out.println(s);
    	notifyAll();
    }
    
    /**
     * Returns the referee for the battles.
     * 
     * @return The referee
     */
    public Referee getRef() {
    	return ref;
    }
    
    
}
