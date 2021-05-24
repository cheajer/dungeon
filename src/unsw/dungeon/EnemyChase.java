package unsw.dungeon;

/**
 * EnemyChase is an aggressive move pattern for the enemies to follow which essentially chases the player.
 */
public class EnemyChase implements EnemyMovement {
    /**
     * movement defines the movement rules the enemies should take for an aggressive chase.
     * @param enemy Enemy, the enemy following the pattern and to be moved.
     * @param player Player, to get the coordinates of the player and move accordingly.
     */
    public void movement(Enemy enemy, Player player) {
        if (enemy instanceof WaterDragon && !WaterDragon.getFlag()) {
            return;
        } else {
            if (player.getX() > enemy.getX() 
                        && enemy.getDungeon().collisionDetection(enemy.getX()+1, enemy.getY(), enemy)) {
                enemy.setX(enemy.getX()+1);
            } else if (player.getX() < enemy.getX()
                        && enemy.getDungeon().collisionDetection(enemy.getX()-1, enemy.getY(), enemy)) {
                enemy.setX(enemy.getX()-1);
            } else if (player.getY() > enemy.getY()
                        && enemy.getDungeon().collisionDetection(enemy.getX(), enemy.getY()+1, enemy)) {
                enemy.setY(enemy.getY()+1);
            } else if (player.getY() < enemy.getY() 
                        && enemy.getDungeon().collisionDetection(enemy.getX(), enemy.getY()-1, enemy)) {
                enemy.setY(enemy.getY()-1);
            }
        }
    }

    // public void movement(Enemy enemy, Player player) {

    //     ArrayList<Pos> queue = new ArrayList<>();
    //     boolean found = false;
    //     Pos enemyPos = new Pos(enemy.getX(), enemy.getY());
    //     Pos playerPos = new Pos(player.getX(), player.getY());
    //     Dungeon dungeon = player.getDungeon();
    //     //boolean[][] enemyPath = new boolean[dungeon.getWidth()][dungeon.getHeight()];
    //     //initialiseEnemyPath(enemyPath, dungeon.getWidth(), dungeon.getHeight());
    //     //enemyPath[enemyPos.x][enemyPos.y] = true;
    //     ArrayList<Pos> enemyPath = new ArrayList<>();
    //     enemyPath.add(enemyPos);
    //     queue.add(enemyPos);
    //     Pos head = enemyPos;
        

    //     //if (!enemyPath[enemyPos.x+1][enemyPos.y] && dungeon.collisionDetection(enemyPos.x-1, enemyPos.y, enemy))

    //     //Breadth First search to find path to player.
    //     while (queue.size() !=0 && !found) {
    //         ArrayList<Pos> surrPos = new ArrayList<>();

    //         head = queue.get(queue.size()-1);
    //         queue.remove(queue.size()-1);
    //         if (head.x > 0) // Can move left
    //             surrPos.add(new Pos(enemyPos.x-1, enemyPos.y));
    //         if (head.x < dungeon.getWidth()-1) // Can move right
    //             surrPos.add(new Pos(enemyPos.x+1, enemyPos.y));
    //         if (head.y > 0) // Can move up
    //             surrPos.add(new Pos(enemyPos.x, enemyPos.y-1));
    //         if (head.y < dungeon.getHeight()-1) // Can move down
    //             surrPos.add(new Pos(enemyPos.x, enemyPos.y+1));

    //         for (Pos pos : surrPos) {
    //             //System.out.println("surrposlooptest");
    //             if (pos.x == player.getX() && pos.y == player.getY()) {
    //                 found=true;
    //                 break;
    //             } else if (!hasTraversed(enemyPath, pos.x, pos.y) && dungeon.collisionDetection(pos.x, pos.y, enemy)) {
    //                 //System.out.println("elseiftest");
    //                 enemyPath.add(head);
    //                 queue.add(pos);
    //             }
    //         }
    //     }

        

    //     for(Pos pos : enemyPath) {
    //         System.out.println("("+pos.x +","+ pos.y+")");
    //         if (head.x != enemyPos.x && head.y!=enemyPos.y) {
    //             System.out.println("setpathtest");
    //             enemyPos.x=pos.x;
    //             enemyPos.y=pos.y;
    //             break;
    //         }
    //     }


    //     enemy.setX(enemyPos.x);
    //     enemy.setY(enemyPos.y);

    // }

    // public boolean hasTraversed(ArrayList<Pos> enemyPath, int x, int y) {
    //     for (Pos pos : enemyPath) {
    //         if (pos.x==x && pos.y==y)
    //             return true;
    //     }
    //     return false;
    // }
    // public void movement(Enemy enemy, Player player) {

    //     Pos enemyPos = new Pos(enemy.getX(), enemy.getY());
    //     Dungeon dungeon = player.getDungeon();
    //     //enemyPath[enemyPos.x][enemyPos.y] = true;

    // //         if (enemyPos.y > 0) // Can move up
    // //         if (enemyPos.y < dungeon.getHeight()-1) // Can move down


    //     if (player.getX() > enemy.getX() && this.left) {
    //         if (enemyPos.x < dungeon.getWidth()-1 && enemy.getDungeon().collisionDetection(enemy.getX()+1, enemy.getY(), enemy)) {
    //             enemy.setX(enemy.getX()+1);
    //             resetDirection();
    //             return;
    //         } else {
    //             this.left=false;
    //             movement(enemy, player);
    //         }
    //     }
    //     if (player.getX() < enemy.getX() && this.right) {
    //         if (enemyPos.x > 0 && enemy.getDungeon().collisionDetection(enemy.getX()-1, enemy.getY(), enemy)) {
    //             enemy.setX(enemy.getX()-1);
    //             resetDirection();
    //             return;
    //         } else {
    //             this.right=false;
    //             movement(enemy, player);
    //         }
    //     }
    //     if (player.getY() > enemy.getY() && this.up) {
    //         if (enemyPos.y < dungeon.getHeight()-1 && enemy.getDungeon().collisionDetection(enemy.getX(), enemy.getY()+1, enemy)) {
    //             enemy.setY(enemy.getY()+1);
    //             resetDirection();
    //             return;
    //         } else {
    //             this.up=false;
    //             movement(enemy, player);
    //         }
    //     } 
    //     if (player.getY() < enemy.getY() && this.down) {
    //         if (enemyPos.y > 0 && enemy.getDungeon().collisionDetection(enemy.getX(), enemy.getY()-1, enemy)) {
    //             enemy.setY(enemy.getY()-1);
    //             resetDirection();
    //             return;
    //         } else {
    //             this.down=false;
    //             movement(enemy, player);
    //         }

    //     }
    // }

    // public void resetDirection() {
    //     this.up=true;
    //     this.down=true;
    //     this.right=true;
    //     this.left=true;
    // }

    // public void tryMove(Enemy enemy) {
    //     Pos enemyPos = new Pos(enemy.getX(), enemy.getY());
    //     Dungeon dungeon = enemy.getDungeon();
    //     if (enemyPos.y > 0 && dungeon.collisionDetection(enemy.getX(), enemy.getY()-1, enemy)) {
    //         enemy.setY(enemy.getY()-1);
    //     }
    //     if (enemyPos.y < dungeon.getHeight()-1 && enemy.getDungeon().collisionDetection(enemy.getX(), enemy.getY()+1, enemy)) {
    //         enemy.setY(enemy.getY()+1);
    //     }
    //     if (enemyPos.x > 0 && enemy.getDungeon().collisionDetection(enemy.getX()-1, enemy.getY(), enemy)) {
    //         enemy.setX(enemy.getX()-1);
    //     }
    //     if (enemyPos.x < dungeon.getWidth()-1 && enemy.getDungeon().collisionDetection(enemy.getX()+1, enemy.getY(), enemy)) {
    //         enemy.setX(enemy.getX()+1);
    //     }

    // }


    // private void initialiseEnemyPath(boolean[][] array, int width, int height) {
    //     for (int i=0; i < width; i++) {
    //         for (int j=0; j < height; j++) {
    //             array[i][j]= false;
    //         }
    //     }
    // }


}