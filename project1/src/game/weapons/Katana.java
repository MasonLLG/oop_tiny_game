package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.passive.OmenSheep;
import game.behaviours.strategy.PriorityBasedStrategy;
import game.items.Sellable;

/**
 * A Katana weapon sold only by Sellen.
 * Deals 50 damage with a 60% chance to hit.
 * Selling the Katana hurts the player and spawns an {@link OmenSheep} nearby.
 */
public class Katana extends WeaponItem implements Sellable {

    /** The sell price of the Katana (default uninitialized). */
    int price;

    /**
     * Constructs a Katana with fixed damage and hit chance.
     * Deals 50 damage with a 60% hit chance.
     */
    public Katana() {
        super("Katana", 'K', 50, "slashes", 60);
    }

    /**
     * Returns the price this weapon can be sold for.
     *
     * @return the sell price of the Katana
     */
    @Override
    public int getSellPrice() {
        return price;
    }

    /**
     * Called when the player sells the Katana.
     * The actor takes 25 damage, and an {@link OmenSheep} is spawned
     * at the nearest available location.
     *
     * @param p the actor who sold the Katana
     * @param g the game map
     */
    @Override
    public void onSell(Actor p, GameMap g) {
        p.hurt(25);
        OmenSheep omenSheep = new OmenSheep(new PriorityBasedStrategy());
        Location loc = g.locationOf(p);
        // Find the next free tile to the right
        while (g.isAnActorAt(loc)) {
            loc = g.at(loc.x() + 1, loc.y());
        }
        g.addActor(omenSheep, g.at(loc.x(), loc.y()));
    }

    /**
     * Returns the name of the weapon.
     *
     * @return "Katana"
     */
    @Override
    public String getName() {
        return "Katana";
    }

    /**
     * Returns a string representation of the weapon.
     *
     * @return "Katana"
     */
    @Override
    public String toString() {
        return "Katana";
    }
}
