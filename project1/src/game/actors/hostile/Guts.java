// game.actors.hostile.Guts.java
package game.actors.hostile;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ListenAction;
import game.actors.HostileAttacker;
import game.actors.NonPlayerCharacter;
import game.behaviours.BehaviourPriority;
import game.behaviours.AttackBehaviour;
import game.behaviours.WanderBehaviour;
import game.statuses.ActorStatus;
import game.weapons.BareFist;

import java.util.List;
import java.util.Random;

/**
 * Guts is a hostile NPC who attacks strong players and delivers dynamic monologues based on the player's health.
 * If the player is healthy (above a predefined health threshold), Guts becomes aggressive and may attack.
 * Otherwise, he mocks the player with a taunt. His dialogue is accessed through the {@link ListenAction}.
 */
public class Guts extends NonPlayerCharacter implements HostileAttacker {

    /**
     * Health threshold below which the player is considered "weak" by Guts.
     */
    private static final int HEALTH_THRESHOLD = 50;
    /**
     * Monologue when the target is considered weak.
     */
    private static final String WEAK_QUOTE = "WEAK! TOO WEAK TO FIGHT ME!";
    /**
     * Monologue pool when the target is considered strong.
     */
    private static final List<String> STRONG_QUOTES = List.of(
            "RAAAAGH!",
            "I’LL CRUSH YOU ALL!"
    );

    /**
     * Random number generator for selecting monologue lines.
     */
    private final Random rand = new Random();

    /**
     * Constructs a Guts instance with predefined behaviours and stats.
     * Guts is an aggressive NPC with strong melee attacks and dynamic dialogue logic.
     */
    public Guts() {
        super("Guts", 'g', 500);
        this.addBehaviour(new WanderBehaviour(), BehaviourPriority.WANDER);
        this.addBehaviour(new AttackBehaviour(this), BehaviourPriority.ATTACK);
        this.addCapability(ActorStatus.HOSTILE_TO_ENEMY);
        this.setIntrinsicWeapon(new BareFist(25, "punches furiously", 50));
    }

    /**
     * Returns the list of allowable actions for an interacting actor
     * If the actor is the player, a {@link ListenAction} is added based on the player's health.
     *
     * @param otherActor The actor interacting with Guts.
     * @param direction  The direction of the actor.
     * @param map        The current game map.
     * @return List of actions the other actor can perform.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        if (otherActor.hasCapability(ActorStatus.PLAYER)) {
            actions.add(new ListenAction(this, List.of(generateMonologueFor(otherActor))));
        }
        return actions;
    }

    /**
     * Generates a monologue line depending on the target actor's health.
     *
     * @param target The target actor (usually the player).
     * @return A monologue string selected based on health.
     */
    private String generateMonologueFor(Actor target) {
        return isTargetHealthy(target) ?
                STRONG_QUOTES.get(rand.nextInt(STRONG_QUOTES.size())) :
                WEAK_QUOTE;
    }

    /**
     * Determines if the target is considered healthy.
     *
     * @param target The actor to evaluate.
     * @return True if target health > threshold.
     */
    private boolean isTargetHealthy(Actor target) {
        return target.getAttribute(BaseActorAttributes.HEALTH) > HEALTH_THRESHOLD;
    }

    /**
     * Guts will only attack targets who are healthy and conscious.
     *
     * @param target The actor being evaluated as a target.
     * @return True if Guts considers the target worth attacking.
     */
    @Override
    public boolean canAttackTarget(Actor target) {
        return target.isConscious() && isTargetHealthy(target);
    }
}