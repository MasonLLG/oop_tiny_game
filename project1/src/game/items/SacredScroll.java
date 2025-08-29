package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.ActorStatus;

/**
 * Represents a sacred scroll item in the game.
 *
 * This item can be picked up and carried by actors.
 * It is displayed on the map using the character 's'.
 */
public class SacredScroll extends Item {

    /**
     * Creates a new SacredScroll item with the name "Sacred scroll",
     * display character 's', and marked as portable.
     */
    public SacredScroll() {
        super("Sacred scroll", 's', true);
    }

    /**
     * Adds the CAN_SPEAK_ORACLE capability to the actor when the SacredScroll is picked up.
     * @param actor the Actor that can pick up the item
     * @return PickUpAction pick up action for the actor
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return new PickUpAction(this) {
            @Override
            public String execute(Actor actor, GameMap map) {
                actor.addCapability(ActorStatus.CAN_SPEAK_ORACLE);
                return super.execute(actor, map);
            }
        };
    }

    /**
     * Removes the CAN_SPEAK_ORACLE capability from the actor when the SacredScroll is dropped.
     * @param actor the Actor that can drop the item
     * @return DropAction drop action for the actor
     */
    @Override
    public DropAction getDropAction(Actor actor) {
        return new DropAction(this) {
            @Override
            public String execute(Actor actor, GameMap map) {
                actor.removeCapability(ActorStatus.CAN_SPEAK_ORACLE);
                return super.execute(actor, map);
            }
        };
    }

}
