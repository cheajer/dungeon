package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");


        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
    
        ObjectiveObserver objObs = new ObjectiveObserver(dungeon, Enemy.getEnemyCount(), 
                                                         SwitchEntity.getSwitchCount(), 
                                                         Treasure.getTreasureCount());
        dungeon.registerObserver(objObs);
        loadGoals(dungeon, json.getJSONObject("goal-condition"));

        SwitchEntity.checkSwitches(dungeon);

        return dungeon;
    }


    /**
     * Parses the goals from the JSON
     * @param dungeon
     * @param goals
     */
    private void loadGoals(Dungeon dungeon, JSONObject goals){
        String first_goal = goals.getString("goal");
        if (first_goal.equals("OR") || first_goal.equals("AND")){
            dungeon.setCondition(dungeon.getCondition() + first_goal);
            JSONArray subgoal = goals.getJSONArray("subgoals");
            for (int i = 0; i < subgoal.length(); i++){
                loadGoals(dungeon, subgoal.getJSONObject(i));
            }
        }
        else {
            switch (first_goal) {
                case "enemies":
                    EnemyObjective enemy_goal = new EnemyObjective();
                    dungeon.getObjectives().add(enemy_goal);
                    break;
                case "treasure":
                    TreasureObjective treasure_goal = new TreasureObjective();
                    dungeon.getObjectives().add(treasure_goal);
                    break;
                case "boulders":
                    SwitchObjective switch_goal = new SwitchObjective();
                    dungeon.getObjectives().add(switch_goal);
                    break;
                case "exit":
                    ExitObjective exit_goal = new ExitObjective();
                    dungeon.getObjectives().add(exit_goal);
                    break;
            }
        }
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
            case "player":
                Player player = new Player(dungeon, x, y);
                dungeon.setPlayer(player);
                onLoad(player);
                entity = player;
                break;
            case "wall":
                Wall wall = new Wall(dungeon, x, y);
                onLoad(wall);
                entity = wall;
                break;
            case "enemy":
                Enemy enemy = new Enemy(x, y, dungeon);
                onLoad(enemy);
                entity = enemy;
                break;
            case "exit":
                Exit exit = new Exit(dungeon, x, y);
                onLoad(exit);
                entity = exit;
                break;
            case "door":
                Door closed_door = new Door(dungeon, x, y);
                Door open_door = new Door(dungeon, dungeon.getWidth() + 1, y);
                open_door.setOpened(true);
                closed_door.setID(Door.getN_doors());
                Door.incrementN_door();
                closed_door.setOpen_door(open_door);
                onLoad(closed_door, open_door);
                entity = closed_door;
                dungeon.addEntity(open_door);
                break;
            case "boulder":
                Boulder boulder = new Boulder(dungeon, x, y);
                onLoad(boulder);
                entity = boulder;
                break;
            case "switch":
                SwitchEntity switchEntity = new SwitchEntity(dungeon, x, y);
                onLoad(switchEntity);
                entity = switchEntity;
                break;
            case "portal":
                Portal portal = new Portal(dungeon, x, y);
                onLoad(portal);
                entity = portal;
                break;
            case "key":
                Key key = new Key(dungeon, x, y);
                onLoad(key);
                entity = key;
                break;
            case "sword":
                Sword sword = new Sword(dungeon, x, y);
                onLoad(sword);
                entity = sword;
                break;
            case "treasure":
                Treasure treasure = new Treasure(dungeon, x, y);
                onLoad(treasure);
                entity = treasure;
                break;
            case "invincibility":
                Potion potion = new Potion(dungeon, x, y);
                onLoad(potion);
                entity = potion;
                break;
            case "water":
                Water water = new Water(dungeon, x, y);
                onLoad(water);
                entity = water;
                break;
            case "boat":
                Boat boat = new Boat(dungeon, x, y);
                onLoad(boat);
                entity = boat;
                break;
            case "enemy_water":
                WaterDragon waterDragon = new WaterDragon(dungeon, x, y);
                onLoad(waterDragon);
                entity = waterDragon;
                break;
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Door closed_door, Door open_door);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(SwitchEntity switchEntity);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Potion potion);

    public abstract void onLoad(Water water);

    public abstract void onLoad(Boat boat);

    public abstract void onLoad(WaterDragon waterDragon);


}
