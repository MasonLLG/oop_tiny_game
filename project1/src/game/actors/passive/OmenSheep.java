package game.actors.passive;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.abilities.Ability;
import game.actions.CureAction;
import game.behaviours.*;
import game.behaviours.strategy.BehaviourSelectionStrategy;
import game.items.eggs.OmenSheepEgg;
import game.statuses.Curable;
import game.actors.Reproducible;
import game.statuses.RotStatus;
import game.actors.NonPlayerCharacter;
import game.grounds.plants.InheritreePlant;
import game.statuses.ActorStatus;

/**
 * Class representing the OmenSheep actor in the game.
 * Extends the NonPlayerCharacter abstract class.
 *
 * @author kellytran
 * Modified by Jichao Liang
 */
public class OmenSheep extends NonPlayerCharacter implements Curable, Reproducible {
    RotStatus rot = new RotStatus(15);


    // New constructor with strategy choice
    public OmenSheep(BehaviourSelectionStrategy strategy) {
        super("Omen Sheep", 'm', 75, strategy);
        int reproduceTime = 7;
        addBehaviour(new WanderBehaviour(), BehaviourPriority.WANDER);
        addBehaviour(new ReproduceBehaviour(this, 0, reproduceTime), BehaviourPriority.REPRODUCE);
        addStatusEffect(rot);
        addCapability(ActorStatus.HOSTILE_TO_ENEMY);
    }


    /**
     * Cures the OmenSheep by setting the surrounding ground to InheritreePlants.
     * This method is called when the actor is cured by another actor.
     *
     * @param curer the actor performing the cure
     * @param map   the game map
     */
    @Override
    public void cure(Actor curer, GameMap map) {
        Location here = map.locationOf(this);
        for (Exit exit : here.getExits()) {
            exit.getDestination().setGround(new InheritreePlant());
        }
    }

    /**
     * Defines the reproduction behavior of the OmenSheep.
     * <p>
     * When this method is called, it drops a new {@link game.items.eggs.OmenSheepEgg}
     * at the OmenSheep's current location on the map. This simulates the OmenSheep laying an egg.
     * </p>
     *
     * @param actor The actor performing the reproduction (typically the OmenSheep itself).
     * @param map   The game map on which the actor exists.
     */
    @Override
    public void reproduce(Actor actor, GameMap map) {
        map.locationOf(actor).addItem(new OmenSheepEgg());
    }

    /**
     * Handles automatic reproduction logic for the OmenSheep.
     * There is no special condition for reproduction, so it always returns true.
     *
     * @param map The game map the OmenSheep is currently on.
     * @return true - reproduce condition is turn based (handled in ReproduceBehaviour)
     */
    @Override
    public boolean checkReproduceCondition(GameMap map) {
        return true;

    }


    /**
     * Provides a list of actions that the actor can perform.
     * If the actor has the CURER ability, it can also perform a cure action on the OmenSheep.
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
