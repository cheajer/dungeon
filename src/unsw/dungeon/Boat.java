package unsw.dungeon;

public class Boat extends Entity {
    public Boat(Dungeon dungeon, int x, int y) {
        super(dungeon,x,y);
    }

    public void pickUpBoat() {
        getDungeon().getPlayer().setHasBoat(true);
        this.pickUp();
    }
    
}