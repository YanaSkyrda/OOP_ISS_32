package com.game.checkersgame.view;
import com.game.checkersgame.models.*;
import android.content.Context;
import android.graphics.*;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;

public class MainView extends View {


    private int fieldSize = 8;
    private int cellSize;

    private Cell blackCell;
    private Cell whiteCell;

    public MainView(Context context) {
        super(context);

        blackCell = new Cell("BLACK");
        whiteCell = new Cell("WHITE");
    }

    private void drawGrid(int columns, int rows, Canvas canvas) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i + j) % 2 == 0) {
                    drawCell(blackCell, j, i, canvas);
                } else
                    drawCell(whiteCell, j, i, canvas);
            }
        }
    }

    private void drawCell(Cell cell, int x, int y, Canvas canvas) {
        cellSize = canvas.getWidth() / fieldSize;
        canvas.drawRect(x * cellSize, y * cellSize,
                x * cellSize+cellSize, y * cellSize + cellSize,
                cell.getPaint());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cellSize = canvas.getWidth() / fieldSize;
        drawGrid(fieldSize, fieldSize, canvas);

    }
}