package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Ground;
import game.statuses.GroundStatus;

/**
 * Class representing a plant in the game.
 * Extends the Ground class.
 *
 * @author kellytran
 */
public abstract class Plant extends Ground {
    private final String name;
    boolean immediateEffectOccurred = false;


    protected int waterCountdown;
    /**
     * Constructor for Plant class.
     *
     * @param name the name of the plant
     */
    public Plant(char displayChar, String name) {
        super(displayChar, name);
        this.name = name;
        resetWaterCountdown();
        this.addCapability(GroundStatus.WATERABLE);
    }

    public void water() {
        this.resetWaterCountdown();
    }

    public String getName() {
        return name;
    }

    protected abstract void resetWaterCountdown();

}