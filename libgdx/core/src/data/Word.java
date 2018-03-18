package data;

/**
 * Subclass of Tuple that contains a word and its score.
 * @author Thomas Geraghty
 * @version 1.0
 */
public class Word extends Tuple<String, Integer>  {

    public Word(String word, int score) {
        super(word, score);
    }

    public int getScore() {
        return super.getRight();
    }

    public String getWord() {
        return super.getLeft();
    }
}
