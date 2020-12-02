package com.oop.labs.gesturetracker;

import com.oop.labs.gesturetracker.dtw.DTWAlgorithm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DTWAlgorithmTest {
    @Test
    public void shouldCorrectlyFindResult() {
        double[] A = {-1d, 8d, -8d, 12d, -10d, 20d, -5d, 13d, 2d};
        double[] B = {-2d, 8d, -5d, 11d, -10d, 2d};
        assertEquals(4.55, DTWAlgorithm.compute(A, B), 0.1d);
    }
}