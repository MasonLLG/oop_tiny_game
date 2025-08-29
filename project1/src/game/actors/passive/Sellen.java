package game.actors.passive;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.BuyAction;
import game.actions.ListenAction;
import game.actors.NonPlayerCharacter;
import game.actors.attributes.GameActorAttributes;
import game.behaviours.MonologueBehaviour;
import game.behaviours.BehaviourPriority;
import game.behaviours.WanderBehaviour;
import game.weapons.Broadsword;
import game.weapons.DragonslayerGreatsword;
import game.weapons.Katana;
import game.statuses.ActorStatus;

import java.util.List;

import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.HEALTH;
import static edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes.STAMINA;

/**
 * Sellen is a passive NPC who offers philosophical monologues and sells weapons to players based on rune count.
 * She also uses a {@link MonologueBehaviour} to occasionally deliver lore-rich lines during idle turns.
 */
public class Sellen extends NonPlayerCharacter {

    /**
     * Constructs a Sellen instance with passive wandering and ambient monologue behaviours.
     */
    public Sellen() {
        super("Sellen", 's', 50);

        this.addBehaviour(new WanderBehaviour(), BehaviourPriority.WANDER);
        this.addBehaviour(new MonologueBehaviour(List.of(
                "The academy casts out those it fears. Yet knowledge, like the stars, cannot be bound forever.",
                "You sense it too, don’t you? The Glintstone hums with buried secrets.",
                "Even in exile, I pursue the shimmer of truth."
        ), 0.3), BehaviourPriority.LOW);
    }


    /**
     * Defines the actions available to another actor interacting with Sellen.
     * <p>
     * If the actor is a player, they can:
     * <ul>
     *     <li>Listen to a lore-related monologue</li>
     *     <li>Purchase weapons depending on rune amount</li>
     * </ul>
     *
     * @param otherActor The actor interacting with Sellen.
     * @param direction  The direction of interaction.
     * @param map        The game map where the interaction occurs.
     * @return An ActionList containing available ListenAction and BuyActions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);

        if (otherActor.hasCapability(ActorStatus.PLAYER)) {
            actions.add(new ListenAction(this, List.of(
                    "The academy casts out those it fears. Yet knowledge, like the stars, cannot be bound forever.",
                    "You sense it too, don’t you? The Glintstone hums, even now."
            )));

            if(otherActor.getAttribute(GameActorAttributes.RUNE) >= 100)
            {
                actions.add(new BuyAction(new Broadsword(100, 20, HEALTH, true)));
            }
            if(otherActor.getAttribute(GameActorAttributes.RUNE) >= 1500)
            {
                actions.add(new BuyAction(new DragonslayerGreatsword(1500, STAMINA, 20)));
            }

            if(otherActor.getAttribute(GameActorAttributes.RUNE) >= 500)
            {
                actions.add(new BuyAction(new Katana()));
            }
        }

        return actions;
    }
}