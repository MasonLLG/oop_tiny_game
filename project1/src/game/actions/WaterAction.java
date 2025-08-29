package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.GroundStatus;
import game.statuses.ManualWatering;
import game.statuses.Waterable;

/**
 * Action for watering plants with a manual watering device.
 */
public class WaterAction extends Action {
    private final Location targetLocation;
    private final ManualWatering tool;

    public WaterAction(Location targetLocation, ManualWatering tool) {
        this.targetLocation = targetLocation;
        this.tool = tool;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (!tool.hasWater()) {
            return actor + " tries to water the plant, but the tool is empty.";
        }

        if (!targetLocation.getGround().hasCapability(GroundStatus.WATERABLE)) {
            return "The ground is no longer waterable.";
        }

        tool.use();
        targetLocation.getGround().addCapability(GroundStatus.WATERED);

        return actor + " waters the plant at " + targetLocation.x() + "," + targetLocation.y() +
                " (" + tool.getUsesLeft() + " uses left)";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " waters the plant at " + targetLocation.x() + "," + targetLocation.y();
    }
}