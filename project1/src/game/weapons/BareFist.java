package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

/**
 * Class representing an intrinsic weapon called a bare fist.
 * This intrinsic weapon deals 25 damage points with a 50% chance
 * to hit the target.
 * @author Adrian Kristanto
 */
public class BareFist extends IntrinsicWeapon {
    /**
     * Constructor for BareFist class.
     * Sets the damage to 25 and the hit rate to 50%.
     */
    public BareFist() {
        super(25, "punches", 50);
    }
    public BareFist(int damage, String verb, int hitRate) {
        super(damage, verb, hitRate);
    }
}
