package com.momotenko.kamaze;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Exit extends Dot {
    public Exit( Point point, int mazeSize) {
        super(getPaint(), point, mazeSize);
    }

    private static Paint getPaint(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        return paint;
    }
}
