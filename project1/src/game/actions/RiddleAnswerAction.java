package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.attributes.GameActorAttributes;
import game.actors.passive.Oracle;

import java.util.*;

/**
 * An action that allows the player to submit an answer to the Oracle's riddle.
 *
 * When executed:
 * - If the answer is correct, the Oracle's current riddle is cleared and a success message is returned.
 * - If incorrect, the riddle is still cleared and a failure message is shown.
 *
 * This action appears in the menu with a label showing the answer being submitted.
 */
public class RiddleAnswerAction extends Action {

    private String answer;
    private boolean correct;
    private Oracle oracle;

    /**
     * Constructs a RiddleAnswerAction with the player's chosen answer.
     *
     * @param answer  the answer string chosen by the player
     * @param correct true if this answer is the correct one
     * @param oracle  the Oracle that posed the riddle
     */
    public RiddleAnswerAction(String answer, boolean correct, Oracle oracle) {
        this.answer = answer;
        this.correct = correct;
        this.oracle = oracle;
    }

    /**
     * Executes the action. Clears the Oracle's current riddle and returns a message
     * based on whether the answer was correct.
     *
     * @param actor the actor performing the action
     * @param map   the game map containing the actor
     * @return a result message indicating correctness
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        oracle.setCurrentRiddle(null); // always clear the riddle after answering
        if(correct)
        {
            actor.modifyAttribute(GameActorAttributes.RUNE, ActorAttributeOperations.INCREASE, 100);
            return "You have answered my riddle correctly traveller, take 100 runes";
        }
        else {
            return "You are an ignorant fool, what else should I have expected from a mortal such as yourself";
        }
    }

    /**
     * Returns a menu description for this action, showing the proposed answer.
     *
     * @param actor the actor viewing the menu
     * @return a string describing this menu action
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Answer riddle with: " + answer;
    }
}
