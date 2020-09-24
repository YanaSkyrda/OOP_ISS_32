package tridiagonalmatrixalgorithm;

import java.util.ArrayList;
import java.util.List;

public class TridiagonalMatrix {
    private List<Double> A;
    private List<Double> B;
    private List<Double> C;

    public TridiagonalMatrix(List<Double> A, List<Double> B, List<Double> C) throws WrongDiagonalSizeException {
        int diagonalSize = B.size();
        this.A = initializeCoefficientArray(A, diagonalSize, true, false);
        this.B = initializeCoefficientArray(B, diagonalSize, false, false);
        this.C = initializeCoefficientArray(C, diagonalSize, false, true);
    }

    public TridiagonalMatrix(TridiagonalMatrix tridiagonalMatrix) {
        this.A = new ArrayList<>(tridiagonalMatrix.A);
        this.B = new ArrayList<>(tridiagonalMatrix.B);
        this.C = new ArrayList<>(tridiagonalMatrix.C);
    }

    private List<Double> initializeFullDiagonal(List<Double> coefficients, boolean firstIsZero, boolean lastIsZero) {
        List<Double> result = new ArrayList<>(coefficients.size());
        result.addAll(coefficients);

        if (firstIsZero) {
            result.set(0, 0d);
        }

        if (lastIsZero) {
            result.set(coefficients.size() - 1, 0d);
        }

        return result;
    }

    private List<Double> initializeIncompleteDiagonal(List<Double> coefficient, boolean firstIsZero, boolean lastIsZero) {
        List<Double> result = new ArrayList<>(coefficient.size() + 1);
        result.addAll(coefficient);

        if (firstIsZero) {
            result.add(0, 0d);
        }

        if (lastIsZero) {
            result.add(0d);
        }

        return result;
    }
    private List<Double> initializeCoefficientArray(List<Double> coefficients, int diagonalSize,
                                                    boolean firstIsZero, boolean lastIsZero)
            throws WrongDiagonalSizeException {
        List<Double> result;

        if (coefficients.size() - diagonalSize == 0) {
            result = initializeFullDiagonal(coefficients, firstIsZero, lastIsZero);
        } else if (diagonalSize - coefficients.size() == 1) {
            result = initializeIncompleteDiagonal(coefficients, firstIsZero, lastIsZero);
        } else {
            throw new WrongDiagonalSizeException("Wrong diagonal size.");
        }

        return result;
    }

    public int getDiagonalSize() {
        return B.size();
    }

    public double getAByIndex(int index) {
        return A.get(index);
    }

    public double getBByIndex(int index) {
        return B.get(index);
    }

    public double getCByIndex(int index) {
        return C.get(index);
    }

    public List<Double> getA() {
        return A;
    }

    public List<Double> getB() {
        return B;
    }

    public List<Double> getC() {
        return C;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TridiagonalMatrix) {
            TridiagonalMatrix otherMatrix = (TridiagonalMatrix) other;
            return (this.A.equals(otherMatrix.A) && this.B.equals(otherMatrix.B) && this.C.equals(otherMatrix.C));
        }
        return false;
    }

}
