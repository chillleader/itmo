package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * После того, как толпа вновь разразилась ликующими криками,
 * Артур обнаружил, что он скользит по воздуху к одному из
 * величественных окон во втором этаже здания, перед которым
 * стоял помост, с которого оратор обращался к народу.
 */
public class ActionScene {

    private Human artur;
    private Human speaker;
    private Building building;
    private Crowd crowd;
    private boolean isSceneSet = false;

    public void initScene() {

        artur = new Human(5, 5, "Artur");
        speaker = new Human( 0, 0, "Speaker");
        building = new Building(0, 0, BuildingType.GREAT_BUILDING);
        crowd = new Crowd(1, 0);
        crowd.addAndMoveHumanToCrowd(new Human(0, 0, "A"));
        crowd.addAndMoveHumanToCrowd(new Human(0, 0, "B"));
        crowd.addAndMoveHumanToCrowd(new Human(0, 0, "C"));
        isSceneSet = true;
    }

    public void startAction() {
        if (!isSceneSet) throw new IllegalStateException("Scene must be set first");

        artur.moveToTarget(building);
    }

    public Human getArtur() {
        return artur;
    }

    public Human getSpeaker() {
        return speaker;
    }

    public Building getBuilding() {
        return building;
    }

    public Crowd getCrowd() {
        return crowd;
    }
}
