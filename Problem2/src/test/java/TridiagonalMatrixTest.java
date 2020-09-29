import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TridiagonalMatrixTest {

    private TridiagonalMatrix tridiagonalMatrix;

    @Test
    public void constructorTest_WRONGINPUT_NULL(){
        try{
            tridiagonalMatrix = new TridiagonalMatrix(null, null, null);
            Assert.fail("Expected wrong input parameters exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong input parameters", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_WRONGINPUT_AVECTOR(){
        List<Double> A = new ArrayList<>(Collections.singletonList(1d));
        List<Double> B = new ArrayList<>(Collections.singletonList(1d));
        List<Double> C = new ArrayList<>(Collections.singletonList(1d));

        try{
            tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
            Assert.fail("Expected wrong size of aVector exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong size of aVector", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_WRONGINPUT_BVECTOR(){
        List<Double> A = new ArrayList<>(Arrays.asList(1d, 1d));
        List<Double> B = new ArrayList<>(Arrays.asList(1d, 1d));
        List<Double> C = new ArrayList<>(Arrays.asList(1d, 1d));

        try{
            tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
            Assert.fail("Expected wrong size of bVector exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong size of bVector", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_WRONGINPUT_CVECTOR(){
        List<Double> A = new ArrayList<>(Arrays.asList(1d, 1d));
        List<Double> B = new ArrayList<>(Collections.singletonList(1d));
        List<Double> C = new ArrayList<>(Arrays.asList(1d, 1d));

        try{
            tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
            Assert.fail("Expected wrong size of cVector exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong size of cVector", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_RIGHTINPUT() throws Exception {
        List<Double> A = new ArrayList<>(Arrays.asList(-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25));
        List<Double> B = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));
        List<Double> C = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));

        tridiagonalMatrix = new TridiagonalMatrix(A, B, C);

        for(int i = 1; i < A.size() - 1; i++){
            if(!A.get(i).equals(tridiagonalMatrix.getaVector().get(i)) || !B.get(i).equals(tridiagonalMatrix.getbVector().get(i)) || !C.get(i).equals(tridiagonalMatrix.getcVector().get(i)))
                Assert.fail("Wrong value in the matrix at index:" + i);
        }

        if(!A.get(A.size() - 1).equals(tridiagonalMatrix.getaVector().get(A.size() - 1)))
            Assert.fail("Wrong value in the matrix at aVector index:" + (A.size() - 1));

        if(!A.get(0).equals(tridiagonalMatrix.getaVector().get(0)))
            Assert.fail("Wrong value in the matrix at aVector index:" + (0));

        if(tridiagonalMatrix.getbVector().get(A.size() - 1) != 0)
            Assert.fail("Wrong value in the matrix at bVector index:" + (A.size() - 1));

        if(tridiagonalMatrix.getcVector().get(0) != 0)
            Assert.fail("Wrong value in the matrix at cVector index:" + (0));



    }

    @Test
    public void changeCoefficient_WRONGINPUT_INDEX() throws Exception {
        List<Double> A = new ArrayList<>(Arrays.asList(-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25));
        List<Double> B = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));
        List<Double> C = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));

        tridiagonalMatrix = new TridiagonalMatrix(A, B, C);

        try{
            Utils.changeCoefficient(tridiagonalMatrix.getaVector(), 15, 100d);
            Assert.fail("Expected IndexOutOfBounds exception");
        } catch (Exception thrown){
            IndexOutOfBoundsException exc = new IndexOutOfBoundsException();
            Assert.assertEquals(thrown.getClass(), exc.getClass());
        }
    }

    @Test
    public void changeCoefficient_RIGHTINPUT() throws Exception {
        List<Double> A = new ArrayList<>(Arrays.asList(-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25));
        List<Double> B = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));
        List<Double> C = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));

        tridiagonalMatrix = new TridiagonalMatrix(A, B, C);

        for(int i = 0; i < 1000; i++){
            Random r = new Random();
            Double randomDouble = r.nextDouble();
            Utils.changeCoefficient(tridiagonalMatrix.getaVector(), i % 7, randomDouble);

            Double expected = tridiagonalMatrix.getaVector().get(i % 7);

            if(!expected.equals(randomDouble))
                Assert.fail("Wrong value in the the vector");
        }
    }
}
