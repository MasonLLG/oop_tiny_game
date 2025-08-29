package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.attributes.GameActorAttributes;
import game.watering.Sprinkler;

public class RefillSprinklerAction extends Action {
    private final Sprinkler sprinkler;

    public RefillSprinklerAction(Sprinkler sprinkler) {
        this.sprinkler = sprinkler;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        int runes = actor.getAttribute(GameActorAttributes.RUNE);
        if (runes >= 20) {
            actor.modifyAttribute(GameActorAttributes.RUNE, ActorAttributeOperations.DECREASE, 20);
            sprinkler.refill();
            return actor + " refilled the sprinkler by spending 20 runes.";
        } else {
            return actor + " does not have enough runes to refill the sprinkler.";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " refills the sprinkler (20 Runes)";
    }
}