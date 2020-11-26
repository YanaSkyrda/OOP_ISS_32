package com.game.checkersgame.models;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {
    private String cellColor;
    private int colorIdle;

    private Paint paint;


    public static final int black = Color.rgb(50, 50, 50);
    public static final int white = Color.WHITE;
    public static final int colorActive = Color.rgb(10, 250, 20);

    public Cell(String color) {
        cellColor = color;
        this.paint = new Paint();

        if (color == "BLACK") {
            setColorIdle(black);
        } else if (color == "WHITE") {
            setColorIdle(white);
        }
    }

    public int getColorIdle() {
        return colorIdle;
    }

    public void setColorIdle(int colorIdle) {
        this.colorIdle = colorIdle;
        this.paint.setColor(colorIdle);
    }

    public Paint getPaint() {
        return paint;
    }


}
