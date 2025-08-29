package game.items.seeds;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.plants.BloodrosePlant;
import game.grounds.plants.Plant;

/**
 * Class representing a Bloodrose seed. Extends the Seed class.
 *
 * @author kellytran
 */
public class BloodroseSeed extends Seed {
    /**
     * Constructor for BloodroseSeed class.
     */
    public BloodroseSeed() {
        super("Bloodrose Seed", true, 75);
    }

    /**
     * Method to plant the seed and grow a Bloodrose.
     *
     * @param location the location where the seed is planted
     * @return a new instance of Bloodrose
     */
    @Override
    public Plant plant(Location location) {
        return new BloodrosePlant();
    }
}
