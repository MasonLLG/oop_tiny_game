package game.items.seeds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.PlantAction;
import game.grounds.plants.Plant;
import game.statuses.GroundStatus;


/**
 * Abstract class representing a seed in the game, which can be planted to grow into Plants.
 * Extends Item class.
 *
 * @author kellytran
 */
public abstract class Seed extends Item {
    int staminaCost;
    /**
     * Constructor for Seed class.
     *
     * @param name the name of the seed
     * @param portable true if the seed can be picked up
     */
    public Seed(String name, boolean portable, int staminaCost) {
        super(name, '*', portable);
        this.staminaCost = staminaCost;
    }

    /**
     * Method that gets the actions that can be performed with the seed, depending on if the surrounding ground is plantable.
     *
     * @param owner the actor that owns the item
     * @param map the map where the actor is performing the action on
     * @return the list of actions that can be performed with the seed
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        Location currentLocation = map.locationOf(owner);
        if (currentLocation.getGround().hasCapability(GroundStatus.PLANTABLE)) {
            actions.add(new PlantAction(this, staminaCost));
        }
        return actions;

    }

    /**
     * Abstract method to be implemented by subclasses to plant the seed.
     */
    public abstract Plant plant(Location location);
}
