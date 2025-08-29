package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.behaviours.*;
import game.behaviours.strategy.BehaviourSelectionStrategy;
import game.behaviours.strategy.PriorityBasedStrategy;
import game.statuses.ActorStatus;

import java.util.*;

/**
 * Abstract class representing a non-player character in the game.
 * This class extends the Actor class and implements the playTurn method to handle the NPC's actions.
 * It also manages a list of behaviours that can be executed during the NPC's turn.
 *
 * @author kellytran
 * Modified by Jichao Liang
 */
public abstract class NonPlayerCharacter extends Actor {
    private final List<BehaviourSorter> behaviours = new ArrayList<>();
    private final BehaviourSelectionStrategy behaviourSelectionStrategy;
    private final Behaviour deathBehaviour = new DeathBehaviour();

    /**
     * Default constructor that creates a NonPlayerCharacter with priority-based behavior strategy.
     * Initializes the NPC with the specified name, display character, and hit points.
     *
     * @param name        the name of the NPC
     * @param displayChar the character representing the NPC on the game map
     * @param hitPoints   the initial health points of the NPC
     */
    public NonPlayerCharacter(String name, char displayChar, int hitPoints) {
        this(name, displayChar, hitPoints, new PriorityBasedStrategy());
    }

    /**
     * Advanced constructor that creates a NonPlayerCharacter with a specified behavior strategy.
     * Allows customization of the NPC's decision-making process for choosing behaviors.
     *
     * @param name        the name of the NPC
     * @param displayChar the character representing the NPC on the game map
     * @param hitPoints   the initial health points of the NPC
     * @param strategy    the behavior selection strategy for the NPC
     */
    public NonPlayerCharacter(String name, char displayChar, int hitPoints,
                              BehaviourSelectionStrategy strategy) {
        super(name, displayChar, hitPoints);
        this.behaviourSelectionStrategy = strategy;
    }

    /**
     * Adds a behaviour to the non-player character's list of behaviours.
     * The behaviours are sorted by their priority.
     *
     * @param behaviour the behaviour to be added
     * @param behaviourPriority the priority of the behaviour
     */
    protected void addBehaviour(Behaviour behaviour, BehaviourPriority behaviourPriority) {
        behaviours.add(new BehaviourSorter(behaviour, behaviourPriority));
        behaviours.sort(Comparator.comparingInt(BehaviourSorter::getPriority));
    }


    /**
     * Handles the NPC's turn execution by:
     * 1. First checking for death-related actions
     * 2. Then using the configured behavior strategy to select an action
     * 3. Returning a DoNothingAction if no valid action is found
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction the Action this Actor took last turn
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed this turn, or DoNothingAction if no valid action
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Check death first
        Action deathAction = deathBehaviour.getAction(this, map);
        if (deathAction != null) return deathAction;

        // Use strategy for other behaviors
        Action action = behaviourSelectionStrategy.selectAction(behaviours, this, map);
        return action != null ? action : new DoNothingAction();
    }

    /**
     * Provides a list of actions that can be performed on the NPC.
     * If this NPC is hostile to the other actor, it can have an attack action performed on it.
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return a list of actions that can be performed on the NPC
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);
        if (otherActor.hasCapability(ActorStatus.PLAYER) && this.hasCapability(ActorStatus.HOSTILE_TO_ENEMY)) {
            // Add basic attack action
            actions.add(new AttackAction(this, map.locationOf(this).toString()));
        }
        return actions;
    }
}
