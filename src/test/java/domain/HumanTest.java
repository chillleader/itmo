package domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class HumanTest {

    private final Human human = new Human(0, 0, "Artur");

    @Test
    public void testMove() {

        Human otherHuman = new Human(10, 10, "Jesse");
        human.moveToTarget(otherHuman);
        assertEquals(otherHuman.getX(), human.getX());
        assertEquals(otherHuman.getY(), human.getY());
    }
}
