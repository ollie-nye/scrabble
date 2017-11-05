package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dictionary {

	private ArrayList<String> ListofWords;

	/*
	 * Word file was taken from
	 * https://github.com/dwyl/english-words/blob/master/words_alpha.txt and
	 * modified by Lorenzo to fill the project requirements. constructor fills
	 * arraylist with words from the text file provided.
	 * @author Asid Khan, Ollie Nye
	 * @version 1.2
	 */
	public Dictionary() {

		try (Scanner wordScanner = new Scanner(new File(getClass().getResource("ScrabbleDictionary.txt").getFile()))) {
			ListofWords = new ArrayList<>();
			while (wordScanner.hasNext()) {
				ListofWords.add(wordScanner.next());
			}
		}
		catch (FileNotFoundException fnfex) {
			System.out.println("Error with dictionary setup. Please remedy this issue before continuing.");
			System.exit(1);
		}

	}

	// Value of many words are within the word list is returned
	public String getWord(int word) {
		return ListofWords.get(word).toString();
	}

	// The total size of arraylist is returned
	public int getSize() {
		return ListofWords.size();
	}
	
	public int searchList(String searchString) {
		//Searches list for a matching regex, adds and returns running total.
		int runningTotal = 0;
		for (String word : ListofWords) {
			if (word.matches(searchString)) {runningTotal += 1;}
		}
		return runningTotal;
	}
}
