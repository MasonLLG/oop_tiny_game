package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface representing items that can be sold by the player.
 * Implementing classes define their selling price and the effect
 * that occurs when the item is sold.
 */
public interface Sellable {

    /**
     * Returns the price the item can be sold for.
     *
     * @return the sell price
     */
    int getSellPrice();

    /**
     * Called when the item is sold by the actor.
     * Can apply effects to the player or the game world.
     *
     * @param p the actor who sold the item
     * @param g the game map
     */
    void onSell(Actor p, GameMap g);

    /**
     * Returns the name of the sellable item.
     *
     * @return the name of the item
     */
    String getName();
}
