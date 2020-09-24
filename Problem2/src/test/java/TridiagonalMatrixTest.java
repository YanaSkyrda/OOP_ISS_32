import org.junit.Assert;
import org.junit.Test;
import java.util.Random;

public class TridiagonalMatrixTest {

    private TridiagonalMatrix tridiagonalMatrix;

    @Test
    public void constructorTest_WRONGINPUT_NULL(){
        try{
            tridiagonalMatrix = new TridiagonalMatrix((double[]) null, null, null);
            Assert.fail("Expected wrong input parameters exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong input parameters", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_WRONGINPUT_AVECTOR(){
        double [] A = new double[1];
        double [] B = new double[1];
        double [] C = new double[1];

        try{
            tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
            Assert.fail("Expected wrong size of aVector exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong size of aVector", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_WRONGINPUT_BVECTOR(){
        double [] A = new double[2];
        double [] B = new double[2];
        double [] C = new double[1];

        try{
            tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
            Assert.fail("Expected wrong size of bVector exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong size of bVector", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_WRONGINPUT_CVECTOR(){
        double [] A = new double[2];
        double [] B = new double[1];
        double [] C = new double[2];

        try{
            tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
            Assert.fail("Expected wrong size of cVector exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong size of cVector", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_RIGHTINPUT() throws Exception {
        double [] A = {-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25};
        double [] B = {1, 1, 1, 1, 1, 1};
        double [] C = {1, 1, 1, 1, 1, 1};

        tridiagonalMatrix = new TridiagonalMatrix(A, B, C);

        for(int i = 1; i < A.length - 1; i++){
            if(A[i] != tridiagonalMatrix.getaVector().get(i) || B[i] != tridiagonalMatrix.getbVector().get(i) || C[i] != tridiagonalMatrix.getcVector().get(i))
                Assert.fail("Wrong value in the matrix at index:" + i);
        }

        if(A[A.length - 1] != tridiagonalMatrix.getaVector().get(A.length - 1))
            Assert.fail("Wrong value in the matrix at aVector index:" + (A.length - 1));

        if(A[0] != tridiagonalMatrix.getaVector().get(0))
            Assert.fail("Wrong value in the matrix at aVector index:" + (0));

        if(tridiagonalMatrix.getbVector().get(A.length - 1) != 0)
            Assert.fail("Wrong value in the matrix at bVector index:" + (A.length - 1));

        if(tridiagonalMatrix.getcVector().get(0) != 0)
            Assert.fail("Wrong value in the matrix at cVector index:" + (0));



    }

    @Test
    public void changeCoefficient_WRONGINPUT_INDEX() throws Exception {
        double [] A = {-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25};
        double [] B = {1, 1, 1, 1, 1, 1};
        double [] C = {1, 1, 1, 1, 1, 1};

        tridiagonalMatrix = new TridiagonalMatrix(A, B, C);

        try{
            tridiagonalMatrix.changeCoefficient(tridiagonalMatrix.getaVector(), 15, 100d);
            Assert.fail("Expected IndexOutOfBounds exception");
        } catch (Exception thrown){
            IndexOutOfBoundsException exc = new IndexOutOfBoundsException();
            Assert.assertEquals(thrown.getClass(), exc.getClass());
        }
    }

    @Test
    public void changeCoefficient_RIGHTINPUT() throws Exception {
        double [] A = {-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25};
        double [] B = {1, 1, 1, 1, 1, 1};
        double [] C = {1, 1, 1, 1, 1, 1};

        tridiagonalMatrix = new TridiagonalMatrix(A, B, C);

        for(int i = 0; i < 1000; i++){
            Random r = new Random();
            Double randomDouble = r.nextDouble();
            tridiagonalMatrix.changeCoefficient(tridiagonalMatrix.getaVector(), i % 7, randomDouble);

            Double expected = tridiagonalMatrix.getaVector().get(i % 7);

            if(!expected.equals(randomDouble))
                Assert.fail("Wrong value in the the vector");
        }
    }
}
