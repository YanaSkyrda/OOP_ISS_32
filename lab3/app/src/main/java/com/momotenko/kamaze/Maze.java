package com.momotenko.kamaze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Maze implements Drawable {
    private Paint wallPaint;
    private List<List<Boolean>> maze;
    private int size;
    private Point startPoint;
    private Point endPoint;
    private int longestPath = 0;


    public Maze(int size) {
        wallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wallPaint.setColor(Color.MAGENTA);
        this.size = size;
        endPoint = new Point(1,1);
        generate();
    }

    private boolean visitedCell(int x, int y) {
        if (x < 0 || y < 0 || x >= size - 1 || y >= size - 1) {
            return true;
        }
        return maze.get(y - 1).get(x)
                || maze.get(y).get(x - 1) //top
                || maze.get(y + 1).get(x) //right
                || maze.get(y).get(x + 1); //bottom
    }

    private void generate() {
        maze = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            maze.add(new ArrayList<>());
            for (int j = 0; j < size; ++j) {
                maze.get(i).add(i % 2 != 0 && j % 2 != 0
                        && i < size - 1 && j < size - 1);
            }
        }
        Random random = new Random();
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(1, 1));
        while (stack.size() > 0) {
            Point current = stack.peek();
            List<Point> unusedNeighbors = new LinkedList<>();

            if (current.x > 2) {
                if (!visitedCell(current.x - 2, current.y)) {
                    unusedNeighbors.add(new Point(current.x - 2, current.y));
                }
            }

            if (current.y > 2) {
                if (!visitedCell(current.x, current.y - 2)) {
                    unusedNeighbors.add(new Point(current.x, current.y - 2));
                }
            }

            if (current.x < size - 2) {
                if (!visitedCell(current.x + 2, current.y)) {
                    unusedNeighbors.add(new Point(current.x + 2, current.y));
                }
            }

            if (current.y < size - 2) {
                if (!visitedCell(current.x, current.y + 2)) {
                    unusedNeighbors.add(new Point(current.x, current.y + 2));
                }
            }

            if (unusedNeighbors.size() > 0) {
                int rnd = random.nextInt(unusedNeighbors.size());
                Point direction = unusedNeighbors.get(rnd);
                int diffX = (direction.x - current.x) / 2;
                int diffY = (direction.y - current.y) / 2;
                maze.get(current.y + diffY).set(current.x + diffX,true);
                stack.push(direction);
            } else {
                if (longestPath < stack.size()) {
                    longestPath = stack.size();
                    startPoint = current;
                }
                stack.pop();
            }
        }
    }

   public boolean playerCanGo(int x, int y){
        if (x < 0 || y < 0){
            return false;
        }

        if (x > size || y > size){
            return false;
        }

        return maze.get(y).get(x);
   }


    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint(){
        return endPoint;
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        float cellSize = (rect.right-rect.left)/size;
        for (int i = 0; i < size; ++i){
            for (int j = 0; j < size;++j){
                if (!maze.get(i).get(j)){
                    float left = j*cellSize+rect.left;
                    float top = i*cellSize+rect.top;
                    canvas.drawRect(left,
                            top,
                            left+cellSize,
                            top+cellSize,
                            wallPaint);
                }
            }
        }
    }
}
