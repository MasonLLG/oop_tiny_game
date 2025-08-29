package game.actors.bedofchaos.parts;

import game.statuses.Growable;

/**
 * Interface for parts of the Bed of Chaos boss that can grow.
 * Empty interface because it means that growable parts do not have to have their storage
 * duplicated in the BedOfChaos class, while allowing for polymorphism as the normal
 * BedOfChaosPart methods are still available.
 *
 * @author Kelly Tran
 */
public interface GrowablePart extends BedOfChaosPart, Growable {
}
