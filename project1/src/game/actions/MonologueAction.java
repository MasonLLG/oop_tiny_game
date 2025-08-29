package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action that allows an actor to say a fixed monologue.
 * This action can be used to simulate an NPC or player expressing a line of dialogue,
 * typically triggered through interaction or environmental conditions.
 */
public class MonologueAction extends Action {

    /**
     * The monologue text that the actor will say when this action is executed.
     */
    private final String monologue;


    /**
     * Constructs a MonologueAction with the given monologue string.
     *
     * @param monologue The line of dialogue to be spoken by the actor.
     */
    public MonologueAction(String monologue) {
        this.monologue = monologue;
    }

    /**
     * Executes the monologue action, returning the full dialogue line.
     *
     * @param actor The actor performing the monologue.
     * @param map   The game map where the actor is located.
     * @return A string representing the actor's spoken line.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        for (int i = 0; i < monologue.length(); i++) {
            System.out.print(monologue.charAt(i));
            System.out.flush();

            try {
                Thread.sleep(100); // ~75 characters per second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(); // Finish line after typing
        return ""; // So display.println(result) prints nothing extra
    }


    /**
     * Returns a short description of this action for display in the game menu.
     *
     * @param actor The actor performing the monologue.
     * @return A menu-friendly string describing the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " murmurs: \"" + monologue + "\"";
    }
}