
class Credit {
    protected String creditPurpose;
    protected Long moneyAmount;
    protected Integer monthsDuration;
    protected String bankName;

    public Credit(String creditPurpose, Long moneyAmount, Integer monthsDuration, String bankName) {
        this.creditPurpose = creditPurpose;
        this.moneyAmount = moneyAmount;
        this.monthsDuration = monthsDuration;
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

    @Override
    public String toString() {
        return "Credit{" +
                "creditPurpose='" + creditPurpose + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", monthsDuration=" + monthsDuration +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
