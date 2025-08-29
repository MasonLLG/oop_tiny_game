package game.dialogue;

import game.actors.passive.Oracle;

/**
 * A strategy interface for generating dialogue content such as riddles, prophecies, or compliments.
 */
public interface DialogueGenerator {
    /**
     * Generates the LLM prompt for this dialogue type.
     * @return prompt string
     */
    String generatePrompt();

    /**
     * Returns a message to display in-game before the AI response.
     * @return preamble string
     */
    String printResponse();

    /**
     * Returns the JSON field to extract from the response, or null if none.
     * @return field name or null
     */
    String getFieldToPrintOut();

    /**
     * Post-processes the full LLM response, updating Oracle state if needed.
     * @param fullResponse the raw response string
     * @param oracle       the Oracle to update (e.g. set Riddle)
     */
    default void processResponse(String fullResponse, Oracle oracle) {
        // default: nothing
    }
}