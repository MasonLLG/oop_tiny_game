package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.GrowAction;
import game.statuses.Growable;

/**
* A behaviour that allows actors to grow if they are capable of doing so.
 * This class implements the Behaviour interface and provides an action allowing the growable object to grow.
 *
 * @author Kelly Tran
 */
public class GrowBehaviour implements Behaviour {
    private final Growable growable;

    /**
     * Constructor for GrowBehaviour
     * @param growable the Growable object that can grow
     */
    public GrowBehaviour(Growable growable) {
        this.growable = growable;
    }

    /**
     * Get an action that allows an actor to grow
     *
     * @return the GrowAction if the actor is ready to grow, null otherwise
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (growable.canGrow()) {
            return new GrowAction(growable);
        }
        return null;
    }
}
