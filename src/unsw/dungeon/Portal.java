package unsw.dungeon;

import javafx.scene.media.AudioClip;
import java.io.File;
/**
 * Portal teleports the player to the other entrance of the portal.
 */
public class Portal extends Entity{

    static int num_portal = 0;
    private int portal_id;

    /**
     * Construct a portal at (x, y)
     * @param x int x-coordinate of the portal.
     * @param y int y-coordinate of the portal.
     */
    public Portal(Dungeon dungeon, int x, int y){
        super(dungeon, x, y);
        this.portal_id = num_portal / 2;
        Portal.num_portal++;
    }

    public int getID(){
        return this.portal_id;
    }

    public void usePortal() {
        for (Entity en : this.getDungeon().getEntities()) {
            if (en instanceof Portal) {
                Portal portal2 = (Portal) en;


                if (this.getID() == portal2.getID()) {
                    if (this.getX() != portal2.getX() || this.getY() != portal2.getY()) {
                        this.getDungeon().getPlayer().setX(portal2.getX());
                        this.getDungeon().getPlayer().setY(portal2.getY());
                        usePortalSound();
                    }
                }
            }
        }
        
    }

    public void usePortalSound() {
        AudioClip portal = new AudioClip(new File("sounds/portal.wav").toURI().toString());
        portal.play();
    }
}
