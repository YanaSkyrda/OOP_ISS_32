import java.util.ArrayList;

public class TridiagonalMatrix {
    private final ArrayList<Double> aVector;
    private final ArrayList<Double> bVector;
    private final ArrayList<Double> cVector;

    private final int size;

    public TridiagonalMatrix(double[] aVector, double[] bVector, double[] cVector) throws Exception {

        if(aVector == null || bVector == null || cVector == null)
            throw new Exception("Wrong input parameters");

        if(aVector.length <= 1)
            throw new Exception("Wrong size of aVector.");

        if(aVector.length != (bVector.length + 1))
            throw new Exception("Wrong size of bVector");

        if(aVector.length != (cVector.length + 1))
            throw new Exception("Wrong size of cVector");

        this.aVector = new ArrayList<>();
        this.bVector = new ArrayList<>();
        this.cVector = new ArrayList<>();

        for(double i : aVector)
            this.aVector.add(i);

        for(double i : bVector)
            this.bVector.add(i);
        this.bVector.add((double)0);

        this.cVector.add((double)0);
        for(double i : cVector)
            this.cVector.add(i);

        this.size = this.aVector.size();
    }

    public TridiagonalMatrix(ArrayList<Double> aVector, ArrayList<Double> bVector, ArrayList<Double> cVector) throws Exception {
        if(aVector == null || bVector == null || cVector == null)
            throw new Exception("Wrong input parameters");

        this.aVector = aVector;
        this.bVector = bVector;
        this.cVector = cVector;

        this.size = this.aVector.size();
    }

    public int getSize(){
        return size;
    }

    public ArrayList<Double> getaVector() {
        return aVector;
    }

    public ArrayList<Double> getbVector() {
        return bVector;
    }

    public ArrayList<Double> getcVector() {
        return cVector;
    }

    public void changeCoefficient(ArrayList<Double> vector, int index, Double value){
        if(vector == null || value == null)
            return;

        vector.set(index, value);
    }

    @Override
    public String toString() {

        StringBuilder out = new StringBuilder();

        for(int i = 0; i < size; i++){
            out.append(cVector.get(i));
            out.append(' ');
            out.append(aVector.get(i));
            out.append(' ');
            out.append(bVector.get(i));
            out.append('\n');
        }

        return out.toString();
    }
}
