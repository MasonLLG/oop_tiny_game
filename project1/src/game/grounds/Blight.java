package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.abilities.Ability;
import game.actions.CureAction;
import game.actions.ShovelAction;
import game.statuses.Curable;
import game.statuses.EntityStatus;
import game.statuses.ActorStatus;
import game.statuses.Shovelable;

/**
 * A class representing a blight covering the ground of the valley.
 * @author Adrian Kristanto
 *
 * Modified by kellytran
 */
public class Blight extends Ground implements Curable, Shovelable {
    /**
     * Constructor for Blight class.
     * Is considered as a CURSED ground.
     */
    public Blight() {
        super('x', "Blight");
        this.addCapability(EntityStatus.CURSED);
    }


    @Override
    public String onShovel(Actor actor, GameMap map, Location here) {
        here.setGround(new Soil());
        return "The blight has been cleared. The ground is now fertile.";
    }


    /**
     * Method to cure the blight and turn it into soil.
     * @param actor the actor that is curing the blight
     * @param map the map where the blight is located
     */
    @Override
    public void cure(Actor actor, GameMap map) {
        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Location location = map.at(x, y);
                if (location.getGround() == this) {
                    location.setGround(new Soil());
                    return;
                }
            }
        }
    }

    /**
     * Returns the actions that can be performed on the blight - currently only curing it if the actor has the CURER ability.
     * @param actor the actor that is performing the action
     * @param here the location of the actor
     * @param direction the direction the actor is facing
     * @return the list of actions that can be performed on the blight
     */
    @Override
    public ActionList allowableActions(Actor actor, Location here, String direction) {
        ActionList list = super.allowableActions(actor, here, direction);
        if (actor.hasCapability(Ability.CURER)) {
            list.add(new CureAction(this, 50, direction));
        }

        if (actor.hasCapability(ActorStatus.PLAYER) && direction.equals("")) {
            list.add(new ShovelAction(this, here));
        }
        return list;
    }
}
