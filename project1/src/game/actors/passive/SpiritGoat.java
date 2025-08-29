package game.actors.passive;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.abilities.Ability;
import game.actions.CureAction;
import game.actors.Reproducible;
import game.behaviours.*;
import game.behaviours.strategy.BehaviourSelectionStrategy;
import game.behaviours.strategy.PriorityBasedStrategy;
import game.statuses.*;
import game.actors.NonPlayerCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class representing a Spirit Goat in the game.
 * The Spirit Goat is a non-player character that wanders around the map and rots over time.
 *
 * @author kellytran
 * Modified by Jichao Liang
 */
public class SpiritGoat extends NonPlayerCharacter implements Curable, Reproducible {
    RotStatus rot = new RotStatus(10);

    private final Random random = new Random();


    public SpiritGoat(BehaviourSelectionStrategy strategy) {
        super("Spirit Goat", 'y', 50, strategy);
        addBehaviour(new WanderBehaviour(), BehaviourPriority.WANDER);
        addBehaviour(new ReproduceBehaviour(this, 0), BehaviourPriority.REPRODUCE);
        addStatusEffect(rot);
        addCapability(ActorStatus.HOSTILE_TO_ENEMY);
    }


    /**
     * Cures the Spirit Goat by resetting its rot tick.
     * This method is called when the actor is cured by another actor.
     *
     * @param curer the actor performing the cure
     * @param map   the game map
     */
    @Override
    public void cure(Actor curer, GameMap map) {
        rot.resetRot();
    }



    /**
     * Defines the reproduction behavior of the Spirit Goat.
     * <p>
     * This method attempts to spawn a new {@link SpiritGoat} in a randomly chosen adjacent location,
     * as long as that location does not already contain another actor.
     * </p>
     *
     * @param actor The actor initiating the reproduction (typically the Spirit Goat itself).
     * @param map   The game map where the Spirit Goat resides.
     */
    @Override
    public void reproduce(Actor actor, GameMap map) {
        Location currentLocation = map.locationOf(actor);
        List<Exit> exits = currentLocation.getExits();

        // Find all adjacent locations that are empty AND have blessed ground
        List<Location> validLocations = new ArrayList<>();
        for (Exit exit : exits) {
            Location adjacent = exit.getDestination();
            if (!adjacent.containsAnActor()) {
                validLocations.add(adjacent);
            }
        }

        // Randomly spawn a new goat in one of those locations if any exist
        if (!validLocations.isEmpty()) {
            Location spawnLocation = validLocations.get(random.nextInt(validLocations.size()));
            map.addActor(new SpiritGoat(new PriorityBasedStrategy()), spawnLocation);
        }
    }

    /**
     * Automatically triggers reproduction for the Spirit Goat when specific environmental conditions are met.
     * <p>
     * This method is invoked at the start of the actor’s turn. It checks the surrounding tiles
     * and triggers reproduction if at least one adjacent location has ground with the
     * {@link game.statuses.EntityStatus#BLESSED_BY_GRACE} capability, returning true to allow reproduction in ReproduceBehaviour.
     * </p>
     *
     * @param map The game map on which the Spirit Goat is currently located.
     */
    @Override
    public boolean checkReproduceCondition(GameMap map) {
        Location currentLocation = map.locationOf(this);

        for (Exit exit : currentLocation.getExits()) {
            Location adjacent = exit.getDestination();
            if (adjacent.getGround().hasCapability(EntityStatus.BLESSED_BY_GRACE)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Provides a list of actions that the actor can perform.
     * If the actor has the CURER ability, it can also perform a cure action on the Spirit Goat.
     *
     * @param actor    the actor performing the action
     * @param direction the direction of the action
     * @param map      the game map
     * @return a list of actions that the actor can perform
     */
    @Override
    public ActionList allowableActions(Actor actor, String direction, GameMap map) {
        ActionList list = super.allowableActions(actor, direction, map);
        if (actor.hasCapability(Ability.CURER)) {
            list.add(new CureAction(this, 0, direction));
        }
        return list;
    }
}
