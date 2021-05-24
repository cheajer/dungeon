package unsw.dungeon;

/**
 * Enemy Objectives defines the requirement to slay all enemies to satisfy a winning condition.
 */
public class EnemyObjective extends Objective {
    /**
     * EnemyObjective constructor.
     */
    public EnemyObjective() {
        super();
    }

    /**
     * Overiding checkCompletion, checks if all enemies have been killed.
     * This si done by checking the Enemy static variable enemycount.
     * @return True if all enemies have been killed, false if otherwise
     */
    @Override
    public boolean checkCompletion() {
        if(Enemy.getEnemyCount() == 0)
            return true;
        return false;
    }
}