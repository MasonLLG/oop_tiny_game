package game.items.eggs.hatching;

import edu.monash.fit2099.engine.positions.Location;
import game.items.eggs.Egg;
import game.util.TurnCounter;

/**
 * A strategy that hatches an egg after a fixed number of turns on the ground.
 * <p>
 * This strategy uses a {@link TurnCounter} to track how many turns the egg has spent
 * on the ground. If the configured threshold is reached and the egg hasn't been picked up,
 * the egg will hatch at its current location.
 * </p>
 * @author Jichao Liang
 */
public class TurnBasedHatchingStrategy implements HatchingStrategy {
    /** Counter to track the number of turns the egg has been on the ground */
    private final TurnCounter counter = new TurnCounter();

    /** The number of turns after which the egg should hatch */
    private final int hatchAfterTurns;

    /**
     * Constructs a new turn-based hatching strategy.
     *
     * @param hatchAfterTurns The number of turns after which the egg should hatch.
     */
    public TurnBasedHatchingStrategy(int hatchAfterTurns) {
        this.hatchAfterTurns = hatchAfterTurns;
    }

    /**
     * Called each turn to determine whether the egg should hatch.
     * <p>
     * If the egg was picked up, hatching is aborted. Otherwise, the internal
     * turn counter is incremented and compared against the hatch threshold.
     * </p>
     *
     * @param location The location where the egg currently resides.
     * @param egg The egg instance being evaluated.
     */
    @Override
    public void tick(Location location, Egg egg) {
        if (egg.wasPickedUp()) return;

        counter.nextTurn();
        if (counter.getCurrentTurn() > hatchAfterTurns) {
            egg.hatch(location);
        }
    }
}
