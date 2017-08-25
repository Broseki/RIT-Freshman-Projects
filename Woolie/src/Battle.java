/**
 * The battles threads are built using this. The battle occurs here, along with all interactions with the
 * SportsComplex and Referee occur here.
 *
 * @author Mike Canning @ RIT CS
 */
class Battle extends Thread {
    private int instanceNum;
    
    private static int battleId = 1;
    
    Woolie w1;
    Woolie w2;

    private SportsComplex complex;
    
    /**
     * Creates a new battle object. Woolie 1 and Woolie 2 are initialized along with the SportsComplex.
     * 
     * @param w1 The first woolie in battle
     * @param w2 The second woolie in battle
     * @param complex The SportsComplex where the battle is occurring
     */
    public Battle( Woolie w1, Woolie w2, SportsComplex complex) {
        this.instanceNum = battleId;
        battleId += 1;
        this.complex = complex;
        this.w1 = w1;
        this.w2 = w2;
     }

    /**
     * Returns the battle number in format Battle-n
     * 
     * @return The battle number
     */
    @Override
    public String toString() {
        return "Battle-" + this.instanceNum;
    }
    
    /**
     * Returns the first woolie
     * 
     * @return The first woolie
     */
    public Woolie getFighter1() {
    	return w1;
    }
    
    /**
     * Returns the second woolie
     * 
     * @return The second woolie
     */
    public Woolie getFighter2() {
    	return w2;
    }
    
    /**
     * Returns the winner of the battle if any
     * 
     * @return The battle winner if there is one; null otherwise.
     */
    public Woolie getWinner() {
    	if(w1.isOK() && w2.isOK()) {
    		return null;
    	}else if(w1.isOK() && !w2.isOK()) {
    		return w1;
    	}else if(!w1.isOK() && w2.isOK()) {
    		return w2;
    	}else {
    		return null;
    	}
    }

    /**
     * This starts the battle. The waiting and battle occurs here
     */
    public void run() {
        complex.println(this.toString() + "[" + w1.toString() + "|" + w2.toString() + "] is waiting for and arena.");
        complex.enterArena();
        complex.println(this.toString() + "[" + w1.toString() + "|" + w2.toString() + "] is in.");
        	int time = 0;
        	while(w1.isOK() && w2.isOK()) {
        		if(time % w1.getHitTime() == 0) {
        			w2.takeDamage(w1.getAttackAmount());
        			String toPrint = "";
        			toPrint = toPrint + Integer.toString(time) + ": " + w1.toString() + "->" + w2.toString() + "\n";
        			toPrint = toPrint + w1.info() + "\n";
        			toPrint = toPrint + w2.info() + "\n";
        			complex.println(toPrint);
        		}
        		
        		if(time % w2.getHitTime() == 0) {
        			w1.takeDamage(w2.getAttackAmount());
        			String toPrint = "";
        			toPrint = toPrint + Integer.toString(time) + ": " + w2.toString() + "->" + w1.toString() + "\n";
        			toPrint = toPrint + w1.info() + "\n";
        			toPrint = toPrint + w2.info() + "\n";
        			complex.println(toPrint);
        		}
        		time ++;
        	}
        	if(!w1.isOK() && !w2.isOK()) {
        		complex.println("There was a tie; retrying battle bettween " + w1.toString() + " and " + w2.toString() + "!");
        		w1.reset();
        		w2.reset();
        	}
        complex.println(this.getWinner().toString() + " HAS WON!");
        this.getWinner().reset();
        complex.getRef().returnWoolie(this.getWinner());
        complex.leaveArena();
    }
}
