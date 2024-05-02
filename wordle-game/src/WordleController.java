/**
 * The controller for the game of Wordle.
 * 
 * @author Kergan Sanderson
 */
public class WordleController {

    /** The model for the controller. */
    private Wordle model;

    /** The GUI for the controller. */
    private WordleGUI view;

    /**
     * Creates the controller with a view and a model.
     */
    public WordleController() {
        view = new WordleGUI(this);
        model = new Wordle();
    }

    /**
     * Updates the current guess in the model given a guess from the view and 
     * returns the results of that guess.
     */
    public void getGuessResults(String guess) {
        if (model.isValidGuess(guess)) {
            // don't allow gameplay if game is over
            if (model.isOutOfTurns() || model.getHasGuessedSecretWord()) {
                view.endGame(model.getSecretWord());
            } else {
                view.displayGuess(model.getGuessResults(guess));

                // check if game is over
                if (model.isOutOfTurns() || model.getHasGuessedSecretWord()) {
                    view.endGame(model.getSecretWord());
                }
            }
        }
    }

    /**
     * Starts a new game by refreshing the model and the view.
     */
    public void startNewGame() {
        view.clearOutput();
        model.resetGame();
    }
}