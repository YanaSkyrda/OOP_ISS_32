package vegetables.exceptions;

public class NegativeCaloriesAmount extends Exception {
    public NegativeCaloriesAmount() { }
    public NegativeCaloriesAmount(String message) {
        super(message);
    }
}
