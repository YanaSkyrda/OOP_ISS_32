import java.util.ArrayList;
import java.util.List;

public class TridiagonalMatrix {
    private final List<Double> aVector;
    private final List<Double> bVector;
    private final List<Double> cVector;

    private final int size;

    public TridiagonalMatrix(List<Double> aVector, List<Double> bVector, List<Double> cVector) throws Exception {
        if(aVector == null || bVector == null || cVector == null)
            throw new Exception("Wrong input parameters");

        if(aVector.size() <= 1)
            throw new Exception("Wrong size of aVector");

        if(aVector.size() != (bVector.size() + 1))
            throw new Exception("Wrong size of bVector");

        if(aVector.size() != (cVector.size() + 1))
            throw new Exception("Wrong size of cVector");

        this.aVector = aVector;                             //Adjustment 1.
        this.bVector = bVector;
        this.cVector = cVector;

        this.bVector.add(0d);
        this.cVector.add(0, 0d);

        this.size = this.aVector.size();
    }

    public TridiagonalMatrix(TridiagonalMatrix tridiagonalMatrix){
        this.aVector = new ArrayList<>(tridiagonalMatrix.aVector);
        this.bVector = new ArrayList<>(tridiagonalMatrix.bVector);
        this.cVector = new ArrayList<>(tridiagonalMatrix.cVector);

        this.size = tridiagonalMatrix.size;
    }

    public int getSize(){
        return size;
    }

    public List<Double> getaVector() {
        return aVector;
    }

    public List<Double> getbVector() {
        return bVector;
    }

    public List<Double> getcVector() {
        return cVector;
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
