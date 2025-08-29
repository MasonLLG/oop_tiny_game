package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Sellable;

/**
 * Action for selling an item to an NPC like Kale.
 */
public class SellAction extends Action {

    private final Sellable sellable;

    public SellAction(Sellable sellable) {
        this.sellable = sellable;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        actor.removeItemFromInventory((Item) sellable);
        sellable.onSell(actor, map);
        return actor + " sold " + sellable.getName() + " for " + sellable.getSellPrice() + " Runes.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " sells " + sellable.getName() + " (" + sellable.getSellPrice() + " Runes)";
    }
}