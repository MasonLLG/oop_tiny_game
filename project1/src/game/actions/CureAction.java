package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.Curable;

/**
 * Class representing a cure action in the game.
 * This class extends the Action class and implements the execute method to perform the cure.
 * It also provides a menu description for the action.
 *
 * @author kellytran
 */
public class CureAction extends Action {
    Curable victim;
    int staminaCost;
    String direction;

    /**
     * Constructor for CureAction
     * @param victim the actor to be cured
     * @param staminaCost the stamina cost of the action
     * @param direction the direction of the action
     */
    public CureAction(Curable victim, int staminaCost, String direction) {
        this.victim = victim;
        this.staminaCost = staminaCost;
        this.direction = direction;
    }

    /**
     * Executes the cure action on the target actor.
     *
     * @param actor the actor performing the cure
     * @param map   the game map
     * @return a string describing the result of the cure
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        victim.cure(actor, map);
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaCost);
        return menuDescription(actor);

    }

    /**
     * Provides a description of the cure action to be displayed in the menu.
     *
     * @param actor the actor performing the cure
     * @return a string describing the cure action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " cures " + victim + " at " + direction;
    }
}
