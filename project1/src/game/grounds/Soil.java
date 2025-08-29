package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;
import game.statuses.GroundStatus;

/**
 * Class representing soil in the game.
 * This class extends the Ground class
 *
 * @author Riordan D. Alfredo
 * Modified by kellytran
 */
public class Soil extends Ground {

    /**
     * Constructor for Soil class.
     * Is considered as a PLANTABLE ground.
     */
    public Soil() {
        super('.', "Soil");
        this.addCapability(GroundStatus.PLANTABLE);
    }

}