package domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionSceneTest {

    private ActionScene actionScene;

    @Before
    public void resetScene() {
        actionScene = new ActionScene();
    }

    @Test
    public void testSceneSetup() {
        actionScene.initScene();
        assertEquals(5, actionScene.getArtur().getX());
        assertEquals(5, actionScene.getArtur().getY());

        assertEquals(BuildingType.GREAT_BUILDING, actionScene.getBuilding().getType());
        assertTrue(actionScene.getCrowd().getPeople().size() >= 3);
        assertEquals(actionScene.getSpeaker().getX(), actionScene.getBuilding().getX());
        assertEquals(actionScene.getSpeaker().getY(), actionScene.getBuilding().getY());
    }

    @Test(expected = IllegalStateException.class)
    public void testActionWithoutSetup() {
        actionScene.startAction();
    }

    @Test
    public void testAction() {
        actionScene.initScene();
        actionScene.startAction();
        assertEquals(actionScene.getBuilding().getX(), actionScene.getArtur().getX());
        assertEquals(actionScene.getBuilding().getY(), actionScene.getArtur().getY());
    }
}
