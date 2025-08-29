package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.statuses.EntityStatus;

public class WitheredSoil extends Ground {
    public WitheredSoil() {
        super('%', "Withered Soil");
        this.addCapability(EntityStatus.CURSED);
    }


}
