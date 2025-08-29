package game.actors.bedofchaos.parts;

import game.actors.bedofchaos.BedOfChaos;
import game.statuses.Growable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Branch class represents a part of the Bed of Chaos boss.
 * It can grow new branches and has an attack power.
 * Implements the BedOfChaosPart interface.
 *
 * @author Kelly Tran
 */
public class Branch implements GrowablePart {
    private final List<BedOfChaosPart> children = new ArrayList<>();
    private final List<GrowablePart> growableParts = new ArrayList<>();
    private final Random random = new Random();
    private boolean canGrow = true;

    /**
     * Gets the attack power of the branch.
     *
     * @return the attack power
     */
    @Override
    public int getAttackPower() {
        int attackPower = 3;

        for (BedOfChaosPart child : children) {
            attackPower += child.getAttackPower();
        }

        for (GrowablePart growablePart : growableParts) {
            attackPower += growablePart.getAttackPower();
        }
        return attackPower;
    }

    /**
     * Checks if the branch can grow.
     * A branch can grow if there is no leaf
     *
     * @return true if the branch can grow, false otherwise
     */
    @Override
    public boolean canGrow() {
        return canGrow;
    }

    /**
     * Grows a new branch or leaf based on a random chance.
     *
     * @return String a message indicating the growth of the branch or leaf
     */
    @Override
    public String grow() {
        StringBuilder result = new StringBuilder();
        final int branchGrowthChance = 50;
        // Check that other child branches can grow
        for (GrowablePart growablePart : growableParts) {
            result.append(growablePart.grow());
        }
        if (!this.canGrow()) {
            return "";
        }
        result.append(this.getClass().getSimpleName()).append(" is growing...\n");

        if (random.nextInt(100) < branchGrowthChance) {
            Branch newBranch = new Branch();
            growableParts.add(newBranch);
            result.append("it grows a ").append(newBranch.getClass().getSimpleName()).append("...\n\n");
            result.append(newBranch.grow());
        } else {
            Leaf leaf = new Leaf();
            children.add(leaf);
            result.append("it grows a ").append(leaf.getClass().getSimpleName()).append("...\n\n");
            canGrow = false;
        }

        return result.toString();
    }

    /**
     * Ensures the ability of this branch is called for this branch and all its children.
     * Also attempts to grow the branch, if no leaf is attached.
     *
     * @return int the result of the special ability trigger
     */
    @Override
    public void triggerSpecialAbility(BedOfChaos bedOfChaos) {
        for (BedOfChaosPart child : children) {
            child.triggerSpecialAbility(bedOfChaos);
        }
        for (GrowablePart growablePart : growableParts) {
            growablePart.triggerSpecialAbility(bedOfChaos);
        }
    }



}
