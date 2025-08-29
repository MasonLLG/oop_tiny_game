package game.watering;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.ActorStatus;
import game.statuses.GroundStatus;

import java.util.List;

/**
 * Base class for all watering devices.
 */
public abstract class WateringDevice extends Item {
    public WateringDevice(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location) {
        ActionList actions = super.allowableActions(actor, location);
        return actions;
    }

    /**
     * Determines whether the watering device needs a refill.
     */
    public abstract boolean needsRefill();

    /**
     * Returns an Action to refill the watering device.
     */
    public abstract Action getRefillAction();

    public static List<WateringDevice> getDevicesFrom(Actor actor) {
        return actor.getItemInventory().stream()
                .filter(WateringDevice.class::isInstance)
                .map(WateringDevice.class::cast)
                .toList();
    }

}