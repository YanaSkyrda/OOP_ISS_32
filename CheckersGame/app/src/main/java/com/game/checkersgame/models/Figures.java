package com.game.checkersgame.models;

import android.graphics.Canvas;

import com.game.checkersgame.utils.Utils;

public class Figures {
    private int cellSize;
    private int checkerSize;
    private int fieldSize;
    private Figure[][] checkers;

    public Figures(int fieldSize) {
        this.fieldSize = fieldSize;
        initCheckers();
    }

    private void initCheckers() {
        checkers = new Figure[fieldSize][fieldSize];

        initWhiteCheckers();
        initBlackCheckers();
    }

    private void initWhiteCheckers() {
        for (int row = 0; row < 2; row++) {
            fillRow(row, "WHITE");
        }
    }

    private void initBlackCheckers() {
        for (int row = fieldSize - 1; row > fieldSize - 3; row--) {
            fillRow(row, "BLACK");
        }
    }

    private void fillRow(int row, String color) {
        for (int i = 0; i < fieldSize; i++) {
            if ((row + i) % 2 == 1)
                checkers[row][i] = new Figure(color);
        }
    }


    public void draw(Canvas canvas) {
        cellSize = canvas.getWidth() / fieldSize;
        checkerSize = (int) (cellSize * 0.2);
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if ((j + i) % 2 == 1 &&
                        checkers[i][j] != null) {
                    drawChecker(i, j, checkers[i][j], canvas);
                }
            }
        }
    }

    private void drawChecker(int row, int column, Figure checker, Canvas canvas) {
        int cx = column * cellSize + cellSize / 2;
        int cy = row * cellSize + cellSize / 2;
        canvas.drawCircle(cx, cy, checkerSize, checker.updatePaint());
    }

    public Figure getChecker(float x, float y) {
        Coordinates coords = Utils.toCoords(x, y, fieldSize, cellSize);
        if (coords == null)
            return null;
        return checkers[coords.yCoord][coords.xCoord];
    }

    public Figure getChecker(int x, int y) {
        return checkers[y][x];
    }

    public Coordinates find(Figure checker) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (checkers[i][j] == null)
                    continue;
                if (checkers[i][j].equals(checker))
                    return new Coordinates(j, i);
            }
        }
        return null;
    }

    public void remove(int x, int y) {
        checkers[y][x] = null;
    }

    public boolean move(Figure checker, int x, int y) {
        boolean haveBeaten = false;
        Coordinates found = find(checker);
        if (found != null)
            haveBeaten = beat(found.xCoord, found.yCoord, x, y);
        checkers[y][x] = checker;
        if (found != null) {
            remove(found.xCoord, found.yCoord);
        }
        return haveBeaten;
    }

    public boolean beat(int xStart, int yStart, int xEnd, int yEnd) {
        int xDir = xEnd - xStart;
        xDir = xDir / Math.abs(xDir);
        int yDir = yEnd - yStart;
        yDir = yDir / Math.abs(yDir);
        int xCurr = xStart;
        int yCurr = yStart;
        boolean checkIfHaveBeaten = false;
        while (xEnd - xCurr != 0) {
            xCurr += xDir;
            yCurr += yDir;
            if (checkers[yCurr][xCurr] != null)
                checkIfHaveBeaten = true;
            checkers[yCurr][xCurr] = null;
        }
        return checkIfHaveBeaten;
    }

    public int getCheckerSize() {
        return checkerSize;
    }
}
