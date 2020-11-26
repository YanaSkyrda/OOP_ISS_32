package com.game.checkersgame.utils;

import com.game.checkersgame.models.Coordinates;

public class Utils {
    public static Coordinates toCoords(float x, float y, int fieldSize, int cellSize) {
        Coordinates res = new Coordinates((int) (x / cellSize), (int) (y / cellSize));
        if (res.xCoord >= fieldSize || res.yCoord >= fieldSize)
            return null;
        return res;
    }

}
