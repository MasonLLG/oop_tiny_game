package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.attributes.GameActorAttributes;
import game.statuses.ActorStatus;
import game.watering.ManualWateringDevice;

/**
 * Action to refill a watering can by spending 10 runes.
 */
public class RefillWateringCanAction extends Action {
    private final ManualWateringDevice wateringCan;

    public RefillWateringCanAction(ManualWateringDevice wateringCan) {
        this.wateringCan = wateringCan;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        int runes = actor.getAttribute(GameActorAttributes.RUNE);
        if (runes >= 10) {
            actor.modifyAttribute(GameActorAttributes.RUNE, ActorAttributeOperations.DECREASE, 10);
            wateringCan.refill();
            return actor + " refilled the watering can by spending 10 runes.";
        } else {
            return actor + " does not have enough runes to refill the watering can.";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " refills the watering can (10 Runes)";
    }
}