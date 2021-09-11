package domain;

public class Human extends StoryObject implements Movable {

    private final String name;

    public Human(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        System.out.println("Human " + name + " was spawned at (" + x + ", " + y + ")");
    }

    @Override
    public void moveToTarget(StoryObject target) {
        setX(target.getX());
        setY(target.getY());
    }
}
