package game.watering;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.AutomaticWatering;
import game.statuses.ItemStatus;


/**
 * Abstract class for automatic watering devices.
 */
public abstract class AutomaticWateringDevice extends WateringDevice implements AutomaticWatering {
    protected int range;


    public AutomaticWateringDevice(String name, char displayChar, boolean portable, int range) {
        super(name, displayChar, portable);
        this.range = range;
        this.addCapability(ItemStatus.AUTOWATERING);
    }



    @Override
    public abstract void autoWater(GameMap map, Location location);


    public abstract void refill();
}