package game.items.eggs;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.passive.GoldenBeetle;
import game.behaviours.strategy.PriorityBasedStrategy;
import game.items.eggs.hatching.StatusSurroundHatchingStrategy;
import game.statuses.EntityStatus;

/**
 * Represents a Golden Egg in the game.
 * Extends the Egg class to inherit properties and behaviors of an egg.
 * Egg hatches if near cursed entities, such as a Blight
 * Can be consumed by the farmer.
 *
 * @author kellytran
 */
public class GoldenEgg extends Egg {
    /** Constructor for the GoldenEgg
     */
    public GoldenEgg() {
        super("Golden Egg", true, new StatusSurroundHatchingStrategy(EntityStatus.CURSED));
    }

    /**
     * Method to consume the GoldenEgg.
     * Eating the GoldenEgg will provide the player with 20 stamina points.
     *
     * @param actor The actor consuming the egg.
     * @param map The game map
     */
    @Override
    public void eat(Actor actor, GameMap map) {
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 20);
    }

    /**
     * Method to hatch the GoldenEgg.
     */
    @Override
    public void hatch(Location location) {
        if (!location.containsAnActor()) {
            location.removeItem(this);
            location.map().addActor(new GoldenBeetle(new PriorityBasedStrategy()), location);
        }
    }

}
