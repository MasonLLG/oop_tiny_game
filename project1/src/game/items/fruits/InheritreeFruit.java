package game.items.fruits;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.EatAction;
import game.actors.attributes.GameActorAttributes;
import game.statuses.ItemStatus;

public class InheritreeFruit extends PlantFruit {

    public InheritreeFruit() {
        super("Inheritree Fruit", 'f', 100, 50);
        this.addCapability(ItemStatus.SELLABLE);
    }

    @Override
    public String getName() {
        return "Inheritree Fruit";
    }



}
