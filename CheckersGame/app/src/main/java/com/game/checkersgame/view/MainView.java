package com.game.checkersgame.view;
import com.game.checkersgame.controller.GameController;
import com.game.checkersgame.utils.TouchHandler;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class MainView extends View {

    private ExecutorService executor = newFixedThreadPool(5);

    private static final int FIELD_SIZE = 8;
    private GameController fieldController;
    private Context context;
    private Future botFuture;

    public MainView(Context context) {
        super(context);
        this.context = context;
        fieldController = new GameController(FIELD_SIZE, this);

        assignTouchListener();
        startBotThread();

    }

    private void startBotThread() {
        botFuture = executor.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    fieldController.startBotCycle();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private void assignTouchListener() {
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Future touchFuture = executor.submit(new TouchHandler(event, fieldController));
                try {
                    touchFuture.get();
                } catch (Exception e) {
                    Log.e("Touch exception", Arrays.toString(e.getStackTrace()));
                    Thread.currentThread().interrupt();
                }
                return true;
            }
        });
    }

    public void updateView() {
        invalidate();
    }

    public String getStatusOfGame() {
        return fieldController.getStatusOfGame();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        fieldController.draw(canvas);
    }

    public Future getBotFuture() {
        return botFuture;
    }

    public GameController getFieldController() {
        return fieldController;
    }
}