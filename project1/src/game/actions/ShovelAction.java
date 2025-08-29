package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.ActorStatus;
import game.statuses.Shovelable;

public class ShovelAction extends Action {
    private final Shovelable target;
    private final Location location;

    public ShovelAction(Shovelable target, Location location) {
        this.target = target;
        this.location = location;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (!actor.hasCapability(ActorStatus.PLAYER)) {
            return actor + " is not a farmer and cannot shovel.";
        }

        if (actor.getAttribute(BaseActorAttributes.STAMINA) < 20) {
            return actor + " does not have enough stamina to shovel.";
        }

        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, 20);
        return actor + " shovels the ground. " + target.onShovel(actor, map, location);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " shovels the cursed ground";
    }
}