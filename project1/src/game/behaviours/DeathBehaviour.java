package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.actors.Actor;
import game.actions.DeathAction;

/**
 * Runs before any other behaviour, ensures that actor is removed from the game if they are not conscious.
 * Implements the Behaviour interface.
 *
 * @author kellytran
 */
public class DeathBehaviour implements Behaviour {

    /**
     * Get an action that handles the death of an actor.
     * This action is triggered when an actor is no longer conscious
     * It removes the actor from the game map and drops their items
     *
     * @param actor the Actor with the behaviour
     * @param map the GameMap containing the Actor
     * @return the DeathAction if the actor is not conscious, null otherwise
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!actor.isConscious()) {
            return new DeathAction();
        }
        return null;
    }
}