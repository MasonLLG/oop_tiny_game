package game.behaviours;

import edu.monash.fit2099.engine.actors.Behaviour;

/**
 * This class is responsible for sorting the behaviours of the actors.
 * It will sort the behaviours based on their priority, by holding the behaviour and the enum
 * @author kellytran
 */
public class BehaviourSorter {
    private final Behaviour behaviour;
    private final BehaviourPriority behaviourPriority;

    /**
     * Constructor for BehaviourSorter
     * @param behaviour the behaviour to be sorted
     * @param behaviourPriority the priority of the behaviour
     */
    public BehaviourSorter(Behaviour behaviour, BehaviourPriority behaviourPriority) {
        this.behaviour = behaviour;
        this.behaviourPriority = behaviourPriority;
    }

    /**
     * Get the behaviour
     * @return the behaviour
     */
    public Behaviour getBehaviour() {
        return behaviour;
    }

    /**
     * Get the behaviour priority
     * @return the behaviour priority
     */
    public int getPriority() {
        return behaviourPriority.getPriority();
    }
}
