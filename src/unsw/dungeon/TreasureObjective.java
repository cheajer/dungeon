package unsw.dungeon;

/**
 * Treasure objective that works by collecting all the treasure will trigger this objective flag.
 */
public class TreasureObjective extends Objective {
    /**
     * Treasure objective constructor
     */
    public TreasureObjective() {
        super();
    }

    /**
     * Check completion by checking the treasure count has been reduced to 0
     * @return true if treasure count is 0, else false.
     */
    @Override
    public boolean checkCompletion() {
        if(Treasure.getTreasureCount() == 0)
            return true;
        return false;
    }
}