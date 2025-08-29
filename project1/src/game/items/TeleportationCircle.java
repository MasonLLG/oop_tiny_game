
package game.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A class representing a teleportation circle item in the game.
 * This item can have sample actions added to it, which can be performed when the item is used.
 * It extends the Item class and overrides the allowableActions method to include these sample actions.
 *
 * @author Jichao Liang
 */
public class TeleportationCircle extends Item {
    private final ActionList sampleActions;

    /**
     * Constructor.
     * @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public TeleportationCircle(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
        this.sampleActions = new ActionList();
    }

    /**
     * Adds a sample action to the teleportation circle.
     * This allows the circle to have predefined actions that can be performed when it is used.
     *
     * @param newAction the action to be added to the teleportation circle
     */
    public void addSampleAction(Action newAction) {
        sampleActions.add(newAction);
    }

    /**
     * Returns a list of actions that can be performed at the location of this item.
     * This method overrides the allowableActions method to include sample actions.
     *
     * @param location the location where the item is present
     * @return an ActionList containing the allowable actions at this location
     */
    @Override
    public ActionList allowableActions(Location location) {
        ActionList actions = super.allowableActions(location);
        for (Action action : sampleActions) {
            actions.add(action);
        }
        return actions;
    }
}
