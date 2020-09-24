package parallelcalculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

class ResultCalculation implements Callable<List<Double>> {
    private List<Double> CModified;
    private List<Double> DModified;
    private List<Double> result;

    private double resultAtParallelPoint;
    private int start;
    private int end;
    private boolean reversedOrder;

    ResultCalculation(List<Double> CModified, List<Double> DModified,
                      double resultAtParallelPoint, int start, int end, boolean reversedOrder) {
        this.start = start;
        this.end = end;
        this.reversedOrder = reversedOrder;
        this.resultAtParallelPoint = resultAtParallelPoint;
        this.CModified = CModified;
        this.DModified = DModified;
        this.result = new ArrayList<>(end - start);
    }

    private void rightToLeftResultCalculation() {
        for (int i = end - 2, resultIndex = 0; i >= start; i--, resultIndex++) {
            result.add(CModified.get(i) * result.get(resultIndex) + DModified.get(i));
        }
    }

    private void leftToRightResultCalculation() {
        for (int i = start + 1, resultIndex = 0; i < end; i++, resultIndex++) {
            result.add(CModified.get(end - start - resultIndex - 2) * result.get(resultIndex)
                    + DModified.get(end - start - resultIndex - 2));
        }
    }

    @Override
    public List<Double> call() {
        result.add(resultAtParallelPoint);
        if (reversedOrder) {
            rightToLeftResultCalculation();
            Collections.reverse(result);
        } else {
            leftToRightResultCalculation();
        }
        return result;
    }
}
