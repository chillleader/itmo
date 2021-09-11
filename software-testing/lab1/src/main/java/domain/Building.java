package domain;

public class Building extends StoryObject {

    private final BuildingType type;

    public BuildingType getType() {
        return type;
    }

    public Building(int x, int y, BuildingType type) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void setCoordinates() {

    }
}
