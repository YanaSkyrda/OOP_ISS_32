package tridiagonalmatrixalgorithm;

import java.util.ArrayList;
import java.util.List;

public class TridiagonalMatrix {
    private List<Double> A;
    private List<Double> B;
    private List<Double> C;

    public TridiagonalMatrix(List<Double> A, List<Double> B, List<Double> C) throws Exception {
        int diagonalSize = B.size();
        this.A = initializeCoefficientArray(A, diagonalSize, 1, diagonalSize);
        this.B = initializeCoefficientArray(B, diagonalSize, 0, diagonalSize);
        this.C = initializeCoefficientArray(C, diagonalSize, 0, diagonalSize - 1);
    }

    public TridiagonalMatrix(TridiagonalMatrix tridiagonalMatrix) {
        this.A = new ArrayList<>(tridiagonalMatrix.A);
        this.B = new ArrayList<>(tridiagonalMatrix.B);
        this.C = new ArrayList<>(tridiagonalMatrix.C);
    }

    private List<Double> initializeCoefficientArray(List<Double> coefficients, int diagonalSize, int start, int end)
            throws Exception {
        List<Double> result = new ArrayList<>(diagonalSize);

        if (coefficients.size() == diagonalSize || coefficients.size() == diagonalSize - 1) {
            if (start == 1) {
                result.add((double)0);
                if (coefficients.size() == diagonalSize - 1) {
                    start = 0;
                }
            }
            for (int i = start; i < diagonalSize - 1; i++) {
                result.add(coefficients.get(i));
            }
            if (end == diagonalSize - 1) {
                result.add((double)0);
            } else {
                if (coefficients.size() == diagonalSize) {
                    result.add(coefficients.get(diagonalSize - 1));
                }
            }
        } else {
            throw new Exception("Wrong diagonal size");
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
            if (this.A.equals(otherMatrix.A) && this.B.equals(otherMatrix.B) && this.C.equals(otherMatrix.C)) {
                return true;
            }
        }
        return false;
    }

}
