package com.game.checkersgame.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Figures {
    public static final double CHECKER_BORDER_MULTIPLIER = 1.15;
    public static final double QUEEN_INNER_CIRCLE_MULTIPLIER = 0.7;
    public static final double CHECKER_RADIUS_MULTIPLIER = 0.35;
    private int cellSize;
    private int checkerSize;
    public int fieldSize;
    public Figure[][] checkersTable;
    private Paint queenPaint;

    public Figures() {
        queenPaint = new Paint();
        queenPaint.setColor(Color.rgb(50, 50, 50));
    }

    public Figures(int fieldSize) {
        this.fieldSize = fieldSize;
        initCheckers();
        queenPaint = new Paint();
        queenPaint.setColor(Color.rgb(50, 50, 50));
    }

    private void initCheckers() {
        checkersTable = new Figure[fieldSize][fieldSize];

        initWhiteCheckers();
        initBlackCheckers();
    }

    private void initWhiteCheckers() {
        for (int row = 0; row < 3; row++) {
            initRow(row, "WHITE");
        }
    }

    private void initBlackCheckers() {
        for (int row = fieldSize - 1; row > fieldSize - 3 -1; row--) {
            initRow(row, "BLACK");
        }
    }

    private void initRow(int row, String color) {
        for (int i = 0; i < fieldSize; i++) {
            if ((row + i) % 2 == 1)
                checkersTable[row][i] = new Figure(color);
        }
    }


    public void draw(Canvas canvas) {
        cellSize = canvas.getWidth() / fieldSize;
        checkerSize = (int) (cellSize * CHECKER_RADIUS_MULTIPLIER);
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if ((j + i) % 2 == 1 &&
                        checkersTable[i][j] != null) {
                    drawChecker(i, j, checkersTable[i][j], canvas);
                }
            }
        }
    }

    private void drawChecker(int row, int column, Figure checker, Canvas canvas) {
        int cx = column * cellSize + cellSize / 2;
        int cy = row * cellSize + cellSize / 2;
        canvas.drawCircle(cx, cy, (float) (checkerSize * CHECKER_BORDER_MULTIPLIER), queenPaint);
        canvas.drawCircle(cx, cy, checkerSize, checker.updatePaint());
        if (checker.getStatus().equals("QUEEN")) {
            canvas.drawCircle(cx, cy, (int) (checkerSize * QUEEN_INNER_CIRCLE_MULTIPLIER), queenPaint);
        }
    }

    public Figure getChecker(int x, int y) {
        if (border(x) && border(y))
            return checkersTable[y][x];
        return null;
    }

    public Figure getChecker(Coordinates coords) {
        return checkersTable[coords.yCoord][coords.xCoord];
    }

    public Coordinates find(Figure checker) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (checkersTable[i][j] == null)
                    continue;
                if (checkersTable[i][j].equals(checker))
                    return new Coordinates(j, i);
            }
        }
        return null;
    }

    public void remove(int x, int y) {
        checkersTable[y][x] = null;
    }

    public boolean move(Figure checker, int x, int y) {
        boolean haveBeaten = false;
        Coordinates found = find(checker);
        if (found != null && !found.equals(new Coordinates(x, y)))
            haveBeaten = beat(found.xCoord, found.yCoord, x, y);
        checkersTable[y][x] = checker;
        if (found != null) {
            remove(found.xCoord, found.yCoord);
        }
        return haveBeaten;
    }

    private boolean beat(int xStart, int yStart,
                         int xEnd, int yEnd) {
        boolean haveBeaten = false;
        int xDir = xEnd - xStart;
        int yDir = yEnd - yStart;
        if (xDir == 0 || yDir == 0)
            return false;
        xDir = xDir / Math.abs(xDir);
        yDir = yDir / Math.abs(yDir);
        int xCurr = xStart;
        int yCurr = yStart;
        while (xEnd - xCurr != 0) {
            xCurr += xDir;
            yCurr += yDir;
            if (checkersTable[yCurr][xCurr] != null)
                haveBeaten = true;
            checkersTable[yCurr][xCurr] = null;
        }
        return haveBeaten;
    }

    public void setCheckerSize(int size) {
        checkerSize = size;
    }

    public int getCheckerSize() {
        return checkerSize;
    }

    public void updateQueens() {
        queensRow(0, "BLACK");
        queensRow(fieldSize - 1, "WHITE");
    }

    private void queensRow(int i, String black) {
        for (Figure checker : checkersTable[i]) {
            if (checker == null)
                continue;
            if (checker.getColor().equals(black)) {
                checker.setStatus("QUEEN");
            }
        }
    }

    public List<Coordinates> buildAvailable(Figure checker) {
        Coordinates coords = find(checker);
        int x = coords.xCoord;
        int y = coords.yCoord;
        List<Coordinates> res = new ArrayList<>();
        if (checker.getStatus().equals("NORMAL")) {
            if (checker.getColor().equals("BLACK")) {
                tryMove(x, y, -1, -1, checker, res);
                tryMove(x, y, 1, -1, checker, res);

                tryBeat(x, y, -1, 1, checker, res);
                tryBeat(x, y, 1, 1, checker, res);
            } else {
                tryBeat(x, y, -1, -1, checker, res);
                tryBeat(x, y, 1, -1, checker, res);

                tryMove(x, y, -1, 1, checker, res);
                tryMove(x, y, 1, 1, checker, res);
            }

        } else if (checker.getStatus().equals("QUEEN")) {
            moveInVector(x, y, 1, 1, checker, res);
            moveInVector(x, y, 1, -1, checker, res);
            moveInVector(x, y, -1, 1, checker, res);
            moveInVector(x, y, -1, -1, checker, res);
        }

        return res;
    }

    private void moveInVector(int x, int y, int vx, int vy, Figure current, List<Coordinates> res) {
        while (border(x + vx) && border(y + vy)) {
            x = x + vx;
            y = y + vy;
            Figure other = getChecker(x, y);
            if (other != null) {
                if (other.getColor().equals(current.getColor()))
                    return;
                if (border(x + vx) && border(y + vy)) {
                    other = getChecker(x + vx, y + vy);
                    if (other == null) {
                        res.add(new Coordinates(x + vx, y + vy));
                    }
                    return;
                }
            } else {
                res.add(new Coordinates(x, y));
            }
        }
    }

    private void beatInVector(int x, int y, int vx, int vy, Figure current, List<Coordinates> res) {
        while (border(x + vx) && border(y + vy)) {
            x = x + vx;
            y = y + vy;
            Figure other = getChecker(x, y);
            if (other != null) {
                if (other.getColor().equals( current.getColor()))
                    return;
                if (border(x + vx) && border(y + vy)) {
                    other = getChecker(x + vx, y + vy);
                    if (other == null) {
                        res.add(new Coordinates(x + vx, y + vy));
                    }
                    return;
                }
            }
        }
    }

    private void tryMove(int x, int y, int dx, int dy, Figure current, List<Coordinates> res) {
        if (border(x, dx) && border(y, dy)) {
            Figure checkerOther = getChecker(x + dx, y + dy);
            if (checkerOther == null) {
                res.add(new Coordinates(x + dx, y + dy));
            } else if (!checkerOther.getColor().equals(current.getColor()) //!!!!!!! іквелс
                    && border(x, 2 * dx)
                    && border(y, 2 * dy)) {
                checkerOther = getChecker(x + 2 * dx, y + 2 * dy);
                if (checkerOther == null) {
                    res.add(new Coordinates(x + 2 * dx, y + 2 * dy));
                }
            }
        }
    }

    public Map<Figure, List<Coordinates>> getAvailableListByColor(String color) {
        Map<Figure, List<Coordinates>> res = new HashMap<>();
        for (Figure[] row : checkersTable) {
            for (Figure checker : row) {
                if (checker == null)
                    continue;
                if (checker.getColor().equals(color)) {
                    List<Coordinates> availableForThisChecker = buildAvailable(checker);
                    res.put(checker, availableForThisChecker);
                }
            }
        }
        return res;
    }
// возможно дефекти з іквелс
    private void tryBeat(int x, int y, int dx, int dy, Figure current, List<Coordinates> res) {
        if (border(x, dx) && border(y, dy)) {
            Figure checkerOther = getChecker(x + dx, y + dy);
            if (checkerOther != null && !checkerOther.getColor().equals(current.getColor())
                    && border(x, 2 * dx)
                    && border(y, 2 * dy)) {
                checkerOther = getChecker(x + 2 * dx, y + 2 * dy);
                if (checkerOther == null) {
                    res.add(new Coordinates(x + 2 * dx, y + 2 * dy));
                }
            }
        }
    }

    private boolean border(int a, int da) {
        return border(a + da);
    }

    private boolean border(int a) {
        return (a >= 0) && a < fieldSize;
    }

    public List<Coordinates> canBeat(Figure checker) {
        List<Coordinates> res = new ArrayList<>();
        Coordinates coords = find(checker);
        int x = coords.xCoord;
        int y = coords.yCoord;
        if (checker.getStatus() == "NORMAL") {
            tryBeat(x, y, -1, 1, checker, res);
            tryBeat(x, y, 1, 1, checker, res);
            tryBeat(x, y, -1, -1, checker, res);
            tryBeat(x, y, 1, -1, checker, res);
        } else {
            beatInVector(x, y, 1, 1, checker, res);
            beatInVector(x, y, 1, -1, checker, res);
            beatInVector(x, y, -1, 1, checker, res);
            beatInVector(x, y, -1, -1, checker, res);
        }
        return res;
    }

    public int count(String color) {
        int cnt = 0;
        for (Figure[] row : checkersTable) {
            for (Figure checker : row) {
                if (checker != null && checker.getColor().equals(color))
                    cnt++;
            }
        }
        return cnt;
    }

    public boolean checkIfDraw(String color) {
        Map<Figure, List<Coordinates>> available = getAvailableListByColor(color);
        for (Map.Entry<Figure, List<Coordinates>> entry : available.entrySet()) {
            if (!entry.getValue().isEmpty())
                return false;
        }
        return true;
    }

    public boolean sameAs(Figures other) {
        if (fieldSize != other.fieldSize)
            return false;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (checkersTable[i][j] == null) {
                    if (other.checkersTable[i][j] == null)
                        continue;
                    else return false;
                } else if (other.checkersTable[i][j] == null) {
                    if (checkersTable[i][j] == null){
                        continue;
                    }

                    else {
                        return false;
                    }
                }

                if (!checkersTable[i][j].getColor().equals(  other.checkersTable[i][j].getColor()) ||
                        !checkersTable[i][j].getStatus().equals( other.checkersTable[i][j].getStatus()))
                    return false;

            }
        }
        return true;
    }
}
