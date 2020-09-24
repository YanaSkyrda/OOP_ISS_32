package parallelcalculation;

import tridiagonalmatrixalgorithm.LinearEquationsSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelAlgorithm {
    private LinearEquationsSystem equationsSystem;
    private List<Double> result;
    private ExecutorService executor;
    private ModifiedCoefficientsHolder leftCoefficients;
    private ModifiedCoefficientsHolder rightCoefficients;
    private double resultAtParallelPoint;
    private double resultBeforeParallelPoint;

    private int parallelPoint;
    private int rootsNumber;

    public ParallelAlgorithm(LinearEquationsSystem equationsSystem) {
        this.equationsSystem = equationsSystem;
        this.executor = Executors.newFixedThreadPool(2);
        this.rootsNumber = equationsSystem.getRightSideValues().size();
        this.parallelPoint = rootsNumber / 2;
    }

    private void calculateModifiedCoefficients() throws ExecutionException, InterruptedException {
        Callable<ModifiedCoefficientsHolder> leftToRight = new CoefficientsModification(equationsSystem,
                0, parallelPoint, false);
        Future<ModifiedCoefficientsHolder> leftToRightCoefficients = executor.submit(leftToRight);

        Callable<ModifiedCoefficientsHolder> rightToLeft = new CoefficientsModification(equationsSystem,
                parallelPoint, rootsNumber, true);
        Future<ModifiedCoefficientsHolder> rightToLeftCoefficients = executor.submit(rightToLeft);

        leftCoefficients = leftToRightCoefficients.get();
        rightCoefficients = rightToLeftCoefficients.get();
    }

    private void calculateResultParallel()
            throws ExecutionException, InterruptedException {
        Callable<List<Double>> resultLeftPart = new ResultCalculation(
                leftCoefficients.getCModified(), leftCoefficients.getDModified(),
                resultBeforeParallelPoint, 0, parallelPoint, true);
        Callable<List<Double>> resultRightPart = new ResultCalculation(
                rightCoefficients.getCModified(), rightCoefficients.getDModified(),
                resultAtParallelPoint, parallelPoint, rootsNumber, false);

        Future<List<Double>> leftResultFuture = executor.submit(resultLeftPart);
        Future<List<Double>> rightResultFuture = executor.submit(resultRightPart);

        result.addAll(leftResultFuture.get());
        result.addAll(rightResultFuture.get());
    }

    private void calculateResultNearParallelPoint() {
        int rightSize = rightCoefficients.getCModified().size();
        int leftSize = leftCoefficients.getCModified().size();
        List<Double> leftCModified = leftCoefficients.getCModified();
        List<Double> leftDModified = leftCoefficients.getDModified();
        List<Double> rightCModified = rightCoefficients.getCModified();
        List<Double> rightDModified = rightCoefficients.getDModified();

        double denominator = 1.0 - rightCModified.get(rightSize - 1) * leftCModified.get(leftSize - 1);

        this.resultAtParallelPoint = (rightDModified.get(rightSize - 1)
                + rightCModified.get(rightSize - 1) * leftDModified.get(leftSize - 1)) / denominator;
        this.resultBeforeParallelPoint = (leftDModified.get(leftSize - 1)
                + leftCModified.get(leftSize - 1) * rightDModified.get(rightSize - 1)) / denominator;
    }

    public List<Double> startAlgorithm() throws ExecutionException, InterruptedException {
        result = new ArrayList<>(rootsNumber);

        calculateModifiedCoefficients();
        calculateResultNearParallelPoint();
        calculateResultParallel();

        return result;
    }
}
