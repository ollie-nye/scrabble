package validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * 
 * @author Ollie Nye
 * @version 1.6
 */
/* 
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - Add words to graph
 * 1.2 - Add search function
 * 1.3 - Fix bug with last character being wildcard
 * 1.4 - Add main method for testing
 * 1.5 - Add percentage progress functionality
 * 1.6 - Add Javadoc
 */
public class Dawg { // Directed Acrylic Word Graph
	
	/**
	 * Path to the dictionary file, possibly set in settings?
	 */
	private String filePath = "ScrabbleDictionary.txt";
	
	/**
	 * Root vertex, has short content
	 */
	private Vertex parent = new Vertex("", false);
	
	/**
	 * Length of the file (lines) used in progress monitor
	 */
	private long fileLength;
	
	/**
	 * Count of lines iterated over in reading in the file
	 */
	private long count = 0;
	
	/**
	 * Current percentage state of the loader (0-100)
	 */
	private int state = 0;
	
	/**
	 * Lines / 100, multiplied to set state each iteration
	 */
	private long divisor = 0;
	
	/**
	 * Creates a new dictionary from the given file path
	 */
	public Dawg( ) {
		try {
			File file = new File(filePath); 			// Create a reference to the dictionary file
			Scanner wordScanner = new Scanner(file); 	// Scanner to iterate over the file
			while (wordScanner.hasNext()) { 			// Standard file reader loop
				fileLength++; 							// Increment line counter
				wordScanner.next();
			}
			wordScanner.close();						// Destroy the scanner
			wordScanner = new Scanner(file);			// Recreate the scanner for a second iteration over the file
			divisor = fileLength / 100;					// Set divisor
			while (wordScanner.hasNext()) {
				String nextWord = wordScanner.next();
				getChild(parent, nextWord);				// Add word to graph, following existing path if it exists
				incrementProgress();					// Output percentage progress
			}
			wordScanner.close();
		}
		catch (FileNotFoundException fnfex) {
			System.out.println("Error with dictionary setup - File Not Found.");
			System.exit(1);
		}
		
	}
	
	/**
	 * Method to keep track of the state of the loading section of the program
	 */
	private void incrementProgress() {
		this.count++;
		if (this.count >= ((this.state + 1) * this.divisor)) {
			this.state++;
			System.out.println(this.state + "%");
		}
	}
	
	/**
	 * Adds a word to the dictionary through a recursive function
	 * @param parent			Parent {see #Vertex vertex} vertex to add the remaining word to
	 * @param remainingWord		Remaining section of the word to add to the graph
	 */
	private void getChild(Vertex parent, String remainingWord) {
		boolean childFound = false;
		
		// If the parent contains the next character already, continue down the tree
		if (parent.getChildren().containsKey(remainingWord.substring(0, 1))) {
			getChild(parent.getChild(remainingWord.substring(0, 1)), remainingWord.substring(1));
			childFound = true;
		}

		// Parent does not contain the next character in the remainingWord
		if (!childFound) {
			Vertex newVertex;
			if (remainingWord.length() == 1) { // If this is the end of the word, then the next character created will be the last in a word
				newVertex = new Vertex(remainingWord, true);
			} else { // Create new vertex, but do not set true to isWord
				newVertex = new Vertex(remainingWord.substring(0, 1), false);
			}
			parent.addChild(newVertex);
			if (remainingWord.length() > 1) { // Final check for final word, continue down the tree if required
				getChild(newVertex, remainingWord.substring(1));
			}
		}
	}
	
	/**
	 * Main function containing a simple console for testing
	 * @param args
	 */
	public static void main(String[] args) {
		Dawg dawg = new Dawg();
		System.out.println("All words added");
		
		Scanner in = new Scanner(System.in);
		
		while (true) {
			String nextSearch = in.nextLine().trim();
			System.out.println("Words matching your string : " + dawg.search(nextSearch));
		}
	}
	
	/**
	 * Parent method to begin searching the tree for the occurence of a word
	 * @param searchString			String to search the tree with
	 * @return						Number of words matching the given string found
	 */
	public int search(String searchString) {
		return search(this.parent, searchString);
	}
	
	/**
	 * Recursive function to search the graph
	 * @param currentNode			Node found from previous iteration that could contain the next letter in this string
	 * @param searchString			String containing remaining searches, shortened by 1 each iteration
	 * @return						Number of words found at this level
	 */
	public int search(Vertex currentNode, String searchString) {
		int words = 0;
		String nextChar = getNextCharacter(searchString);
		if (nextChar.equals("")) { // End of the string
			if (currentNode.isWord()) {
				words++;
			}
		} else { // Continue depth first search
			if (nextChar.equals("*")) { // Wildcard search
				for (Entry<String, Vertex> child : currentNode.getChildren().entrySet()) {
					words += search(child.getValue(), reduceString(searchString)); // Recurse on each child node
				}
			} else {
				Vertex nextChild = currentNode.getChild(nextChar);
				if (nextChild != null) {
					words += search(nextChild, reduceString(searchString));	// Recurse on existing child node that matches the search string
				}
			}
		}
		return words;
	}
	
	/**
	 * Gets the next character from the front of a string
	 * @param string				String to shorten
	 * @return						Single character if string is long enough, else an empty string
	 */
	private String getNextCharacter(String string) {
		if (string.length() >= 1) {
			return string.substring(0, 1);
		} else {
			return "";
		}
	}
	
	/**
	 * Shortens the given string by one character, allowing it to pass to the next level of recursion
	 * @param string				String to shorten
	 * @return						String given, minus the first character if long enough, else an empty string
	 */
	private String reduceString(String string) {
		if (string.length() > 1) {
			return string.substring(1);
		} else {
			return "";
		}
	}
}
