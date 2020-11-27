package com.game.checkersgame;

import com.game.checkersgame.models.Board;
import com.game.checkersgame.models.Cell;
import com.game.checkersgame.models.Coordinates;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CellsTest {

    private Board board;

    @Before
    public void prepareBoard() {
        board = new Board(8);
    }

    @Test
    public void getCell() {
        Cell whiteCell = board.getCell(new Coordinates(0, 0));
        Cell blackCell = board.getCell(new Coordinates(1, 0));
        Assert.assertNotNull(whiteCell);
        Assert.assertNotNull(blackCell);
        Assert.assertEquals("WHITE", whiteCell.getCellColor());
        Assert.assertEquals("BLACK", blackCell.getCellColor());

        Cell outer = board.getCell(new Coordinates(10000, 10000));
        Assert.assertNull(outer);
    }

    @Test
    public void unselectAll() {
        Cell whiteCell = board.getCell(new Coordinates(0, 0));
        Cell blackCell = board.getCell(new Coordinates(1, 0));
        Assert.assertEquals("IDLE", whiteCell.getCurrentState());
        Assert.assertEquals("IDLE", blackCell.getCurrentState());

        whiteCell.setCurrentState("ACTIVE");


        Assert.assertEquals("ACTIVE", whiteCell.getCurrentState());


        board.unselectAll();
        Assert.assertEquals("IDLE", whiteCell.getCurrentState());

    }
}