package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dictionary {
	
     private ArrayList<String> ListofWords;
     
     /*
      * word file was taken from https://github.com/dwyl/english-words/blob/master/words_alpha.txt
      * constructor fills arraylist with words from words.dat file.
     */
public Dictionary() throws FileNotFoundException{
    	 
    try (Scanner wordScanner = new Scanner(new File(getClass().getResource("words.dat").getFile()))){
         ListofWords = new ArrayList<>();
    	  while(wordScanner.hasNext()){
    			 ListofWords.add(wordScanner.next());
    		 }	 
    	 }
     
     }
// Value of many words are within the word list is returned
public String getWord(int word){
	return ListofWords.get(word);		
}
// The total size of arraylist is returned
public int getSize(){
	return ListofWords.size();	
}
   }
     
     


