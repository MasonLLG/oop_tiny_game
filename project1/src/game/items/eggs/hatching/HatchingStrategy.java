package game.items.eggs.hatching;

import edu.monash.fit2099.engine.positions.Location;
import game.items.eggs.Egg;

/**
 * Strategy interface for defining how an egg hatches.
 * @author Jichao Liang
 */
public interface HatchingStrategy {
    /**
     * Called each turn to check whether hatching conditions are met and to perform hatching.
     *
     * @param location The location of the egg.
     * @param egg      The egg instance.
     */
    void tick(Location location, Egg egg);
}