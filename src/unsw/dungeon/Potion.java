package unsw.dungeon;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Invincibility potion which causes enemies to explode when touched and run away from the player.
 */
public class Potion extends Entity {
    private Timer timer;
    /**
     * Create a potion at (x, y)
     * @param x int x-coordinate for the potion.
     * @param y int y-coordinate for the potion.
     */
    public Potion(Dungeon dungeon, int x, int y) {
        super(dungeon, x,y);
        this.timer = new Timer();
    }
    /**
     * Consume poition which essentially just makes all the enemies run away.
     */
    public void consumePotion() {
        EnemyRun pattern = new EnemyRun();
        Enemy.setPattern(pattern);
        this.setX((int)Math.floor(this.getDungeon().getWidth()/2));
        this.setY(this.getDungeon().getHeight()+2);
        this.timer.cancel();
        this.timer = new Timer();

        TimerTask action = new TimerTask() {
            public void run() {
                expire();
            }
        };
        this.timer.schedule(action, 5000);
        timer.cancel();
        timer=null;
    }

    public void expire() {
        EnemyChase pattern = new EnemyChase();
        EnemyStationary station = new EnemyStationary();
        Enemy.setPattern(pattern);
        WaterDragon.setPattern(station);
        this.setX(this.getDungeon().getWidth()+1);
        this.setY(this.getDungeon().getHeight()+1);

    }
}