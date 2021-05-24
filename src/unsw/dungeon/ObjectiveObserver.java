package unsw.dungeon;
import java.util.*;

public class ObjectiveObserver implements Observer {
    private int enemyCount = 0;
    private int treasureCount = 0;
    private int switchCount = 0;
    private boolean exitFlag = false;
    private Dungeon dungeon;
    public ObjectiveObserver(Dungeon dungeon, int eCount, int sCount, int tCount) {
        this.enemyCount = eCount;
        this.treasureCount = tCount;
        this.switchCount = sCount;
        this.dungeon = dungeon;
    }

    @Override
    public void update() {
        this.treasureCount = Treasure.getTreasureCount();
        this.enemyCount = Enemy.getEnemyCount();
        this.switchCount = SwitchEntity.getSwitchCount();
        this.exitFlag = Exit.getCompletionFlag();
    }
    
    public void update(Treasure obj) {
        this.treasureCount = Treasure.getTreasureCount();
    }

    public void update(Enemy obj) {
        this.enemyCount = Enemy.getEnemyCount();
    }

    public void update(SwitchEntity obj) {
        this.switchCount = SwitchEntity.getSwitchCount();
    }

    public void update(Exit obj) {
        this.exitFlag = Exit.getCompletionFlag();
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public int getTreasureCount() {
        return treasureCount;
    }

    public int getSwitchCount() {
        return switchCount;
    }

    @Override
    public boolean checkCompletion() {
        if (dungeon.getCondition().equals("AND")) {
            for (Objective o : dungeon.getObjectives()) {
                if (o instanceof EnemyObjective && enemyCount != 0)
                    return false;
                if (o instanceof TreasureObjective && treasureCount != 0)
                    return false;
                if (o instanceof SwitchObjective && switchCount != 0)
                    return false;
                if (o instanceof ExitObjective && !exitFlag)
                    return false;
            }
            return true;
        } else if (dungeon.getCondition().equals("ANDOR")) {
            Objective mainGoal = dungeon.getObjectives().get(0);
            List<Objective> subGoals = new ArrayList<>();
            for (int i=1; i < dungeon.getObjectives().size(); i++) {
                subGoals.add(dungeon.getObjectives().get(i));
            }

            if (mainGoal instanceof EnemyObjective && enemyCount != 0)
                return false;
            if (mainGoal instanceof TreasureObjective && treasureCount != 0)
                return false;
            if (mainGoal instanceof SwitchObjective && switchCount != 0)
                return false;
            if (mainGoal instanceof ExitObjective && !exitFlag)
                return false;

            for (Objective o : subGoals) {
                if (o instanceof EnemyObjective && enemyCount == 0)
                    return true;
                if (o instanceof TreasureObjective && treasureCount == 0)
                    return true;
                if (o instanceof SwitchObjective && switchCount == 0)
                    return true;
            }
        } else {
            for (Objective o : dungeon.getObjectives()) {
                if (o instanceof EnemyObjective && enemyCount == 0)
                    return true;
                if (o instanceof TreasureObjective && treasureCount == 0)
                    return true;
                if (o instanceof SwitchObjective && switchCount == 0)
                    return true;
                if (o instanceof ExitObjective && exitFlag == true)
                    return true;            
            }

        }
        return false;
    }
}