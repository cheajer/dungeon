/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;


// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.GridPane;


// import java.io.File;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private List<Observer> observers;
    private String condition = "";
    private boolean dungeonCompletion;
    private boolean[][] inventory;
    private List<Objective> objectives;


    /**
     * Dungeon constructor. Contains the width and height of the dungeon which is a grid
     * an entities List, which is every entity which can be found on the map.
     * the player entity on the map.
     * The objectives which need to be completed on the map.
     * @param width int, the width of the dungeon.
     * @param height int, the height of the dungeon.
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.observers = new ArrayList<>();
        this.objectives = new ArrayList<>();
        this.dungeonCompletion=false;
        this.inventory = new boolean[3][2];
        for (int i=0; i < 3; i++) {
            for (int j=0; j<2; j++) {
                this.inventory[i][j]=false;
            }
        }

    }


    /**
     * Return the width of the dungeon as an int.
     * @return int, the width of the dungeon.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the height of the dungeon as an int.
     * @return int, the height of the dungeon.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the player object that is found in the dungeon.
     * @return Player, the palyer object of the dungeon.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player attribute for the dungeon.
     * @param player Player, the player object.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Add entity to the dungeon entity list.
     * @param entity Entity, or subclasses, the entity to be added to the dungeon.
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Set the winning condition of the dungeon, condition being logical operators. i.e. AND, OR, etc.
     * They get added on as found, so ANDORANDOR and be found.
     * @param string String, the string to set the winning condition of the dungeon.
     */
    public void setCondition(String string){
        this.condition = string;
    }

    /**
     * Get the winning condition for this dungeon
     * @return String, the winning condition of the dungeon in play.
     */
    public String getCondition(){
        return this.condition;
    }
    /**
     * Everytime a action is performed, the ticker is there to move enemies and check if winning objectives
     * have been completed.
     */
    public boolean getDungeonCompletion() {
        return this.dungeonCompletion;
    }
    public void setDungeonCompletion() {
        this.dungeonCompletion=true;
    }


    /**
     * Create a list and find and add all the enemies in the dungeon to it.
     * @return List, contains all the enemies on the map.
     */
    public List<Enemy> getEnemies() {
        List<Enemy> enList = new ArrayList<>();
        for (Entity x : entities) {
            if (x instanceof Enemy) {
                Enemy y = (Enemy)x;
                enList.add(y);
            }
        }

        return enList;
    }

    // The x and y coords passed in are the intended squares of movement
    // Boulder has a weird glitch where if you push a boulder into the portal, you will get teleported instead.

    /**
     * Collision detection. This is a general function which is roughly used by all the entities which can move.
     * If the entity is trying to move into a wall, it will return false, so there is a collision and entity cannot move
     * If there is a door, if its open, any entity can move through it.
     * However, if it is a closed door, and the colliding object is a player, then check if they have the key
     * if they do, unlock the door.
     * If the calling object is a boulder, then check if it is trying to move into another boulder and block if it is.
     * check if the boulder is trying to move onto a switch, if it is on the switch ignore and continue.
     * If the boulder is not on a switch, then move onto it and activate the switch.
     * If the caller is a player, then if the object being collided with is a:
     * boulder, run a collision test to see if it can move
     * portal, to teleport the player
     * key, sword, potion, treasure, pick them up
     * exit, check the winning conditions and if they are satisfied, then complete the level.
     * enemy, attack the enemy if the player moves on to the enemy
     * if the caller is an enemy
     * if they are tryingto move onto the player, attack the player.
     * @param x int, the destination x-coordinate for the calling object
     * @param y int, the destination y-coordinate for the calling object
     * @param obj Entity, the calling entity.
     * @return boolean, true or false, depending on whether the entity can move (true) or cant (false)
     */
    public boolean collisionDetection(int x, int y, Entity obj){
        for (Entity entity: entities) {
            if (entity.getX() == x && entity.getY() == y) {
                if (entity instanceof Wall) {
                    return false;
                }
                if (entity instanceof Door) {
                    if (entity.checkBoulderOn()) {
                        continue;
                    }
                    Door door = (Door) entity;
                    if (door.getOpened()) {
                        return true;
                    }
                    if (obj instanceof Player) {
                        if (door.checkPlayerCanOpen())
                            return true;
                    }
                    return false;
                }

                if (obj instanceof Boulder) {
                    if (entity instanceof Boulder) {
                        return false;
                    }
                    if (entity instanceof SwitchEntity) {
                        if (entity.checkBoulderOn()) {
                            continue;
                        } else {
                            SwitchEntity en = (SwitchEntity) entity;
                            en.setActiveSwitch();
                            notifyObservers();
                            continue;
                        }
                    }
                }
                if (obj instanceof Player) {
                    if (entity instanceof Water){
                        if (player.getHasBoat())
                            return true;
                        return false;
                    }
                    if (entity instanceof Boulder) {
                        Boulder boulder = (Boulder)entity;
                        boulder.moveBoulder();
                        return false;
                    }
                    if (entity instanceof Portal) {
                        Portal portal = (Portal) entity;
                        portal.usePortal();
                        return false;
                    }

                    if (entity instanceof Key) {
                        Key key = (Key)entity;
                        key.pickUpKey();
                        break;
                    }
                    if (entity instanceof Boat){
                        Boat boat = (Boat)entity;
                        boat.pickUpBoat();
                        break;
                    }
                    if (entity instanceof Sword) {
                        Sword sword = (Sword)entity;
                        sword.pickUpSword();
                        break;
                    }

                    if (entity instanceof Potion){
                        Potion potion = (Potion)entity;
                        potion.consumePotion();
                        entity.playClip();
                        break;
                    }

                    if (entity instanceof Treasure) {
                        Treasure treasure = (Treasure)entity;
                        treasure.pickUpTreasure();
                    }

                    if (entity instanceof SwitchEntity) {
                        continue;
                    }
                    if (entity instanceof Exit) {
                        Exit exit = (Exit)entity;
                        return exit.tryExit();

                    }
                    if (entity instanceof Enemy) {
                        Enemy en = (Enemy) entity;
                        player.fightPlayer(en);
                        notifyObservers();
                        return false;
                    }
                }
                if (obj instanceof Enemy) {
                    if (entity instanceof Boulder){
                        return false;
                    }
                    if (entity instanceof Enemy) {
                        return false;
                    }
                    if (entity instanceof Player) {
                        Enemy enemy = (Enemy) obj;
                        player.fightPlayer(enemy);
                        return true;
                    }
                }
            }
        }
        return true;
    }

    public boolean[][] getInventory() {
        return inventory;
    }

    /**
     * Returns the list of entities of the dungeon
     * @return List<Entity>
     */
    public List<Entity> getEntities() {
        return entities;
    }

    public List<Objective> getObjectives(){
        return objectives;
    }
    public List<Observer> getObservers(){
        return observers;
    }
    public void notifyObservers() {
        for (Observer x : observers) {
            x.update();
        }
    }
    public void registerObserver(Observer obs) {
        this.observers.add(obs);
    }

    public void resetDungeon() {
        Treasure.setTreasureCount(0);
        Enemy.setEnemyCount(0);
        SwitchEntity.setSwitchCount(0);
        this.entities = null;
        this.width = 0;
        this.height = 0;
        this.player = null;
        this.observers = null;
        this.condition = null;
        this.dungeonCompletion = false;
        this.inventory = null;
        this.objectives = null;
    }
}
