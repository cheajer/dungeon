package unsw.dungeon;
import javafx.scene.media.AudioClip;
import java.io.File;
/**
 * Enemy is an entity which is going to chase the player and kill them if they do not have invincibility or a sword
 * Otherwise the enemy will kill the player and they will gameover.
 */
public class Enemy extends Entity {

    private static EnemyMovement pattern;
    private Dungeon dungeon;
    private static int enemyCount=0;
    private int id;

    /**
     * Enemy constructor.
     * @param x int x-coordinate of the enemy
     * @param y int y-coordinate of the enemy
     * @param dungeon Pointer to the dungeon object.
     */
    public Enemy(int x, int y, Dungeon dungeon){
        super(dungeon, x, y);
        this.dungeon=dungeon;
        this.id=enemyCount;
        pattern=new EnemyChase();
        enemyCount++;
        
    }
    

    /**
     * Check if the enemy is about to collide with the player, if so then fight them
     * @param dungeon Dungeon, the dungeon in which the game is taking place in.
     */
    public void checkCollision(Dungeon dungeon) {
        if (dungeon.getPlayer().getX() == this.getX() && dungeon.getPlayer().getY() == this.getY()) {
            dungeon.getPlayer().fightPlayer(this);
        }
    }

    /**
     * Get the ID of the enemy
     * @return int, ID of the enemy
     */
    public int getID() {
        return id;
    }

    /**
     * check if the object being compared with is equal to the one calling equals.
     * @param enemy Enemy, the one to be compared to.
     * @return True if the enemy being compared is the same as the enemy calling equals.
     */
    public boolean equals(Enemy enemy) {
        if (id == enemy.getID())
            return true;
        return false;
    }

    /**
     * Reduce the class variable which counts the number of enemies on the map.
     */
    public static void dropEnemyCount() {
        enemyCount--;
    }

    /**
     * Return the number of enemies present on the map.
     * @return int, the number of enemies on the map.
     */
    public static int getEnemyCount() {
        return enemyCount;
    }
    public static void setEnemyCount(int count) {
        enemyCount=count;
    }

    /**
     * Move the enemies in the movement pattern they are following currently.
     * @param player Player, player class that is to be chased.
     */
    public void move(Player player) {
        pattern.movement(this, player);
    }

    /**
     * Set the movement pattern of the enemies on the map.
     * @param movement EnemyMovement, the move pattern for the enelies to follow.
     */
    public static void setPattern(EnemyMovement movement) {
        pattern=movement;
    }

    /**
     * Return the movement pattern the enemies are currently following
     * @return EnemyMovement
     */
    public static EnemyMovement getPattern() {
        return pattern;
    }

    /**
     * Return the dungeon object
     * @return Dungeon
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * Kill the enemy, remove them from the entity list.
     * @param enemy Enemy, enemy that is going to be removed
     */
    public void killEnemy() {
        playKillEnemySound();
        Enemy.dropEnemyCount();
        dungeon.getEntities().remove(this);
        
    }

    public void playKillEnemySound() {
        AudioClip kill = new AudioClip(new File("sounds/enemy_death.wav").toURI().toString());
        kill.play();
    }

}
