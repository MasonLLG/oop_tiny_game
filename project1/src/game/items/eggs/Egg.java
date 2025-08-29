package game.items.eggs;


import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.EatAction;
import game.items.eggs.hatching.HatchingStrategy;
import game.statuses.Edible;


/**
 * An abstract class representing a hatchable and edible egg item in the game.
 * <p>
 * Eggs can optionally hatch after satisfying specific conditions defined by a {@link HatchingStrategy},
 * such as remaining on the ground for a set number of turns. The {@code Egg} class also supports being picked up,
 * eaten, or dropped, and modifies actor capabilities accordingly.
 * </p>
 * <p>
 * Subclasses must implement the {@link #hatch(Location)} method to define the behavior upon hatching,
 * such as spawning a creature. The hatching logic itself is delegated to a configurable strategy, allowing for
 * multiple hatching styles (e.g., turn-based, proximity-based, heat-triggered).
 * </p>
 * @author Jichao Liang
 * Modified by kellytran
 */
public abstract class Egg extends Item implements Edible {
    private boolean hasBeenPickedUp = false;
    private final HatchingStrategy hatchingStrategy;

    /**
     * Constructs a new Egg item with a specific hatching behavior strategy.
     *
     * @param name               the name of the egg
     * @param portable           whether the egg is portable by actors
     * @param hatchingStrategy   the strategy that controls how and when the egg hatches
     */
    public Egg(String name, boolean portable, HatchingStrategy hatchingStrategy) {
        super(name, '0', portable);
        this.hatchingStrategy = hatchingStrategy;
    }

    /**
     * Called when the egg hatches.
     * Subclasses must define what is spawned or happens upon hatching.
     *
     * @param location The location at which the egg is hatching.
     */
    public abstract void hatch(Location location);

    /**
     * Returns whether the egg has been picked up by an actor.
     * <p>
     * Eggs that have been picked up typically will not hatch automatically.
     *
     * @return true if the egg has been picked up, false otherwise
     */
    public boolean wasPickedUp() {
        return hasBeenPickedUp;
    }

    /**
     * Called every turn while the egg is on the ground.
     * Delegates to the configured hatching strategy to determine if hatching conditions are met.
     *
     * @param currentLocation The map location where the egg currently resides.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        if (hatchingStrategy != null) {
            hatchingStrategy.tick(currentLocation, this);
        }
    }

    /**
     * Defines the effect of picking up the egg.
     * <p>
     * Marks the egg as picked up (disabling hatching)
     *
     * @param actor The actor who picks up the egg.
     * @return A customized pickup action with post-processing logic.
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return new PickUpAction(this) {
            @Override
            public String execute(Actor actor, GameMap map) {
                hasBeenPickedUp = true;
                return super.execute(actor, map);
            }
        };
    }

    /**
     * Lists available actions an actor can perform on this egg while it is in their inventory.
     * <p>
     * Adds an {@link EatAction} to the list of actions, allowing the actor to consume the egg.
     *
     * @param owner The actor holding the egg.
     * @param map   The current game map.
     * @return A list of possible actions.
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        actions.add(new EatAction(this));
        return actions;
    }
}
