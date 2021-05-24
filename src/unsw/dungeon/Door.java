package unsw.dungeon;

/**
 *  The Door class extends entity and models the door in game which is shut by default.
 *  Doors are opened with corresponding keys, which have the same ID, which cna be picked
 *  up from around the map, which are then discarded after use.
 */
public class Door extends Entity{

    private static int n_doors = 0;
    private boolean opened;
    private int ID;
    private Door open_door;

    /**
     * Door constructor which calls the entity constructor and is 'closed' by default
     * @param x int x-coordinate where the door is located
     * @param y int y-coordinate where the door is located
     */
    public Door (Dungeon dungeon, int x, int y){
        super(dungeon, x, y);
        this.opened = false;
    }

    /**
     * Set the ID of the door, which is used when identifying corresponding keys
     * @param id ID is the unique indentifier for the door.
     */
    public void setID(int id){this.ID = id; }

    /**
     * Returns the ID of the Door.
     * @return (int) ID of the door
     */
    public int getID(){
        return this.ID;
    }

    /**
     * Returns the attribute if the door is opened or not
     * @return boolean, true or false if the door has been opened or not.
     */
    public boolean getOpened(){
        return this.opened;
    }

    /**
     * Set the opened parameter to true or false
     * @param bool boolean, set when door changes state (opened/closed)
     */
    public void setOpened(boolean bool){
        this.opened = bool;
    }

    /**
     * Swap the position of the closed door with the opened door.
     */
    public void open_closed_door(){
        int temp_x = this.getX();
        Door temp_door = this.open_door;
        this.setX(temp_door.getX());
        temp_door.setX(temp_x);
    }

    /**
     * Set the open_door attribute to point at the door object which has the spirte of an open door
     * that has the opened parameter as true
     * @param open_door A Door Object which should have an open door sprite
     */
    public void setOpen_door(Door open_door){
        this.open_door = open_door;
    }

    /**
     * Return the class variable which is a counter for the number of doors which are present on the map
     * @return int, number of doors on the map
     */
    public static int getN_doors(){
        return Door.n_doors;
    }
    /**
     * Increment the door class variable which counts the number of doors.
     */
    public static void incrementN_door(){
        Door.n_doors++;
    }

    public boolean checkPlayerCanOpen() {
        for (Entity en : getDungeon().getPlayer().getInventory()) {
            if (en instanceof Key) {
                Key key = (Key)en;
                if (this.getID() == key.getID()) {
                    this.setOpened(true);
                    this.open_closed_door();
                    this.playClip();
                    return true;
                }
            }
        }
        return false;
    }

}
