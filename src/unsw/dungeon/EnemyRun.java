package unsw.dungeon;

/**
 * An implementation of movement which causes the enemies to run from the player.
 */
public class EnemyRun implements EnemyMovement{

    /**
     * The collision logic to detect whether which direction to attempt to run to to get away from the player and
     * make a valid movement which doesn't break the game.
     * @param enemy Enemy, the enemy to be chasing the player.
     * @param player Player, the player to be chased.
     */
    public void movement(Enemy enemy, Player player) {
        if (player.getX() > enemy.getX() //player is right of enemy
            && enemy.getDungeon().collisionDetection(enemy.getX()-1, enemy.getY(), enemy)) {
            if(enemy.getX()-1 < enemy.getDungeon().getWidth()+1 && enemy.getX()-1 > 0)
                enemy.setX(enemy.getX()-1);
        } else if (player.getX() < enemy.getX() // player is left of enemy
                   && enemy.getDungeon().collisionDetection(enemy.getX()+1, enemy.getY(), enemy)) {
            if(enemy.getX()+1 < enemy.getDungeon().getWidth()+1 && enemy.getX()-1 > 0)
                enemy.setX(enemy.getX()+1);
        } else if (player.getY() > enemy.getY() //player is under enemy
                   && enemy.getDungeon().collisionDetection(enemy.getX(), enemy.getY()-1, enemy)) {
            if(enemy.getY()-1 < enemy.getDungeon().getHeight()+1 && enemy.getY()-1 > 0)
                enemy.setY(enemy.getY()-1);
        } else if (player.getY() < enemy.getY() //player is above enemy
                   && enemy.getDungeon().collisionDetection(enemy.getX(), enemy.getY()+1, enemy)) {
            if(enemy.getY()+1 < enemy.getDungeon().getHeight()+1 && enemy.getY()+1 > 0)
                enemy.setY(enemy.getY()+1);
        }
    }
}