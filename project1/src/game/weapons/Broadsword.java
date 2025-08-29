package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Sellable;

/**
 * A Broadsword weapon that deals 30 damage with 50% hit chance.
 * It has effects when sold, based on the merchant it was bought from.
 */
public class Broadsword extends WeaponItem implements Sellable {

    /** The price of the broadsword. */
    int price;

    /** The amount to change the attribute by. */
    int change;

    /** The attribute to be affected. */
    BaseActorAttributes attrrib;

    /** Whether to modify the maximum attribute or the current attribute. */
    boolean max;

    /**
     * Creates a Broadsword with special attribute effects.
     *
     * @param price the price of the broadsword
     * @param change the amount to change the attribute
     * @param attrrib the attribute to be affected
     * @param max true to change the max attribute, false to change current
     */
    public Broadsword(int price, int change, BaseActorAttributes attrrib, boolean max) {
        super("Broadsword", 'B', 30, "slashes", 50);
        this.price = price;
        this.change = change;
        this.attrrib = attrrib;
        this.max = max;
    }

    /**
     * Returns the price the broadsword can be sold for.
     *
     * @return price
     */
    @Override
    public int getSellPrice() {
        return price;
    }

    /**
     * Called when the actor sells the broadsword.
     * Modifies actor attributes based on how it was bought.
     *
     * @param p the actor
     * @param g the game map
     */
    @Override
    public void onSell(Actor p, GameMap g) {
        if(max) {
            p.modifyAttributeMaximum(attrrib, ActorAttributeOperations.INCREASE, change);
        }
        else {
            p.modifyAttribute(attrrib, ActorAttributeOperations.INCREASE, change);
        }
    }

    /**
     * Returns the name of the weapon.
     *
     * @return "Broadsword"
     */
    @Override
    public String getName() {
        return "Broadsword";
    }

    /**
     * Returns the string representation of the weapon.
     *
     * @return "Broadsword"
     */
    @Override
    public String toString() {
        return "Broadsword";
    }
}
