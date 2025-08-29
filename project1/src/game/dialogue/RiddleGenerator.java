package game.dialogue;

import game.actors.passive.Oracle;
import game.oracleutil.Riddle;
import game.util.ApiHandler;

/**
 * A dialogue generator that creates a riddle in JSON format related to a farmer in Elden Ring.
 *
 * The prompt requests the language model to return a structured JSON response containing:
 * - The riddle text
 * - A list of possible answers
 * - The correct answer
 *
 * This class also includes logic to parse and pass the riddle to the Oracle.
 */
public class RiddleGenerator implements DialogueGenerator {

    /**
     * Returns the prompt to be sent to the language model for generating a riddle.
     * The prompt enforces a specific JSON structure with no additional explanation.
     *
     * @return a prompt instructing the model to produce a riddle in JSON format
     */
    @Override
    public String generatePrompt() {
        return "Write a riddle focused around a farmer in Elden Ring. Return your result as JSON with exactly these fields:\n"
                + "  \"Riddle\": \"…\",\n"
                + "  \"PossibleAnswers\": [\"…\",\"…\",…],\n"
                + "  \"Answer\": \"…\"\n"
                + "Nothing else—no extra text.";
    }

    /**
     * Returns a description of how the Oracle delivers the riddle.
     *
     * @return a line describing the Oracle's mysterious delivery
     */
    @Override
    public String printResponse() {
        return "The Oracle peers into your mind and speaks a riddle...";
    }

    /**
     * Specifies which JSON field should be printed during streaming.
     *
     * @return the name of the field to extract and print, which is "Riddle"
     */
    @Override
    public String getFieldToPrintOut() {
        return "Riddle";
    }

    /**
     * Parses the full JSON response from the model, constructs a Riddle object,
     * and assigns it to the Oracle.
     *
     * @param fullResponse the raw JSON string returned from the language model
     * @param oracle the Oracle actor to receive and store the current riddle
     */
    @Override
    public void processResponse(String fullResponse, Oracle oracle) {
        ApiHandler.Result parsed = ApiHandler.parseRiddleJson(fullResponse);
        Riddle riddle = new Riddle(
                parsed.riddle,
                parsed.possibleAnswers,
                parsed.possibleAnswers.indexOf(parsed.answer)
        );
        oracle.setCurrentRiddle(riddle);
    }
}
