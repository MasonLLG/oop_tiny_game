package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import game.statuses.ActorStatus;

/**
 * A behaviour that makes an actor follow a random followable actor.. Implements the Behaviour interface.
 *
 * @author kellytran
 */
public class FollowBehaviour implements Behaviour {
    private boolean currentlyFollowing = false;
    private Actor followTarget = null;

    /** Gets the action that makes the actor follow the player.
     *
     * @param actor the Actor that has this behaviour
     * @param map the GameMap containing the Actor
     * @return an Action that moves the Actor towards the player, or null if no action is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // Check if current actor to be followed is still alive
        if (followTarget != null && !followTarget.isConscious()) {
            currentlyFollowing = false;
            followTarget = null;
        }

        // Find new actor location by iterating thorugh each coordinate on the map
        if (!currentlyFollowing) {
            for (int x = 0; x < map.getXRange().max(); x++) {
                for (int y = 0; y < map.getYRange().max(); y++) {
                    Location location = map.at(x, y);
                    if (location.containsAnActor() &&
                            location.getActor().hasCapability(ActorStatus.FOLLOWABLE)) {
                        followTarget = location.getActor();
                        currentlyFollowing = true;
                        break;
                    }
                }
                if (currentlyFollowing) break;
            }

            if (followTarget == null) {
                return null;
            }
        }

        Location here = map.locationOf(actor);
        Location actorLocation = map.locationOf(followTarget);

        // Calculate the current distance to the player
        int currentDistance = distance(here, actorLocation);

        // Check all exits for the one that gets us closest to the player
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();

            // Check if the actor can enter this location
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, actorLocation);

                // If this move gets us closer to the player, do it
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }

        return new DoNothingAction();
    }

    /**
     * Calculate the distance between two locations.
     * @param a the first location
     * @param b the second location
     * @return the distance between the two locations
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}