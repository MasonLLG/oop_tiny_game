package game.statuses;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.StatusEffect;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DeathAction;

/**
 * A class representing a rotting status effect.
 * This class is used to represent the rotting status effect on an actor.
 * The actor will be removed from the map after a certain number of turns.
 *
 * @author Kelly Tran
 */
public class RotStatus extends StatusEffect {
    private int rotCounter;
    private final int maxCounter;

    /**
     * Constructor for RotBehaviour for individual classes to set their rotCounter
     * Add +1 to the rotCounter to account for the first turn
     * @param rotCounter amount of turns creature takes to rot
     */
    public RotStatus(int rotCounter) {
        super("Rotting");
        this.maxCounter = rotCounter + 1;
        this.rotCounter = rotCounter + 1;
    }

    /**
     * This method is called every turn to rot the actor
     * @param location the location of the actor
     * @param actor the actor that is rotting
     */
    @Override
    public void tick(Location location, Actor actor) {
        rotCounter--;
        if (rotCounter <= 0) {
            new DeathAction(actor).execute(actor, location.map());
        }
    }

    /**
     * This method resets the rotCounter to the maxCounter
     */
    public void resetRot() {
        this.rotCounter = maxCounter;
    }

}
