package scrabble;

import java.util.ArrayList;


public class LetterScore {
private ArrayList<String> letterSets;

public LetterScore(){
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

//private void addScore(score){}

private boolean isDouble(){
	
	}
private boolean isTriple(){
	
}
private int getScoreOfLetter(char letter){
	for (String x : letterSets) {
        if (letterSets.contains(letter)) {
            return letterSets.indexOf(letter);
        }
    }
	}
}




