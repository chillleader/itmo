package domain;

import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class CrowdTest {

    private final Crowd crowd = new Crowd(0, 0);

    private final Human human = spy(new Human(10, 10, "Bob"));

    @Test
    public void testAddAndMoveHuman() {

        crowd.addAndMoveHumanToCrowd(human);

        verify(human, times(1)).moveToTarget(crowd);
        assertTrue(crowd.getPeople().contains(human));
    }
}
