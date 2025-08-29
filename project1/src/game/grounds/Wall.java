package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;

/**
 * A class representing a wall that cannot be entered by any actor
 * @author Riordan D. Alfredo
 *
 * Modified by kellytran
 */
public class Wall extends Ground {

    /**
     * Constructor for Wall class.
     */
    public Wall() {
        super('#', "Wall");
    }

    /**
     * This method is overridden to prevent any actor from entering the wall.
     * @param actor the actor trying to enter the wall
     * @return false, as no actor can enter a wall
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
