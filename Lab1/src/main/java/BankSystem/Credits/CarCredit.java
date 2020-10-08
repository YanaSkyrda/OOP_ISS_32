package BankSystem.Credits;

public class CarCredit extends Credit {

    public CarCredit(Long moneyAmount, Integer monthsDuration, String bankName) {
        super("Car credit", moneyAmount, monthsDuration, bankName);
    }
}