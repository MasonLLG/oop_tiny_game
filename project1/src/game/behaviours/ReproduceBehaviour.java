package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ReproduceAction;
import game.actors.Reproducible;
import game.util.TurnCounter;

/**
 * A Behaviour that allows an Actor to reproduce
 * Implements the Behaviour interface.
 * @author kellytran
 */
public class ReproduceBehaviour implements Behaviour {
    private final Reproducible reproducible;
    private final int staminaCost;
    private final int reproduceTime;
    private final TurnCounter reproduceCounter;

    /**
     * Constructor for ReproduceBehaviour
     *
     * @param reproducible the Reproducible object that can reproduce
     * @param staminaCost the stamina cost for reproduction
     * @param reproduceTime the number of turns between reproduction attempts
     */
    public ReproduceBehaviour(Reproducible reproducible, int staminaCost, int reproduceTime) {
        this.reproducible = reproducible;
        this.staminaCost = staminaCost;
        this.reproduceTime = reproduceTime;
        this.reproduceCounter = new TurnCounter();
    }

    /**
     * Constructor with default interval of 1 (check every turn)
     *
     * @param reproducible The entity that can reproduce
     * @param staminaCost The stamina cost for reproduction
     */
    public ReproduceBehaviour(Reproducible reproducible, int staminaCost) {
        this(reproducible, staminaCost, 1);
    }

    /**
     * Get an action that allows an actor to reproduce
     *
     * @param actor the Actor with the behaviour
     * @param map   the GameMap containing the Actor
     * @return the ReproduceAction if the actor is ready to reproduce, null otherwise
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        reproduceCounter.nextTurn();
        if (reproducible.checkReproduceCondition(map) && reproduceCounter.isTurnMultipleOf(this.reproduceTime)) {
            return new ReproduceAction(reproducible, staminaCost);
        }
        return null;
    }
}