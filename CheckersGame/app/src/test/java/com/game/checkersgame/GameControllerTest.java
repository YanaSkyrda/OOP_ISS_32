package com.game.checkersgame;

import com.game.checkersgame.controller.GameController;
import com.game.checkersgame.models.Coordinates;
import com.game.checkersgame.models.Figure;
import com.game.checkersgame.models.Figures;
import com.game.checkersgame.utils.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameControllerTest {
    private GameController fieldController;
    private int[][] defaultTable = new int[][]{
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0}};

    private int[][] movedTable = new int[][]{
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 0, 0, 0, 0, 0},
            {0, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0}};

    private int[][] drawTable = new int[][]{
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0}};

    private int[][] whiteWon = new int[][]{
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    private int[][] blackWon = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0}};

    private int[][] whiteBeatsInRow = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 2, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0}};


    private Figures checkersDefault;
    private Figures checkersMoved;
    private Figures checkersDraw;
    private Figures checkersWhiteWon;
    private Figures checkersBlackWon;
    private Figures whiteRowBeat;

    @Before
    public void initCheckers() {
        int checkerSize = 10;
        int fieldSize = 8;
        checkersDefault = Utils.generateFromTable(defaultTable);
        checkersDefault.setCheckerSize(checkerSize);

        checkersMoved = Utils.generateFromTable(movedTable);
        checkersMoved.setCheckerSize(checkerSize);

        checkersDraw = Utils.generateFromTable(drawTable);
        checkersDraw.setCheckerSize(checkerSize);

        checkersWhiteWon = Utils.generateFromTable(whiteWon);
        checkersWhiteWon.setCheckerSize(checkerSize);

        checkersBlackWon = Utils.generateFromTable(blackWon);
        checkersBlackWon.setCheckerSize(checkerSize);

        whiteRowBeat = Utils.generateFromTable(whiteBeatsInRow);
        whiteRowBeat.setCheckerSize(checkerSize);
    }

    @Before
    public void setUp() {
        fieldController = new GameController(8);
        fieldController.getCheckers().setCheckerSize(10);
        fieldController.getBoard().setCellSize(10);
    }

    @Test
    public void initTest() {
        Assert.assertEquals(12, fieldController.getCheckers().count("BLACK"));
        Assert.assertEquals(12, fieldController.getCheckers().count("WHITE"));

        Assert.assertEquals(8, fieldController.getFieldSize());
        Assert.assertEquals(8, fieldController.getBoard().getFieldSize());

        Assert.assertEquals("WHITE", fieldController.getBoard().getCell(new Coordinates(0, 0)).getCellColor());
        Assert.assertEquals("BLACK", fieldController.getBoard().getCell(new Coordinates(1, 0)).getCellColor());

        Assert.assertTrue(checkersDefault.sameAs(fieldController.getCheckers()));
    }

    @Test
    public void botStep() {
        Assert.assertTrue(checkersDefault.sameAs(fieldController.getCheckers()));
        fieldController.setBotDelay(0);
        fieldController.startBotTurn();
        Assert.assertFalse(checkersDefault.sameAs(fieldController.getCheckers()));
    }

    @Test
    public void playerStep() {
        Assert.assertTrue(checkersDefault.sameAs(fieldController.getCheckers()));
        Assert.assertEquals("BLACK", GameController.playerSide);
        fieldController.activatePlayer(5, 55);
        Assert.assertTrue(checkersDefault.sameAs(fieldController.getCheckers()));
        Assert.assertEquals("IDLE", fieldController.getBoard().getCell(new Coordinates(0, 5)).getCurrentState());
        Assert.assertEquals("ACTIVE", fieldController.getBoard().getCell(new Coordinates(1, 4)).getCurrentState());
        Assert.assertEquals("IDLE", fieldController.getBoard().getCell(new Coordinates(2, 3)).getCurrentState());

        fieldController.activatePlayer(15, 45);
        Assert.assertTrue(checkersMoved.sameAs(fieldController.getCheckers()));
        Assert.assertFalse(fieldController.canBeatMore(new Coordinates(1, 4)));
    }

    @Test
    public void playerMove() {
        Assert.assertTrue(checkersDefault.sameAs(fieldController.getCheckers()));
        Assert.assertEquals("BLACK", GameController.playerSide);
        fieldController.activatePlayer(5, 55);
        boolean beated = fieldController.getCheckers().move(fieldController.getSelected(), 1, 4);
        Assert.assertFalse(beated);
        Assert.assertTrue(checkersMoved.sameAs(fieldController.getCheckers()));
        Assert.assertFalse(fieldController.canBeatMore(new Coordinates(1, 4)));
    }

    @Test
    public void beatingInARow() {
        fieldController.setCheckers(whiteRowBeat);
        fieldController.setGameState("WHITE");
        fieldController.startBotTurn();
        int countBlacks = fieldController.getCheckers().count("BLACK");
        Assert.assertTrue(10 > countBlacks);

    }

    @Test
    public void drawState() {
        fieldController.setCheckers(checkersDraw);
        Assert.assertEquals("Draw", fieldController.getStatusOfGame());
    }

    @Test
    public void whiteWon() {
        fieldController.setCheckers(checkersWhiteWon);
        Assert.assertEquals("White won", fieldController.getStatusOfGame());
    }


}
