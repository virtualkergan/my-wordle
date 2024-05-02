/**
 * The results of a guess in the game of Wordle.
 * 
 * @author Kergan Sanderson
 */
public class GuessResult {

    /** The letters in the guess. */
    private char[] letters;

    /** The colors for the guess. */
    private char[] colors;

    /** The index of the guess in the game. */
    private int index;

    /**
     * Creates the guess result given the word and its colors.
     * 
     * @param guess the guess itself
     * @param colors the resulting colors for the guess
     */
    public GuessResult(String guess, char[] guessColors, int index) {
        this.letters = new char[Wordle.LETTERS_IN_WORD];
        this.colors = guessColors;
        this.index = index;

        for (int i = 0; i < Wordle.LETTERS_IN_WORD; i++) {
            this.letters[i] = guess.charAt(i);
        }
    }

    /**
     * Getter method for the letters.
     * 
     * @return the letters field of the GuessResult
     */
    public char[] getLetters() {
        return letters;
    }

    /**
     * Getter method for the letters.
     * 
     * @return the letters field of the GuessResult
     */
    public char[] getColors() {
        return colors;
    }

    /**
     * Getter method for the letters.
     * 
     * @return the letters field of the GuessResult
     */
    public int getIndex() {
        return index;
    }
}
