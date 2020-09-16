package com.momotenko.task2;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;

public class MainTest extends TestCase {
    final double THRESHOLD = 0.001;

    ArrayList<ArrayList<Double>> test1Matrix = new ArrayList<ArrayList<Double>>(
            new ArrayList<ArrayList<Double>>(Arrays.asList(
                    new ArrayList<Double>(Arrays.asList(1.0,2.0,0.0,0.0)),
                    new ArrayList<Double>(Arrays.asList(3.0,2.0,-1.0,0.0)),
                    new ArrayList<Double>(Arrays.asList(0.0,-5.0,-20.0,5.0)),
                    new ArrayList<Double>(Arrays.asList(0.0,0.0,2.0,-4.0)))));

    ArrayList<Double> test1D = new ArrayList<Double>(
            Arrays.asList(4.0,-5.0,-1.0,3.0));

    ArrayList<Double> test1Answer = new ArrayList<Double>(
            Arrays.asList(-681.0/130, 1201.0/260, - 96.0/65, -387.0/260));

    //arr1.size() == arr2.size() !!
    private boolean testHelper(ArrayList<Double> arr1, ArrayList<Double> arr2, double eps){
        for (int i = 0, size = arr2.size(); i < size; ++i){
            if (Math.abs(arr1.get(i) - arr2.get(i)) > eps){
                return false;
            }
        }

        return true;
    }

    @Test
    public void testSolveLinear(){
        Main a = new Main(test1Matrix, test1D);

        assertTrue(testHelper(a.solveLinear(), test1Answer, THRESHOLD));
    }

}
