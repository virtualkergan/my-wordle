import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A model for the game of Wordle.
 *
 * @author Kergan Sanderson
 */
public class Wordle {

    /**
     * For testing purposes only.
     * TODO remove this main method.
     */
    public static void main(String[] args) {
        Wordle model = new Wordle();
        String[] words = model.getWordList();
        System.out.println("First word in array: " + words[0]);
        System.out.println("First answer (cigar): " + words[ANSWER_INDEX]);
        System.out.println("Last word in array: " + words[words.length - 1]);
    }

    /** The number of letters in a guess. */
    public static final int LETTERS_IN_GUESS = 5;

    /** The number of guesses in a game. */
    public static final int GUESSES_IN_GAME = 6;

    /** The number of words in the word list. */
    public static final int TOTAL_NUMBER_OF_WORDS = 14855;

    /** Index of the first answer in the word list. */
    public static final int ANSWER_INDEX = 12546;

    /** The New York Times Wordle word list. */
    private String[] wordList;

    /** The current secret word. */
    private String secretWord;

    /**
     * Creates the model, creating the word list and picking a secret word.
     */
    public Wordle() {

        // read the contents of the word list into a scanner
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("data/wordle-wordlist.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find data/wordle-wordlist.csv");
        }
        input.useDelimiter(",");

        // fill the wordList array with the contents of the file
        wordList = new String[TOTAL_NUMBER_OF_WORDS];
        for (int i = 0; i < TOTAL_NUMBER_OF_WORDS; i++) {
            wordList[i] = input.next();
        }
    }

    // TODO remove this testing getter method
    public String[] getWordList() {
        return this.wordList;
    }
}
