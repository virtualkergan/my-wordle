import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A GUI for my game of wordle.
 * 
 * @author Kergan Sanderson
 */
public class WordleGUI implements ActionListener {

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
                letters[i][j].setPreferredSize(new Dimension(80, 80));
                letterPanel.add(letters[i][j]);
            }
        }

        frame.add(letterPanel, BorderLayout.CENTER);

        // place the button and the guess entry field
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(wordField);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.pack();

        // add the GUI as an event listener to the button
        submitButton.addActionListener(this);

        // make the frame visible
        frame.setVisible(true);
    }

    /**
     * Called when the submit button is pressed.
     */
    public void actionPerformed(ActionEvent event) {
        controller.getGuessResults(wordField.getText());
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
        Color[] guessColors = result.getColors();
        int guessIndex = result.getIndex();

        for (int i = 0; i < Wordle.LETTERS_IN_WORD; i++) {
            letters[guessIndex][i].setText(
                    Character.toString(guessLetters[i]).toUpperCase());
            letters[guessIndex][i].setBackground(guessColors[i]);
        }
    }

    /**
     * Ends the game by shutting down functionality of the GUI and displaying a 
     * pop up dialog box.
     */
    public void endGame(String secretWord) {
        JOptionPane.showMessageDialog(frame, "The word was: " + secretWord);
        wordField.setEditable(false);

    }
}