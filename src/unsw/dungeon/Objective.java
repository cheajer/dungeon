package unsw.dungeon;

/**
 * Objectives which act as winning conditions for the game.
 */
public abstract class Objective {

    /**
     * Will be true or false, depending on whether the objective has been completed or not.
     */
    private boolean objectiveFlag;

    /**
     * Objective constructor, create and objective with its completionflag set to false
     */
    public Objective(){
        this.objectiveFlag = false;
    }

    /**
     * Subclasses need to implement how to check if the objective has been completed
     * @return
     */
    public abstract boolean checkCompletion();

    public boolean getObjectiveFlag() {
        return objectiveFlag;
    }

    public void setObjectiveFlag(boolean bool) {
        this.objectiveFlag=bool;
    }

}
