package com.game.checkersgame.controller;

import android.graphics.Canvas;

import com.game.checkersgame.bot.CheckerBot;
import com.game.checkersgame.models.Board;
import com.game.checkersgame.models.Cell;
import com.game.checkersgame.models.Coordinates;
import com.game.checkersgame.models.Figure;
import com.game.checkersgame.models.Figures;
import com.game.checkersgame.utils.Utils;
import com.game.checkersgame.view.MainView;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    public static final String playerSide = "BLACK";
    private MainView caller;
    private int fieldSize;

    private Board board;

    public void setCheckers(Figures checkers) {
        this.checkers = checkers;
    }

    private Figures checkers;

    private Figure selected;


    private String gameState = "BLACK";
    private volatile boolean playerBeatRow = false;
    private List<Coordinates> availableCoords = new ArrayList<>();
    private Coordinates lastPosition;

    private int botDelay = 250;

    public GameController(int fieldSize) {
        this.fieldSize = fieldSize;
        caller = null;
        board = new Board(fieldSize);
        checkers = new Figures(fieldSize);
    }

    public GameController(int fieldSize, MainView caller) {
        this.fieldSize = fieldSize;
        this.caller = caller;
        board = new Board(fieldSize);
        checkers = new Figures(fieldSize);
    }

    public void draw(Canvas canvas) {
        board.draw(canvas);
        checkers.draw(canvas);
    }

    public void activatePlayer(float x, float y) {
        if (gameState.equals(playerSide)) {
            Coordinates tapCoords = Utils.toCoords(x, y, fieldSize, board.getCellSize());

            if (selected == null) {
                trySelect(tapCoords);
                // update view
                callUpdate();
            } else {

                if (coordsInAvailableCells(tapCoords)) {
                    playerBeatRow = doPlayerStep(tapCoords);
                    updateQueens();
                    lastPosition = checkers.find(selected);

                    if (playerBeatRow && canBeatMore(tapCoords)) {
                        playerBeatRow = true;
                        trySelectBeatable(lastPosition);
                    } else {
                        playerBeatRow = false;
                        unselectAll();
                    }
                } else {
                    unselectAll();
                    callUpdate();
                    return;
                }
                // update view
                callUpdate();

                if (!playerBeatRow) {
                    unselectAll();
                    reverseState();
                }
            }


        }
    }
    public void startBotCycle() {
        while (!Thread.currentThread().isInterrupted()) {
            if (gameState.equals(other(playerSide))) {
                if (!playerBeatRow) {
                    startBotTurn();
                    reverseState();
                    updateQueens();
                }
            }
        }
    }
    public void startBotTurn() {
        boolean botBeatRow = false;
        Coordinates checkerCoords = CheckerBot.chooseChecker(checkers, other(playerSide), botDelay);
        if (checkerCoords == null)
            return;
        Figure checkerBot = checkers.getChecker(checkerCoords);
        trySelect(checkerCoords);
        // update view
        callUpdate();
        Coordinates moveCoords = CheckerBot.chooseMove(checkers, checkerBot, botDelay);
        if (moveCoords == null)
            return;
        botBeatRow = checkers.move(checkerBot, moveCoords.xCoord, moveCoords.yCoord);
        unselectAll();
        // update view
        callUpdate();

        while (botBeatRow && canBeatMore(moveCoords)) {
            unselectAll();
            trySelectBeatable(moveCoords);

            callUpdate();
            moveCoords = CheckerBot.chooseMove(checkers, checkerBot, botDelay);
            if (moveCoords != null)
                botBeatRow = checkers.move(checkerBot, moveCoords.xCoord, moveCoords.yCoord);
            else botBeatRow = false;

            callUpdate();
        }
        unselectAll();
    }

    public void callUpdate() {
        if (caller != null)
            caller.invalidate();
    }

    private boolean doPlayerStep(Coordinates tapCoords) {
        Coordinates oldCoords = checkers.find(selected);
        if (oldCoords.equals(tapCoords))
            return false;
        boolean hasBeaten = checkers.move(selected, tapCoords.xCoord, tapCoords.yCoord);
        return hasBeaten;
    }

    public boolean canBeatMore(Coordinates lastPosition) {
        if (lastPosition == null)
            return false;
        Figure checker = checkers.getChecker(lastPosition);
        List<Coordinates> beatable = checkers.canBeat(checker);
        return !beatable.isEmpty();
    }

    private boolean coordsInAvailableCells(Coordinates tapCoords) {
        if (tapCoords == null)
            return false;
        for (Coordinates coords : availableCoords) {
            if (tapCoords.equals(coords))
                return true;
        }
        return false;
    }

    private void trySelect(Coordinates coords) {
        if (coords == null)
            return;
        unselectAll();
        Cell cell = board.getCell(coords);
        Figure checker = checkers.getChecker(coords);

        if (cell != null && checker != null &&
                cell.getCellColor().equals("BLACK") && checker.getColor().equals(gameState)) {
            cell.setCurrentState("ACTIVE");
            selected = checker;

            showAvailable(checker);
        }
    }


    private void trySelectBeatable(Coordinates coords) {
        if (coords == null)
            return;
        Cell cell = board.getCell(coords);
        Figure checker = checkers.getChecker(coords);

        if (cell != null && checker != null &&
                cell.getCellColor().equals("BLACK") && checker.getColor().equals(gameState)) {
            cell.setCurrentState("ACTIVE");
            selected = checker;

            activateAvailableBeatable(checker);
        }
    }


    public void reverseState() {
        gameState = other(gameState);
    }

    public static String other(String color) {
        if (color.equals("BLACK"))
            return "WHITE";
        else
            return "BLACK";
    }

    private void unselectAll() {
        selected = null;
        availableCoords.clear();
        board.unselectAll();
    }

    private void showAvailable(Figure checker) {
        if (activationPreparation(checker)) return;
        availableCoords = checkers.buildAvailable(checker);
        setActiveToAvailable();
    }

    private void activateAvailableBeatable(Figure checker) {
        if (activationPreparation(checker)) return;
        availableCoords = checkers.canBeat(checker);
        setActiveToAvailable();
    }

    private boolean activationPreparation(Figure checker) {
        if (checker == null)
            return true;
        availableCoords.clear();
        board.unselectAll();
        return false;
    }

    private void setActiveToAvailable() {
        for (Coordinates coords : availableCoords) {
            board.getCell(coords).setCurrentState("ACTIVE");
        }
    }

    private void updateQueens() {
        checkers.updateQueens();
    }

    public String getStatusOfGame() {
        if (checkers.count("BLACK") == 0)
            return "White won";
        if (checkers.count("WHITE") == 0)
            return "Black won";
        if (checkers.checkIfDraw(gameState))
            return "Draw";
        if (gameState.equals("WHITE")) {
            return "White turn";
        } else return "Black turn";
    }
    public static String getPlayerSide() {
        return playerSide;
    }

    public MainView getCaller() {
        return caller;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public Board getBoard() {
        return board;
    }

    public Figures getCheckers() {
        return checkers;
    }

    public Figure getSelected() {
        return selected;
    }

    public String getGameState() {
        return gameState;
    }

    public boolean isPlayerBeatRow() {
        return playerBeatRow;
    }

    public List<Coordinates> getAvailableCoords() {
        return availableCoords;
    }

    public Coordinates getLastPosition() {
        return lastPosition;
    }

    public int getBotDelay() {
        return botDelay;
    }

    public void setBotDelay(int botDelay) {
        this.botDelay = botDelay;
    }
    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

}
