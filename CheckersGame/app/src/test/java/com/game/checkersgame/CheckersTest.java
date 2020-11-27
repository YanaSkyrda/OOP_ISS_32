package com.game.checkersgame;

import com.game.checkersgame.models.Figure;
import com.game.checkersgame.models.Figures;
import com.game.checkersgame.utils.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckersTest {
    private int[][] clearTable = new int[][]{
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}};

    private int[][] defaultTable = new int[][]{
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0}};

    private int[][] resverseDefaultTable = new int[][]{
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0}};

    private int[][] whiteBeatBlackTable = new int[][]{
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 2, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    private int[][] queenFigtsBlack = new int[][]{
            {0, 3, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    private Figures checkersDefault;
    private Figures checkersEmpty;
    private Figures checkersReverseDefault;
    private Figures checkersQueenFigtsBlack;
    private Figures checkersWhiteBeatBlack;

    private Figures inited;

    @Before
    public void initCheckers() {
        int checkerSize = 10;
        int fieldSize = 8;
        checkersDefault = Utils.generateFromTable(defaultTable);
        checkersDefault.setCheckerSize(checkerSize);

        checkersEmpty = Utils.generateFromTable(clearTable);
        checkersEmpty.setCheckerSize(checkerSize);

        checkersReverseDefault = Utils.generateFromTable(resverseDefaultTable);
        checkersReverseDefault.setCheckerSize(checkerSize);

        checkersWhiteBeatBlack = Utils.generateFromTable(whiteBeatBlackTable);
        checkersWhiteBeatBlack.setCheckerSize(checkerSize);

        checkersQueenFigtsBlack = Utils.generateFromTable(queenFigtsBlack);
        checkersQueenFigtsBlack.setCheckerSize(checkerSize);

        inited = new Figures(8);
    }

    @Test
    public void getChecker() {
        Assert.assertNull(checkersEmpty.getChecker(0, 0));
        Assert.assertNull(checkersEmpty.getChecker(1, 0));
        Assert.assertNull(checkersEmpty.getChecker(1000, 1000));

        Assert.assertNull(checkersDefault.getChecker(0, 0));
        Assert.assertNotNull(checkersDefault.getChecker(1, 0));
        Assert.assertNull(checkersDefault.getChecker(1000, 1000));
    }

    @Test
    public void checkerMoveBeats() {
        Figure checkerToMove = checkersWhiteBeatBlack.getChecker(1, 0);
        Assert.assertTrue(checkersWhiteBeatBlack.move(checkerToMove, 2, 1));
    }

    @Test
    public void checkerMoveDontBeat() {
        Figure checkerToMove = checkersWhiteBeatBlack.getChecker(1, 0);
        Assert.assertFalse(checkersWhiteBeatBlack.move(checkerToMove, 0, 1));
    }

    @Test
    public void queenMoveBeats() {
        Figure checkerToMove = checkersQueenFigtsBlack.getChecker(1, 0);
        Assert.assertFalse(checkersQueenFigtsBlack.move(checkerToMove, 2, 1));
        Assert.assertTrue(checkersQueenFigtsBlack.move(checkerToMove, 6, 5));
    }
}
