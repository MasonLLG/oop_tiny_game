package game.statuses;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface representing something an Actor can cure.
 * This interface is used to define the cure method for any class that implements it.
 *
 * @author Kelly Tran
 */
public interface Curable {
    void cure(Actor actor, GameMap map);
}
