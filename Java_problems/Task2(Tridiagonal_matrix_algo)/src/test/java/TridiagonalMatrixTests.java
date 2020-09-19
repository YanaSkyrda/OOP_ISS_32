import org.junit.jupiter.api.Test;
import tridiagonalmatrixalgorithm.TridiagonalMatrix;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TridiagonalMatrixTests {

    private TridiagonalMatrix coefficients = new TridiagonalMatrix(Arrays.asList(1d, 2d, 4d),
            Arrays.asList(5d, 6d, 7d), Arrays.asList(8d, 9d, 10d));

    TridiagonalMatrixTests() throws Exception { }

    @Test
    void constructorFromList() throws Exception {
        assertEquals(Arrays.asList(5d, 6d, 7d), coefficients.getB());
        assertEquals(Arrays.asList(0d, 2d, 4d), coefficients.getA());
        assertEquals(Arrays.asList(8d, 9d, 0d), coefficients.getC());

        coefficients = new TridiagonalMatrix(Arrays.asList(2d, 4d),
                Arrays.asList(5d, 6d, 7d), Arrays.asList(8d, 9d));

        assertEquals(Arrays.asList(5d, 6d, 7d), coefficients.getB());
        assertEquals(Arrays.asList(0d, 2d, 4d), coefficients.getA());
        assertEquals(Arrays.asList(8d, 9d, 0d), coefficients.getC());

        Throwable exception = assertThrows(Exception.class, () ->
                new TridiagonalMatrix(Collections.singletonList(2d), Arrays.asList(5d, 6d, 7d), Arrays.asList(8d, 9d)));
        assertEquals("Wrong diagonal size", exception.getMessage());

        exception = assertThrows(Exception.class, () ->
                new TridiagonalMatrix(Arrays.asList(2d, 4d), Arrays.asList(5d, 6d, 7d), Collections.singletonList(9d)));
        assertEquals("Wrong diagonal size", exception.getMessage());

        exception = assertThrows(Exception.class, () ->
                new TridiagonalMatrix(Arrays.asList(2d, 4d), Arrays.asList(5d, 6d, 7d, 6d), Arrays.asList(8d, 9d)));
        assertEquals("Wrong diagonal size", exception.getMessage());
    }

    @Test
    void constructorFromMatrix() throws Exception {
        coefficients = new TridiagonalMatrix(new TridiagonalMatrix(Arrays.asList(1d, 2d, 4d),
                Arrays.asList(5d, 6d, 7d), Arrays.asList(8d, 9d, 10d)));


        assertEquals( Arrays.asList(5d, 6d, 7d), coefficients.getB());
        assertEquals(Arrays.asList(0d, 2d, 4d), coefficients.getA());
        assertEquals(Arrays.asList(8d, 9d, 0d), coefficients.getC());
    }

    @Test
    void diagonalSizeGetter() throws Exception {
        coefficients = new TridiagonalMatrix(Arrays.asList(1d, 2d, 4d),
                Arrays.asList(5d, 6d, 7d), Arrays.asList(8d, 9d, 10d));

        assertEquals(3, coefficients.getDiagonalSize());

        coefficients = new TridiagonalMatrix(Arrays.asList(2d, 4d),
                Arrays.asList(5d, 6d, 7d), Arrays.asList(8d, 9d));

        assertEquals(3, coefficients.getDiagonalSize());

    }

    @Test
    void getByIndexGetter() {
        assertEquals(0, coefficients.getAByIndex(0));
        assertEquals(2, coefficients.getAByIndex(1));
        assertEquals(4, coefficients.getAByIndex(2));

        assertEquals(5, coefficients.getBByIndex(0));
        assertEquals(6, coefficients.getBByIndex(1));
        assertEquals(7, coefficients.getBByIndex(2));

        assertEquals(8, coefficients.getCByIndex(0));
        assertEquals(9, coefficients.getCByIndex(1));
        assertEquals(0, coefficients.getCByIndex(2));
    }

    @Test
    void equalsCheck() throws Exception {
        TridiagonalMatrix other = new TridiagonalMatrix(new TridiagonalMatrix(Arrays.asList(1d, 2d, 4d),
                Arrays.asList(5d, 6d, 7d), Arrays.asList(8d, 9d, 10d)));

        assertEquals(coefficients, other);
        assertNotEquals(1d, coefficients);
        assertNotEquals("1", coefficients);
    }
}
