package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface for harvestable plants.
 */
public interface Harvestable {
    boolean isMature();
    void harvest(Actor actor, Location location);
}