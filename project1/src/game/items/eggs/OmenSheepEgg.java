package game.items.eggs;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.passive.OmenSheep;
import game.behaviours.strategy.PriorityBasedStrategy;
import game.items.eggs.hatching.TurnBasedHatchingStrategy;

/**
 * A concrete implementation of an {@link Egg} representing an OmenSheep Egg.
 * <p>
 * This egg uses a {@link TurnBasedHatchingStrategy} that allows it to automatically hatch
 * after being on the ground for 3 turns, as long as it hasn’t been picked up.
 * When hatched, it spawns an {@link OmenSheep} if the location is unoccupied.
 * </p>
 * <p>
 * Additionally, this egg is edible. When consumed, it increases the actor’s maximum health by 10
 * and is removed from their inventory.
 * </p>
 * @author Jichao Liang
 */
public class OmenSheepEgg extends Egg {

    /**
     * Constructor for the OmenSheepEgg.
     * <p>
     * Sets the egg name and defines it as portable with automatic turn-based hatching enabled after 3 turns.
     * </p>
     */
    public OmenSheepEgg() {
        super("OmenSheep Egg", true, new TurnBasedHatchingStrategy(3));
    }

    /**
     * Defines the effect when the egg is eaten.
     * <p>
     * Increases the actor's maximum health by 10 and removes the egg from the actor's inventory.
     * </p>
     *
     * @param actor The actor consuming the egg.
     * @param map   The map the actor is on.
     */
    @Override
    public void eat(Actor actor, GameMap map) {
        actor.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 10);
        actor.removeItemFromInventory(this);
    }

    /**
     * Defines what happens when the egg hatches.
     * <p>
     * If the current location does not already contain an actor, the egg is removed
     * and a new {@link OmenSheep} is spawned at the same location.
     * </p>
     *
     * @param location The location where the egg is hatching.
     */
    @Override
    public void hatch(Location location) {
        if (!location.containsAnActor()) {
            location.removeItem(this);
            location.map().addActor(new OmenSheep(new PriorityBasedStrategy()), location);
        }
    }
}

