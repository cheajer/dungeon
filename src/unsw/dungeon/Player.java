package unsw.dungeon;
import java.util.List;
import java.util.ArrayList;


// import java.util.List;
// import java.util.ArrayList;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private List<Entity> inventory;
    private boolean alive;
    private Sword sword;
    private boolean invulnerable;
    private boolean hasBoat;

    /**
     * Create a player positioned in square (x,y)
     * @param x int x-coordinate for the x-position of the player
     * @param y int y-coordinate for the y-position of the player
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.dungeon = dungeon;
        this.alive = true;
        this.sword = null;
        this.inventory = new ArrayList<>();
        this.invulnerable = false;
        this.hasBoat = false;
    }

    /**
     * Check if the player has a sword
     * @return true if they do, else false
     */
    public boolean checkSword() {
        if (sword == null) 
            return false;
        return true;
    }

    public boolean checkInvulnerability() {
        return this.invulnerable;
    }

    public void checkSwordHP() {
        if (this.sword.getHP() == 0) {
            this.sword=null;
        }
            
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }

    /**
     * Use the sword to attack an enemy.
     */
    public void useSword() {
        this.sword.useSword();
    }

    /**
     * Set the player as invulnerable
     * @param bool boolean, true or false to set the invulnerability flag
     */
    public void setInvulnerable(boolean bool){
        this.invulnerable = bool;
    }

    public boolean getStatus() {
        return alive;
    }

    public void setStatus(boolean bool) {
        this.alive=bool;
    }

    public boolean getHasBoat(){return this.hasBoat;}

    public void setHasBoat(boolean bool) {
        this.hasBoat=bool;
    }

    /**
     * If there is no collision with anything moving up, then move set the player y-coordinates + 1
     */
    public void moveUp() {
        if (this.dungeon.collisionDetection(getX(), getY() - 1, this)) {
            if (getY() > 0)
                y().set(getY() - 1);
        }
    }
    /**
     * If there is no collision with anything moving down, then move set the player y-coordinates - 1
     */
    public void moveDown() {
        if (this.dungeon.collisionDetection(getX(), getY() + 1, this)) {
            if (getY() < dungeon.getHeight() - 1)
                y().set(getY() + 1);
        }
    }

    /**
     * If there is no collision with anything moving left, then move set the player x-coordinates - 1
     */
    public void moveLeft() {
        if (this.dungeon.collisionDetection(getX() - 1, getY(), this)) {
            if (getX() > 0)
                x().set(getX() - 1);
        }
    }

    /**
     * If there is no collision with anything moving right, then move set the player x-coordinates + 1
     */
    public void moveRight() {
        if (this.dungeon.collisionDetection(getX() + 1, getY(), this)) {
            if (getX() < dungeon.getWidth() - 1)
                x().set(getX() + 1);
        }
    }

    /**
     * Return the List<Entity> inventory
     * @return List<Entity>
     */
    public List<Entity> getInventory(){
        return this.inventory;
    }

    /**
     * If the player collides with an enemy, if the enemies are runnin, kill the enemy then set them all back to chase the player
     * Else, if the player has a sword, kill the enemy
     * else, kill the player
     * @param enemy Enemy, the enemy object which is being collided with.
     */
    public void fightPlayer(Enemy enemy) {
        if (Enemy.getPattern() instanceof EnemyRun) {
            enemy.killEnemy();
            enemy.setX(dungeon.getWidth() + 1);
        } else if (this.checkSword()) {
            this.useSword();
            this.checkSwordHP();
            enemy.setX(dungeon.getWidth()+10);
            enemy.setY(dungeon.getHeight()+10);    
            enemy.killEnemy();
        } else {
            this.setStatus(false);
            this.setX(dungeon.getWidth()+1);
            System.out.println("You have died.."); 
        }
    }



}
