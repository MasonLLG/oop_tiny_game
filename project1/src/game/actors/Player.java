package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DeathAction;
import game.actors.attributes.GameActorAttributes;
import game.weapons.BareFist;
import game.statuses.ActorStatus;

/**
 * Class representing the Player.
 * @author Adrian Kristanto
 * Edited by kellytran
 */
public class Player extends Actor {
    Location spawnpoint;

    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     * @param stamina     Player's starting number of stamina
     * @param spawnpoint  The location where the player will spawn
     */
    public Player(String name, char displayChar, int hitPoints, int stamina, Location spawnpoint) {
        super(name, displayChar, hitPoints);
        this.addCapability(ActorStatus.HOSTILE_TO_ENEMY);
        this.addCapability(ActorStatus.PLAYER);
        this.addCapability(ActorStatus.FOLLOWABLE);
        this.setIntrinsicWeapon(new BareFist());
        this.addAttribute(BaseActorAttributes.STAMINA, new BaseActorAttribute(stamina));
        this.addAttribute(GameActorAttributes.RUNE, new BaseActorAttribute(999999));
        this.modifyAttribute(GameActorAttributes.RUNE, ActorAttributeOperations.UPDATE, 0);
        this.spawnpoint = spawnpoint;

    }

    /**
     * Get current stamina value.
     */
    public int getStamina() {
        return this.getAttribute(BaseActorAttributes.STAMINA);
    }

    /**
     * Reduce stamina by a certain amount (minimum 0).
     */
    public void reduceStamina(int amount) {
        int current = this.getAttribute(BaseActorAttributes.STAMINA);
        int newValue = Math.max(0, current - amount);
        this.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.UPDATE, newValue);
    }

    /**
     * Return the Player's health and stamina.
     *
     * @return String representation of the Player's health and stamina
     */
    @Override
    public String toString() {
        return String.format("%s Health, (%d/%d) Stamina, %s Runes", super.toString(),
                this.getAttribute(BaseActorAttributes.STAMINA), this.getAttributeMaximum(BaseActorAttributes.STAMINA), this.getAttribute(GameActorAttributes.RUNE));
    }

    /**
     * The Player's turn.
     *
     * @param actions    List of Actions available to the Player
     * @param lastAction The last Action performed by the Player
     * @param map       The GameMap the Player is on
     * @param display    The Display to use to show information to the Player
     * @return The Action chosen by the Player
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // First check if the player is dead
        if (!this.isConscious()) {
            new DeathAction().execute(this, map);
        }

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }



}
