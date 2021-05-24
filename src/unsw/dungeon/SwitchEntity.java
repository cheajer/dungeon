package unsw.dungeon;

/**
 * Switch which acts as a triggre for switch win condition, if they are all activated.
 */
public class SwitchEntity extends Entity{

    private static int switchCount = 0;

    private boolean activeSwitch;

    /**
     * Create a switch at (x,y)
     * @param x int x-coordinate of the switch to be placed.
     * @param y int y-coordinates of the switch to be placed.
     */
    public SwitchEntity (Dungeon dungeon, int x, int y){
        super(dungeon, x, y);
        this.activeSwitch = false;
        switchCount++;
    }
    public static void setSwitchCount(int count) {
        switchCount=count;
    }
    /**
     * Activate the switch by turning activeSwitch true.
     */
    public void setActiveSwitch(){
        this.activeSwitch = true;
        this.playClip();
        switchCount--;
    }

    /**
     * Activate the switch by turning activeSwitch false.
     */
    public void setInActiveSwitch(){
        this.activeSwitch = false;
        this.playClip();
        switchCount++;
    }

    public boolean getStatus() {
        return activeSwitch;
    }

    public static int getSwitchCount() {
        return switchCount;
    }
    public static void checkSwitches(Dungeon dungeon) {
        for(Entity ent : dungeon.getEntities()) {
            if (ent instanceof SwitchEntity && ent.checkBoulderOn()) {
                SwitchEntity switchEnt = (SwitchEntity)ent;
                switchEnt.setActiveSwitch();
                dungeon.notifyObservers();
            }
        }
    }
}
