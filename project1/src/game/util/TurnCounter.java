package game.util;

/**
 * A utility class to track the number of turns an actor or feature has progressed through.
 * @author Jichao Liang
 */
public class TurnCounter {
    private int currentTurn = 0;

    /**
     * Increments the turn counter.
     */
    public void nextTurn() {
        currentTurn++;
    }

    /**
     * Gets the current turn count.
     *
     * @return the number of turns passed
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Checks if the current turn is divisible by the given interval.
     *
     * @param interval number to check divisibility against
     * @return true if currentTurn % interval == 0
     */
    public boolean isTurnMultipleOf(int interval) {
        return currentTurn > 0 && currentTurn % interval == 0;
    }

    /**
     * Resets the counter to zero.
     */
    public void reset() {
        currentTurn = 0;
    }
}
