package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.util.FancyMessage;
import game.statuses.ActorStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * An action that handles the death of an actor.
 * This action is triggered when an actor is no longer conscious
 * It removes the actor from the game map and drops their items
 *
 * @author kellytran
 */
public class DeathAction extends Action {
    Actor attacker;


    /**
     * Constructor for DeathAction for when the actor has an attacker
     *
     * @param attacker the actor that caused the death
     */
    public DeathAction(Actor attacker) {
        this.attacker = attacker;
    }

    /**
     * Constructor for DeathAction for when the actor does not have an attacker
     */
    public DeathAction() {}


    /**
     * Executes the death action on the target actor, drops their items. I
     * If the target is the player, terminate the game.
     *
     * @param target The actor dying.
     * @param map The map the actor is on.
     * @return A string describing the result of the death.
     */
    @Override
    public String execute(Actor target, GameMap map) {
        String message;
        List<Item> toDrop = new ArrayList<>( target.getItemInventory() );
        for (Item item : toDrop) {
            map.locationOf(target).addItem(item);
            target.removeItemFromInventory(item);
        }

        if (attacker != null) {
            message = target.unconscious(attacker, map);
        } else {
            message = target.unconscious(map);
        }

        // If the target that's dying is the player, print the death message and end the game
        if (target.hasCapability(ActorStatus.PLAYER)) {
            for (String line : FancyMessage.YOU_DIED.split("\n")) {
                new Display().println(line);
                try {
                    Thread.sleep(200);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            System.exit(0);
        }
        return message;
    }


    /**
     * Provides a description of the death action to be displayed in the menu - is redundant as DeathAction can never be
     * chosen in the menu
     *
     * @param actor the actor performing the death action
     * @return a string describing the death action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " died.";
    }
}
