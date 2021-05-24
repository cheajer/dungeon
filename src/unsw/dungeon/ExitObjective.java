package unsw.dungeon;

/**
 * Make exiting through the exit gate an objective, if it is needed to be one.
 */
public class ExitObjective extends Objective {

    /**
     * Creates an exit objective
     */
    public ExitObjective() {
        super();
    }

    /**
     * It will always be true that the exit condition is done, as it relies on other conditions being complete.
     * @return true.
     */
    @Override
    public boolean checkCompletion() {
        return true;
    }
}