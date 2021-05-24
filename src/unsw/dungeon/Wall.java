package unsw.dungeon;

/**
 * Wall which encapsulates the dungeon
 */
public class Wall extends Entity {

    /**
     * Create a wall at (x, y)
     * @param x
     * @param y
     */
    public Wall(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }

}
