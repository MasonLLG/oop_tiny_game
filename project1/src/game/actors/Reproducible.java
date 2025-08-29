package game.actors;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Represents an entity capable of reproducing, typically by spawning offspring
 * or generating a new entity in the game world.
 * <p>
 * Implementing classes define the logic for how and under what conditions reproduction occurs.
 * This may involve checking adjacent tiles, spawning new actors, or dropping items such as eggs.
 * </p>
 * The reproduction process is typically triggered during an actor’s turn or by special behaviours.
 * @author Jichao Liang
 */
public interface Reproducible {
    void reproduce(Actor actor, GameMap map);
    boolean checkReproduceCondition(GameMap map);
}

