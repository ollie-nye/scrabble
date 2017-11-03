package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dictionary {
	
     private ArrayList<String> ListofWords;
     
     /*
      * Word file was taken from https://github.com/dwyl/english-words/blob/master/words_alpha.txt
      * and modified by Lorenzo to fill the project requirements.  
      * constructor fills arraylist with words from the text file provided.
      * @author  Asid Khan
      * @version  1.0.1
     */
public Dictionary() throws FileNotFoundException{
    	 
    try (Scanner wordScanner = new Scanner(new File(getClass().getResource("ScrabbleDicionary.txt").getFile()))){
         ListofWords = new ArrayList<>();
    	 while(wordScanner.hasNext()){
    	   ListofWords.add(wordScanner.next());
    	  }	 
    	}
     
}
// Value of many words are within the word list is returned
public String getWord(int word){
	return ListofWords.get(word).toString();		
}
// The total size of arraylist is returned
public int getSize(){
	return ListofWords.size();	
}
   }


