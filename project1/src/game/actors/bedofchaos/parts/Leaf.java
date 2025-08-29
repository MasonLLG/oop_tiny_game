package game.actors.bedofchaos.parts;

import game.actors.bedofchaos.BedOfChaos;

/**
 * Leaf class represents a part of the Bed of Chaos boss.
 * It has a base attack power and a healing ability.
 * Implements the BedOfChaosPart interface.
 * Cannot grow other parts.
 *
 * @author Kelly Tran
 */
public class Leaf implements BedOfChaosPart {
    private final int baseAttackPower = 1;
    private final int healAmount = 5;

    /**
     * Gets the attack power of the leaf.
     *
     * @return int baseAttackPower the attack power
     */
    @Override
    public int getAttackPower() {
        return baseAttackPower;
    }

    /**
     * Triggers the special ability of the leaf - it heals the Bed of Chaos.
     */
    @Override
    public void triggerSpecialAbility(BedOfChaos bedOfChaos) {
        bedOfChaos.heal(healAmount);
    }



}