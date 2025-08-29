package game.actors.bedofchaos;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.HostileAttacker;
import game.actors.NonPlayerCharacter;
import game.actors.bedofchaos.parts.BedOfChaosPart;
import game.actors.bedofchaos.parts.Branch;
import game.actors.bedofchaos.parts.GrowablePart;
import game.actors.bedofchaos.parts.Leaf;
import game.behaviours.AttackBehaviour;
import game.behaviours.BehaviourPriority;
import game.behaviours.GrowBehaviour;
import game.statuses.ActorStatus;
import game.statuses.Growable;
import game.weapons.Chaos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the Bed of Chaos Boss, a hostile non-player character in the game.
 * It has the ability to grow branches and attack players.
 * Implements HostileAttacker to define its attacking behavior.
 *
 * @author Kelly Tran
 */
public class BedOfChaos extends NonPlayerCharacter implements HostileAttacker, Growable {
    private final int baseAttackPower = 25;
    private final int attackAccuracy = 75;
    private final Chaos chaosWeapon = new Chaos(baseAttackPower, attackAccuracy);
    private final Random random = new Random();
    private final List<BedOfChaosPart> roots = new ArrayList<>();
    private final List<GrowablePart> growableParts = new ArrayList<>();
    private final Location location;


    /**
     * Constructor for the Bed of Chaos.
     * Initializes the Bed of Chaos with a name, display character, and hit points.
     * Sets up its attack behavior and intrinsic weapon.
     */
    public BedOfChaos(Location location) {
        super("Bed of Chaos", 'T', 1000);
        this.location = location;
        this.addBehaviour(new AttackBehaviour(this), BehaviourPriority.ATTACK);
        this.addBehaviour(new GrowBehaviour(this), BehaviourPriority.GROW);
        this.addCapability(ActorStatus.HOSTILE_TO_ENEMY);
        this.setIntrinsicWeapon(chaosWeapon);
    }

    /**
     * Gets the attack power of the Bed of Chaos.
     * This includes the base attack power and the attack power from its root branch(es).
     *
     * @return int total attack damage
     */
    public int getTotalAttackDamage() {
        int total = baseAttackPower;
        for (BedOfChaosPart root : roots) {
            total += root.getAttackPower();
        }
         for (GrowablePart growablePart : growableParts) {
            total += growablePart.getAttackPower();
        }
        return total;
    }

    /**
     * Grows the Bed of Chaos by growing its root branch.
     * If the root branch does not exist, it initializes it.
     * Updates the attack power of the chaos weapon after growing.
     */
    @Override
    public String grow() {
        int oldHP = this.getAttribute(BaseActorAttributes.HEALTH);
        StringBuilder result = new StringBuilder("\n");
        final int branchGrowthChance = 50;
        if (random.nextInt(100) < branchGrowthChance) {
            Branch newRoot = new Branch();
            result.append("it grows a ").append(newRoot.getClass().getSimpleName()).append("...\n\n");
            growableParts.add(newRoot);
        } else {
            Leaf newRoot = new Leaf();
            result.append("it grows a ").append(newRoot.getClass().getSimpleName()).append("...\n\n");
            roots.add(newRoot);
        }

        for (GrowablePart growablePart : growableParts) {
            result.append(growablePart.grow());
            growablePart.triggerSpecialAbility(this);
        }

        for (BedOfChaosPart root : roots) {
            root.triggerSpecialAbility(this);
        }

        if (this.getAttribute(BaseActorAttributes.HEALTH) > oldHP) {
            result.append(this).append(" is healed\n");
        }
        chaosWeapon.updateAttackPower(getTotalAttackDamage());
        return result.toString();
    }


        /**
         * Checks if the Bed of Chaos is able to grow, depending on if the player is nearby.
         * @return boolean true if the Bed of Chaos can grow, false otherwise
         */
    @Override
    public boolean canGrow() {
        for (Exit exit : this.location.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor target = destination.getActor();
                if (target.hasCapability(ActorStatus.PLAYER)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Checks if the Bed of Chaos can attack a target actor, which is the player or any HOSTILE_TO_ENEMY actor.
     * @param target the actor to check
     *
     * @return boolean true if the target is a player or HOSTILE_TO_ENEMY, false otherwise
     */
    @Override
    public boolean canAttackTarget(Actor target) {
        return target.hasCapability(ActorStatus.PLAYER) || target.hasCapability(ActorStatus.HOSTILE_TO_ENEMY);
    }

}
