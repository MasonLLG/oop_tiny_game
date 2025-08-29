package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import game.abilities.Ability;

/**
 * A class representing a Talisman that an actor can pick up and drop
 * @author Adrian Kristanto
 * Modified by kellytran
 */
public class Talisman extends Item {
    /**
     * Constructor for Talisman class.
     * The Talisman is a special item that grants the CURER ability to the actor.
     */
    public Talisman() {
        super("Talisman", 'o', true);
    }

    /**
     * Adds the CURER ability to the actor when the Talisman is picked up.
     * @param actor the Actor that can pick up the item
     * @return the picking up Action on the item
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return new PickUpAction(this) {
            @Override
            public String execute(Actor actor, GameMap map) {
                actor.addCapability(Ability.CURER);
                return super.execute(actor, map);
            }
        };
    }

    /**
     * Removes the CURER ability to the actor when the Talisman is dropped.
     * @param actor the Actor that can drop the item
     * @return the drop Action on the item
     */
    @Override
    public DropAction getDropAction(Actor actor) {
        return new DropAction(this) {
            @Override
            public String execute(Actor actor, GameMap map) {
                actor.removeCapability(Ability.CURER);
                return super.execute(actor, map);
            }
        };
    }
}
