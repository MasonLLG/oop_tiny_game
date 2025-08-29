package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.Edible;

/**
 * An action that allows an actor to consume an item that implements the {@link Edible} interface.
 * When executed, it calls the {@code eat()} method on the food item, applying its effects to the actor.
 *
 * This action should be used for consumables such as eggs or healing items.
 * @author Jichao Liang
 */
public class EatAction extends Action {
    /** The edible item to be consumed. */
    private final Edible food;

    /**
     * Constructor.
     *
     * @param food the edible item to be eaten
     */
    public EatAction(Edible food) {
        this.food = food;
    }

    /**
     * Executes the eat action by invoking the {@code eat()} method on the edible item.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return A string describing the outcome of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        food.eat(actor, map);
        return menuDescription(actor);
    }

    /**
     * Describes the action for the game menu.
     *
     * @param actor The actor performing the action.
     * @return A string representation for display in the action menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " eat " + food;
    }
}
