package game.statuses;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public interface AutomaticWatering {
    void autoWater(GameMap map, Location location);
}
