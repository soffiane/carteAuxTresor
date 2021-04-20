package fr.carbonit.treasurehunt.model;

public enum LineType {
    MAP("C"),
    ADVENTURER("A"),
    MOUNTAIN("M"),
    TREASURE("T");

    private String value;

    LineType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
