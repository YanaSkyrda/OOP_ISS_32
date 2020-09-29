import java.util.List;

//Adjustment 4.
public class Utils {
    public static void changeCoefficient(List<Double> vector, int index, Double value){
        if(vector == null || value == null)
            return;
        vector.set(index, value);
    }

}
