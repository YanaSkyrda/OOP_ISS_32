package com.game.checkersgame.models;

public class Coordinates {
    public int xCoord;
    public int yCoord;

    public Coordinates(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    @Override
    public int hashCode() {
        return xCoord + yCoord * 13;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinates))
            return false;

        Coordinates coordinate = (Coordinates) (o);
        return coordinate.xCoord == xCoord && coordinate.yCoord == yCoord;
    }



}