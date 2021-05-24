package unsw.dungeon;

public class Water extends Entity {

    /**
     * C
     * reate a wall at (x, y)
     * @param x
     * @param y
     */
    public Water(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }
    public static boolean checkIfInWater(Dungeon dungeon) {
        for (Entity ent : dungeon.getEntities()) {
            if (ent instanceof Water) {
                if (dungeon.getPlayer().getX()==ent.getX() && dungeon.getPlayer().getY()==ent.getY()) {
                    WaterDragon.setPattern(true);
                    return true;
                }
            }
        }
        WaterDragon.setPattern(false);
        return false;
    }

}
