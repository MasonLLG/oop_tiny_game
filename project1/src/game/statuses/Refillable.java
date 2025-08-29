package game.statuses;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;

public interface Refillable {
    boolean needsRefill();
    boolean canRefill(Actor actor);
    Action getRefillAction();
}
