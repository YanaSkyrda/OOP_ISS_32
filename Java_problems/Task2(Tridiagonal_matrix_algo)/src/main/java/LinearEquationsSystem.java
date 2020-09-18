import java.util.ArrayList;
import java.util.List;

public class LinearEquationsSystem {
    private TridiagonalMatrix coefficients;
    private List<Double> rightSideValues;
    private List<Double> resultX;

    public LinearEquationsSystem(TridiagonalMatrix coefficientsMatrix, List<Double> rightSideValues) throws Exception {
        if (coefficientsMatrix.getDiagonalSize() == rightSideValues.size()) {
            this.rightSideValues = new ArrayList<>(rightSideValues);
            this.coefficients = new TridiagonalMatrix(coefficientsMatrix);
        } else {
            throw new Exception("Size of coefficients matrix and right side values doesn't match.");
        }
    }

    public LinearEquationsSystem(List<Double> A, List<Double> B, List<Double> C, List<Double> rightSideValues) throws Exception {
        if (B.size() == rightSideValues.size()) {
            this.rightSideValues = new ArrayList<>(rightSideValues);
            this.coefficients = new TridiagonalMatrix(A,B,C);
        } else {
            throw new Exception("Size of coefficients matrix and right side values doesn't match.");
        }
    }
}
