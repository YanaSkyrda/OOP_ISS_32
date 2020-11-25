package com.game.checkersgame.models;
import android.graphics.Paint;

public class Cell {
    private int color;
    private Paint paint;
    private int size;

    public Cell(int size, int color) {
        this.size = size;
        this.color = color;
        this.paint = new Paint();
        this.paint.setColor(color);
    }

    public int getSize() {
        return size;
    }


    public Paint getPaint() {
        return paint;
    }


}