package game.behaviours.strategy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.BehaviourSorter;

import java.util.List;

/**
 * Interface defining the strategy pattern for selecting actions from behaviors.
 * Implementations of this interface determine how NPCs choose which behavior
 * to execute during their turn.
 *
 * @author Jichao Liang
 */
public interface BehaviourSelectionStrategy {
    /**
     * Selects an action from a list of behaviors based on the implementation strategy.
     *
     * @param behaviours the list of available behaviors sorted by priority
     * @param actor      the NPC making the decision
     * @param map        the current game map
     * @return the selected action, or null if no valid action was found
     */
    Action selectAction(List<BehaviourSorter> behaviours, Actor actor, GameMap map);
}
