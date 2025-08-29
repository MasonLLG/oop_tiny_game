package game.watering;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.RefillWateringCanAction;
import game.actions.WaterAction;
import game.statuses.ActorStatus;
import game.statuses.GroundStatus;
import game.statuses.ItemStatus;
import game.statuses.ManualWatering;

/**
 * Abstract class for manual watering devices.
 */
public abstract class ManualWateringDevice extends WateringDevice implements ManualWatering {
    protected int uses;
    protected final int maxUses;

    public ManualWateringDevice(String name, char displayChar, boolean portable, int maxUses) {
        super(name, displayChar, portable);
        this.maxUses = maxUses;
        this.uses = maxUses;
        this.addCapability(ItemStatus.MANUALWATERING);
    }


    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        Location currentLocation = map.locationOf(owner);

        for (Exit exit : currentLocation.getExits()) {
            Location adjacent = exit.getDestination();
            if (adjacent.getGround().hasCapability(GroundStatus.WATERABLE)) {
                actions.add(new WaterAction(adjacent, this));
            }
        }

        for (Exit exit : currentLocation.getExits()) {
            Actor adjacentActor = exit.getDestination().getActor();
            if (adjacentActor != null && adjacentActor.hasCapability(ActorStatus.CONSUMABLE)) {
                actions.add(new RefillWateringCanAction(this));
                break;
            }
        }

        return actions;
    }

    @Override
    public boolean hasWater() {
        return uses > 0;
    }

    @Override
    public void use() {
        if (uses > 0) uses--;
    }

    @Override
    public int getUsesLeft() {
        return uses;
    }

    @Override
    public void refill() {
        this.uses = maxUses;
    }

    @Override
    public String toString() {
        return String.format("%s (%d/%d)", super.toString(), uses, maxUses);
    }
}