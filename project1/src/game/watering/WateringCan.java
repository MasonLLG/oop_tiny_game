package game.watering;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.RefillSprinklerAction;
import game.actions.RefillWateringCanAction;
import game.actions.WaterAction;
import game.actors.attributes.GameActorAttributes;
import game.items.Sellable;
import game.statuses.ActorStatus;
import game.statuses.GroundStatus;
import game.statuses.Refillable;

public class WateringCan extends ManualWateringDevice implements Sellable {
    private static final int MAX_USES = 5;

    public WateringCan() {
        super("Watering Can", '~', true, MAX_USES);
    }

    @Override
    public int getSellPrice() {
        return 50;
    }

    @Override
    public void onSell(Actor p, GameMap g) {
        p.addItemToInventory(this);
    }

    @Override
    public String getName() {
        return "Watering Can";
    }

    @Override
    public boolean needsRefill() {
        return this.uses < maxUses;
    }

    @Override
    public Action getRefillAction() {
        return new RefillWateringCanAction(this);
    }

}