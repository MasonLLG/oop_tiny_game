package game.grounds.plants;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Harvestable;
import game.grounds.Soil;
import game.grounds.WitheredSoil;
import game.items.fruits.InheritreeFruit;
import game.statuses.*;
import game.watering.Sprinkler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the Inheritree in the game. Extends the Plant class.
 * The Inheritree has an immediate effect that turns cursed ground into soil, and a tick effect that heals nearby actors,
 * replenishing their health and stamina.
 *
 * @author kellytran
 * Modified by Jichao Liang
 */
public class InheritreePlant extends Plant implements Waterable, Harvestable {
    private static final int MATURITY_TURNS = 10;
    private int turnsExisted = 0;
    /**
     * Constructor for Inheritree class.
     */
    public InheritreePlant() {
        super('t', "Inheritree");
        this.addCapability(EntityStatus.BLESSED_BY_GRACE); //add states BLESSED_BY_GRADE
        this.addCapability(GroundStatus.WATERABLE);
        resetWaterCountdown();
    }

    private void dropFruitNearby(Location center) {
        List<Location> availableSpots = new ArrayList<>();
        for (Exit exit : center.getExits()) {
            Location dest = exit.getDestination();
            if (!dest.containsAnActor() && dest.getItems().isEmpty()) {
                availableSpots.add(dest);
            }
        }

        if (!availableSpots.isEmpty()) {
            Location dropSpot = availableSpots.get(new Random().nextInt(availableSpots.size()));
            dropSpot.addItem(new InheritreeFruit());
            System.out.println("Inheritree dropped a fruit at (" + dropSpot.x() + "," + dropSpot.y() + ")");
        }
    }

    /**
     * This method is called every turn, heals and replenishes health and stamina of nearby actors.
     * Has an immediate effect when planted.
     *
     * @param location the location where the Inheritree is planted
     */
    @Override
    public void tick(Location location) {
        if (this.hasCapability(GroundStatus.WATERED)) {
            this.removeCapability(GroundStatus.WATERED);
            this.water();
        }
        turnsExisted++;
        waterCountdown--;
        System.out.println("[Inheritree @ (" + location.x() + "," + location.y() + ")] Remaining Water: " + waterCountdown);
        if (waterCountdown <= 0) {
            location.setGround(new WitheredSoil());
            return;
        }
        if (isMature()) {
            dropFruitNearby(location);
            turnsExisted = 0;
        }

        if (immediateEffectOccurred) {
            for (Exit exit : location.getExits()) {
                Location destination = exit.getDestination();
                if (destination.containsAnActor()) {
                    Actor target = destination.getActor();
                    target.heal(5);
                    if (target.hasAttribute(BaseActorAttributes.STAMINA)) {
                        target.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, 5);
                    }
                }
            }
        } else {
            immediateEffectOccurred = true;
            for (Exit exit : location.getExits()) {
                Location destination = exit.getDestination();
                if (destination.getGround().hasCapability(EntityStatus.CURSED)) {
                    destination.setGround(new Soil());
                }
            }
        }
    }

    @Override
    protected void resetWaterCountdown() {
        this.waterCountdown = 6; // 5 rounds
    }


    @Override
    public boolean isMature() {
        return turnsExisted >= MATURITY_TURNS;
    }

    @Override
    public void harvest(Actor actor, Location location) {
        if (isMature()) {
            location.addItem(new InheritreeFruit());
            turnsExisted = 0;
            System.out.println("You harvested an Inheritree Fruit!");
        } else {
            System.out.println("Inheritree is not mature yet.");
        }
    }
}

