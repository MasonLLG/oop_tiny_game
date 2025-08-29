package game.statuses;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Represents an item or entity that can be consumed (eaten) by an {@link Actor}.
 * <p>
 * Implementing classes define the behavior of what happens when the item is eaten,
 * such as restoring health, applying buffs, or removing the item from inventory.
 * </p>
 * This interface allows for flexible and reusable definitions of edible objects across the game.
 * @author Jichao Liang
 */
public interface Edible {
    /**
     * Defines the effect of consuming the item.
     *
     * @param actor The actor who is eating the item.
     * @param map   The game map the actor is currently on.
     */
    void eat(Actor actor, GameMap map);
}
