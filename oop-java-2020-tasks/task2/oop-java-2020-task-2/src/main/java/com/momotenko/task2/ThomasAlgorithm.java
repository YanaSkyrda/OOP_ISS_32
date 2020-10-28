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

    public List<Double> solveLinear() {
        cTemp.set(0, c.get(0) / b.get(0));
        dTemp.set(0, d.get(0) / b.get(0));

        int size = a.size();
        Double temp;
        List<Double> answer = new ArrayList<>(Arrays.asList(new Double[size]));

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

        for (int i = size - 2; i >= 0; --i) {
            answer.set(i, dTemp.get(i) - cTemp.get(i) * answer.get(i + 1));
        }

        return answer;
    }

    public List<Double> solveParallel() {
        int size = b.size();

        if (size < 6) {
            return solveLinear();
        }

        int middle = size / 2;

        List<Double> answer = new ArrayList<>(Arrays.asList(new Double[size]));
        List<Double> e = new ArrayList<>(Arrays.asList(new Double[size]));
        List<Double> n = new ArrayList<>(Arrays.asList(new Double[size]));
        List<Double> al = new ArrayList<>(Arrays.asList(new Double[size]));
        List<Double> be = new ArrayList<>(Arrays.asList(new Double[size]));

        Thread thread1 = new Thread(() -> {
            e.set(size - 1, -a.get(size - 1) / b.get(size - 1));
            n.set(size - 1, d.get(size - 1) / b.get(size - 1));

            for (int i = size - 2; i >= middle; --i) {
                e.set(i, -a.get(i) / (b.get(i) + c.get(i) * e.get(i + 1)));
                n.set(i, (d.get(i) - c.get(i) * n.get(i + 1)) / (b.get(i) + c.get(i) * e.get(i + 1)));
            }
        });


        Thread thread2 = new Thread(() -> {
            al.set(1, -c.get(0) / b.get(0));
            be.set(1, d.get(0) / b.get(0));

            for (int i = 2; i <= middle; ++i) {
                al.set(i, -c.get(i - 1) / (a.get(i - 1) * al.get(i - 1) + b.get(i - 1)));
                be.set(i, (d.get(i - 1) - a.get(i - 1) * be.get(i - 1)) / (a.get(i - 1) * al.get(i - 1) + b.get(i - 1)));
            }
        });


        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();

            //calculate xmiddle and xmiddle+1
            answer.set(middle - 1, (al.get(middle) * n.get(middle) + be.get(middle)) / (1 - al.get(middle) * e.get(middle)));
            answer.set(middle, e.get(middle) * answer.get(middle - 1) + n.get(middle));

            thread1 = new Thread(() -> {
                for (int i = middle - 1; i >= 0; --i) {
                    answer.set(i, al.get(i + 1) * answer.get(i + 1) + be.get(i + 1));
                }
            });


            thread2 = new Thread(() -> {
                for (int i = middle + 1; i < size; ++i) {
                    answer.set(i, e.get(i) * answer.get(i - 1) + n.get(i));
                }
            });

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
        } catch (InterruptedException interruptedException) {
            System.out.println("Parallel process was interrupted");
            System.out.println("Trying to calculate with linear method");
            return solveLinear();
        }

        return answer;
    }

    public ThomasAlgorithm(List<List<Double>> leftMatrix, List<Double> rightPart) {
        this.d = new ArrayList<>(rightPart);

        int size = leftMatrix.size();
        a = new ArrayList<>(Arrays.asList(new Double[size]));
        b = new ArrayList<>(Arrays.asList(new Double[size]));
        c = new ArrayList<>(Arrays.asList(new Double[size]));

        a.set(0, (double) 0);
        c.set(size - 1, (double) 0);

        for (int i = 0; i < size; ++i) {
            if (i - 1 >= 0) {
                a.set(i, leftMatrix.get(i).get(i - 1));
            }

            b.set(i, leftMatrix.get(i).get(i));

            if (i + 1 < size) {
                c.set(i, leftMatrix.get(i).get(i + 1));
            }
        }

        cTemp = new ArrayList<>(Arrays.asList(new Double[size]));
        dTemp = new ArrayList<>(Arrays.asList(new Double[size]));
    }
}
