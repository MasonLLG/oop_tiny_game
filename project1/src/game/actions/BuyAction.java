package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.Player;
import game.items.Sellable;

/**
 * An action that allows the player to buy a {@link Sellable} item.
 * When executed, the item is set as the player's intrinsic weapon,
 * the corresponding rune cost is deducted, and the item's {@code onSell}
 * effect is triggered.
 */
public class BuyAction extends Action {

    /** The item the player is buying. */
    public Sellable tosell;

    /**
     * Constructs a BuyAction for a given sellable item.
     *
     * @param tosell the item to be bought
     */
    public BuyAction(Sellable tosell) {
        this.tosell = tosell;
    }

    /**
     * Executes the buy action.
     * Sets the item as the player's intrinsic weapon, deducts runes,
     * and triggers the item's sell behavior.
     *
     * @param actor the actor performing the action (assumed to be a {@link Player})
     * @param map   the current game map
     * @return a string indicating the item was sold
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.deductBalance(tosell.getSellPrice());
        tosell.onSell(actor, map);
        return "Sold item";
    }

    /**
     * Describes the action in the game menu.
     *
     * @param actor the actor performing the action
     * @return a string description for the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Buy " + tosell.getName();
    }
}
