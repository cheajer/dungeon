package unsw.dungeon;

/**
 * Treasure is an item which is picked up to complete an objective in the game.
 */
public class Treasure extends Entity {

    private static int treasureCount=0;

    /**
     * Create treasure at (x, y)
     * @param x int x-coordinate of the item.
     * @param y int y-coordinate of the item.
     */
    public Treasure(Dungeon dungeon, int x, int y) {
        super(dungeon, x,y);
        treasureCount++;
    }

    public static void setTreasureCount(int count) {
        treasureCount=count;
    }

    public static int getTreasureCount() {
        return treasureCount;
    }

    /**
     * If treasure if found, then decrement the amount left on the map.
     */
    public static void foundTreasure() {
        treasureCount--;
    }
    /**
     * Check if the treasure condition has been reached by checking is there are 0 treasures left on the map
     * @return true if there is no treasure on the map, false otherwise
     */
    public static boolean checkTreasureObjective() {
        if (treasureCount == 0) {
            return true;
        }
        return false;
    }
    public void pickUpTreasure() {
        foundTreasure();
        this.setX(getDungeon().getWidth()+1);
        this.playClip();
    }

}