package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;

/**
 * A class that represents the floor inside a building.
 * @author Riordan D. Alfredo
 *
 * Modified by kellytran
 */
public class Floor extends Ground {
    /**
     * Constructor for Floor class.
     * The floor is represented by the character '_'.
     */
    public Floor() {
        super('_', "Floor");
    }
}
