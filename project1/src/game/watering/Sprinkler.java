package game.watering;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.RefillSprinklerAction;
import game.actions.RefillWateringCanAction;
import game.actors.attributes.GameActorAttributes;
import game.items.Sellable;
import game.statuses.GroundStatus;
import game.statuses.Refillable;
import game.statuses.Waterable;
import game.watering.AutomaticWateringDevice;

public class Sprinkler extends AutomaticWateringDevice implements Sellable {
    private static final int WATER_RANGE = 1;
    private int turnsSinceLastWater = 0;
    private int uses = 10;
    private int maxUses = 10;

    public Sprinkler() {
        super("Sprinkler", 'S', true, WATER_RANGE);
    }

    public int getUsesLeft(){
        return this.uses;
    }

    @Override
    public void autoWater(GameMap map, Location center) {
        int centerX = center.x();
        int centerY = center.y();

        for (int x = centerX - range; x <= centerX + range; x++) {
            for (int y = centerY - range; y <= centerY + range; y++) {
                if (map.getXRange().contains(x) && map.getYRange().contains(y)) {
                    Location target = map.at(x, y);
                    if (target.getGround().hasCapability(GroundStatus.WATERABLE)) {
                        target.getGround().addCapability(GroundStatus.WATERED);
                    }
                }
            }
        }
    }
    @Override
    public void tick(Location location) {
        super.tick(location);
        if (turnsSinceLastWater == 0 && uses > 0) {
            autoWater(location.map(), location);
            uses--;
            System.out.println(location + ": " + this);
        }
        turnsSinceLastWater++;
        if (turnsSinceLastWater >= 5 && uses > 0) {
            autoWater(location.map(), location);
            uses--;
            turnsSinceLastWater = 0;
            System.out.println(location + ": " + this);
        }
    }

    @Override
    public void refill() {
        this.uses = maxUses;
    }

    @Override
    public String toString() {
        int turnsLeft = Math.max(5 - turnsSinceLastWater, 0);
        return String.format("Sprinkler (Water left: %d, Next water in: %d turns)", uses, turnsLeft);
    }


    @Override
    public int getSellPrice() {
        return 200;
    }

    @Override
    public void onSell(Actor p, GameMap g) {
        p.addItemToInventory(this);
    }

    @Override
    public String getName() {
        return "Sprinkler";
    }

    @Override
    public boolean needsRefill() {
        return this.uses < maxUses;
    }

    @Override
    public Action getRefillAction() {
        return new RefillSprinklerAction(this);
    }
}