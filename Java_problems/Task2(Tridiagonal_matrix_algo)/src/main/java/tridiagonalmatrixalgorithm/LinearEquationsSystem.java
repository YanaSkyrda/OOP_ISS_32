package tridiagonalmatrixalgorithm;

import parallelcalculation.ParallelAlgorithm;
import java.util.*;
import java.util.concurrent.*;

public class LinearEquationsSystem {
    private final TridiagonalMatrix coefficients;
    private final List<Double> rightSideValues;
    private List<Double> result;
    private List<Double> CModified;
    private List<Double> DModified;

    public LinearEquationsSystem(TridiagonalMatrix coefficientsMatrix, List<Double> rightSideValues)
            throws WrongMatrixElementsNumberException {
        if (coefficientsMatrix.getDiagonalSize() == rightSideValues.size()) {
            this.rightSideValues = new ArrayList<>(rightSideValues);
            this.coefficients = new TridiagonalMatrix(coefficientsMatrix);
        } else {
            throw new WrongMatrixElementsNumberException("Size of coefficients matrix and right side values doesn't match.");
        }
    }

    public LinearEquationsSystem(List<Double> A, List<Double> B, List<Double> C, List<Double> rightSideValues)
            throws WrongMatrixElementsNumberException, WrongDiagonalSizeException {
        if (B.size() == rightSideValues.size()) {
            this.rightSideValues = new ArrayList<>(rightSideValues);
            this.coefficients = new TridiagonalMatrix(A,B,C);
        } else {
            throw new WrongMatrixElementsNumberException("Size of coefficients matrix and right side values doesn't match.");
        }
    }

    private void forwardSweepCalculation(int diagonalSize) {
        CModified = new ArrayList<>();
        DModified = new ArrayList<>();

        CModified.add((-1) * this.coefficients.getCByIndex(0) / this.coefficients.getBByIndex(0));
        DModified.add(this.rightSideValues.get(0) / this.coefficients.getBByIndex(0));

        for (int i = 1; i < diagonalSize; i++) {
            double denominator = this.coefficients.getBByIndex(i) + CModified.get(i - 1) * this.coefficients.getAByIndex(i);
            CModified.add((-1) * this.coefficients.getCByIndex(i) / denominator);
            DModified.add((this.rightSideValues.get(i) - DModified.get(i - 1) * this.coefficients.getAByIndex(i)) /
                    denominator);
        }
    }

    private void backSweepCalculation(int diagonalSize) {
        this.result = new ArrayList<>(diagonalSize);
        this.result.add(this.DModified.get(diagonalSize - 1));
        int prevIndexResult = 0;
        for (int i = diagonalSize - 2; i >= 0; i--) {
            this.result.add(this.DModified.get(i) + this.CModified.get(i) * this.result.get(prevIndexResult));
            ++prevIndexResult;
        }
        Collections.reverse(this.result);
    }

    public List<Double> getResult(boolean parallel) throws ArithmeticException {
        if (this.result == null) {
            if (parallel) {
                try {
                    ParallelAlgorithm parallelAlgorithm = new ParallelAlgorithm(this);
                    this.result = parallelAlgorithm.startAlgorithm();
                } catch (InterruptedException | ExecutionException e) {
                    this.result = null;
                   System.out.println("Something went wrong while computing result in parallel version. Try again.");
                   e.printStackTrace();
                }
            } else {
                int diagonalSize = this.rightSideValues.size();
                forwardSweepCalculation(diagonalSize);
                backSweepCalculation(diagonalSize);
            }
        }

        return this.result;
    }

    public TridiagonalMatrix getCoefficients() {
        return coefficients;
    }

    public List<Double> getRightSideValues() {
        return rightSideValues;
    }
}
