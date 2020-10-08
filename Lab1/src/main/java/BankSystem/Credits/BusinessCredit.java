package BankSystem.Credits;

public class BusinessCredit extends Credit {

    public BusinessCredit(Long moneyAmount, Integer monthsDuration, String bankName) {
        super("Business credit", moneyAmount, monthsDuration, bankName);
    }
}
