package com.momotenko.kamaze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameManager extends GestureDetector.SimpleOnGestureListener {
    private List<Drawable> drawables;
    private View view;
    private Player player;
    private Exit exit;
    private Maze maze;
    private Rect rect = new Rect();
    private int mazeSize = 5;
    private int screenSize = 5;

    public GameManager() {
        create();
    }

    void create(){
        maze = new Maze(mazeSize);
        exit = new Exit(maze.getEndPoint(),mazeSize);
        player = new Player(maze.getStartPoint(), mazeSize);
        drawables = new ArrayList<>();
        drawables.add(maze);
        drawables.add(exit);
        drawables.add(player);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int diffX = 0, diffY = 0;
        diffX = Math.round(e2.getX() - e1.getX());
        diffY = Math.round(e2.getY() - e1.getY());

        if (Math.abs(diffX) > Math.abs(diffY)) {
            diffX = diffX > 0 ? 1 : -1;
            diffY = 0;
        } else {
            diffX = 0;
            diffY = diffY > 0 ? 1 : -1;
        }


        int stepX = player.getX(), stepY = player.getY();
        while(maze.playerCanGo(stepX+diffX,stepY+diffY)){
            stepX += diffX;
            stepY +=diffY;

            if (diffX!=0){
                if (maze.playerCanGo(stepX,stepY+1) || maze.playerCanGo(stepX,stepY-1)){
                    break;
                }
            }

            if (diffY != 0){
                if (maze.playerCanGo(stepX+1,stepY) || maze.playerCanGo(stepX-1,stepY)){
                    break;
                }
            }
        }
        player.goTo(stepX, stepY);

        if (player.getPoint().equals(exit.getPoint())){
            mazeSize += 2;
            create();
        }

        view.invalidate();
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    public void draw(Canvas canvas) {
        for (Drawable i : drawables) {
            i.draw(canvas, rect);
        }
    }


    public void setScreenSize(int width, int height) {
        screenSize = Math.min(width, height);
        rect.set(
                (width - screenSize) / 2,
                (height - screenSize) / 2,
                (width + screenSize) / 2,
                (height + screenSize) / 2
        );
    }

    public void setView(View view) {
        this.view = view;
    }
}
