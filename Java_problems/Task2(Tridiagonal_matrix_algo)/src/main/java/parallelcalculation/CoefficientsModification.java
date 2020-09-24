package parallelcalculation;

import tridiagonalmatrixalgorithm.LinearEquationsSystem;
import tridiagonalmatrixalgorithm.TridiagonalMatrix;

import java.util.List;
import java.util.concurrent.Callable;

class CoefficientsModification implements Callable<ModifiedCoefficientsHolder> {
    private ModifiedCoefficientsHolder modifiedCoefficient;
    private TridiagonalMatrix coefficients;
    private List<Double> rightSideValues;
    private int start;
    private int end;
    private boolean reversedOrder;


    CoefficientsModification(LinearEquationsSystem equationsSystem, int start, int end, boolean reversedOrder) {
        this.coefficients = equationsSystem.getCoefficients();
        this.rightSideValues = equationsSystem.getRightSideValues();
        this.start = start;
        this.end = end;
        this.reversedOrder = reversedOrder;
        this.modifiedCoefficient = new ModifiedCoefficientsHolder(end - start);
    }

    private void leftToRightCoefficientsCalculation() {
        modifiedCoefficient.addToCModified((-1) * coefficients.getCByIndex(start) / coefficients.getBByIndex(start));
        modifiedCoefficient.addToDModified(rightSideValues.get(start) / coefficients.getBByIndex(start));

        for (int i = start + 1, modifiedIndex = 0; i < end; i++, modifiedIndex++) {
            double denominator = coefficients.getBByIndex(i)
                    + coefficients.getAByIndex(i) * modifiedCoefficient.getCModified().get(modifiedIndex);
            modifiedCoefficient.addToCModified((-1) * coefficients.getCByIndex(i)
                    / denominator);
            modifiedCoefficient.addToDModified((rightSideValues.get(i)
                    - coefficients.getAByIndex(i) * modifiedCoefficient.getDModified().get(modifiedIndex))
                    / denominator);
        }
    }

    private void rightToLeftCoefficientsCalculation() {
        modifiedCoefficient.addToCModified((-1) * coefficients.getAByIndex(end - 1) / coefficients.getBByIndex(end - 1));
        modifiedCoefficient.addToDModified(rightSideValues.get(end - 1) / coefficients.getBByIndex(end - 1));

        for (int i = end - 2, modifiedIndex = 0; i >= start; i--, modifiedIndex++) {
            double denominator = coefficients.getBByIndex(i)
                    + coefficients.getCByIndex(i) * modifiedCoefficient.getCModified().get(modifiedIndex);
            modifiedCoefficient.addToCModified((-1) * coefficients.getAByIndex(i)
                    / denominator);
            modifiedCoefficient.addToDModified((rightSideValues.get(i)
                    - coefficients.getCByIndex(i) * modifiedCoefficient.getDModified().get(modifiedIndex))
                    / denominator);
        }
    }

    @Override
    public ModifiedCoefficientsHolder call() {
        if (reversedOrder) {
            rightToLeftCoefficientsCalculation();
        } else {
            leftToRightCoefficientsCalculation();
        }
        return modifiedCoefficient;
    }
}

