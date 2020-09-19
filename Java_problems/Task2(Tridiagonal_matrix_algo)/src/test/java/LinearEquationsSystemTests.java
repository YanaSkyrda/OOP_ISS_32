import org.junit.jupiter.api.Test;
import tridiagonalmatrixalgorithm.LinearEquationsSystem;
import tridiagonalmatrixalgorithm.TridiagonalMatrix;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LinearEquationsSystemTests {
    private TridiagonalMatrix coefficients = new TridiagonalMatrix(Arrays.asList(0d, -3d, -5d, -6d, -5d),
            Arrays.asList(2d, 8d, 12d, 18d, 10d), Arrays.asList(-1d, -1d, 2d, -4d));

    private List<Double> rightSideValues = Arrays.asList(-25d, 72d, -69d, -156d, 20d);

    private LinearEquationsSystem equationsSystem = new LinearEquationsSystem(coefficients, rightSideValues);

    LinearEquationsSystemTests() throws Exception { }

    @Test
    void constructorWithMatrix() throws Exception {
        equationsSystem = new LinearEquationsSystem(coefficients, rightSideValues);
        assertEquals(coefficients, equationsSystem.getCoefficients());
        assertEquals(rightSideValues, equationsSystem.getRightSideValues());

        Throwable exception = assertThrows(Exception.class, () ->
                new LinearEquationsSystem(coefficients, Arrays.asList(1d, 6d, 7d)));
        assertEquals("Size of coefficients matrix and right side values doesn't match.", exception.getMessage());

        exception = assertThrows(Exception.class, () ->
                new LinearEquationsSystem(coefficients, Arrays.asList(1d, 6d, 7d, 10d, 53d, 3d, 12d)));
        assertEquals("Size of coefficients matrix and right side values doesn't match.",
                exception.getMessage());
    }

    @Test
    void constructorFromLists() throws Exception {
        equationsSystem = new LinearEquationsSystem(Arrays.asList(0d, -3d, -5d, -6d, -5d),
                Arrays.asList(2d, 8d, 12d, 18d, 10d), Arrays.asList(-1d, -1d, 2d, -4d), rightSideValues);

        assertEquals(coefficients, equationsSystem.getCoefficients());
        assertEquals(rightSideValues, equationsSystem.getRightSideValues());

        Throwable exception = assertThrows(Exception.class, () ->
                new LinearEquationsSystem(Arrays.asList(0d, -3d, -5d, -6d, -5d),
                        Arrays.asList(2d, 8d, 12d, 18d, 10d), Arrays.asList(-1d, -1d, 2d, -4d), Arrays.asList(1d, 6d, 7d)));
        assertEquals("Size of coefficients matrix and right side values doesn't match.",
                exception.getMessage());

        exception = assertThrows(Exception.class, () ->
                new LinearEquationsSystem(Arrays.asList(0d, -3d, -5d, -6d, -5d), Arrays.asList(2d, 8d, 12d, 18d, 10d),
                        Arrays.asList(-1d, -1d, 2d, -4d), Arrays.asList(1d, 6d, 7d, 10d, 53d, 3d, 12d)));
        assertEquals("Size of coefficients matrix and right side values doesn't match.",
                exception.getMessage());
    }

    @Test
    void coefficientsGetter() {
        assertEquals(coefficients, equationsSystem.getCoefficients());
    }


    @Test
    void rightSideValuesGetter() {
        assertEquals(rightSideValues, equationsSystem.getRightSideValues());
    }

    @Test
    void resultCheck() {
        List<Double> result = equationsSystem.getResult();
        List<Double> expectedResult = Arrays.asList(-10d, 5d, -2d, -10d, -3d);

        for (int i = 0; i < result.size(); i++) {
            assertEquals(expectedResult.get(i), result.get(i), 0.01);
        }

    }
}
