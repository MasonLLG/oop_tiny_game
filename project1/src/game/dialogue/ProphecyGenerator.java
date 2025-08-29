package game.dialogue;

/**
 * A dialogue generator that produces a mysterious prophecy from the Oracle.
 *
 * The prophecy involves a farmer receiving a vision about the future, often involving an egg.
 */
public class ProphecyGenerator implements DialogueGenerator {

    /**
     * Returns the prompt to be sent to the language model for generating a prophecy.
     *
     * @return a prompt requesting a mysterious and future-oriented prophecy for a farmer
     */
    @Override
    public String generatePrompt() {
        return "Write a short prophecy about a farmer, who is receiving the prophecy and an egg. Make it mysterious and future-focused.";
    }

    /**
     * Returns a short description of how the Oracle delivers the prophecy.
     *
     * @return a line describing the Oracle's mystical tone
     */
    @Override
    public String printResponse() {
        return "The Oracle whispers ancient words of fate...";
    }

    /**
     * Specifies a JSON field to extract from the model's response.
     *
     * @return null, indicating that the full content of the response should be used
     */
    @Override
    public String getFieldToPrintOut() {
        return null;
    }
}
