package domain;

import java.util.ArrayList;
import java.util.List;

public class Crowd extends StoryObject {

    private List<Human> people = new ArrayList<>();

    public Crowd(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addAndMoveHumanToCrowd(Human human) {

        human.moveToTarget(this);
        people.add(human);
    }

    public List<Human> getPeople() {
        return people;
    }
}
