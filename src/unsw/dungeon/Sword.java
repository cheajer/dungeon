package unsw.dungeon;

/**
 * Sword which is used by the player to kill enemies
 */
public class Sword extends Entity {
    private int hp = 5;

    /**
     * Spawn a sword at (x, y)
     * @param x int x-coordinates for the sword
     * @param y int y-coordinates for the sword
     */
    public Sword(Dungeon dungeon, int x,int y) {
        super(dungeon, x,y);
    }

    /**
     * Decrease the Hit points of the sword as it is used.
     */
    public void useSword() {
        this.hp--;
    }

    /**
     * Get the remaining HP of the sword
     * @return int
     */
    public int getHP() {
        return hp;
    }

    public void pickUpSword() {
        getDungeon().getPlayer().getInventory().add(this);
        this.setX((int)Math.floor(getDungeon().getWidth()/2)-2);
        this.setY(getDungeon().getHeight()+2);
        getDungeon().getEntities().remove(this);
        getDungeon().getPlayer().setSword(this);
        this.playClip();
    }
}