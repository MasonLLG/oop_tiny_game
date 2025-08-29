package game.dialogue;

/**
 * A dialogue generator that creates a warm compliment from the Oracle to the farmer.
 *
 * Used to prompt a friendly and heartfelt message, specifically complimenting
 * how the farmer handled an egg.
 */
public class ComplimentGenerator implements DialogueGenerator {

    /**
     * Returns the prompt to be sent to the language model for generating the compliment.
     *
     * @return a descriptive prompt asking for a heartfelt compliment from an oracle to a farmer
     */
    @Override
    public String generatePrompt() {
        return "Write a heartfelt compliment from an oracle to a farmer about how they handled an egg. Make it warm and encouraging.";
    }

    /**
     * Returns a short description of how the Oracle reacts when delivering the response.
     *
     * @return a line describing the Oracle's warm praise
     */
    @Override
    public String printResponse() {
        return "The Oracle smiles warmly and offers praise...";
    }

    /**
     * Specifies a JSON field to extract from the model's response.
     *
     * @return null, indicating the full response should be used directly
     */
    @Override
    public String getFieldToPrintOut() {
        return null;
    }
}
