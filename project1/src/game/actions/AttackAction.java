package game.actions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;


/**
 * Class representing an attack action in the game.
 * This class extends the Action class and implements the execute method to perform the attack.
 * It also provides a menu description for the action.
 * If the target is not conscious after the attack, it calls the DeathAction to handle the death of the target.
 *
 * @author kellytran
 */
public class AttackAction extends Action {
    private final Actor target;
    private final String direction;
    private Weapon weapon;

    /**
     * Constructor for AttackAction
     *
     * @param target    the target actor
     * @param direction the direction of the attack
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    /**
     * Constructor for AttackAction with a weapon item
     *
     * @param target    the target actor
     * @param direction the direction of the attack
     * @param weapon the weapon item used for the attack
     */
    public AttackAction(Actor target, String direction, Weapon weapon) {
        this.target = target;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Executes the attack action on the target actor.
     * If the target is not conscious after the attack, it calls the DeathAction to handle the death of the target.
     * Code adapted from the original AttackAction class, by Adrian Kristanto in hunstsman/AttackAction.java
     *
     * @param actor the actor performing the attack
     * @param map   the game map
     * @return a string describing the result of the attack
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (weapon == null) {
            weapon = actor.getIntrinsicWeapon();
        }

        return weapon.attack(actor, target, map);

    }


    /**
     * Provides a description of the attack action to be displayed in the menu.
     * Code adapted from the original AttackAction class, by Adrian Kristanto in hunstsman/AttackAction.java
     *
     * @param actor the actor performing the attack
     * @return a string describing the attack action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction + " with " + (weapon != null ? weapon : "Intrinsic Weapon");
    }

}
