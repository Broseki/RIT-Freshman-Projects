import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The referee runs the battles, and loads all of the players. It starts the battle threads and keeps track
 * of the winners.
 * 
 * @author Mike Canning @ RIT CS
 */
public class Referee {
	
	private ArrayList<Woolie> woolies = new ArrayList<>();
	private ArrayList<Woolie> availableWoolies = new ArrayList<>();
	private ArrayList<Battle> battleGroups = new ArrayList<>();
	SportsComplex complex;
	
	public static int seed = 451894789;
	
	/**
	 * Creates a new referee object, and loads a config file.
	 * 
	 * @param args The filename of the config file to load
	 */
	public Referee(String[] args) {
		FileReader file = null;
    	
    	try {
			file = new FileReader(args[0]);
		} catch (FileNotFoundException e) {
			System.err.println("The supplied battle file was not found!");
		}
    	
    	BufferedReader br = new BufferedReader(file);
    	
        try {
			complex = new SportsComplex(Integer.parseInt(br.readLine()), this);
		} catch (NumberFormatException e) {
			System.err.println("There was an error reading the given config file!");
		} catch (IOException e) {
			System.err.println("There was an error reading the given config file!");
		}
        
        String line;
		try {
			while((line = br.readLine()) != null) {
				Random rand = new Random();
				rand.setSeed(seed);
				woolies.add(new Woolie(rand, line.split(",")));
			}
		} catch (IOException e) {
			System.err.println("There was an error reading the given config file!");
		}
		doTournament();
	}
	
	/**
	 * Runs a tournament. Keeps running until only one woolie remains
	 */
	public void doTournament() {
		int currentRound = 1;
		availableWoolies.clear();
		availableWoolies.addAll(woolies);
		while(availableWoolies.size() > 1) {
			System.out.println("Round " + currentRound);
			System.out.println("========");
			doOneRound();
			currentRound ++;
		}
		System.out.println(availableWoolies.get(0) + " is the champion!");
	}
	
	/**
	 * Actually runs a single battle.
	 */
	public void doOneRound() {
		ArrayList<Thread> battleThreads = new ArrayList<>();
		battleGroups.clear();
		while(availableWoolies.size() > 1) {
			Woolie woolie1 = availableWoolies.get(0);
			availableWoolies.remove(0);
			Woolie woolie2 = availableWoolies.get(0);
			availableWoolies.remove(0);
			Battle battle = new Battle(woolie1, woolie2, complex);
			battleGroups.add(battle);
			System.out.println(battleGroups.size());
		}
		
		for(int i=0; i < battleGroups.size(); i ++) {
			
			Thread thread = new Thread(battleGroups.get(i));
			battleThreads.add(thread);
		}
		for(int i=0; i < battleThreads.size(); i ++) {
			battleThreads.get(i).start();
		}
		for(int i=0; i < battleThreads.size(); i ++) {
			try {
				battleThreads.get(i).join();
			} catch (InterruptedException e) {
				System.err.println("There was an interruption in the main thread!");
			}
		}
	}
	
	/**
	 * Loads a woolie back into the woolie list for the next round of battles
	 * 
	 * @param woolie The woolie to return to the list of woolies
	 */
	public void returnWoolie(Woolie woolie) {
		availableWoolies.add(woolie);
	}
	
	/**
	 * The main method accepts the initial command input and sends it to the Referee constructor.
	 * 
	 * @param args The command arguments containing the filename of the configuration file
	 */
	public static void main(String[] args) {
    	new Referee(args);
	}
	
	
}
