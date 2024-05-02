import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * A model for the game of Wordle.
 *
 * @author Kergan Sanderson
 */
public class Wordle {

    /** The number of letters in a guess. */
    public static final int LETTERS_IN_WORD = 5;

    /** The number of guesses in a game. */
    public static final int GUESSES_IN_GAME = 6;

    /** The number of words in the word list. */
    public static final int TOTAL_NUMBER_OF_WORDS = 14855;

    /** Index of the first of the answers in the word list. */
    public static final int ANSWER_INDEX = 12546;

    /** The English alphabet. */
    public static final char[] THE_ALPHABET = {'a', 'b', 'c', 'd', 'e', 'f',
                                               'g', 'h', 'i', 'j', 'k', 'l',
                                               'm', 'n', 'o', 'p', 'q', 'r',
                                               's', 't', 'u', 'v', 'w', 'x',
                                               'y', 'z'};

    /** The New York Times Wordle word list. */
    private String[] wordList;

    /** The current secret word. */
    private String secretWord;

    /** Whether or not the player has guessed the secret word yet. */
    private boolean hasGuessedSecretWord;

    /** The number of each letter in the secret word. */
    private int[] lettersInSecretWord;

    /** The number of guesses the player has made. */
    private int numberOfGuesses;

    /**
     * Creates the model, storing the word list and picking a secret word.
     */
    public Wordle() {

        // read the contents of the word list into a scanner
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream(
                    "data/wordle-wordlist.csv"));
        } catch (FileNotFoundException e) {
            System.out.println(
                    "Unable to find data/wordle-wordlist.csv");
        }
        input.useDelimiter(",");

        // fill the wordList array with the contents of the file
        wordList = new String[TOTAL_NUMBER_OF_WORDS];
        for (int i = 0; i < TOTAL_NUMBER_OF_WORDS; i++) {
            wordList[i] = input.next();
        }

        // select a random secret word
        Random rand = new Random();
        secretWord = wordList[
                rand.nextInt(ANSWER_INDEX, TOTAL_NUMBER_OF_WORDS)];
        
        // record the number of each letter in the secret word
        lettersInSecretWord = new int[THE_ALPHABET.length];
        updateLettersInSecretWord();

        // set number of guesses equal to 0
        numberOfGuesses = 0;

        hasGuessedSecretWord = false;
    }

    /**
     * Getter method for the secret word.
     * 
     * @return the secret word
     */
    public String getSecretWord() {
        return secretWord;
    }

    /**
     * Determine if a word is a valid guess.
     * 
     * @param word the guessed word
     * @return true if the word is a valid guess
     */
    public boolean isValidGuess(String word) {
        if (word.length() != LETTERS_IN_WORD) {
            return false;
        }
        for (String s : wordList) {
            if (s.equals(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the game is over. The game is over if either the player is 
     * out of guesses or if the current guess is the secret word.
     * 
     * @return true if the player is out of guesses or the current guess is the 
     *         secret word
     */
    public boolean isOutOfTurns() {
        return numberOfGuesses == GUESSES_IN_GAME;
    }

    /**
     * Returns whether the player has already guessed the secret word or not.
     * 
     * @return true if the player has already guessed the secret word
     */
    public boolean getHasGuessedSecretWord() {
        return hasGuessedSecretWord;
    }

    /**
     * Update the record of letters in the secret word.
     */
    public void updateLettersInSecretWord() {
        for (int i = 0; i < LETTERS_IN_WORD; i++) {
            for (int j = 0; j < THE_ALPHABET.length; j++) {
                if (secretWord.charAt(i) == THE_ALPHABET[j]) {
                    lettersInSecretWord[j]++;
                    break;
                }
            }
        }
    }

    /**
     * Assess and update the colors of the letters of the current guess.
     * If you are unfamiliar with Wordle, the colors give you clues as to how 
     * your guessed letters appear in the secret word.
     * G is green, Y is yellow, and R is gray.
     * 
     * @return the colors of the current guess
     */
    public GuessResult getGuessResults(String guess) {
        // used to store the index of a letter in the alphabet
        int lIndex = 0;

        // used to store the frequency of each guess letter in the secret word
        int[] matchedLetters = new int[THE_ALPHABET.length];

        // the output of the method
        char[] guessColors = {'R', 'R', 'R', 'R', 'R'};

        // get green letters first
        for (int i = 0; i < LETTERS_IN_WORD; i++) {

            if (guess.charAt(i) == secretWord.charAt(i)) {
                guessColors[i] = 'G';
                lIndex = indexOfLetter(guess.charAt(i));
                matchedLetters[lIndex]++;
            }
        }

        // get yellow letters next
        for (int i = 0; i < LETTERS_IN_WORD; i++) {

            // skip already green letters to avoid double counting
            if (guessColors[i] == 'G') {
                continue;
            }

            lIndex = indexOfLetter(guess.charAt(i));

            // don't record 'Y' if there is no more of that letter
            if (matchedLetters[lIndex] < lettersInSecretWord[lIndex]) {
                guessColors[i] = 'Y';
                matchedLetters[lIndex]++;
            }
        }

        // update the number of guesses that have been made in the game
        numberOfGuesses++;

        // update whether the user has guessed the secret word
        if (guess.equals(secretWord)) {
            hasGuessedSecretWord = true;
        }

        return new GuessResult(guess, guessColors, numberOfGuesses - 1);
    }

    /**
     * Returns the index of a letter in the alphabet.
     * Note that this method is static since it is reused in WordleGUI.
     * 
     * @param letter the letter who's index will be returned
     * @return the index 0 - 25 of a letter in the alphabet
     */
    public static int indexOfLetter(char letter) {
        for (int i = 0; i < THE_ALPHABET.length; i++) {
            if (letter == THE_ALPHABET[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Resets the model, choosing a new secret word to start a new game.
     */
    public void resetGame() {
        // gets a new secret word
        Random rand = new Random();
        secretWord = wordList[
                rand.nextInt(ANSWER_INDEX, TOTAL_NUMBER_OF_WORDS)];
        
        // record the number of each letter in the secret word
        lettersInSecretWord = new int[THE_ALPHABET.length];
        updateLettersInSecretWord();

        // set number of guesses equal to 0
        numberOfGuesses = 0;

        // player hasn't guessed the secret word
        hasGuessedSecretWord = false;
    }
}
