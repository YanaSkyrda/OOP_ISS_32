package com.game.checkersgame.models;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {
    private String cellColor;
    private int colorIdle;

    private Paint paint;

    private String currentState = "IDLE";
    public int black = Color.rgb(50, 50, 50);
    public int white = Color.WHITE;
    public int colorActive = Color.rgb(10, 250, 20);

    public Cell(String color) {
        cellColor = color;
        this.paint = new Paint();

        if (color.equals("BLACK") ) {
            setColorIdle(black);
        } else if (color.equals("WHITE")) {
            setColorIdle(white);
        }
    }
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
        if (currentState.equals("IDLE")) {
            paint.setColor(colorIdle);
        } else if (currentState.equals("ACTIVE")) {
            paint.setColor(colorActive);
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

    public String getCellColor() {
        return cellColor;
    }
    public String getCurrentState() {
        return currentState;
    }
}
