package game.world;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.bedofchaos.BedOfChaos;
import game.actors.hostile.Guts;
import game.actors.passive.*;
import game.actors.Player;
import game.behaviours.strategy.PriorityBasedStrategy;
import game.behaviours.strategy.RandomSelectionStrategy;
import game.items.SacredScroll;
import game.items.TeleportationCircle;
import game.items.eggs.GoldenEgg;
import game.items.eggs.OmenSheepEgg;
import game.items.fruits.InheritreeFruit;
import game.items.seeds.BloodroseSeed;
import game.items.seeds.InheritreeSeed;
import game.items.Talisman;
import game.grounds.Blight;
import game.grounds.Floor;
import game.grounds.Soil;
import game.grounds.Wall;
import game.util.FancyMessage;
import game.watering.Sprinkler;
import game.watering.WateringCan;

/**
 * The main class to setup and run the game.
 * @author Adrian Kristanto
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Blight(),
                new Wall(), new Floor(), new Soil());

        List<String> map = Arrays.asList(
                "xxxx...xxxxxxxxxxxxxxxxxxxxxxx........xx",
                "xxx.....xxxxxxx..xxxxxxxxxxxxx.........x",
                "..........xxxx....xxxxxxxxxxxxxx.......x",
                "....xxx...........xxxxxxxxxxxxxxx.....xx",
                "...xxxxx...........xxxxxxxxxxxxxx.....xx",
                "...xxxxxxxxxx.......xxxxxxxx...xx......x",
                "....xxxxxxxxxx........xxxxxx...xxx......",
                "....xxxxxxxxxxx.........xxx....xxxx.....",
                "....xxxxxxxxxxx................xxxx.....",
                "...xxxx...xxxxxx.....#####.....xxx......",
                "...xxx....xxxxxxx....#___#.....xx.......",
                "..xxxx...xxxxxxxxx...#___#....xx........",
                "xxxxx...xxxxxxxxxx...##_##...xxx.......x",
                "xxxxx..xxxxxxxxxxx.........xxxxx......xx",
                "xxxxx..xxxxxxxxxxxx.......xxxxxx......xx");

        GameMap gameMap = new GameMap("Valley of the Inheritree", groundFactory, map);
        world.addGameMap(gameMap);

        // Livmeld Map
        List<String> limveldMap = Arrays.asList(
                ".............xxxx",
                "..............xxx",
                "................x",
                ".................",
                "................x",
                "...............xx",
                "..............xxx",
                "..............xxx",
                "..............xxx",
                ".............xxxx",
                ".............xxxx",
                "....xxx.....xxxxx",
                "....xxxx...xxxxxx");
        GameMap limveld = new GameMap("Limveld", groundFactory, limveldMap);
        world.addGameMap(limveld);

        // two destinations for each teleportation circle for testing
        TeleportationCircle leavegameMap = new TeleportationCircle("Telepartation Circle To leave gameMap", 'A',false);
        leavegameMap.addSampleAction(new MoveActorAction(limveld.at(7, 2), "to Limveld!"));
        leavegameMap.addSampleAction(new MoveActorAction(limveld.at(1, 2), "to Limveld2!"));
        gameMap.at(22, 10).addItem(leavegameMap);

        TeleportationCircle leaveLivmeld = new TeleportationCircle("Telepartation Circle To leave Livmeld", 'A',false);
        leaveLivmeld.addSampleAction(new MoveActorAction(gameMap.at(22, 10), "to gameMap!"));
        leaveLivmeld.addSampleAction(new MoveActorAction(gameMap.at(23, 10), "to gameMap2!"));
        limveld.at(1, 2).addItem(leaveLivmeld);


        // BEHOLD, ELDEN THING!
        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        int playerSpawnX = 23;
        int playerSpawnY = 10;
        Player player = new Player("Farmer", '@', 100, 200, gameMap.at(playerSpawnX, playerSpawnY));

        // two ways for each kind of creatures to test
        OmenSheep omenSheep = new OmenSheep(new RandomSelectionStrategy());
        OmenSheep omenSheep1 = new OmenSheep(new PriorityBasedStrategy());
        SpiritGoat spiritGoat = new SpiritGoat(new RandomSelectionStrategy());
        SpiritGoat spiritGoat1 = new SpiritGoat(new PriorityBasedStrategy());
        GoldenBeetle goldenBeetle = new GoldenBeetle(new RandomSelectionStrategy());
        GoldenBeetle goldenBeetle1 = new GoldenBeetle(new PriorityBasedStrategy());
        Sellen sellen = new Sellen();
        Oracle johnsena = new Oracle();
        Guts guts = new Guts();
        BedOfChaos bedOfChaos = new BedOfChaos(gameMap.at(23, 14));

        world.addPlayer(player, gameMap.at(23, 10));
        gameMap.addActor(johnsena, gameMap.at(23, 14));
        player.addItemToInventory(new InheritreeSeed());
        player.addItemToInventory(new BloodroseSeed());
        player.addItemToInventory(new Sprinkler());
        player.addItemToInventory(new WateringCan());
        player.addItemToInventory(new InheritreeFruit());

        // game setup
        gameMap.at(24, 11).addItem(new Talisman());
        gameMap.at(24, 10).addItem(new WateringCan());
        gameMap.at(24, 13).addItem(new GoldenEgg());
        //gameMap.at(24, 13).addItem(new OmenSheepEgg());
        gameMap.at(24, 13).addItem(new SacredScroll());

        world.run();
    }
}
