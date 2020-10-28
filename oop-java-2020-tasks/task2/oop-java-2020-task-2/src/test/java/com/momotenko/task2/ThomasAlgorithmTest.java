package com.momotenko.task2;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ThomasAlgorithmTest extends TestCase {
    final double THRESHOLD = 0.001;

    List<List<Double>> test1Matrix = new ArrayList<>(
            new ArrayList<>(Arrays.asList(
                    new ArrayList<>(Arrays.asList(1.0,2.0,0.0,0.0)),
                    new ArrayList<>(Arrays.asList(3.0,2.0,-1.0,0.0)),
                    new ArrayList<>(Arrays.asList(0.0,-5.0,-20.0,5.0)),
                    new ArrayList<>(Arrays.asList(0.0,0.0,2.0,-4.0)))
            ));

    List<List<Double>> test2Matrix = new ArrayList<>(
            new ArrayList<>(Arrays.asList(
                    new ArrayList<>(Arrays.asList(2.0,3.0,0.0,0.0,0.0,0.0)),
                    new ArrayList<>(Arrays.asList(6.0,4.0,2.0,0.0,0.0,0.0)),
                    new ArrayList<>(Arrays.asList(0.0,6.0,2.0,5.0,0.0,0.0)),
                    new ArrayList<>(Arrays.asList(0.0,0.0,4.0,-8.0,-9.0,0.0)),
                    new ArrayList<>(Arrays.asList(0.0,0.0,0.0,-7.0,2.0,-8.0)),
                    new ArrayList<>(Arrays.asList(0.0,0.0,0.0,0.0,5.0,-5.0)))
            ));

    List<List<Double>> test3Matrix = new ArrayList<>(
            new ArrayList<>(Arrays.asList(
                    new ArrayList<>(Arrays.asList(5.0,4.0,0.0,0.0,0.0,0.0)),
                    new ArrayList<>(Arrays.asList(1.0,5.0,3.0,0.0,0.0,0.0)),
                    new ArrayList<>(Arrays.asList(0.0,3.0,5.0,1.0,0.0,0.0)),
                    new ArrayList<>(Arrays.asList(0.0,0.0,1.0,10.0,1.0,0.0)),
                    new ArrayList<>(Arrays.asList(0.0,0.0,0.0,2.0,9.0,3.0)),
                    new ArrayList<>(Arrays.asList(0.0,0.0,0.0,0.0,1.0,2.0)))
            ));

    List<Double> test1D = new ArrayList<>(
            Arrays.asList(4.0,-5.0,-1.0,3.0));

    List<Double> test2D = new ArrayList<>(
            Arrays.asList(8.0,4.0,1.0,-8.0,5.0,-8.0));


    List<Double> test3D = new ArrayList<>(
            Arrays.asList(6.0,8.0,7.0,-3.0,2.0,1.0));

    //arr1.size() == arr2.size() !!
    private boolean checkAnswer(List<List<Double>> matrix, List<Double> d, List<Double> answer, double eps){
        int size = matrix.size();
        if (size != answer.size())
            return false;

        Double temp;

        for (int i = 0; i < size; ++i){
            temp = 0.0;

            for (int j = 0; j < size; ++j){
                if (matrix.get(i).get(j) != null){
                    temp += (matrix.get(i).get(j) * answer.get(j));
                }
            }

            if (Math.abs(temp - d.get(i)) > eps){
                return false;
            }
        }

        return true;
    }

    private Double getRandom(int cap){
        return Double.valueOf(new Random().nextInt(2*cap)- cap);
    }

    private List<List<Double>> generateMatrix(int cap){
    List<List<Double>> result = new ArrayList<>(cap);
    for (int i = 0; i< cap; ++i){
        result.add(new ArrayList<>());
    }

        List<Double> temp = new ArrayList<>(Arrays.asList(new Double[cap]));

        temp.set(0, getRandom(cap));
        temp.set(1, getRandom(cap));
        result.set(0,temp);
        temp = new ArrayList<>(Arrays.asList(new Double[cap]));
        temp.set(cap-1, getRandom(cap));
        temp.set(cap-2, getRandom(cap));
        result.set(cap-1,temp);

        for (int i = 1; i < cap-1; ++i){
            temp = new ArrayList<>(Arrays.asList(new Double[cap]));
            temp.set(i-1, getRandom(cap));
            temp.set(i, getRandom(cap));
            temp.set(i+1,getRandom(cap));
            result.set(i,temp);
        }

        return result;
    }

    private List<Double> generateList(int cap){
        List<Double> result = new ArrayList<>(cap);

        for(int i = 0; i < cap;++i){
            result.add(getRandom(cap));
        }

        return result;
    }

    @Test
    public void testSolveLinear(){
        ThomasAlgorithm a = new ThomasAlgorithm(test1Matrix, test1D);

        final long start = System.currentTimeMillis();
        assertTrue(checkAnswer(test1Matrix,test1D,a.solveLinear(),THRESHOLD));
        final long end = System.currentTimeMillis();

        System.out.println("test linear 1 time: " + (end-start));
    }

    @Test
    public void testSolveLinear2(){
        ThomasAlgorithm a = new ThomasAlgorithm(test2Matrix, test2D);

        final long start = System.currentTimeMillis();
        assertTrue(checkAnswer(test2Matrix,test2D,a.solveLinear(),THRESHOLD));
        final long end = System.currentTimeMillis();

        System.out.println("test linear 2 time: " + (end-start));
    }

    @Test
    public void testSolveLinear3(){
        ThomasAlgorithm a = new ThomasAlgorithm(test3Matrix, test3D);

        final long start = System.currentTimeMillis();
        assertTrue(checkAnswer(test3Matrix,test3D,a.solveLinear(),THRESHOLD));
        final long end = System.currentTimeMillis();

        System.out.println("test linear 3 time: " + (end-start));
    }

    @Test
    public void testSolveLinear4(){
        List<List<Double>> generatedMatrix = generateMatrix(5000);
        List<Double> generatedList = generateList(5000);

        ThomasAlgorithm a = new ThomasAlgorithm(generatedMatrix, generatedList);

        long start = System.currentTimeMillis();
        assertTrue(checkAnswer(generatedMatrix,generatedList,a.solveLinear(),THRESHOLD));
        long end = System.currentTimeMillis();

        System.out.println("test linear 4 time: " + (end-start));

        start = System.currentTimeMillis();
        assertTrue(checkAnswer(generatedMatrix,generatedList,a.solveParallel(),THRESHOLD));
        end = System.currentTimeMillis();

        System.out.println("test parallel 4 time: " + (end-start));
    }

    @Test
    public void testSolveLinear5(){
        List<List<Double>> generatedMatrix = generateMatrix(20000);
        List<Double> generatedList = generateList(20000);

        ThomasAlgorithm a = new ThomasAlgorithm(generatedMatrix, generatedList);

        List<Double> answer;

        long start = System.currentTimeMillis();
        answer = a.solveLinear();
        long end = System.currentTimeMillis();

        assertTrue(checkAnswer(generatedMatrix,generatedList,answer,THRESHOLD));

        System.out.println("test linear 5 time: " + (end-start));

        start = System.currentTimeMillis();
        answer = a.solveParallel();
        end = System.currentTimeMillis();

        assertTrue(checkAnswer(generatedMatrix,generatedList,answer,THRESHOLD));

        System.out.println("test parallel 5 time: " + (end-start));
    }

    @Test
    public void testSolveParallel(){
        ThomasAlgorithm a = new ThomasAlgorithm(test1Matrix, test1D);

        final long start = System.currentTimeMillis();
        assertTrue(checkAnswer(test1Matrix,test1D,a.solveParallel(),THRESHOLD));
        final long end = System.currentTimeMillis();

        System.out.println("test parallel 1 time: " + (end-start));
    }

    @Test
    public void testSolveParallel2(){
        ThomasAlgorithm a = new ThomasAlgorithm(test2Matrix, test2D);

        final long start = System.currentTimeMillis();
        assertTrue(checkAnswer(test2Matrix,test2D,a.solveParallel(),THRESHOLD));
        final long end = System.currentTimeMillis();

        System.out.println("test parallel 2 time: " + (end-start));
    }

    @Test
    public void testSolveParallel3(){
        ThomasAlgorithm a = new ThomasAlgorithm(test3Matrix, test3D);

        final long start = System.currentTimeMillis();
        assertTrue(checkAnswer(test3Matrix,test3D,a.solveParallel(),THRESHOLD));
        final long end = System.currentTimeMillis();

        System.out.println("test parallel 3 time: " + (end-start));
    }

}
