package game.behaviours.strategy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.BehaviourSorter;

import java.util.List;
import java.util.Random;


/**
 * Random behavior selection strategy.
 * This implementation selects a random behavior each turn and returns its action.
 * If the selected behavior returns null, no action is performed.
 *
 * @author Jichao Liang
 */
public class RandomSelectionStrategy implements BehaviourSelectionStrategy {
    private final Random random = new Random();
    /**
     * Selects an action by choosing a random behavior.
     *
     * @param behaviours the list of available behaviors
     * @param actor      the NPC making the decision
     * @param map        the current game map
     * @return the action from a randomly selected behavior, or null if the selected behavior was invalid
     */
    @Override
    public Action selectAction(List<BehaviourSorter> behaviours, Actor actor, GameMap map) {
        if (behaviours.isEmpty()) return null;
        int index = random.nextInt(behaviours.size());
        return behaviours.get(index).getBehaviour().getAction(actor, map);
    }
}
