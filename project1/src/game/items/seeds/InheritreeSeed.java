package game.items.seeds;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.plants.InheritreePlant;
import game.grounds.plants.Plant;

/**
 * Class representing an Inheritree seed. Extends the Seed class.
 *
 * @author kellytran
 */
public class InheritreeSeed extends Seed {
    /**
     * Constructor for InheritreeSeed class.
     */
    public InheritreeSeed() {
        super("Inheritree Seed", true, 25);
    }

    /**
     * Method to plant the seed and grow an Inheritree.
     *
     * @param location the location where the seed is planted
     * @return a new instance of Inheritree
     */
    @Override
    public Plant plant(Location location) {
        return new InheritreePlant();
    }
}
