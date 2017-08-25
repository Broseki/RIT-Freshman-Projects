package spellingchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import edu.rit.cs.Configuration;
import edu.rit.cs.WordMatcher;

/**
 * This class is the configuration for the SpellCheck backtracking. This contains methods
 * for determining if a string is valid and if it is a goal. It also contains methods
 * for returning successors to the current configuration.
 *
 * @author Mike Canning @ RIT CS
 */
public class WordConfig implements Configuration {
	
	private ArrayList<Character> wordArray = new ArrayList<>();
	private KBGraph graph;
	private ArrayList<Configuration> successors = new ArrayList<Configuration>();
	private static ArrayList<Configuration> allSuccessors = new ArrayList<Configuration>();
	private WordMatcher wordMatcher;
	private int currentIndex;
	private ArrayList<Integer> changed = new ArrayList<>();
	
	/**
     * This takes in a configuration file and constructs a new configuration
     * based on that.
     *
     * @param wordConfig The base configuration to build off of
     */
	public WordConfig(WordConfig wordConfig) {
		wordArray = wordConfig.wordArray;
		graph = wordConfig.graph;
		wordMatcher = wordConfig.wordMatcher;
		changed = (ArrayList<Integer>) wordConfig.changed.clone();
	}

	/**
     * This constructs a configuration given a graph, and a word.
     *
     * @param kbGraph The graph of all keys on the keyboard and their adjacent keys
     * @param word The word to set into the configuration
     */
	public WordConfig(KBGraph kbGraph, String word) {
		
		for(int i = 0; i < word.length(); i ++) {
			wordArray.add(word.charAt(i));
		}
		
		this.graph = kbGraph;
		
		try {
			wordMatcher = new WordMatcher("data/words.txt");
		} catch (IOException e) {
			System.err.println("The words file was not found at data/words.txt");
		}
		
		currentIndex = 0;
		
	}
	

	/**
     * Generates all possible successors that have not already been checked and
     * returns a list of them.
     *
     * @return A list of successor configurations
     */
	@Override
	public Collection<Configuration> getSuccessors() {
		for(int x = 0; x < this.toString().length(); x ++) {
			if(!this.changed.contains(x)) {
				for(int i = 0; i < graph.getNeighbors(wordArray.get(x)).size(); i ++) {
						Character changeTo = (Character)graph.getNeighbors(wordArray.get(x)).toArray()[i];
						ArrayList<Character> newWordArray = (ArrayList<Character>) wordArray.clone();
						newWordArray.set(x, changeTo);
						WordConfig wc = new WordConfig(this);
						wc.wordArray = newWordArray;
						if(!alreadyExists(wc.toString())){
							allSuccessors.add(wc);
							wc.currentIndex = x;
							wc.changed.add(wc.currentIndex);
							successors.add(wc);
						}
				}
			}
		}
		return successors;
	}
	
	/**
     * Checks to see if a configuration is valid based on word prefixes, and returns true if
     * it is, and false if it is not.
     *
     * @return True if the configuration is valid, false otherwise.
     */
	@Override
	public boolean isValid() {
		if(wordMatcher.prefixExists(this.toString().substring(0, currentIndex + 1))) {
			return true;
		}else {
			return false;
		}
	}

	/**
     * Checks to see if a current configuration is in the dictionary, and returns true
     * if it is, false if it is not.
     *
     * @return True is the current configuration is goal, false if it is not
     */
	@Override
	public boolean isGoal() {
		if(wordMatcher.wordExists(this.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * Returns the string in the current configuration
     *
     * @return The string in this configuration
     */
	public String toString() {
		String returningString = "";
		for(int i = 0; i < wordArray.size(); i ++) {
			returningString = returningString + wordArray.get(i).toString();
		}
		return returningString;
	}
	
	/**
     * Returns the string is a given Character ArrayList
     *
     * @param array An ArrayList of Characters.
     * @return A string representation of the characters in the given ArrayList
     */
	public String arrayToString(ArrayList<Character> array) {
		String returningString = "";
		for(int i = 0; i < array.size(); i ++) {
			returningString = returningString + array.get(i).toString();
		}
		return returningString;
	}
	
	/**
     *	Checks to see if a given string has already been checked.
     *
     * @param str The string being checked
     * @return True if the string was already checked, false otherwise.
     * 
     */
	private boolean alreadyExists(String str) {
		for(int i = 0; i < allSuccessors.size(); i ++) {
			if(str.equals(allSuccessors.get(i).toString())) {
				return true;
				}
			}
		return false;
	}

}
