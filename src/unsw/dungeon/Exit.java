package unsw.dungeon;

/**
 * Class which is the exit for the dungeon
 */
public class Exit extends Entity{

    private static boolean objectiveComplete;

    /**
     * Creates an exit at location (x, y)
     * @param x int x-coordinate for the exit to be placed
     * @param y int y-coordinate for the exit to be placed
     */
    public Exit (Dungeon dungeon, int x, int y){
        super(dungeon, x, y);
        objectiveComplete = false;
    }

    public static boolean getCompletionFlag(){
        return objectiveComplete;
    }

    public static void setCompletionFlag(boolean bool){
        objectiveComplete = bool;
    }

    public boolean tryExit() {
        setCompletionFlag(true);
        this.getDungeon().notifyObservers();
        if (this.getDungeon().getObservers().get(0).checkCompletion())
            return true;
        setCompletionFlag(false);
        this.getDungeon().notifyObservers();
        return false;
    }
}
