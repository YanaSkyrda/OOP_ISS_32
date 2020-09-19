package tridiagonalmatrixalgorithm;

import java.util.*;

public class LinearEquationsSystem {
    private final TridiagonalMatrix coefficients;
    private final List<Double> rightSideValues;
    private List<Double> resultX = null;

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

    public List<Double> getResult() {
        if (this.resultX == null) {
            List<Double> CModified = new ArrayList<>();
            List<Double> DModified = new ArrayList<>();
            int diagonalSize = this.rightSideValues.size();

            CModified.add((-1) * this.coefficients.getCByIndex(0) / this.coefficients.getBByIndex(0));

            for (int i = 1; i < diagonalSize - 1; i++) {
                CModified.add((-1) * this.coefficients.getCByIndex(i) /
                        (this.coefficients.getBByIndex(i) + CModified.get(i - 1) * this.coefficients.getAByIndex(i)));
            }

            //this should be in another thread, but ?? CModified.get(i - 1)
            DModified.add(this.rightSideValues.get(0) / this.coefficients.getBByIndex(0));
            for (int i = 1; i < diagonalSize; i++) {
                double a_i = this.coefficients.getAByIndex(i);
                DModified.add((this.rightSideValues.get(i) - DModified.get(i - 1) * a_i) /
                        (this.coefficients.getBByIndex(i) + CModified.get(i - 1) * a_i));
            }

            this.resultX = new ArrayList<Double>(diagonalSize);
            resultX.add(DModified.get(diagonalSize - 1));
            int prevIndexResult = 0;
            for (int i = diagonalSize - 2; i >= 0; i--) {
                resultX.add(DModified.get(i) + CModified.get(i) * resultX.get(prevIndexResult));
                ++prevIndexResult;
            }
            Collections.reverse(resultX);
        }

        return this.resultX;
    }

    public TridiagonalMatrix getCoefficients() {
        return coefficients;
    }

    public List<Double> getRightSideValues() {
        return rightSideValues;
    }

    public static void main(String[] args) throws Exception {
        List<Double> A = new ArrayList<Double>(Arrays.asList(0d, -3d, -5d, -6d, -5d));
        List<Double> B = new ArrayList<Double>(Arrays.asList(2d, 8d, 12d, 18d, 10d));
        List<Double> C = new ArrayList<Double>(Arrays.asList(-1d, -1d, 2d, -4d));
        List<Double> D = new ArrayList<Double>(Arrays.asList(-25d, 72d, -69d, -156d, 20d));
        LinearEquationsSystem equationsSystem = new LinearEquationsSystem(A, B, C, D);
        for (double x : equationsSystem.getResult()) {
            System.out.print(String.format("%.2f", x) + "  ");
        }
        System.out.println();
    }
}
