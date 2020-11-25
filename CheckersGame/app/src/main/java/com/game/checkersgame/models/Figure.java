package com.game.checkersgame.models;

import android.graphics.Color;
import android.graphics.Paint;

public class Figure {
    private String color;
    private String state = "default";
    private Paint paint;

    private static int errorColor = Color.rgb(0, 250, 0);

    public  final int whiteNormal = Color.rgb(200, 200, 200);
    public static final int blackNormal = Color.rgb(230, 20, 20);

    public Figure(String checkerColor) {
        color = checkerColor;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    public String getColor() {
        return color;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        updatePaint();
    }

    public Paint updatePaint() {
        switch (color) {
            case "white":
                paint.setColor(whiteNormal);
                break;
            case "black":
                paint.setColor(blackNormal);
                break;
            default: // можна потім убрать і поставить брейк
                paint.setColor(errorColor);
        }
        return paint;
    }
}
