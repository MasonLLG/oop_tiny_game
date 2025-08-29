package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.Growable;

/**
 * Class representing an action that allows a Growable object to grow.
 * This class extends the Action class and implements the execute method to perform the growth.
 *
 * @author Kelly Tran
 */
public class GrowAction extends Action {
    private final Growable growable;

    /**
     * Constructor for GrowAction
     *
     * @param growable the Growable object that can grow
     */
    public GrowAction(Growable growable) {
        this.growable = growable;
    }

    /**
     * Execute the action, allowing the Growable to grow.
     *
     * @param actor the actor performing the action (not used here)
     * @param map the map the actor is on (not used here)
     * @return a description of the action performed
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (growable.canGrow()) {
            return menuDescription(actor) + growable.grow();
        } else {
            return growable + " cannot grow at this time.";
        }
    }

    /**
     * Returns a description of the action for display in the game menu (not used, only for return statement in execute).
     * @param actor The actor performing the action.
     * @return a string describing the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return growable + " is growing...";
    }
}
