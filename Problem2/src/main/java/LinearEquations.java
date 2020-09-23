import java.util.ArrayList;

public class LinearEquations {
    private final TridiagonalMatrix tridiagonalMatrix;
    private final ArrayList<Double> dVector;
    private final ArrayList<Double> xVector;

    private final int size;
    private volatile int coffIndexReady;

    public static void main(String[] args) throws Exception {
        double [] A = {-2.25, -2.25, -2.25, -2.25, -2.25, -2.25, -2.25};
        double [] B = {1, 1, 1, 1, 1, 1};
        double [] C = {1, 1, 1, 1, 1, 1};
        double [] D = {0, 0, 0, 0, 0, 0, -100};

        TridiagonalMatrix tridiagonalMatrix = new TridiagonalMatrix(A, B ,C);
        LinearEquations linearEquations = new LinearEquations(tridiagonalMatrix, D);

        System.out.println(linearEquations.toString());

        linearEquations.thomasAlgorithmOneThread(true);

        //System.out.println(linearEquations.toString());
        System.out.println(linearEquations.toStringXVector());


    }

    public LinearEquations(TridiagonalMatrix tridiagonalMatrix, double[] dVector) throws Exception {
        if(dVector == null)
            throw new Exception("Wrong input dVector");

        if(tridiagonalMatrix == null)
            throw new Exception("Wrong input matrix");

        this.tridiagonalMatrix = tridiagonalMatrix;
        this.size = tridiagonalMatrix.getSize();

        this.dVector = new ArrayList<>();
        this.xVector = new ArrayList<>(this.size);

        for(int i = 0; i < this.size; i++)
            xVector.add(0d);

        for(double i : dVector)
            this.dVector.add(i);

        coffIndexReady = 0;
    }

    public LinearEquations(TridiagonalMatrix tridiagonalMatrix, ArrayList<Double> dVector) throws Exception {
        if(dVector == null)
            throw new Exception("Wrong input dVector");

        if(tridiagonalMatrix == null)
            throw new Exception("Wrong input matrix");

        this.tridiagonalMatrix = tridiagonalMatrix;
        this.dVector = dVector;

        this.size = tridiagonalMatrix.getSize();

        this.xVector = new ArrayList<>();

        coffIndexReady = 0;
    }

    private void eliminateLowerDiagonalOneThread(){
        tridiagonalMatrix.changeCoefficient(tridiagonalMatrix.getbVector(), 0, (tridiagonalMatrix.getbVector().get(0)) / tridiagonalMatrix.getaVector().get(0));
        this.dVector.set(0, this.dVector.get(0) / tridiagonalMatrix.getaVector().get(0));

        for(int i = 1; i < this.size - 1; i++){
            tridiagonalMatrix.changeCoefficient(tridiagonalMatrix.getbVector(), i, (tridiagonalMatrix.getbVector().get(i) / (tridiagonalMatrix.getaVector().get(i) - tridiagonalMatrix.getbVector().get(i - 1) * tridiagonalMatrix.getcVector().get(i))));
            this.dVector.set(i, ((this.dVector.get(i) - this.dVector.get(i - 1) * tridiagonalMatrix.getcVector().get(i)) / (tridiagonalMatrix.getaVector().get(i) - tridiagonalMatrix.getbVector().get(i - 1) * tridiagonalMatrix.getcVector().get(i))));
        }

        this.dVector.set(this.size - 1, ((this.dVector.get(this.size - 1) - this.dVector.get(this.size - 2) * tridiagonalMatrix.getcVector().get(this.size - 1)) / (tridiagonalMatrix.getaVector().get(this.size - 1) - tridiagonalMatrix.getbVector().get(this.size - 2) * tridiagonalMatrix.getcVector().get(this.size - 1))));

    }

    private void eliminateLowerDiagonalMultiThread() throws InterruptedException {
        Runnable bCoffsEval;
        bCoffsEval = () -> {
            System.out.println(Thread.currentThread().toString() + "Started");
            tridiagonalMatrix.changeCoefficient(tridiagonalMatrix.getbVector(), 0, (tridiagonalMatrix.getbVector().get(0)) / tridiagonalMatrix.getaVector().get(0));

            for(int i = 1; i < this.size - 1; i++){
                tridiagonalMatrix.changeCoefficient(tridiagonalMatrix.getbVector(), i, (tridiagonalMatrix.getbVector().get(i) / (tridiagonalMatrix.getaVector().get(i) - tridiagonalMatrix.getbVector().get(i - 1) * tridiagonalMatrix.getcVector().get(i))));
                coffIndexReady = i;
            }
            System.out.println(Thread.currentThread().toString() + "Ended");
        };

        new Thread(bCoffsEval).start();

        System.out.println(Thread.currentThread().toString() + "Started");
        this.dVector.set(0, this.dVector.get(0) / tridiagonalMatrix.getaVector().get(0));

        for(int i = 1; i < this.size - 1; i++){
            while(i - 1 >= coffIndexReady) {
                Thread.sleep(1);
                System.out.println(Thread.currentThread().toString() + "waited 1msec");
            }
            this.dVector.set(i, ((this.dVector.get(i) - this.dVector.get(i - 1) * tridiagonalMatrix.getcVector().get(i)) / (tridiagonalMatrix.getaVector().get(i) - tridiagonalMatrix.getbVector().get(i - 1) * tridiagonalMatrix.getcVector().get(i))));
        }
        this.dVector.set(this.size - 1, ((this.dVector.get(this.size - 1) - this.dVector.get(this.size - 2) * tridiagonalMatrix.getcVector().get(this.size - 1)) / (tridiagonalMatrix.getaVector().get(this.size - 1) - tridiagonalMatrix.getbVector().get(this.size - 2) * tridiagonalMatrix.getcVector().get(this.size - 1))));
        System.out.println(Thread.currentThread().toString() + "Ended");
    }

    private void solveUnknowns(){
        //this.xVector.set(this.size - 1, this.dVector.get(this.size - 1) / tridiagonalMatrix.getaVector().get(this.size - 1));
        this.xVector.set(this.size - 1, this.dVector.get(this.size - 1));

        for(int i = this.size - 2; i >= 0; i--){
            //this.xVector.set(i, this.dVector.get(i) - this.dVector.get(i) * this.xVector.get(i + 1));
            this.xVector.set(i, this.dVector.get(i) - tridiagonalMatrix.getbVector().get(i) * this.xVector.get(i + 1));
        }
    }

    //cixi-1 + aixi + bixi+1 = di ; i = 1...n
    //c1 = 0
    //bn = 0
    public void thomasAlgorithmOneThread(boolean isMultiThreaded) throws InterruptedException {
        if(isMultiThreaded)
            eliminateLowerDiagonalMultiThread();
        else
            eliminateLowerDiagonalOneThread();
        solveUnknowns();
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
