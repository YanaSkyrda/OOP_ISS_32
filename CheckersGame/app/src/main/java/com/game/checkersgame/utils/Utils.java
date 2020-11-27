package com.game.checkersgame.utils;

import com.game.checkersgame.models.Coordinates;
import com.game.checkersgame.models.Figure;
import com.game.checkersgame.models.Figures;

public class Utils {
    public static Coordinates toCoords(float x, float y, int fieldSize, int cellSize) {
        Coordinates res = new Coordinates((int) (x / cellSize), (int) (y / cellSize));
        if (res.xCoord >= fieldSize || res.yCoord >= fieldSize)
            return null;
        return res;
    }
    public static final int NONE = 0;
    public static final int WNORMAL = 1;
    public static final int BNORMAL = 2;
    public static final int WQUEEN = 3;
    public static final int BQUEEN = 4;

    public static Figures generateFromTable(int[][] table) {
        Figures res = new Figures();
        res.fieldSize = table.length;
        res.checkersTable = new Figure[table.length][table.length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                Figure newChecker = null;
                switch (table[i][j]) {
                    case NONE:
                        break;
                    case BNORMAL:
                        newChecker = new Figure("BLACK");
                        break;
                    case WNORMAL:
                        newChecker = new Figure("WHITE");
                        break;
                    case BQUEEN:
                        newChecker = new Figure("BLACK");
                        newChecker.setStatus("QUEEN");
                        break;
                    case WQUEEN:
                        newChecker = new Figure("WHITE");
                        newChecker.setStatus("QUEEN");
                        break;
                    default:
                        break;
                }
                if (newChecker != null) {
                    res.checkersTable[i][j] = newChecker;
                }
            }
        }
        return res;
    }
}
