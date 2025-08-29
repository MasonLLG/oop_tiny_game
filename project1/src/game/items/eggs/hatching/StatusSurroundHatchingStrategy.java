package game.items.eggs.hatching;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.items.eggs.Egg;

/**
 * A strategy that hatches an egg based on the status of surrounding entities.
 * For example, it may hatch if there are cursed entities nearby.
 *
 * @author kellytran
 */
public class StatusSurroundHatchingStrategy implements HatchingStrategy {

    private final Enum<?> statusToCheck;

    /**
     * Constructor for the StatusSurroundHatchingStrategy.
     */
    public StatusSurroundHatchingStrategy(Enum<?> statusToCheck) {
        this.statusToCheck = statusToCheck;
    }

    /**
     * Checks the surrounding entities for the specified status, and hatches the egg if found.
     * @param location The location of the egg.
     * @param egg      The egg instance.
     */
    public void tick(Location location, Egg egg) {
        if (egg.wasPickedUp()) return;

        // Check the surrounding entities (ground, actor) for the specified status
        for (Exit exit : location.getExits()) {
            Location here = exit.getDestination();
            if (here.getGround().hasCapability(statusToCheck)) {
                egg.hatch(location);
                break;
            }
            if (here.containsAnActor()) {
                if (here.getActor().hasCapability(statusToCheck)) {
                    egg.hatch(location);
                    break;
                }
            }
        }
    }

}
