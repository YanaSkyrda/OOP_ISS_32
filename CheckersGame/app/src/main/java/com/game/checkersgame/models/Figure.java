package com.game.checkersgame.models;

import android.graphics.Color;
import android.graphics.Paint;

public class Figure {
    private String color;
    private String status = "NORMAL";
    private Paint paint;

    private static int errorColor = Color.rgb(0, 250, 0);
    public static final int blackNormal = Color.rgb(210,105,30);
    public  final int whiteNormal = Color.rgb(200, 200, 200);
    public Figure(String checkerColor) {
        color = checkerColor;
        paint = new Paint();
    }

    public Paint updatePaint() {
        switch (color) {
            case "WHITE":
                paint.setColor(whiteNormal);
                break;
            case "BLACK":
                paint.setColor(blackNormal);
                break;
            default:
                paint.setColor(errorColor);
        }
        return paint;
    }

    public String getColor() {
        return color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        updatePaint();
    }


}
