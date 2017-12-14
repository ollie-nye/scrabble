package validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import data.Result;

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
	
	public Dawg( ) {
		try (Scanner wordScanner = new Scanner(new File(filePath))) {
			while (wordScanner.hasNext()) {
				String nextWord = wordScanner.next();
				getChild(parent, nextWord);
				System.out.println(nextWord + " added");
			}
		}
		catch (FileNotFoundException fnfex) {
			System.out.println("Error with dictionary setup - File Not Found.");
			System.exit(1);
		}
		
	}
	
	private void getChild(Vertex parent, String remainingWord) {
		boolean childFound = false;
		
		if (parent.getNodes().containsKey(remainingWord.substring(0, 1))) {
			getChild(parent.getNodes().get(remainingWord.substring(0, 1)), remainingWord.substring(1));
			childFound = true;
		}

		if (!childFound) {
			Vertex newVertex;
			if (remainingWord.length() == 1) {
				newVertex = new Vertex(remainingWord, true);
			} else {
				newVertex = new Vertex(remainingWord.substring(0, 1), false);
			}
			parent.addNode(newVertex);
			if (remainingWord.length() > 1) {
				getChild(newVertex, remainingWord.substring(1));
			}
		}
	}
	
	
	
	/*
	 * Follow path along vertices to create a word. End of lines become isWord->true
	 * For each letter in a word, call function recursively.
	 * Function given a vertex and string to search for. Returns void to caller, assumed word was added.
	 * 
	 * 
	 * 
	 */
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Dawg dawg = new Dawg();
		System.out.println("All words added");
		
		System.out.println(dawg.search("intelligent"));
	}
	
	public int search(String searchString) {
		return search(this.parent, searchString, "");
	}
	
	public int search(Vertex parent, String searchString, String word) {
		int wordCount = 0;
		String nextChar;
		if (searchString.length() == 0) {
			if (parent.isWord()) {
				wordCount += 1;
				System.out.println(word + " found");
			}
		} else {
			nextChar = searchString.substring(0, 1);
			if (nextChar.equals("*")) {
				for (Entry<String, Vertex> node : parent.getNodes().entrySet()) {
					nextChar = node.getKey();
					word += nextChar;
					String nextSearch = (searchString.length() > 1)?searchString.substring(1):"";
					int words = search(node.getValue(), nextSearch, word);
					word = word.substring(0, word.length() - 1);
					wordCount += words;
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
	}
}
