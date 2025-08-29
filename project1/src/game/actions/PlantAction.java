package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.seeds.Seed;

/**
 * An action that allows an actor to plant a seed in the game.
 * This class extends the Action class and implements the execute method to perform the planting.
 *
 * @author kellytran
 */
public class PlantAction extends Action {
    private final Seed seed;
    private final int staminaCost;

    /**
     * Constructor for PlantAction.
     *
     * @param seed the seed to be planted
     */
    public PlantAction(Seed seed, int staminaCost) {
        this.seed = seed;
        this.staminaCost = staminaCost;
    }


    /**
     * Execute the action of planting the seed, setting the ground to the plant it grows into,
     *
     * @param actor the actor planting the seed
     * @param map   the map the actor is on
     * @return a string describing the result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        actor.removeItemFromInventory(seed);
        here.setGround(seed.plant(here));
        System.out.println("Planting: " + seed.plant(here).getClass().getName());
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, staminaCost);
        return menuDescription(actor);
    }

    /**
     * Provides a description of the action to be displayed in the menu.
     *
     * @param actor the actor performing the action
     * @return a string describing the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " plants the " + seed;
    }


}
