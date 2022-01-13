package de.techfak.gse.mbiyik.model;

public class Coordinate {
    private final int xCoord;
    private final int yCoord;

    public Coordinate(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
    public int getX() {
        return xCoord;
    }
    public int getY() {
        return yCoord;
    }
}
