package game.actors.passive;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.*;
import game.actors.NonPlayerCharacter;
import game.actors.attributes.GameActorAttributes;
import game.behaviours.BehaviourPriority;
import game.behaviours.WanderBehaviour;
import game.items.Sellable;
import game.items.fruits.PlantFruit;
import game.statuses.*;
import game.watering.Sprinkler;
import game.watering.WateringCan;
import game.watering.WateringDevice;
import game.weapons.Broadsword;
import game.weapons.DragonslayerGreatsword;

import java.util.List;

import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * Merchant Kale is a passive NPC who offers weapons for purchase and delivers context-aware monologues
 * based on the player's status and nearby environmental conditions.
 * If the player has sufficient runes, Kale will offer specific weapons for sale.
 * He also responds with unique dialogue depending on whether the player is surrounded by blight,
 * has no items, or has limited resources.
 */
public class Kale extends NonPlayerCharacter {


    /**
     * Constructs a Merchant Kale instance with wandering behaviour and 200 HP.
     */
    public Kale() {
        super("Merchant Kale", 'k', 200);
        this.addBehaviour(new WanderBehaviour(), BehaviourPriority.WANDER);
        this.addCapability(ActorStatus.CONSUMABLE);
    }

    /**
     * Determines the actions available to the interacting actor.
     * If the interacting actor is a player, Kale will:
     * <ul>
     *   <li>Deliver a monologue based on the player's current context</li>
     *   <li>Offer weapons for sale based on the player's rune count</li>
     * </ul>
     *
     * @param otherActor The actor interacting with Kale.
     * @param direction  The direction from which the actor approaches.
     * @param map        The current game map.
     * @return An ActionList containing ListenAction and possible BuyActions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);

        if (otherActor.hasCapability(ActorStatus.PLAYER)) {
            Location here = map.locationOf(this);
            String monologue = generateMonologue(otherActor, here);
            actions.add(new ListenAction(this, List.of(monologue)));
            if(otherActor.getAttribute(GameActorAttributes.RUNE) >= 150)
            {
                actions.add(new BuyAction(new Broadsword(150, 30, STAMINA, true)));
            }
            if(otherActor.getAttribute(GameActorAttributes.RUNE) >= 1700)
            {
                actions.add(new BuyAction(new DragonslayerGreatsword(1700, STAMINA, 20)));
            }
            if (otherActor.getAttribute(GameActorAttributes.RUNE) >= 50) {
                actions.add(new BuyAction(new WateringCan()));
            }
            if (otherActor.getAttribute(GameActorAttributes.RUNE) >= 200) {
                actions.add(new BuyAction(new Sprinkler()));
            }
            for (WateringDevice device : WateringDevice.getDevicesFrom(otherActor)) {
                if (device.needsRefill()) {
                    actions.add(device.getRefillAction());
                }
            }
        }
        for (PlantFruit fruit : PlantFruit.getFruitsFrom(otherActor)) {
            actions.add(new SellAction(fruit));
        }

        return actions;
    }


    /**
     * Generates a monologue line depending on the player's rune amount, inventory, and surrounding environment.
     *
     * @param otherActor The actor interacting with Kale.
     * @param here   The current location of Kale.
     * @return A monologue string appropriate to the player's context.
     */
    private String generateMonologue(Actor otherActor, Location here) {
        boolean surroundedByBlight = false;
        for (Exit exit : here.getExits()) {
            if (exit.getDestination().getGround().hasCapability(EntityStatus.CURSED)) {
                surroundedByBlight = true;
                break;
            }
        }

        int runes = otherActor.getAttribute(GameActorAttributes.RUNE);
        boolean hasNoItems = otherActor.getItemInventory().isEmpty();

        if (runes < 500) {
            return "Ah, hard times, I see. Keep your head low and your blade sharp.";
        } else if (hasNoItems) {
            return "Not a scrap to your name? Even a farmer should carry a trinket or two.";
        } else if (surroundedByBlight) {
            return "Rest by the flame when you can, friend. These lands will wear you thin.";
        } else {
            return "A merchant’s life is a lonely one. But the roads… they whisper secrets to those who listen.";
        }
    }
}
