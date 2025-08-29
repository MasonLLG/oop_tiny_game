package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.List;
import java.util.Random;

/**
 * An action that allows the player to listen to an NPC's monologue.
 * A monologue line is randomly chosen from the provided list.
 */
public class ListenAction extends Action {
    /**
     * The NPC whose monologue is being listened to.
     */
    private final Actor npc;
    /**
     * The pool of monologue lines available for this NPC.
     */
    private final List<String> monologuePool;
    /**
     * Random number generator for selecting a monologue line.
     */
    private final Random rand = new Random();

    /**
     * Constructor.
     *
     * @param npc The NPC being listened to
     * @param monologuePool A list of monologue lines
     */
    public ListenAction(Actor npc, List<String> monologuePool) {
        this.npc = npc;
        this.monologuePool = monologuePool;
    }

    /**
     * Executes the action by returning one random monologue line from the NPC.
     * If the pool is empty, the NPC says nothing.
     *
     * @param actor The actor performing the action (usually the player).
     * @param map   The current game map (unused in this action).
     * @return A string representing the dialogue line.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (monologuePool.isEmpty()) {
            return npc + " remains silent.";
        }
        String quote = monologuePool.get(rand.nextInt(monologuePool.size()));
        for (int i = 0; i < quote.length(); i++) {
            System.out.print(quote.charAt(i));
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
     * Returns a description of the action for display in the game menu.
     *
     * @param actor The actor performing the action.
     * @return A brief description shown in the action menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " listens to " + npc;
    }
}