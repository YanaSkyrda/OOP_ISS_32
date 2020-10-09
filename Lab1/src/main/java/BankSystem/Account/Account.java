package BankSystem.Account;

import BankSystem.Bank.Bank;
import BankSystem.Credits.Credit;
import Utils.Utils;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Account {
    public static final Scanner scan = new Scanner(System.in);

    private final String fullName;
    private Long creditLimit;
    private Long moneyAmount;
    private List<Credit> activeCredits;
    private final List<Bank> availableBanks;

    public Account(String fullName, Long creditLimit, List<Credit> activeCredits, List<Bank> availableBanks) {
        this.fullName = fullName;

        if(creditLimit < 0)
            creditLimit = 1000L;
        this.creditLimit = creditLimit;
        if(activeCredits == null)
            this.activeCredits = new Vector<>();
        else
            this.activeCredits = activeCredits;
        this.availableBanks = availableBanks;
        this.moneyAmount = creditLimit;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getCreditLimit() {
        return creditLimit;
    }

    /**
     * Adds the credit to activeCredits list.
     * @param credit credit to add
     */
    private void addCredit(Credit credit){
        this.activeCredits.add(credit);
    }

    /**
     * Enlarges the credit limit. If newCreditLimit less than zero prints an error
     * @param newCreditLimit new wished credit limit
     */
    private void enlargeCreditLimit(Long newCreditLimit){
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
    private List<Credit> findActiveCredit(String creditName){
        if(this.activeCredits == null || this.activeCredits.size() == 0){
            return null;
        }

        List<Credit> foundCredits = new Vector<>();
        for (Credit activeCredits : this.activeCredits) {
            if (activeCredits.getCreditPurpose().equals(creditName)) {
                foundCredits.add(activeCredits);
            }
        }

        return foundCredits;
    }

    /**
     * Finds the credits in the activeCredits list that match the credit purpose(name) and the bank name
     * @param creditName the purpose of the credit
     * @param bankName the bank of the credit
     * @return the list of the credits that matches the given purpose(name) and the given bank
     */
    private List<Credit> findActiveCredit(String creditName, String bankName){
        if(this.activeCredits == null || this.activeCredits.size() == 0){
            //System.out.println("No active credits");
            return null;
        }
        List<Credit> foundCredits = new Vector<>();
        for (Credit availableCredit : this.activeCredits) {
            if (availableCredit.getCreditPurpose().equals(creditName) && availableCredit.getBankName().equals(bankName)) {
                foundCredits.add(availableCredit);
            }
        }

        return foundCredits;
    }

    /**
     * Finds all possible credits in all banks
     * @return the list of all possible credits
     */
    private List<Credit> findAllBankCredits(){
        List<Credit> foundCredits = new Vector<>();
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
    private List<Credit> findAimBankCredits(String creditPurpose){
        List<Credit> foundCredits = new Vector<>();
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
    private void withdrawMoney(Long wantedAmount) {
        if(wantedAmount <= 0L){
            System.out.println("Wrong input");
            return;
        }

        if (this.moneyAmount < wantedAmount) {
            System.out.println("You have not enough money");
            return;
        }

        if (this.moneyAmount - this.creditLimit < wantedAmount) {
            System.out.println("You will use your credit limit");
            System.out.println("1:CONTINUE");
            System.out.println("2:CANCEL");

            int input = 100;
            String inputStr;
            boolean correctInput = false;

            while (!correctInput) {
                inputStr = scan.next();
                if (Utils.isInteger(inputStr)) {
                    input = Integer.parseInt(inputStr);
                    if (input != 1 && input != 2)
                        System.out.println("Wrong input(withdrawMoney: wrong number)");
                    else
                        correctInput = true;
                } else {
                    System.out.println("Wrong input(withdrawMoney: string input)");
                }
            }

            if (input == 1) {
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
    private void depositMoney(Long amount){
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
    private void creditOperate(Credit credit){
        System.out.println("Choose an option");
        System.out.println("1:REPAY MONTH PART");
        System.out.println("2:REPAY WHOLE SUM");
        System.out.println("3:CANCEL");

        int input = 100;
        String inputStr;
        boolean correctInput = false;

        while (!correctInput) {
            inputStr = scan.next();
            if (Utils.isInteger(inputStr)) {
                input = Integer.parseInt(inputStr);
                if (input != 1 && input != 2 && input != 3)
                    System.out.println("Wrong input(creditOperate: wrong number)");
                else
                    correctInput = true;
            } else {
                System.out.println("Wrong input(creditOperate: string input)");
            }
        }

        switch (input){
            case 1:
                Long partToPay = credit.getMoneyAmount() / credit.getMonthsDuration();
                if(this.moneyAmount < partToPay){
                    System.out.println("Not enough money");
                } else {
                    this.moneyAmount = this.moneyAmount - partToPay;
                    if(!credit.payMonthPart())
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
    private void creditListOperate(List<Credit> creditList, boolean addCredit){
        if(creditList == null || creditList.size() == 0){
            System.out.println("Credit list is empty");
            return;
        }

        if(addCredit)
            System.out.println("What credit do You want to take?");
        else
            System.out.println("Input credit number to operate with credit");
        System.out.println("0.EXIT");

        int input = -2;
        boolean correctInput = false;
        String inputStr;

        while (!correctInput) {
            inputStr = scan.next();
            if (Utils.isInteger(inputStr)) {
                input = Integer.parseInt(inputStr);
                if (input < 0 || input > creditList.size())
                    System.out.println("Wrong input(creditListOperate: wrong number)");
                else{
                    correctInput = true;
                }

            } else {
                System.out.println("Wrong input(creditListOperate: string input)");
            }
        }

        //test.close();

        if(input == 0)
            return;

        if(addCredit){
            this.addCredit(creditList.get(input - 1));
            return;
        }

        creditOperate(creditList.get(input - 1));
    }

    /**
     * The menu of the program
     */
    public void menu(){
        System.out.println("Welcome " + this.fullName);
        System.out.println("Choose your option");
        System.out.println("1.GET ALL MY CREDITS");
        System.out.println("2.FIND MY CREDITS BY PURPOSE");
        System.out.println("3.FIND MY CREDITS BY PURPOSE AND BANK NAME");
        System.out.println("4.TAKE NEW CREDIT");
        System.out.println("5.ENLARGE CREDIT LIMIT");
        System.out.println("6.DEPOSIT MONEY");
        System.out.println("7.WITHDRAW MONEY");
        System.out.println("8.ACCOUNT INFO");
        System.out.println("9.QUIT");

        int option = 7;
        boolean correctInput = false;
        String optionStr;

        while(true){
            System.out.println("BACK TO MENU");
            while (!correctInput) {
                optionStr = scan.next();
                if (Utils.isInteger(optionStr)) {
                    option = Integer.parseInt(optionStr);
                    if (option < 1 && option > 9){
                        //scan.next();
                        System.out.println("Wrong input(menu: wrong number)");
                    }
                    else
                        correctInput = true;
                } else {
                    //scan.next();
                    System.out.println("Wrong input(menu: string input)");
                }
            }

            switch (option){
                case 1:
                    Utils.printCreditList(this.activeCredits);
                    creditListOperate(this.activeCredits, false);
                    break;
                case 2:{
                    String creditPurpose = Utils.getInputCredit("Input credit purpose");
                    List<Credit> list = findActiveCredit(creditPurpose);
                    Utils.printCreditList(list);
                    creditListOperate(list, false);
                }
                    break;
                case 3: {
                    String creditPurpose = Utils.getInputCredit("Input credit purpose");
                    String bankName = Utils.getInputBank("Input bank name");
                    List<Credit> list = findActiveCredit(creditPurpose, bankName);
                    Utils.printCreditList(list);
                    creditListOperate(list, false);
                }
                    break;
                case 4:{
                    System.out.println("Choose an option");
                    System.out.println("1:LOOK THROUGH ALL POSSIBLE CREDITS");
                    System.out.println("2:LOOK THROUGH WISHED PURPOSE CREDITS");

                    int input = 100;
                    boolean correctInput1 = false;
                    String inputStr;

                    while (!correctInput1) {
                        inputStr = scan.next();
                        if (Utils.isInteger(inputStr)) {
                            input = Integer.parseInt(inputStr);
                            if (input != 1 && input != 2)
                                System.out.println("Wrong input(menu case4: wrong number)");
                            else
                                correctInput1 = true;
                        } else {
                            System.out.println("Wrong input(menu case4: string input)");
                        }
                    }

                    List<Credit> list;
                    if(input == 1)
                        list = this.findAllBankCredits();
                    else{
                        String purpose = Utils.getInputCredit("Input credit purpose");
                        list = this.findAimBankCredits(purpose);
                    }

                    Utils.printCreditList(list);
                    creditListOperate(list, true);
                }
                    break;
                case 5:{
                    Long input = Utils.getInputLong("Input wished credit limit");
                    this.enlargeCreditLimit(input);
                }
                    break;
                case 6:{
                    Long input = Utils.getInputLong("Input money to deposit");
                    this.depositMoney(input);
                }
                    break;
                case 7: {
                    Long input = Utils.getInputLong("Input money to withdraw");
                    this.withdrawMoney(input);
                }
                    break;
                case 8:
                    Utils.printAccountInfo(this);
                    break;
                case 9:
                    scan.close();
                    return;
            }
            correctInput = false;
        }
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
