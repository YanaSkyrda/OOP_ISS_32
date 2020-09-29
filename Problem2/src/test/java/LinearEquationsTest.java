import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LinearEquationsTest {

    LinearEquations linearEquations;

    @Test
    public void constructorTest_WRONGINPUT_NULLDVECTOR() throws Exception {

        List<Double> A = new ArrayList<>(Arrays.asList(-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25));
        List<Double> B = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));
        List<Double> C = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));

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
        List<Double> D = new ArrayList<>(Arrays.asList(0d, 0d, 0d, 0d, 0d, 0d, -100d));

        try{
            linearEquations = new LinearEquations(null, D);
            Assert.fail("Expected wrong input matrix exception");
        } catch (Exception thrown){
            Assert.assertEquals("Wrong input matrix", thrown.getMessage());
        }
    }

    @Test
    public void constructorTest_RIGHTINPUT() throws Exception {
        List<Double> A = new ArrayList<>(Arrays.asList(-2.25, -2.25));
        List<Double> B = new ArrayList<>(Collections.singletonList(1d));
        List<Double> C = new ArrayList<>(Collections.singletonList(1d));
        List<Double> D = new ArrayList<>(Arrays.asList(0d, 0d));

        TridiagonalMatrix tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
        linearEquations = new LinearEquations(tridiagonalMatrix, D);


        for(int i = 0; i < D.size(); i++){
            if(!D.get(i).equals(linearEquations.getdVector().get(i)))
                Assert.fail("Wrong value in the dVector at index:" + i);
        }
    }

    @Test
    public void thomasMethodMonoThreaded_RIGHTINPUT() throws Exception {
        List<Double> A = new ArrayList<>(Arrays.asList(2d, 2d, 2d));
        List<Double> B = new ArrayList<>(Arrays.asList(3d, 3d));
        List<Double> C = new ArrayList<>(Arrays.asList(1d, 1d));
        List<Double> D = new ArrayList<>(Arrays.asList(4d, 4d, 4d));

        TridiagonalMatrix tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
        linearEquations = new LinearEquations(tridiagonalMatrix, D);

        linearEquations.thomasAlgorithm(false);

        if(linearEquations.getxVector().get(0) != -4)
            Assert.fail("Wrong value of the x1");
        if(linearEquations.getxVector().get(1) != 4)
            Assert.fail("Wrong value of the x2");
        if(linearEquations.getxVector().get(2) != 0)
            Assert.fail("Wrong value of the x3");
    }

    @Test
    public void thomasMethodMultiThreaded_RIGHTINPUT() throws Exception {
        List<Double> A = new ArrayList<>(Arrays.asList(2d, 2d, 2d));
        List<Double> B = new ArrayList<>(Arrays.asList(3d, 3d));
        List<Double> C = new ArrayList<>(Arrays.asList(1d, 1d));
        List<Double> D = new ArrayList<>(Arrays.asList(4d, 4d, 4d));

        TridiagonalMatrix tridiagonalMatrix = new TridiagonalMatrix(A, B, C);
        linearEquations = new LinearEquations(tridiagonalMatrix, D);

        linearEquations.thomasAlgorithm(true);

        if(linearEquations.getxVector().get(0) != -4)
            Assert.fail("Wrong value of the x1");
        if(linearEquations.getxVector().get(1) != 4)
            Assert.fail("Wrong value of the x2");
        if(linearEquations.getxVector().get(2) != 0)
            Assert.fail("Wrong value of the x3");
    }
}
