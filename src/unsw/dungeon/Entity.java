package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.scene.media.AudioClip;
import java.io.File;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private Dungeon dungeon;

    /**
     * Create an entity positioned in square (x,y)
     * @param x int x-coordinate for the Entity position
     * @param y int y-coordinate for the Entity positon
     */
    public Entity(Dungeon dungeon, int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.dungeon = dungeon;
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public void setY(int y) {
        this.y.set(y);
    }

    /**
     * Check whether a boulder is ontop of an entity, if it is then move the boulder instead of affecting the entity
     * below instead.
     * @param entity Entity, The entity which is going to be checked if it is under the boulder
     * @return boolean, if the boulder is on something, return True, else return false.
     */
    public boolean checkBoulderOn() {
        for (Entity ent:dungeon.getEntities()) {
            if (this.getX() == ent.getX() && this.getY() == ent.getY()) {
                if (ent instanceof Boulder) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Check if a switch is underneath the entity which is being checked
     * @param entity The entity being checked if there is a switch underneath it.
     * @return boolean, false. If there is something else underneath the switch, it will deactivate the switch. i.e. boulder moves and something else moves onto the switch.
     */
    public boolean checkSwitchUnderneath() {
        for (Entity ent:dungeon.getEntities()) {
            if (this.getX() == ent.getX() && this.getY() == ent.getY()) {
                if (ent instanceof SwitchEntity) {
                    SwitchEntity switchEnt = (SwitchEntity)ent;
                    switchEnt.setInActiveSwitch();
                    dungeon.notifyObservers();
                }
            }
        }
        return false;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void pickUp() {
        boolean flag = false;
        // entity.setX(1);
        // entity.setY(getHeight()+1);
        for (int i=0; i < 3; i++) {
            for (int j=0; j<2; j++) {
                if (!dungeon.getInventory()[i][j]) {
                    //System.out.println(i+" "+j);
                    this.setX(1+i);
                    this.setY(dungeon.getHeight()+j);
                    dungeon.getInventory()[i][j]=true;
                    flag=true;
                    playClip();
                }
            }
            if (flag == true)
                break;
        }
    }
    public void playClip() {
        AudioClip clip;
        if (this instanceof Sword) {
            clip = new AudioClip(new File("sounds/sword_pickup.wav").toURI().toString());
        } else if (this instanceof Potion) {
            clip = new AudioClip(new File("sounds/potion_drink.wav").toURI().toString());
        } else if (this instanceof Enemy) {
            clip = new AudioClip(new File("sounds/enemy_death.wav").toURI().toString());
        } else if (this instanceof Treasure) {
            clip = new AudioClip(new File("sounds/treasure_pickup.wav").toURI().toString());
        } else if (this instanceof Portal) {
            clip = new AudioClip(new File("sounds/portal.wav").toURI().toString());
        } else if (this instanceof Boulder) {
            clip = new AudioClip(new File("sounds/boulder_push.mp3").toURI().toString());
        } else if (this instanceof SwitchEntity) {
            clip = new AudioClip(new File("sounds/switch_press.mp3").toURI().toString());
        } else if (this instanceof Door) {
            clip = new AudioClip(new File("sounds/door_open.wav").toURI().toString());
        } else {
            clip = new AudioClip(new File("sounds/item_pickup.wav").toURI().toString());
        }
        clip.play();
            
    }
    

}
