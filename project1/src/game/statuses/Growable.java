package game.statuses;

/**
 * An interface representing a growable status.
 * This can be implemented by any class that is able to grow, or expand in some way.
 *
 * @author Kelly Tran
 */
public interface Growable {
    /**
     * Method to grow the object.
     * This method should define how the object grows and return a message indicating the growth.
     *
     * @return String a message indicating the growth of the object
     */
    String grow();
    /**
     * Method to check if the object can grow.
     * This method should return true if the object is in a state that allows it to grow,
     * and false otherwise.
     *
     * @return boolean true if the object can grow, false otherwise
     */
    boolean canGrow();
}
