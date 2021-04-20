package fr.carbonit.treasurehunt.model;

public enum Moves {
    MOVE_FORWARD("A"),
    MOVE_RIGHT("G"),
    MOVE_LEFT("D");

    private final String value;

    Moves(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
