package game.actors.passive;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.oracleutil.ProphecyType;
import game.oracleutil.Riddle;
import game.actions.PropheciseAction;
import game.actions.RiddleAnswerAction;
import game.behaviours.BehaviourPriority;
import game.behaviours.WanderBehaviour;
import game.dialogue.DialogueGenerator;
import game.dialogue.ComplimentGenerator;
import game.dialogue.ProphecyGenerator;
import game.dialogue.RiddleGenerator;
import game.actors.NonPlayerCharacter;
import game.statuses.ActorStatus;

import java.util.Random;

/**
 * Oracle is a passive NPC who can perform several mystical and helpful roles.
 *
 * The Oracle:
 * - Offers prophetic speeches, riddles, or compliments using PropheciseAction.
 * - Stores the current active riddle and enables the player to respond to it.
 * - Allows item transactions (e.g., buying) when the player holds a SacredScroll.
 * - Wanders the map by default as its idle behavior.
 */
public class Oracle extends NonPlayerCharacter {

    private Riddle currentRiddle;
    private final Random rng = new Random();

    /**
     * Constructs a new Oracle character named "Merchant Kale" with a display character of 'k'
     * and 200 hit points. Adds wander behavior at low priority.
     */
    public Oracle() {
        super("The Oracle", 'o', 200);
        this.addBehaviour(new WanderBehaviour(), BehaviourPriority.WANDER);
    }

    /**
     * Updates the Oracle's currently stored riddle.
     * This may be called when a new riddle is generated, or with null to clear it.
     *
     * @param riddle the new Riddle to store, or null to clear
     */
    public void setCurrentRiddle(Riddle riddle) {
        this.currentRiddle = riddle;
    }

    /**
     * Returns the riddle currently stored by the Oracle.
     *
     * @return the active Riddle, or null if none is stored
     */
    public Riddle getCurrentRiddle() {
        return currentRiddle;
    }

    /**
     * Determines what actions another actor (usually the player) can perform with the Oracle.
     * The behavior depends on several conditions:
     * - If the actor lacks a SacredScroll, no interaction is available.
     * - If a riddle is pending, it allows the actor to answer it.
     * - Otherwise, offers a new PropheciseAction using a randomly chosen generator type.
     *
     * @param otherActor the actor interacting with the Oracle
     * @param direction the direction of the Oracle relative to the actor
     * @param map the game map where the interaction is taking place
     * @return a list of actions the actor can perform with the Oracle
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);

        // Only interact if player holds a SacredScroll
        if (!otherActor.hasCapability(ActorStatus.CAN_SPEAK_ORACLE)) {
            return actions;
        }

        if (!(otherActor.hasCapability(ActorStatus.PLAYER))) {
            return actions;
        }

        // If there's a riddle awaiting response, add answer actions
        if (currentRiddle != null) {
            for (int i = 0; i < currentRiddle.getPossibleAnswers().size(); i++) {
                String answer = currentRiddle.getPossibleAnswers().get(i);
                boolean isCorrect = (i == currentRiddle.getCorrectAnswerIndex());
                actions.add(new RiddleAnswerAction(answer, isCorrect, this));
            }
            return actions;
        }

        // Otherwise, choose a new type of dialogue to generate
        ProphecyType[] types = ProphecyType.values();
        ProphecyType type = types[rng.nextInt(types.length)];
        DialogueGenerator generator;

        switch (type) {
            case PROPHECY:
                generator = new ProphecyGenerator();
                break;
            case RIDDLE:
                generator = new RiddleGenerator();
                break;
            case COMPLIMENT:
                generator = new ComplimentGenerator();
                break;
            default:
                throw new IllegalStateException("Unexpected ProphecyType: " + type);
        }

        actions.add(new PropheciseAction(generator, this));
        return actions;
    }
}
