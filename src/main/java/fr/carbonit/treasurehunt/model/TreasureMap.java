package fr.carbonit.treasurehunt.model;

public class TreasureMap {

    private final int mapWidth;
    private final int mapHeight;
    private boolean isMountain;
    private int treasures;
    private Adventurer adventurer;

    public TreasureMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.isMountain = false;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public boolean isMountain() {
        return isMountain;
    }

    public void setMountain(boolean mountain) {
        isMountain = mountain;
    }

    public int getTreasures() {
        return treasures;
    }

    public void setTreasures(int treasures) {
        this.treasures = treasures;
    }

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public void setAdventurer(Adventurer adventurer) {
        this.adventurer = adventurer;
    }
}
