package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Reproducible;


/**
 * Action that calls the reproducible's reproduce method.
 * Extends the action class.
 * @author kellytran
 */
public class ReproduceAction extends Action {
    Reproducible reproducible;
    int staminaCost;

    /**
     * Constructor for ReproduceAction
     *
     * @param reproducible the actor to be reproduced
     * @param staminaCost the stamina cost of the action
     */
    public ReproduceAction(Reproducible reproducible, int staminaCost) {
        this.reproducible = reproducible;
        this.staminaCost = staminaCost;
    }


    /**
     * Executes the reproduce action using the unique reproduce method of the parent actor.
     * @param actor - unused here as action executed by reproducible
     * @param map - map of reproducible
     *
     * @return a string describing the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        reproducible.reproduce(actor, map);
        return menuDescription(actor);
    }

    /**
     * Provides a description of the reproduce action to be displayed in the menu (not used)
     * @param actor the actor performing the reproduction (not used)
     * @return a string describing the reproduce action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " reproduces";
    }
}
