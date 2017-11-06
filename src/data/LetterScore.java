package scrabble;

import java.util.ArrayList;


public class LetterScore {
private ArrayList<String> letterSets;
private int score;

public LetterScore(){
	score = 0;
 letterSets = new ArrayList<String>();
	
	 letterSets.add(0,"*");
     letterSets.add(1, "aeioulnrst");
     letterSets.add(2, "dg");
     letterSets.add(3, "bcmp");
     letterSets.add(4, "fhvwy");
     letterSets.add(5, "k");
     letterSets.add(8, "jx");
     letterSets.add(10, "qz");
     
 }

private void addScore(int score){
	this.score = score+score;
}

private int isDouble(){
	return (score*2);
	}
private int isTriple(){
	return (score*3);
}
private int getScoreOfLetter(char letter){
	for (String x : letterSets) {
        if (letterSets.contains(letter)) {
            return letterSets.indexOf(letter);
        }
    }
	
	throw new IllegalArgumentException("'" + letter + "' is invalid for scrabble.");
	}
}




