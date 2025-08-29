package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

import game.actors.passive.Oracle;
import game.dialogue.DialogueGenerator;
import game.util.ApiHandler;

/**
 * An action that allows an actor to invoke the Oracle for a prophetic message,
 * which may be a compliment, riddle, or mysterious prophecy.
 * This class leverages a {@link DialogueGenerator} strategy to dynamically generate
 * dialogue prompts and handle responses, enabling flexible NPC interaction.
 * If the response is a riddle, it is stored in the Oracle for future reference.
 */
public class PropheciseAction extends Action {

    private final DialogueGenerator generator;
    private final Oracle oracle;

    /**
     * Constructs a new {@code PropheciseAction}.
     *
     * @param generator the dialogue generator responsible for generating a prompt and handling the response
     * @param oracle    the Oracle instance to update if the response includes persistent data (e.g., a {@code Riddle})
     */
    public PropheciseAction(DialogueGenerator generator, Oracle oracle) {
        this.generator = generator;
        this.oracle = oracle;
    }

    /**
     * Executes the prophecy action. It streams a response from the language model
     * using the provided generator's prompt, prints it with a preamble, and processes
     * the result (e.g. stores a riddle in the Oracle).
     *
     * @param actor the actor invoking the Oracle
     * @param map   the map the actor is located on
     * @return an empty string as the action's result is primarily side-effect based (e.g. printed dialogue)
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Print actor speech prefix
        System.out.print(actor + " says: \"");
        System.out.flush();

        // Print the generator's preamble (e.g. "The Oracle whispers...")
        System.out.println(generator.printResponse());

        // Stream and print the language model response
        StringBuilder fullResponse = ApiHandler.streamAndPrintResponse(
                generator.generatePrompt(),
                generator.getFieldToPrintOut());

        // Allow the generator to process and act on the full response
        generator.processResponse(fullResponse.toString(), oracle);

        // Close the speech quotation
        System.out.println("\"");

        return "";
    }

    /**
     * Provides a short description of the action to appear in a user-facing menu.
     *
     * @param actor the actor performing the action
     * @return a string describing the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " invokes the Oracle";
    }
}
