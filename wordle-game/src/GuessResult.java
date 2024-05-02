import java.awt.*;

/**
 * The results of a guess in the game of Wordle.
 * 
 * @author Kergan Sanderson
 */
public class GuessResult {

    /** The letters in the guess. */
    private char[] letters;

    /** The colors for the guess. */
    private Color[] colors;

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
        this.colors = new Color[Wordle.LETTERS_IN_WORD];
        this.index = index;

        for (int i = 0; i < Wordle.LETTERS_IN_WORD; i++) {
            letters[i] = guess.charAt(i);

            if (guessColors[i] == 'G') {
                colors[i] = new Color(65, 179, 65);
            } else if (guessColors[i] == 'Y') {
                colors[i] = new Color(196, 194, 48);
            } else {
                colors[i] = new Color(150, 150, 150);
            }
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
    public Color[] getColors() {
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
