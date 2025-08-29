package game.statuses;

public interface ManualWatering {
    boolean hasWater();
    void use();
    int getUsesLeft();

    void refill();
}
