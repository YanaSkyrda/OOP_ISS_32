package com.game.checkersgame.models;


import android.graphics.Canvas;


public class Board {
    private int fieldSize;
    private int cellSize;
    private Cell[][] cellsMatrix;


    public Board(int fieldSize) {
        this.fieldSize = fieldSize;
        initializeCells(fieldSize, fieldSize);
    }
    public void unselectAll() {
        for (Cell[] cellRow : cellsMatrix) {
            for (Cell cell : cellRow) {
                cell.setCurrentState("IDLE");
            }
        }
    }

    private void initializeCells(int columns, int rows) {
        cellsMatrix = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i + j) % 2 == 0) {
                    cellsMatrix[j][i] = new Cell("WHITE");
                } else
                    cellsMatrix[j][i] = new Cell("BLACK");
            }
        }
    }

    public void draw(Canvas canvas) {
        cellSize = canvas.getWidth() / fieldSize;
        drawGrid(fieldSize, fieldSize, canvas);
    }


    private void drawGrid(int columns, int rows, Canvas canvas) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                drawCell(cellsMatrix[j][i], j, i, canvas);
            }
        }
    }

    private void drawCell(Cell cell, int x, int y, Canvas canvas) {

        canvas.drawRect(x * cellSize, y * cellSize,
                x * cellSize + cellSize, y * cellSize + cellSize,
                cell.getPaint());
    }

    public Cell getCell(Coordinates coords) {
        if (!border(coords))
            return null;
        return cellsMatrix[coords.xCoord][coords.yCoord];
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int size) {
        cellSize = size;
    }



    private boolean border(Coordinates coords) {
        return border(coords.xCoord) && border(coords.yCoord);
    }

    private boolean border(int a, int da) {
        return border(a + da);
    }

    private boolean border(int a) {
        return (a >= 0) && a < fieldSize;
    }

    public int getFieldSize() {
        return fieldSize;
    }
}
