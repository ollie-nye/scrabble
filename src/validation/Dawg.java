package validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * 
 * @author Ollie Nye
 * @version 1.1
 */
/* 
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - 
 */
public class Dawg { // Directed Acrylic Word Graph
	
	private String filePath = "ScrabbleDictionary.txt";
	
	private Vertex parent = new Vertex("", false);
	
	private long fileLength;
	private long count = 0;
	private int state = 0;
	private long divisor = 0;
	
	public Dawg( ) {
		try {
			File file = new File(filePath);
			Scanner wordScanner = new Scanner(file);
			while (wordScanner.hasNext()) {
				fileLength++;
				wordScanner.next();
			}
			wordScanner.close();
			wordScanner = new Scanner(file);
			divisor = fileLength / 100;
			while (wordScanner.hasNext()) {
				String nextWord = wordScanner.next();
				getChild(parent, nextWord);
				incrementProgress();
			}
			wordScanner.close();
		}
		catch (FileNotFoundException fnfex) {
			System.out.println("Error with dictionary setup - File Not Found.");
			System.exit(1);
		}
		
	}
	
	private void incrementProgress() {
		this.count++;
		if (this.count >= ((this.state + 1) * this.divisor)) {
			this.state++;
			System.out.println(this.state + "%");
		}
	}
	
	private void getChild(Vertex parent, String remainingWord) {
		boolean childFound = false;
		
		if (parent.getChildren().containsKey(remainingWord.substring(0, 1))) {
			getChild(parent.getChild(remainingWord.substring(0, 1)), remainingWord.substring(1));
			childFound = true;
		}

		if (!childFound) {
			Vertex newVertex;
			if (remainingWord.length() == 1) {
				newVertex = new Vertex(remainingWord, true);
			} else {
				newVertex = new Vertex(remainingWord.substring(0, 1), false);
			}
			parent.addChild(newVertex);
			if (remainingWord.length() > 1) {
				getChild(newVertex, remainingWord.substring(1));
			}
		}
	}
	
	public static void main(String[] args) {
		Dawg dawg = new Dawg();
		System.out.println("All words added");
		
		System.out.println(dawg.search("h*llo"));
	}
	
	/*
	public Result searchDictionary(String regex) {
		//traverse graph along directed edges. If a wildcard is encountered, add all vertices to search path
		
	} 
	*/
	
	public int search(String searchString) {
		return search(this.parent, searchString);
	}
	
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
					words += search(child.getValue(), reduceString(searchString));
				}
			} else {
				if (currentNode.isWord()) {
					words++;
				} else {
					words += search(currentNode.getChild(nextChar), reduceString(searchString));
				}
			}
		}
		return words;
		
		/*
		int wordCount = 0;
		String nextChar;
		if (searchString.length() == 0) {
			if (parent.isWord()) {
				wordCount += 1;
				System.out.println(word + " matched");
			}
		} else {
			nextChar = searchString.substring(0, 1);
			
			if (nextChar.equals("*")) {
				for (Entry<String, Vertex> node : parent.getNodes().entrySet()) {
					nextChar = node.getKey();
					String nextSearch = node.getKey() + ((searchString.length() > 1)?searchString.substring(1):"");
					wordCount += search(node.getValue(), nextSearch, word);
				}			
			} else {
				if (parent.getNodes().get(searchString.substring(0, 1)) == null) {
					if (parent.isWord()) {
						wordCount += 1;
					}
				} else {
					word += nextChar;
					wordCount += search(parent.getNodes().get(searchString.substring(0, 1)), searchString.substring(1), word);
				}
			}
		}
		word = word.substring(0, word.length() - 1);
		return wordCount;
		
		*/
	}
	
	/*
	 * Take parent, test all children for matching content
	 * If wildcard, search all children
	 * If end of line and not found, return false
	 * If end of line and found, return true
	 */
	
	private String getNextCharacter(String string) {
		return string.substring(0, 1);
	}
	
	private String reduceString(String string) {
		if (string.length() > 1) {
			return string.substring(1);
		} else {
			return "";
		}
	}
}
