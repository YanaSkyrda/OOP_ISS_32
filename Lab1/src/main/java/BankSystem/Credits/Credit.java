package BankSystem.Credits;

public class Credit {
    protected String creditPurpose;
    protected Long moneyAmount;
    protected Long moneyLeft;
    protected Integer monthsDuration;
    protected Integer monthsLeft;
    protected String bankName;

    public Credit(String creditPurpose, Long moneyAmount, Integer monthsDuration, String bankName) {
        this.creditPurpose = creditPurpose;

        if(moneyAmount <= 0)
            this.moneyAmount = 10000L;
        else
            this.moneyAmount = moneyAmount;

        this.moneyLeft = this.moneyAmount;

        if(monthsDuration <= 0)
            this.monthsDuration = 12;
        else
            this.monthsDuration = monthsDuration;

        this.monthsLeft = this.monthsDuration;
        this.bankName = bankName;
    }

    public String getCreditPurpose() {
        return creditPurpose;
    }

    public Long getMoneyAmount() {
        return moneyAmount;
    }

    public Integer getMonthsDuration() {
        return monthsDuration;
    }

    public String getBankName() {
        return bankName;
    }

    public Long getMoneyLeft() {
        return moneyLeft;
    }

    public Integer getMonthsLeft() {
        return monthsLeft;
    }

    @Override
    public String toString() {
        return  "  Credit purpose: " + creditPurpose +
                ", Money amount to pay: " + moneyLeft +
                ", Months duration: " + monthsLeft +
                ", BankSystem.Bank.Bank name: " + bankName;
    }

    /**
     * Makes a month payment that equals moneyAmount / monthsDuration
     * @return whether the credit is still active
     */
    public boolean payMonthPart(){
        moneyLeft = moneyLeft - moneyAmount / monthsDuration;
        monthsLeft--;

        return monthsLeft > 0 && moneyLeft > 0;
    }
}
