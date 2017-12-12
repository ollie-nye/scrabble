package validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

/**
 * Dictionary for main game, provides lookup and regex access
 * 
 * @author Ollie Nye, Asid Khan
 * @version 1.4
 */
/* 
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - Add getWord method
 * 1.2 - Incorporate wordlist from https://github.com/dwyl/english-words/blob/master/words_alpha.txt
 * 1.3 - Improve search methods to utilise regex
 * 1.4 - Refactor ListofWords to listOfWords for readability
 */

public class Dictionary {

	/**
	 * ArrayList holding the dictionary
	 */
	private ArrayList<String> listOfWords;
	
	/**
	 * Path to dictonary file
	 */
	private String filePath = "ScrabbleDictionary.txt";
	
	/**
	 * Populates listOfWords with contents of the file specified in the method
	 */
	public Dictionary() {
		try (Scanner wordScanner = new Scanner(new File(filePath))) {
			listOfWords = new ArrayList<>();
			while (wordScanner.hasNext()) {
				listOfWords.add(wordScanner.next());
			}
		}
		catch (FileNotFoundException fnfex) {
			System.out.println("Error with dictionary setup - File Not Found.");
			System.exit(1);
		}

	}

	/**
	 * Gets a word by index
	 * @param word	Index of word to fetch
	 * @return		Word at the index given 
	 */
	public String getWord(int word) {
		return listOfWords.get(word).toString();
	}

	/**
	 * Gets the size of the dictionary 
	 * @return 		Size of the dictionary ArrayList
	 */
	public int getSize() {
		return listOfWords.size();
	}
	
	/**
	 * Tests every word in the dictionary against a given regex string 
	 * @param searchString		String to match words against
	 * @return					Number of words that match the given regex string
	 */
	public int searchList(String searchString) {
		int runningTotal = 0;
		for (String word : listOfWords) {
			if (word.matches(searchString)) {runningTotal += 1;}
		}
		return runningTotal;
	}
}
