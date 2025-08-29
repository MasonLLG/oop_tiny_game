package game.actors.passive;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.EatAction;
import game.actors.NonPlayerCharacter;
import game.actors.attributes.GameActorAttributes;
import game.behaviours.BehaviourPriority;
import game.behaviours.FollowBehaviour;
import game.behaviours.ReproduceBehaviour;
import game.behaviours.WanderBehaviour;
import game.behaviours.*;
import game.behaviours.strategy.BehaviourSelectionStrategy;
import game.items.eggs.GoldenEgg;
import game.statuses.ActorStatus;
import game.statuses.Edible;
import game.actors.Reproducible;

/**
 * Class representing the GoldenBeetle actor in the game.
 * Extends the NonPlayerCharacter abstract class.
 * @author kellytran
 */
public class GoldenBeetle extends NonPlayerCharacter implements Reproducible, Edible {
    /**
     * Constructor for GoldenBeetle
     */
    public GoldenBeetle(BehaviourSelectionStrategy strategy) {
        super("Golden Beetle", 'b', 25, strategy);
        int reproduceTime = 5;
        addBehaviour(new WanderBehaviour(), BehaviourPriority.WANDER);
        addBehaviour(new ReproduceBehaviour(this, 0, reproduceTime), BehaviourPriority.REPRODUCE);
        addBehaviour(new FollowBehaviour(), BehaviourPriority.FOLLOW);
        addCapability(ActorStatus.HOSTILE_TO_ENEMY);
        addCapability(ActorStatus.CONSUMABLE);
    }

    /**
     * Defines the reproduction behavior of the GoldenBeetle.
     * Adds a GoldenEgg item to the map at the actor's current location.
     */
    @Override
    public void reproduce(Actor actor, GameMap map) {
        map.locationOf(actor).addItem(new GoldenEgg());
    }

    /**
     * Checks if the GoldenBeetle can reproduce.
     * @param map - the game map
     * @return true - reproduce condition is turn based (handled in ReproduceBehaviour)
     */
    @Override
    public boolean checkReproduceCondition(GameMap map) {
        return true;
    }

    /**
     * Defines the effect when the GoldenBeetle is eaten.
     * Increases the Farmer's health by 15 and grants them 1000 runes.
     *
     * @param actor The actor consuming the beetle.
     * @param map   The map the actor is on.
     */
    @Override
    public void eat(Actor actor, GameMap map) {
        actor.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15);
        actor.modifyAttribute(GameActorAttributes.RUNE, ActorAttributeOperations.INCREASE, 1000);
        map.removeActor(this);
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        actions.add(new EatAction(this));
        return actions;
    }


}
