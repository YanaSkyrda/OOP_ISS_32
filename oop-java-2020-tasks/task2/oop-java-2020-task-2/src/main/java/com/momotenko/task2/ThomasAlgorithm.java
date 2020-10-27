package com.momotenko.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThomasAlgorithm {
    private List<Double> a;
    private List<Double> b;
    private List<Double> c;
    private List<Double> d;
    private List<Double> cTemp;
    private List<Double> dTemp;

    public List<Double> solveLinear() throws ArithmeticException{
        cTemp.set(0, c.get(0) / b.get(0));
        dTemp.set(0, d.get(0) / b.get(0));

        int size = a.size();
        Double temp;
        ArrayList<Double> answer = new ArrayList<>(Arrays.asList(new Double[size]));

        for (int i = 1; i < size; ++i) {
            try {
                temp = 1 / (b.get(i) - cTemp.get(i - 1) * a.get(i));

                if (i != size - 1) {
                    cTemp.set(i, c.get(i) * temp); //c`i = ci / (bi - ci-1*ai)
                }

                dTemp.set(i, (d.get(i) - dTemp.get(i - 1) * a.get(i)) * temp); //d`i = di - d`i-1 * a1 / (bi - ci-1*ai)

                if (temp == Double.POSITIVE_INFINITY ||
                        temp == Double.NEGATIVE_INFINITY ||
                        temp.isNaN()) {
                    throw new ArithmeticException();
                }

            } catch (ArithmeticException ae) {
                System.out.println("Division on zero occurred");
            }
        }

        answer.set(size - 1, dTemp.get(size - 1));

        for (int i = size - 2; i >= 0; --i){
            answer.set(i, dTemp.get(i) - cTemp.get(i) * answer.get(i + 1));
        }

        return answer;
    }

    public ThomasAlgorithm(List<List<Double>> leftMatrix, List<Double> rightPart){
        this.d = new ArrayList<>(rightPart);

        int size = leftMatrix.size();
        a = new ArrayList<>(Arrays.asList(new Double[size]));
        b = new ArrayList<>(Arrays.asList(new Double[size]));
        c = new ArrayList<>(Arrays.asList(new Double[size]));

        a.set(0, (double) 0);
        c.set(size - 1, (double) 0);

        for (int i = 0; i < size; ++i){
            if (i - 1 >= 0){
                a.set(i, leftMatrix.get(i).get(i - 1));
            }

            b.set(i, leftMatrix.get(i).get(i));

            if (i + 1 < size){
                c.set(i, leftMatrix.get(i).get(i + 1));
            }
        }

        cTemp = new ArrayList<>(Arrays.asList(new Double[size]));
        dTemp = new ArrayList<>(Arrays.asList(new Double[size]));
    }
}
