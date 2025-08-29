package game.behaviours;

import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.MonologueAction;

import java.util.List;
import java.util.Random;

/**
 * A behaviour that allows an NPC to say a random monologue line with a given probability.
 */
public class MonologueBehaviour implements Behaviour {

    private final List<String> monologueList;
    private final double probability;
    private final Random rand = new Random();

    /**
     * Constructor
     * @param monologueList a list of possible lines to say
     * @param probability the chance (0.0–1.0) that the monologue will be spoken on a turn
     */
    public MonologueBehaviour(List<String> monologueList, double probability) {
        this.monologueList = monologueList;
        this.probability = probability;
    }

    /**
     * Returns a MonologueAction if the chance is triggered.
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return a MonologueAction or null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!monologueList.isEmpty() && rand.nextDouble() < probability) {
            String line = monologueList.get(rand.nextInt(monologueList.size()));
            return new MonologueAction(line);
        }
        return null;
    }
}