package bankSystem.account;

import bankSystem.bank.Bank;
import bankSystem.credit.Credit;
import view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    private final String fullName;
    private Long creditLimit;
    private Long moneyAmount;
    private List<Credit> activeCredits;
    private List<Bank> availableBanks;

    public Account(String fullName, Long creditLimit, List<Credit> activeCredits, List<Bank> availableBanks) {
        this.fullName = fullName;

        if(creditLimit < 0)
            creditLimit = 1000L;
        this.creditLimit = creditLimit;
        this.activeCredits = Objects.requireNonNullElseGet(activeCredits, ArrayList::new);
        /*
        if(activeCredits == null)
            this.activeCredits = new ArrayList<>();
        else
            this.activeCredits = activeCredits;
         */
        this.availableBanks = availableBanks;
        this.moneyAmount = creditLimit;
    }

    public String getFullName() {
        return fullName;
    }

    public List<Credit> getActiveCredits() {
        return activeCredits;
    }

    public Long getCreditLimit() {
        return creditLimit;
    }

    public Long getMoneyAmount() {
        return moneyAmount;
    }

    public void setActiveCredits(List<Credit> activeCredits) {
        this.activeCredits = activeCredits;
    }

    public void setAvailableBanks(List<Bank> availableBanks) {
        this.availableBanks = availableBanks;
    }

    /**
     * Adds the credit to activeCredits list.
     * @param credit credit to add
     */
    public void addCredit(Credit credit){
        this.activeCredits.add(credit);
    }

    /**
     * Enlarges the credit limit. If newCreditLimit less than zero prints an error
     * @param newCreditLimit new wished credit limit
     */
    public void enlargeCreditLimit(Long newCreditLimit){
        if(newCreditLimit > this.creditLimit){
            Long personalMoney = this.moneyAmount - this.creditLimit;
            this.creditLimit = newCreditLimit;
            this.moneyAmount = newCreditLimit + personalMoney;

            return;
        }

        System.out.println("The value is less than current");
    }

    /**
     * Finds the credits in the activeCredits list that match the credit purpose(name)
     * @param creditName the purpose of the credit
     * @return the list of the credits that matches the given purpose(name)
     */
    public List<Credit> findActiveCredit(String creditName){
        if(this.activeCredits == null || this.activeCredits.size() == 0){
            return null;
        }

        List<Credit> foundCredits = new ArrayList<>();
        for (Credit activeCredits : this.activeCredits) {
            if (activeCredits.getCreditPurpose().equals(creditName)) {
                foundCredits.add(activeCredits);
            }
        }

        return foundCredits;
    }

    /**
     * Finds all possible credits in all banks
     * @return the list of all possible credits
     */
    public List<Credit> findAllBankCredits(){
        List<Credit> foundCredits = new ArrayList<>();
        for (Bank availableBank : this.availableBanks) {
            foundCredits.addAll(availableBank.getAvailableCredits());
        }

        return foundCredits;
    }

    /**
     * Finds all possible credits that matches the given creditPurpose(credit name) in all banks
     * @param creditPurpose aka credit name
     * @return the list of all possible credits that matches the given purpose(name)
     */
    public List<Credit> findAimBankCredits(String creditPurpose){
        List<Credit> foundCredits = new ArrayList<>();
        for (Bank availableBank : this.availableBanks) {
            for(int j = 0; j < availableBank.getAvailableCredits().size(); j++){
                if(availableBank.getAvailableCredits().get(j).getCreditPurpose().equals(creditPurpose))
                    foundCredits.add(availableBank.getAvailableCredits().get(j));
            }
        }

        return foundCredits;
    }

    /**
     * Withdraws money from current account. If the wantedAmount is larger than active money then error prints.
     * @param wantedAmount amount that user wants to withdraw
     */
    public void withdrawMoney(Long wantedAmount) {
        if(wantedAmount <= 0L){
            System.out.println("Wrong input");
            return;
        }

        if (this.moneyAmount < wantedAmount) {
            System.out.println("You have not enough money");
            return;
        }

        if (this.moneyAmount - this.creditLimit < wantedAmount) {
            if (View.withdrawMoneyView()) {
                this.moneyAmount = this.moneyAmount - wantedAmount;
                return;
            }
            return;
        }

        this.moneyAmount = this.moneyAmount - wantedAmount;
    }

    /**
     * Deposit money on current account. If the amount is less than zero then error prints.
     * @param amount amount of the money to deposit
     */
    public void depositMoney(Long amount){
        if(amount <= 0L){
            System.out.println("Wrong input");
            return;
        }

        this.moneyAmount = this.moneyAmount + amount;
    }

    /**
     * The element of the menu which allows the user to operate with the credit.
     * User can repay month part, repay whole sum or quit
     *
     * @param credit credit to operate with
     */
    public void creditOperate(Credit credit){

        int input = View.creditOperateView();

        switch (input){
            case 1:
                Long partToPay = credit.getMoneyAmount() / credit.getMonthsDuration();
                if(this.moneyAmount < partToPay){
                    System.out.println("Not enough money");
                } else {
                    this.moneyAmount = this.moneyAmount - partToPay;
                    credit.payMonthPart();
                    if(!credit.isAlive())
                        this.activeCredits.remove(credit);
                }
                return;
            case 2:
                if(this.moneyAmount < credit.getMoneyAmount()){
                    System.out.println("Not enough money");
                } else {
                    this.moneyAmount = this.moneyAmount - credit.getMoneyAmount();
                    this.activeCredits.remove(credit);
                }
                return;
            case 3:
                return;
            case 100:
                System.out.println("Unexpected error");
        }
    }

    /**
     * The element of the menu which allows the user to operate with the credit list.
     * User can choose the credit to operate with or quit
     *
     * @param creditList the list to operate with
     * @param addCredit stores whether this credit needs to be only added to activeCredits list or not.
     *                  Used in order to divide two different ways of behavior of the menu
     */
    public void creditListOperate(List<Credit> creditList, boolean addCredit){
        if(creditList == null || creditList.size() == 0){
            System.out.println("Credit list is empty");
            return;
        }

        int input = View.creditListOperateView(addCredit, creditList.size());

        if(input == 0)
            return;

        if(addCredit){
            this.addCredit(creditList.get(input - 1));
            return;
        }

        creditOperate(creditList.get(input - 1));
    }


    @Override
    public String toString() {
        if(activeCredits != null)
            return  "Full name: " + fullName +
                    ", Credit limit: " + creditLimit +
                    ", Money: " + moneyAmount +
                    ", Active credits: " + activeCredits.size() +
                    " credits";

        return "Full name: " + fullName +
                ", Credit limit: " + creditLimit +
                ", Money: " + moneyAmount +
                ", Active credits: 0 credits";
    }
}
