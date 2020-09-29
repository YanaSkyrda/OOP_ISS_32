import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LinearEquations {
    private final TridiagonalMatrix tridiagonalMatrix;
    private final List<Double> dVector;
    private final List<Double> xVector;

    private final int size;
    private volatile int coffIndexReady;    //не будет добавлен в стек потока

    private static final Logger log = Logger.getLogger(LinearEquations.class.getName());

    public List<Double> getdVector() {
        return dVector;
    }

    public List<Double> getxVector() {
        return xVector;
    }

    public LinearEquations(TridiagonalMatrix tridiagonalMatrix, List<Double> dVector) throws Exception {
        if(dVector == null)
            throw new Exception("Wrong input dVector");

        if(tridiagonalMatrix == null)
            throw new Exception("Wrong input matrix");

        if(tridiagonalMatrix.getSize() != dVector.size()){
            this.tridiagonalMatrix = null;
            this.dVector = null;
            xVector = null;
            size = 0;
            return;
        }

        this.tridiagonalMatrix = tridiagonalMatrix;
        this.size = tridiagonalMatrix.getSize();

        this.dVector = dVector;
        this.xVector = new ArrayList<>(this.size);

        for(int i = 0; i < this.size; i++)
            xVector.add(0d);

        coffIndexReady = 0;
    }

    private void addLastDVectorCoeff(){
        this.dVector.set(this.size - 1, ((this.dVector.get(this.size - 1) - this.dVector.get(this.size - 2) * tridiagonalMatrix.getcVector().get(this.size - 1)) / (tridiagonalMatrix.getaVector().get(this.size - 1) - tridiagonalMatrix.getbVector().get(this.size - 2) * tridiagonalMatrix.getcVector().get(this.size - 1))));

    }                   //Adjustment 5.

    private void eliminateLowerDiagonalOneThread(){
        Utils.changeCoefficient(tridiagonalMatrix.getbVector(), 0, (tridiagonalMatrix.getbVector().get(0)) / tridiagonalMatrix.getaVector().get(0));
        this.dVector.set(0, this.dVector.get(0) / tridiagonalMatrix.getaVector().get(0));

        for(int i = 1; i < this.size - 1; i++){
            //Formula
            Utils.changeCoefficient(tridiagonalMatrix.getbVector(), i, (tridiagonalMatrix.getbVector().get(i) / (tridiagonalMatrix.getaVector().get(i) - tridiagonalMatrix.getbVector().get(i - 1) * tridiagonalMatrix.getcVector().get(i))));
            this.dVector.set(i, ((this.dVector.get(i) - this.dVector.get(i - 1) * tridiagonalMatrix.getcVector().get(i)) / (tridiagonalMatrix.getaVector().get(i) - tridiagonalMatrix.getbVector().get(i - 1) * tridiagonalMatrix.getcVector().get(i))));
        }
    }

    private synchronized void eliminateLowerDiagonalMultiThread() throws InterruptedException {
        Runnable bCoffsEval;
        bCoffsEval = () -> {
            synchronized (this){
                log.info(Thread.currentThread().toString() + " started");               //Adjustment 6.
                log.info(Thread.currentThread().toString() + " eliminating the lower diagonal coefficients...");
                Utils.changeCoefficient(tridiagonalMatrix.getbVector(), 0, (tridiagonalMatrix.getbVector().get(0)) / tridiagonalMatrix.getaVector().get(0));

                for(int i = 1; i < this.size - 1; i++){
                    Utils.changeCoefficient(tridiagonalMatrix.getbVector(), i, (tridiagonalMatrix.getbVector().get(i) / (tridiagonalMatrix.getaVector().get(i) - tridiagonalMatrix.getbVector().get(i - 1) * tridiagonalMatrix.getcVector().get(i))));
                    coffIndexReady = i;
                    notify();
                }
                log.info(Thread.currentThread().toString() + " ended");
            }

        };

        new Thread(bCoffsEval).start();

        log.info(Thread.currentThread().toString() + " started");
        log.info(Thread.currentThread().toString() + " eliminating the lower diagonal coefficients...");
        this.dVector.set(0, this.dVector.get(0) / tridiagonalMatrix.getaVector().get(0));

        for(int i = 1; i < this.size - 1; i++){
            while(i - 1 >= coffIndexReady) {
                wait();                                                                  //Adjustment 7.
                log.info(Thread.currentThread().toString() + " waiting for the other thread...");
            }
            this.dVector.set(i, ((this.dVector.get(i) - this.dVector.get(i - 1) * tridiagonalMatrix.getcVector().get(i)) / (tridiagonalMatrix.getaVector().get(i) - tridiagonalMatrix.getbVector().get(i - 1) * tridiagonalMatrix.getcVector().get(i))));
        }
        log.info(Thread.currentThread().toString() + " ended");
    }

    private void solve(){
        this.xVector.set(this.size - 1, this.dVector.get(this.size - 1));

        for(int i = this.size - 2; i >= 0; i--){
            this.xVector.set(i, this.dVector.get(i) - tridiagonalMatrix.getbVector().get(i) * this.xVector.get(i + 1));
        }
    }

    public void thomasAlgorithm(boolean isMultiThreaded) throws InterruptedException {
        if(isMultiThreaded)
            eliminateLowerDiagonalMultiThread();
        else
            eliminateLowerDiagonalOneThread();

        addLastDVectorCoeff();

        solve();
    }

    @Override
    public String toString() {

        StringBuilder out = new StringBuilder();

        for(int i = 0; i < size; i++){
            out.append('(');
            out.append(this.tridiagonalMatrix.getcVector().get(i));
            out.append(' ');
            out.append(this.tridiagonalMatrix.getaVector().get(i));
            out.append(' ');
            out.append(this.tridiagonalMatrix.getbVector().get(i));
            out.append(") (x");
            out.append(i);
            out.append(") = (");
            out.append(this.dVector.get(i));
            out.append(')');
            out.append('\n');
        }

        return out.toString();
    }

    public String toStringXVector(){
        StringBuilder out = new StringBuilder();

        for(int i = 0; i < size; i++){
            out.append(this.xVector.get(i));
            out.append(", ");
        }
        out.append('\n');

        return out.toString();
    }
}
