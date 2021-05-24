package unsw.dungeon;

public class WaterDragon extends Enemy {

    private static boolean flag;

    public WaterDragon(Dungeon dungeon, int x, int y) {
        super(x,y,dungeon);
        flag=false;
    }
    public static boolean getFlag() {
        return flag;
    }
    public static void setPattern(boolean bool) {
        flag=bool;
    }
    
}