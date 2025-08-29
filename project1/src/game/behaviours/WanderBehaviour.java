package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.Random;

/**
 * A behaviour that makes an actor wander randomly around the map.
 * Implements the Behaviour interface.
 *
 * @author kellytran
 */
public class WanderBehaviour implements Behaviour {

    private final Random random = new Random();

    /**
     *
     * @param actor the Actor that has this behaviour
     * @param map the GameMap containing the Actor
     * The logic for this method is based on the WanderBehaviour class in the demo package, created by Riordan D. Alfredo.
     *
     * @return an Action that moves the Actor to a random adjacent location, or null if no movement is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        ArrayList<Action> actions = new ArrayList<>();

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                actions.add(new MoveActorAction(destination, exit.getName()));
            }
        }

        if (!actions.isEmpty()) {
            return actions.get(random.nextInt(actions.size()));
        }

        return null;
    }
}