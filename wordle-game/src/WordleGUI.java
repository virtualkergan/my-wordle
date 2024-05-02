import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A GUI for my game of wordle.
 * 
 * @author Kergan Sanderson
 */
public class WordleGUI implements ActionListener {

    /** The green color used for the letters. */
    public static final Color GREEN = new Color(65, 179, 65);

    /** The yellow color used for the letters. */
    public static final Color YELLOW = new Color(196, 194, 48);
    
    /** The gray color used for the letters. */
    public static final Color GRAY = new Color(150, 150, 150);

    /** The order of the QWERTY keyboard layout. */
    public static final char[] QWERTY = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i',
                                         'o', 'p', 'a', 's', 'd', 'f', 'g', 'h',
                                         'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b',
                                         'n', 'm'};
    
    /** Map of alphabet indeces to QWERTY indeces. */
    public static final int[] A_TO_Q = {10, 23, 21, 12, 2, 13, 14, 15, 7, 16, 17, 18, 25, 24, 8, 9, 0, 3, 11, 4, 6, 22, 1, 20, 5, 19};

    /** The controller for this game of Wordle. */
    private WordleController controller;

    /** The JFrame for this GUI. */
    private JFrame frame;

    /** The button to press to submit your guess. */
    private JButton submitButton;

    /** The field to type in your guess. */
    private JTextField wordField;

    /** The display fields for previous guess letters. */
    private JTextField[][] letters;

    /** The display fields for the keyboard representation. */
    private JTextField[] keyboard;

    /**
     * Creates a GUI for the game of Wordle.
     * 
     * @param controller the controller for the game of Wordle
     */
    public WordleGUI(WordleController controller) {

        // create reference to the controller
        this.controller = controller;

        // create instance variables
        frame = new JFrame();
        submitButton = new JButton("Guess");
        wordField = new JTextField(5);
        letters = new JTextField[6][5];
        keyboard = new JTextField[26];

        // set up the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(new Dimension(200,200));
        frame.setTitle("Wordle");
        frame.setLayout(new BorderLayout());

        // create a font with increased size for display of letters
        JTextField tempField = new JTextField();
        Font letterFont = new Font(tempField.getFont().getName(),
                tempField.getFont().getStyle(),60);
        tempField = null;
        
        // fill the letter panel with the letter text fields
        JPanel letterPanel = new JPanel(new GridLayout(6,5));
        for (int i = 0; i < Wordle.GUESSES_IN_GAME; i++) {
            for (int j = 0; j < Wordle.LETTERS_IN_WORD; j++) {
                letters[i][j] = new JTextField();
                letters[i][j].setFont(letterFont);
                letters[i][j].setForeground(Color.WHITE);
                letters[i][j].setEditable(false);
                letters[i][j].setHorizontalAlignment(JTextField.CENTER);
                letters[i][j].setPreferredSize(new Dimension(80,80));
                letterPanel.add(letters[i][j]);
            }
        }

        // Create the space for the guess entry and the keyboard display
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // create the keyboard representation
        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridLayout(3, 11));

        for (int i = 0; i < 19; i++) {
            keyboard[i] = new JTextField(1);
            keyboard[i].setText(Character.toString(QWERTY[i]));
            keyboard[i].setEditable(false);
            keyboard[i].setHorizontalAlignment(JTextField.CENTER);
            keyboardPanel.add(keyboard[i]);
        }

        keyboardPanel.add(new JLabel());
        keyboardPanel.add(new JLabel());
        
        for (int i = 19; i < 26; i++) {
            keyboard[i] = new JTextField(1);
            keyboard[i].setText(Character.toString(QWERTY[i]));
            keyboard[i].setEditable(false);
            keyboard[i].setHorizontalAlignment(JTextField.CENTER);
            keyboardPanel.add(keyboard[i]);
        }

        keyboardPanel.add(new JLabel());
        keyboardPanel.add(new JLabel());

        // place the button and the guess entry field
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(wordField);

        // populate the bottomPanel
        bottomPanel.add(keyboardPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // finish the frame
        frame.add(letterPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();

        // add the GUI as an event listener to the button
        submitButton.addActionListener(this);

        // make the frame visible in the center of the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Called when the submit button is pressed.
     */
    public void actionPerformed(ActionEvent event) {
        controller.getGuessResults(wordField.getText().toLowerCase());
        wordField.setText("");
    }

    /**
     * Sets the colors and letters for a row of guesses given the guess and 
     * the colors.
     * 
     * @param letters the letters of the guess to display
     * @param colors the colors of the guess to display
     * @param guessIndex the index of the row to display the letters on
     */
    public void displayGuess(GuessResult result) {
        char[] guessLetters = result.getLetters();
        char[] guessColors = result.getColors();
        int guessIndex = result.getIndex();

        // display the letters of the guess and their colors
        for (int i = 0; i < Wordle.LETTERS_IN_WORD; i++) {
            letters[guessIndex][i].setText(
                    Character.toString(guessLetters[i]).toUpperCase());
            letters[guessIndex][i].setBackground(charToColor(guessColors[i]));
        }

        // update the keyboard representation using the guess results
        updateKeyboard(result);
    }

    /**
     * Updates the keyboard representation of the GUI.
     */
    public void updateKeyboard(GuessResult result) {
        char[] guessLetters = result.getLetters();
        char[] guessColors = result.getColors();

        for (int i = 0; i < 5; i++) {
            // get the index of the letter in the alphabet
            int index = Wordle.indexOfLetter(guessLetters[i]);
            // converts the index to the index in qwerty
            index = A_TO_Q[index];

            if (keyboard[index].getBackground() == charToColor('G')) {
                // skip the already green letters
                continue;
            } else if (keyboard[index].getBackground() == charToColor('Y')) {
                // if a letter is yellow, only change it if the guess is green
                if (guessColors[i] == 'G') {
                    keyboard[index].setBackground(charToColor('G'));
                    keyboard[index].setForeground(Color.WHITE);
                }
            } else {
                // always update a white or gray guessed letter to gray
                keyboard[index].setBackground(charToColor(guessColors[i]));
                keyboard[index].setForeground(Color.WHITE);
            }
        }
    }

    /**
     * Converts from a color indicating character to that color.
     * 
     * @param color the color indicating character
     * @return the Color object associated with the color character
     */
    public Color charToColor(char color) {
        if (color == 'G') {
            return GREEN;
        } else if (color == 'Y') {
            return YELLOW;
        } else {
            return GRAY;
        }
    }

    /**
     * Ends the game by shutting down functionality of the GUI and displaying a 
     * pop up dialog box.
     */
    public void endGame(String secretWord) {
        wordField.setEditable(false);
        int choice = JOptionPane.showConfirmDialog(frame,
                "The word was: " + secretWord + "\n"
                + "Want to play again?");
        if (choice == JOptionPane.YES_OPTION) {
            controller.startNewGame();
        } else {
            System.exit(1);
        }
    }

    /**
     * Clears the output for a new game.
     */
    public void clearOutput() {

        // reset displayed guesses
        for (int i = 0; i < Wordle.GUESSES_IN_GAME; i++) {
            for (int j = 0; j < Wordle.LETTERS_IN_WORD; j++) {
                letters[i][j].setText("");
                letters[i][j].setBackground(Color.WHITE);
            }
        }
        // open the guess field for more guesses
        wordField.setEditable(true);

        // reset the keyboard representation
        for (JTextField key : keyboard) {
            key.setForeground(Color.BLACK);
            key.setBackground(Color.WHITE);
        }
    }
}