package fr.carbonit.treasurehunt.model;

public class Line {

    private String line;
    private LineType lineType;

    public Line() {
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }
}
