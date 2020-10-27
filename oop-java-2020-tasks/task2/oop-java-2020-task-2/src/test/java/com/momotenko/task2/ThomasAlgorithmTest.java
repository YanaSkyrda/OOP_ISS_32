package com.momotenko.task2;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThomasAlgorithmTest extends TestCase {
    final double THRESHOLD = 0.001;

    List<List<Double>> test1Matrix = new ArrayList<>(
            new ArrayList<>(Arrays.asList(
                    new ArrayList<>(Arrays.asList(1.0,2.0,0.0,0.0)),
                    new ArrayList<>(Arrays.asList(3.0,2.0,-1.0,0.0)),
                    new ArrayList<>(Arrays.asList(0.0,-5.0,-20.0,5.0)),
                    new ArrayList<>(Arrays.asList(0.0,0.0,2.0,-4.0)))));

    List<Double> test1D = new ArrayList<Double>(
            Arrays.asList(4.0,-5.0,-1.0,3.0));

    List<Double> test1Answer = new ArrayList<Double>(
            Arrays.asList(-681.0/130, 1201.0/260, - 96.0/65, -387.0/260));

    //arr1.size() == arr2.size() !!
    private boolean testHelper(List<Double> arr1, List<Double> arr2, double eps){
        for (int i = 0, size = arr2.size(); i < size; ++i){
            if (Math.abs(arr1.get(i) - arr2.get(i)) > eps){
                return false;
            }
        }

        return true;
    }

    @Test
    public void testSolveLinear(){
        ThomasAlgorithm a = new ThomasAlgorithm(test1Matrix, test1D);

        assertTrue(testHelper(a.solveLinear(), test1Answer, THRESHOLD));
    }

}
