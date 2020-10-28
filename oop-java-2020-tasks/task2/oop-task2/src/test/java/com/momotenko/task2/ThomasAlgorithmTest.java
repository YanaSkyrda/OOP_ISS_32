package com.momotenko.task2;

import com.momotenko.task2.utils.ThomasAlgorithmTestArgumentProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThomasAlgorithmTest {
    private final double THRESHOLD = 0.01;

    private boolean assertEpsilon(double first, double second, double eps){
        return (Math.abs(first - second) < eps);
    }

    private boolean checkAnswer(List<Double> a, List<Double> b, List<Double> c,List<Double> d,List<Double> answer, double eps){
        int size = a.size();

        if (size != answer.size() || size < 3)
            return false;

        Double temp;

        if (!assertEpsilon(b.get(0)*answer.get(0)+c.get(0)*answer.get(1),d.get(0),THRESHOLD)){
            return false;
        }

        if (!assertEpsilon(a.get(size - 1)*answer.get(size-2)+b.get(size-1)*answer.get(size-1),d.get(size-1),THRESHOLD)){
            return false;
        }

        for (int i = 1; i < size - 1; ++i){
            if (!assertEpsilon(a.get(i)*answer.get(i-1) + b.get(i)*answer.get(i)+c.get(i)*answer.get(i+1),d.get(i),THRESHOLD)){
                return false;
            }
        }

        return true;
    }

    @DisplayName("test linear solving")
    @ParameterizedTest(name="Checking {index} example")
    @ArgumentsSource(ThomasAlgorithmTestArgumentProvider.class)
    void testLinear(List<List<Double>> matrix){
        ThomasAlgorithm a = new ThomasAlgorithm(matrix.get(0),matrix.get(1), matrix.get(2), matrix.get(3));

        List<Double> answer = a.solveLinear();

        assertTrue(checkAnswer(matrix.get(0),matrix.get(1),matrix.get(2),matrix.get(3),answer,THRESHOLD));
    }

    @DisplayName("test parallel solving")
    @ParameterizedTest(name="Checking {index} example")
    @ArgumentsSource(ThomasAlgorithmTestArgumentProvider.class)
    void testParallel(List<List<Double>> matrix){
        ThomasAlgorithm a = new ThomasAlgorithm(matrix.get(0),matrix.get(1), matrix.get(2), matrix.get(3));

        List<Double> answer = a.solveParallel();

        assertTrue(checkAnswer(matrix.get(0),matrix.get(1),matrix.get(2),matrix.get(3),answer,THRESHOLD));
    }
}
