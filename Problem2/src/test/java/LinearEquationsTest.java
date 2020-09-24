import org.junit.Assert;
import org.junit.Test;

public class LinearEquationsTest {

    LinearEquations linearEquations;

    @Test
    public void constructorTest_WRONGINPUT_NULLDVECTOR() throws Exception {

        double [] A = {-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25};
        double [] B = {1, 1, 1, 1, 1, 1};
        double [] C = {1, 1, 1, 1, 1, 1};

        TridiagonalMatrix tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
        try{
            linearEquations = new LinearEquations(tridiagonalMatrix, null);
            Assert.fail("Expected wrong input dVector exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong input dVector", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_WRONGINPUT_NULLMATRIX() {
        double [] D = {0, 0, 0, 0, 0, 0, -100};

        try{
            linearEquations = new LinearEquations(null, D);
            Assert.fail("Expected wrong input matrix exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong input matrix", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_RIGHTINPUT() throws Exception {
        double [] A = {-2.25, -2.25};
        double [] B = {1};
        double [] C = {1};
        double [] D = {0, 0};

        TridiagonalMatrix tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
        linearEquations = new LinearEquations(tridiagonalMatrix, D);


        for(int i = 0; i < D.length; i++){
            if(D[i] != linearEquations.getdVector().get(i))
                Assert.fail("Wrong value in the dVector at index:" + i);
        }
    }

    @Test
    public void thomasMethdMonoThreaded_RIGHTINPUT() throws Exception {
        double [] A = {2, 2, 2};
        double [] B = {3, 3};
        double [] C = {1, 1};
        double [] D = {4, 4, 4};

        TridiagonalMatrix tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
        linearEquations = new LinearEquations(tridiagonalMatrix, D);

        System.out.println(linearEquations.toString());
        System.out.println('\n');

        linearEquations.thomasAlgorithm(false);

        if(linearEquations.getxVector().get(0) != -4)
            Assert.fail("Wrong value of the x1");
        if(linearEquations.getxVector().get(1) != 4)
            Assert.fail("Wrong value of the x2");
        if(linearEquations.getxVector().get(2) != 0)
            Assert.fail("Wrong value of the x3");
    }
}
