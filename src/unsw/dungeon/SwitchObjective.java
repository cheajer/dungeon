package unsw.dungeon;

/**
 * Switchobjective which tracks if all the switches have been activated.
 */
public class SwitchObjective extends Objective {

    /**
     * Call the Objective Constructor
     */
    public SwitchObjective() {
        super();
    }

    /**
     * Test if objective complete if all switches have been activated.
     * @return true if all switches active, else not.
     */
    @Override
    public boolean checkCompletion() {
        System.out.println(SwitchEntity.getSwitchCount());
        if(SwitchEntity.getSwitchCount() == 1)
            return true;
        return false;
    }
}