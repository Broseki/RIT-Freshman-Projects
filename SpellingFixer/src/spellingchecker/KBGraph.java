package spellingchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a graph with the keys adjacent to a given character.
 *
 * @author Mike Canning @ RIT CS
 */
public class KBGraph {
	
	private HashMap<Character, HashSet<Character>> charList = new HashMap<Character, HashSet<Character>>();
	
	/**
	 * This takes in a file name and generates a graph of characters given that file's contents
	 *
	 * @param fileName The name of the file containing the graph data
	 * @return A KBGraph based off of the given data
	 */
	public static KBGraph undirectedFromFile(String fileName) {
		String line;
		KBGraph graph = new KBGraph();
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			
			
			while((line = reader.readLine()) != null) {
				String[] line_split = line.split(" ");
				Character firstChar = line_split[0].charAt(0);
				Character secondChar = line_split[1].charAt(0);
				
				graph.addNeighbor(firstChar, secondChar);
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("The specified graph file does not exist!");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return graph;
	}
	
	/**
	 * Adds a neighboring key to an existing key on the graph
	 *
	 * @param c The key to add the neighbor to
	 * @param neighbor The neighbor being added to the key
	 */
	public void addNeighbor(Character c, Character neighbor) {
		if (hasNode(c)) {
			charList.get(c).add(neighbor);
		}else {
			makeNode(c);
			addNeighbor(c, neighbor);
		}
	}
	
	/**
	 * Returns the neighbors of a given key as a set
	 *
	 * @param c The key to fetch the neighbors for
	 * @return The neighbors of a given key as a set
	 */
	public Set<Character> getNeighbors(Character c) {
		return (Set<Character>) charList.get(c);
	}
	
	/**
	 * Checks to see if a given key exists in the graph
	 *
	 * @param c The key to check for in the graph
	 * @return True if the key exists, false otherwise
	 */
	public boolean hasNode(Character c) {
		if (charList.containsKey(c)) {
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * Creates a new node for a key in the graph
	 *
	 * @param c The key to create a node for
	 */
	public void makeNode(Character c) {
		charList.put(c, new HashSet<Character>());
	}

}
