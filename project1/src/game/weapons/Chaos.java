package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Class representing an intrinsic weapon called Chaos.
 * This intrinsic weapon is used by the Bed of Chaos.
 *
 * @author Kelly Tran
 */
public class Chaos extends IntrinsicWeapon {
    /**
     * Constructor for Chaos class.
     */
    public Chaos(int attackPower, int hitRate) {
        super(attackPower, "smacks", hitRate);
    }

    /**
     * Method to update the attack power of the Chaos weapon, when the Bed of Chaos grows.
     */
    public void updateAttackPower(int newAttackPower) {
        this.damage = newAttackPower;
    }
}
