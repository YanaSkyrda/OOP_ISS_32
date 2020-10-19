package bankSystem.credit;

public class Credit {

    protected String creditPurpose;
    protected Long moneyAmount;
    protected Long moneyLeft;
    protected Integer monthsDuration;
    protected Integer monthsLeft;

    public Credit(String creditPurpose, Long moneyAmount, Integer monthsDuration) {
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
                ", Months duration: " + monthsLeft;
    }

    /**
     * Makes a month payment that equals moneyAmount / monthsDuration
     */
    public void payMonthPart(){
        moneyLeft = moneyLeft - moneyAmount / monthsDuration;
        monthsLeft--;
    }

    /**
     * Checks whether the credit is still alive
     * @return 1 - alive; 0 - dead
     */
    public boolean isAlive(){
        return monthsLeft > 0 && moneyLeft > 0;
    }
}
