public class OtherCredit extends Credit{
    public OtherCredit(Long moneyAmount, Integer monthsDuration, String bankName) {
        super("Other credit", moneyAmount, monthsDuration, bankName);
    }

    public String test(){
        return this.getCreditPurpose();

    }
}

