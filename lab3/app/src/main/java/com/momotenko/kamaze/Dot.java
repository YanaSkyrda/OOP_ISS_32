package com.momotenko.kamaze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Dot implements Drawable {
    protected Paint paint;

    public Point getPoint() {
        return point;
    }

    protected Point point;
    protected int mazeSize;

    public Dot(Paint paint, Point point, int mazeSize) {
        this.paint = paint;
        this.point = point;
        this.mazeSize = mazeSize;
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        float cellSize = (rect.right-rect.left)/ mazeSize;
        canvas.drawRect(
                rect.left +  point.x*cellSize,
                rect.top +  point.y*cellSize,
                rect.left + point.x *cellSize + cellSize,
                rect.top + point.y* cellSize + cellSize,
                paint);
    }
}
