package unsw.dungeon;

/**
 * Interface for the enemy movement patterns.
 */
public interface EnemyMovement {
    /**
     * movement defines the movement the enemies will take.
     * @param enemy Enemy, the enemy to be chasing the player.
     * @param player Player, the player to be chased.
     */
    public void movement(Enemy enemy, Player player);

    
}