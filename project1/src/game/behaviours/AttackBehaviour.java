package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.actors.HostileAttacker;
import game.statuses.ActorStatus;

/**
 * A behaviour that allows actors to attack the player or any other hostile actor.
 * Implements the Behaviour interface.
 *
 * @author kellytran
 */
public class AttackBehaviour implements Behaviour {
    private final HostileAttacker attacker;
    public AttackBehaviour(HostileAttacker attacker) {
        this.attacker = attacker;
    }

    /**
     * Get an action that attacks a target in an adjacent location
     * @param actor the Actor with the behaviour
     * @param map the GameMap containing the Actor
     * @return the attacking Action on the target Actor
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (!destination.containsAnActor()) continue;

            Actor target = destination.getActor();

            if (actor.hasCapability(ActorStatus.HOSTILE_TO_ENEMY) && attacker.canAttackTarget(target)) {
                return new AttackAction(target, exit.getName());
            }
        }

        return null;
    }
}