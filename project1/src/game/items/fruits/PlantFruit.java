package game.items.fruits;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.EatAction;
import game.actors.attributes.GameActorAttributes;
import game.items.Sellable;
import game.statuses.Edible;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A generic fruit item that can be consumed or sold.
 */
public abstract class PlantFruit extends Item implements Sellable, Edible {

    protected int sellPrice;
    protected int staminaRestore;

    public PlantFruit(String name, char displayChar, int sellPrice, int staminaRestore) {
        super(name, displayChar,true);
        this.sellPrice = sellPrice;
        this.staminaRestore = staminaRestore;
    }

    @Override
    public int getSellPrice() {
        return sellPrice;
    }

    @Override
    public void onSell(Actor actor, GameMap map) {
        actor.modifyAttribute(GameActorAttributes.RUNE, ActorAttributeOperations.INCREASE, sellPrice);
    }


    @Override
    public void eat(Actor actor, GameMap map) {
        actor.modifyAttribute(BaseActorAttributes.STAMINA,
                ActorAttributeOperations.INCREASE, staminaRestore);
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        actions.add(new EatAction(this));
        return actions;
    }

    public static List<PlantFruit> getFruitsFrom(Actor actor) {
        return actor.getItemInventory().stream()
                .filter(PlantFruit.class::isInstance)
                .map(PlantFruit.class::cast)
                .collect(Collectors.toList());
    }
}
