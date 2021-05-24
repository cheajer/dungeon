package unsw.dungeon;

/**
 * Key objects which are used to open doors.
 */
public class Key extends Entity {

    static private int n_keys = 0;

    private int id;

    /**
     * Spawn a key at (x, y)
     * @param x int x-coordinate to spawn the key
     * @param y int y-coordinates to spawn the key
     */
    public Key (Dungeon dungeon, int x, int y) {
        super(dungeon, x,y);
        this.id = getN_keys();
        incrementN_keys();
    }

    static int getN_keys(){
        return Key.n_keys;
    }

    static void incrementN_keys(){
        Key.n_keys++;
    }

    public void setID(int id) {
        this.id=id;
    }

    public int getID() {
        return id;
    }

    /**
     * Checks if the door ID matches the key. If it does, then retur ntrue else false
     * @param door Door, door to be compared to
     * @return true if door and key id equal, else return false.
     */
    public boolean openDoor(Door door) {
        if(door.getID() == id) 
            return true;
        return false; 
    }

    public void pickUpKey() {
        getDungeon().getPlayer().getInventory().add(this);
        this.pickUp();
        //getDungeon().getEntities().remove(this);
    }
    
}
