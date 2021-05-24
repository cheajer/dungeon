package unsw.dungeon;

/**
 *  Class for the boulder entity, a rock in game which the player is able to push.
 *  Boulder extends entity.
 */
public class Boulder extends Entity{
    /**
    * Boulder constructor, calls the super to construct the wall.
    * @param x int The x-coordinate to spawn the Boulder.
    * @param y int The y-coordinate to spawn the Boulder.
    * */
    public Boulder (Dungeon dungeon, int x, int y){
        super(dungeon, x, y);
    }

    public void moveBoulder() {
        int player_relative_X = this.getX() - this.getDungeon().getPlayer().getX();
        int player_relative_Y = this.getY() - this.getDungeon().getPlayer().getY();
        if (player_relative_X == 1) {
            if (this.getDungeon().collisionDetection(this.getX() + 1, this.getY(), this)) {
                this.checkSwitchUnderneath();
                this.setX(this.getX() + 1);
            }
        } else if (player_relative_X == -1) {
            if (this.getDungeon().collisionDetection(this.getX() - 1, this.getY(), this)) {
                this.checkSwitchUnderneath();
                this.setX(this.getX() - 1);
            }
        } else if (player_relative_Y == 1) {
            if (this.getDungeon().collisionDetection(this.getX(), this.getY() + 1, this)) {
                this.checkSwitchUnderneath();
                this.setY(this.getY() + 1);
            }
        } else if (player_relative_Y == -1) {
            if (this.getDungeon().collisionDetection(this.getX(), this.getY() - 1, this)) {
                this.checkSwitchUnderneath();
                this.setY(this.getY() - 1);
            }
        }
        this.playClip();
    }

}
