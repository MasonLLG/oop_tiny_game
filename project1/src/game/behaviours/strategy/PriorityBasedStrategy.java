package game.behaviours.strategy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.BehaviourSorter;

import java.util.List;


/**
 * Priority-based behavior selection strategy.
 * This implementation tries behaviors in order of priority (lowest number first)
 * and returns the first valid action it finds.
 *
 * @author Jichao Liang
 */
public class PriorityBasedStrategy implements BehaviourSelectionStrategy {
    /**
     * Selects an action by trying behaviors in priority order.
     *
     * @param behaviours the list of available behaviors sorted by priority
     * @param actor      the NPC making the decision
     * @param map        the current game map
     * @return the first valid action found, or null if none were valid
     */
    @Override
    public Action selectAction(List<BehaviourSorter> behaviours, Actor actor, GameMap map) {
        for (BehaviourSorter sorter : behaviours) {
            Action action = sorter.getBehaviour().getAction(actor, map);
            if (action != null) return action;
        }
        return null;
    }
}
