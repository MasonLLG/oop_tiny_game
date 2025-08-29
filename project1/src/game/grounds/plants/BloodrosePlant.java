package game.grounds.plants;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.WaterAction;
import game.grounds.WitheredSoil;
import game.statuses.*;
import game.watering.Sprinkler;

/**
 * Class representing a Bloodrose plant. Extends the Plant class.
 * The Bloodrose plant has an immediate effect that hurts the player when planted, and a tick effect that hurts nearby actors.
 *
 * @author kellytran
 */
public class BloodrosePlant extends Plant implements Waterable {

    /**
     * Constructor for Bloodrose class.
     */
    public BloodrosePlant() {
        super('w', "Bloodrose");
        this.addCapability(GroundStatus.WATERABLE);
        resetWaterCountdown();
    }


    /**
     * This method is called every turn, will reduce health of nearby actors every tick
     * Has an immediate effect when planted, which hurts the Player in the map no matter where they are.
     *
     * @param location the location where the Inheritree is planted
     */
    @Override
    public void tick(Location location) {
        if (this.hasCapability(GroundStatus.WATERED)) {
            this.removeCapability(GroundStatus.WATERED);
            this.water();
        }
        waterCountdown--;
        if (waterCountdown <= 0) {
            location.setGround(new WitheredSoil());
            return;
        }
        if (immediateEffectOccurred) {
            if (location.containsAnActor()) {
                location.getActor().hurt(10);
            }

            for (Exit exit : location.getExits()) {
                Location destination = exit.getDestination();
                if (destination.containsAnActor()) {
                    destination.getActor().hurt(10);
                }
            }
        } else { // Apply immediate effect instead
            immediateEffectOccurred = true;
            GameMap map = location.map();
            for (int x = 0; x < map.getXRange().max(); x++) {
                for (int y = 0; y < map.getYRange().max(); y++) {
                    Location actorLocation = map.at(x, y);
                    if (actorLocation.containsAnActor() &&
                            actorLocation.getActor().hasCapability(ActorStatus.PLAYER)) {
                        actorLocation.getActor().hurt(5);
                        break;
                    }
                }
            }
        }
    }



    @Override
    protected void resetWaterCountdown() {
        this.waterCountdown = 8; // 7 Rounds
    }

}




