package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.passive.GoldenBeetle;
import game.behaviours.strategy.PriorityBasedStrategy;
import game.items.Sellable;

import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * A Dragonslayer Greatsword weapon that deals 70 damage with a 75% hit chance.
 * Selling this sword gives attribute boosts and may spawn a Golden Beetle depending on the attribute.
 */
public class DragonslayerGreatsword extends WeaponItem implements Sellable {

    /** The price at which the weapon can be sold. */
    int price;

    /** The amount to increase the attribute by. */
    int by;

    /** The attribute to be increased on sell. */
    BaseActorAttributes attrrib;

    /**
     * Constructor for the Dragonslayer Greatsword.
     *
     * @param price   the selling price
     * @param attrrib the attribute to increase
     * @param by      the amount to increase the attribute by
     */
    public DragonslayerGreatsword(int price, BaseActorAttributes attrrib, int by) {
        super("Dragonslayer Greatsword", 'D', 70, "slashes", 75);
        this.price = price;
        this.attrrib = attrrib;
        this.by = by;
    }

    /**
     * Returns the price this weapon can be sold for.
     *
     * @return the sell price
     */
    @Override
    public int getSellPrice() {
        return this.price;
    }

    /**
     * Called when the player sells the weapon.
     * Increases actor's maximum health by 15, and increases a custom attribute.
     * If the attribute is STAMINA, heals the actor by 5 and spawns a Golden Beetle near the player.
     *
     * @param p the actor selling the weapon
     * @param g the game map
     */
    @Override
    public void onSell(Actor p, GameMap g) {
        p.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, 15);
        p.modifyAttribute(attrrib, ActorAttributeOperations.INCREASE, by);
        if (this.attrrib == STAMINA) {
            p.heal(5);
            Location loc = g.locationOf(p);
            // Try finding an empty square to the right
            while (g.isAnActorAt(loc)) {
                loc = g.at(loc.x() + 1, loc.y());
            }
            g.addActor(new GoldenBeetle(new PriorityBasedStrategy()), g.at(loc.x(), loc.y()));
        }
    }

    /**
     * Returns the name of the weapon.
     *
     * @return "DemonslayerGreatsword"
     */
    @Override
    public String getName() {
        return "DemonslayerGreatsword";
    }

    /**
     * Returns a string representation of the weapon.
     *
     * @return "DemonslayerGreatsword"
     */
    @Override
    public String toString() {
        return "DemonslayerGreatsword";
    }
}
