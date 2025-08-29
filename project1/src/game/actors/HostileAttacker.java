package game.actors;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * An interface representing actors that can conditionally attack other actors.
 * Used to define hostile attack logic where aggression depends on specific conditions,
 * such as the target's health, status, or other contextual factors.
 */
public interface HostileAttacker {

    /**
     * Determines whether the hostile actor is allowed to attack the specified target.
     *
     * @param target The potential target actor.
     * @return True if the target can be attacked, false otherwise.
     */
    boolean canAttackTarget(Actor target);
}
