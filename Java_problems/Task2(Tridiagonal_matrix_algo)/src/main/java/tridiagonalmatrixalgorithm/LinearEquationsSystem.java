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

    public List<Double> getResult(boolean parallel) throws ArithmeticException {
        if (this.resultX == null) {
            if (parallel) {
                try {
                    getResultParallel();
                } catch (InterruptedException e) {
                   System.out.println("Something went wrong while computing result: InterruptedException. Try again.");
                   e.printStackTrace();
                }
            } else {
                List<Double> CModified = new ArrayList<>();
                List<Double> DModified = new ArrayList<>();
                int diagonalSize = this.rightSideValues.size();

                CModified.add((-1) * this.coefficients.getCByIndex(0) / this.coefficients.getBByIndex(0));

                for (int i = 1; i < diagonalSize - 1; i++) {
                    CModified.add((-1) * this.coefficients.getCByIndex(i)
                            / (this.coefficients.getBByIndex(i) + CModified.get(i - 1) * this.coefficients.getAByIndex(i)));
                }

                DModified.add(this.rightSideValues.get(0) / this.coefficients.getBByIndex(0));
                for (int i = 1; i < diagonalSize; i++) {
                    double a_i = this.coefficients.getAByIndex(i);
                    DModified.add((this.rightSideValues.get(i) - DModified.get(i - 1) * a_i)
                            / (this.coefficients.getBByIndex(i) + CModified.get(i - 1) * a_i));
                }

                this.resultX = new ArrayList<>(diagonalSize);
                resultX.add(DModified.get(diagonalSize - 1));
                int prevIndexResult = 0;
                for (int i = diagonalSize - 2; i >= 0; i--) {
                    resultX.add(DModified.get(i) + CModified.get(i) * resultX.get(prevIndexResult));
                    ++prevIndexResult;
                }
                Collections.reverse(resultX);
            }
        }

        return this.resultX;
    }

    private class TwoSidedModifiesCalculation extends Thread {
        List<Double> A;
        List<Double> B;
        List<Double> C;
        List<Double> D;
        int start;
        int end;
        boolean reversedOrder;

        volatile List<Double> CModified;
        volatile List<Double> DModified;

        TwoSidedModifiesCalculation(int start, int end, boolean reversedOrder) {
            this.A = coefficients.getA();
            this.B = coefficients.getB();
            this.C = coefficients.getC();
            this.D = rightSideValues;
            this.start = start;
            this.end = end;
            this.reversedOrder = reversedOrder;
            this.CModified = new ArrayList<>(end - start);
            this.DModified = new ArrayList<>(end - start);
        }

        @Override
        public void run() {
            if (reversedOrder) {
                CModified.add((-1) * A.get(end - 1) / B.get(end - 1));
                DModified.add(D.get(end - 1) / B.get(end - 1));
            } else {
                CModified.add((-1) * C.get(start) / B.get(start));
                DModified.add(D.get(start) / B.get(start));
            }

            if (reversedOrder) {
                for (int i = end - 2, modifiedIndex = 0; i >= start; i--, modifiedIndex++) {
                    double denominator = B.get(i) + C.get(i) * CModified.get(modifiedIndex);
                    CModified.add((-1) * A.get(i) / denominator);
                    DModified.add((D.get(i) - C.get(i) * DModified.get(modifiedIndex)) / denominator);
                }
            } else {
                for (int i = start + 1, modifiedIndex = 0; i < end; i++, modifiedIndex++) {
                    double denominator = B.get(i) + A.get(i) * CModified.get(modifiedIndex);
                    CModified.add((-1) * C.get(i) / denominator);
                    DModified.add((D.get(i) - A.get(i) * DModified.get(modifiedIndex)) / denominator);
                }
            }
        }
    }

    private class TwoSidedResultCalculation extends Thread {
        List<Double> A;
        List<Double> B;
        List<Double> C;
        List<Double> D;
        int start;
        int end;
        boolean reversedOrder;

        List<Double> CModified;
        List<Double> DModified;
        double resultAtParallelPoint;
        volatile List<Double> result;

        TwoSidedResultCalculation(List<Double> CModified, List<Double> DModified, double resultAtParallelPoint,
                                  int start, int end, boolean reversedOrder) {
            this.A = coefficients.getA();
            this.B = coefficients.getB();
            this.C = coefficients.getC();
            this.D = rightSideValues;
            this.start = start;
            this.end = end;
            this.reversedOrder = reversedOrder;
            this.resultAtParallelPoint = resultAtParallelPoint;
            this.CModified = CModified;
            this.DModified = DModified;
            this.result = new ArrayList<>(end - start);
        }

        @Override
        public void run() {
            result.add(resultAtParallelPoint);
            if (reversedOrder) {
                for (int i = end - 2, resultIndex = 0; i >= start; i--, resultIndex++) {
                    result.add(CModified.get(i) * result.get(resultIndex) + DModified.get(i));
                }
            } else {
                for (int i = start + 1, resultIndex = 0; i < end; i++, resultIndex++) {
                    result.add(CModified.get(end - start - resultIndex - 2) * result.get(resultIndex)
                            + DModified.get(end - start - resultIndex - 2));
                }
            }
        }
    }

    private List<Double> getResultParallel() throws ArithmeticException, InterruptedException {
        if (resultX == null) {
            int rootsNumber = this.rightSideValues.size();
            int parallelPoint = rootsNumber / 2;
            resultX = new ArrayList<>(rootsNumber);
            TwoSidedModifiesCalculation leftToRight = new TwoSidedModifiesCalculation(0, parallelPoint, false);
            TwoSidedModifiesCalculation rightToLeft = new TwoSidedModifiesCalculation(parallelPoint, rootsNumber, true);

            leftToRight.start();
            rightToLeft.start();

            try {
                leftToRight.join();
                rightToLeft.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Double> leftCModified = leftToRight.CModified;
            List<Double> leftDModified = leftToRight.DModified;
            List<Double> rightCModified = rightToLeft.CModified;
            List<Double> rightDModified = rightToLeft.DModified;
            int rightSize = rightCModified.size();
            int leftSize = leftCModified.size();

            double denominator = 1.0 - rightCModified.get(rightSize - 1) * leftCModified.get(leftSize - 1);
            double resultAtParallelPoint = (rightDModified.get(rightSize - 1)
                    + rightCModified.get(rightSize - 1) * leftDModified.get(leftSize - 1)) / denominator;
            double resultBeforeParallelPoint = (leftDModified.get(leftSize - 1)
                    + leftCModified.get(leftSize - 1) * rightDModified.get(rightSize - 1)) / denominator;

            TwoSidedResultCalculation firstPartResult = new TwoSidedResultCalculation(leftCModified, leftDModified,
                    resultBeforeParallelPoint, 0, parallelPoint, true);
            TwoSidedResultCalculation secondPartResult = new TwoSidedResultCalculation(rightCModified, rightDModified,
                    resultAtParallelPoint, parallelPoint, rootsNumber, false);

            firstPartResult.start();
            secondPartResult.start();

            firstPartResult.join();
            secondPartResult.join();

            Collections.reverse(firstPartResult.result);
            resultX.addAll(firstPartResult.result);
            resultX.addAll(secondPartResult.result);
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
         TridiagonalMatrix coefficients = new TridiagonalMatrix(Arrays.asList(0d, -3d, -5d, -6d, -5d),
                Arrays.asList(2d, 8d, 12d, 18d, 10d), Arrays.asList(-1d, -1d, 2d, -4d));

         List<Double> rightSideValues = Arrays.asList(-25d, 72d, -69d, -156d, 20d);

         LinearEquationsSystem equationsSystem = new LinearEquationsSystem(coefficients, rightSideValues);
        for (double x : equationsSystem.getResultParallel()) {
            System.out.print(String.format("%.2f", x) + "  ");
        }
        System.out.println();
    }
}
