import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {           //Adjustment 2.
        List<Double> A = new ArrayList<>(Arrays.asList(-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25));
        List<Double> B = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));
        List<Double> C = new ArrayList<>(Arrays.asList(1d, 1d, 1d, 1d, 1d, 1d));
        List<Double> D = new ArrayList<>(Arrays.asList(0d, 0d, 0d, 0d, 0d, 0d, -100d));

        TridiagonalMatrix tridiagonalMatrix = new TridiagonalMatrix(A, B ,C);
        LinearEquations linearEquations = new LinearEquations(new TridiagonalMatrix(tridiagonalMatrix), D); //Adjustment 3.

        //System.out.println(tridiagonalMatrix.toString());

        //System.out.println(linearEquations.toString());

        linearEquations.thomasAlgorithm(true);

        //System.out.println(linearEquations.toString());
        System.out.println(linearEquations.toStringXVector());
    }
}
