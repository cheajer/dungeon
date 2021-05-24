package unsw.dungeon;

public interface Observer {
    public abstract boolean checkCompletion();
    public abstract void update();
}