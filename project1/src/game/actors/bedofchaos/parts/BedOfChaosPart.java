package game.actors.bedofchaos.parts;

import game.actors.bedofchaos.BedOfChaos;

/**
 * Interface for parts of the Bed of Chaos boss.
 * Each part can grow when the Bed of Chaos is active.
 * All parts should implement this interface to define their growth behavior.
 *
 * @author Kelly Tran
 */
public interface BedOfChaosPart {
    /**
     * Method to get the attack power of the part.
     * @return int attack power of the part
     */
    int getAttackPower();

    /**
     * Method to trigger the special ability of the part on the Bed of Chaos.
     * @param bedOfChaos - the Bed of Chaos instance that this part belongs to
     */
    void triggerSpecialAbility(BedOfChaos bedOfChaos);
}
