package com.game.checkersgame.utils;

import android.view.MotionEvent;

import com.game.checkersgame.controller.GameController;

public class TouchHandler implements Runnable{
    private MotionEvent event;
    private GameController fieldController;

    public TouchHandler(MotionEvent event, GameController fieldController) {
        this.event = event;
        this.fieldController = fieldController;
    }


    public void run() {
        if (event == null)
            return;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            fieldController.activatePlayer(event.getX(), event.getY());
        }
    }
}
