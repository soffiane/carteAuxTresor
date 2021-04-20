package fr.carbonit.treasurehunt.model;

public enum Orientation {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("O");

    private String value;

    Orientation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
