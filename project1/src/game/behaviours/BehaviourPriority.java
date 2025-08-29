package game.behaviours;

/**
 * Enum representing the priority of different behaviours.
 * The lower the number, the higher the priority.
 * @author kellytran
 */
public enum BehaviourPriority {
    DEATH(1),
    ROTTING(2),
    ATTACK(3),
    REPRODUCE(4),
    FOLLOW(5),
    WANDER(6),
    GROW(7),
    PICKUP(8),
    DROP(8),
    WAIT(9),
    LOW(10);

    private final int priority;

    BehaviourPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
