package fr.carbonit.treasurehunt.model;

import static fr.carbonit.treasurehunt.Constantes.SEPARATOR;

public class Coordinates {

    private int horizontalAxis;
    private int verticalAxis;

    public Coordinates() {
    }

    public Coordinates(int horizontalAxis, int verticalAxis) {
        this.horizontalAxis = horizontalAxis;
        this.verticalAxis = verticalAxis;
    }

    public Coordinates(Line line) {
        String[] contents = line.getLine().split(SEPARATOR);
        this.horizontalAxis = Integer.parseInt(contents[2]);
        this.verticalAxis = Integer.parseInt(contents[1]);
    }

    public int getHorizontalAxis() {
        return horizontalAxis;
    }

    public void setHorizontalAxis(int horizontalAxis) {
        this.horizontalAxis = horizontalAxis;
    }

    public int getVerticalAxis() {
        return verticalAxis;
    }

    public void setVerticalAxis(int verticalAxis) {
        this.verticalAxis = verticalAxis;
    }
}
