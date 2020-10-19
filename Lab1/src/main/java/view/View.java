package view;

import bankSystem.account.Account;
import bankSystem.credit.Credit;
import utils.Utils;

import java.util.List;
import java.util.Scanner;

public class View {

    public static final Scanner scanner = new Scanner(System.in);

    public static boolean withdrawMoneyView(){
        System.out.println("You will use your credit limit");
        System.out.println("1:CONTINUE");
        System.out.println("2:CANCEL");

        int input = 100;
        String inputStr;
        boolean correctInput = false;

        while (!correctInput) {
            inputStr = scanner.next();
            if (Utils.isInteger(inputStr)) {
                input = Integer.parseInt(inputStr);
                if (input != 1 && input != 2)
                    System.out.println("Wrong input(withdrawMoneyView: wrong number)");
                else
                    correctInput = true;
            } else {
                System.out.println("Wrong input(withdrawMoneyView: string input)");
            }
        }
        return input == 1;
    }

    public static int creditOperateView(){
        System.out.println("Choose an option");
        System.out.println("1:REPAY MONTH PART");
        System.out.println("2:REPAY WHOLE SUM");
        System.out.println("3:CANCEL");

        int input = 100;
        String inputStr;
        boolean correctInput = false;

        while (!correctInput) {
            inputStr = scanner.next();
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

        return input;
    }

    public static int creditListOperateView(boolean addCredit, int creditListSize){

        if(addCredit)
            System.out.println("What credit do You want to take?");
        else
            System.out.println("Input credit number to operate with credit");
        System.out.println("0.EXIT");

        int input = -2;
        boolean correctInput = false;
        String inputStr;

        while (!correctInput) {
            inputStr = scanner.next();
            if (Utils.isInteger(inputStr)) {
                input = Integer.parseInt(inputStr);
                if (input < 0 || input > creditListSize)
                    System.out.println("Wrong input(creditListOperate: wrong number)");
                else{
                    correctInput = true;
                }

            } else {
                System.out.println("Wrong input(creditListOperate: string input)");
            }
        }
        return input;
    }

    public static void menu(Account account){

        System.out.println("Welcome " + account.getFullName());
        System.out.println("Choose your option");
        System.out.println("1.GET ALL MY CREDITS");
        System.out.println("2.FIND MY CREDITS BY PURPOSE");
        System.out.println("3.TAKE NEW CREDIT");
        System.out.println("4.ENLARGE CREDIT LIMIT");
        System.out.println("5.DEPOSIT MONEY");
        System.out.println("6.WITHDRAW MONEY");
        System.out.println("7.ACCOUNT INFO");
        System.out.println("8.QUIT");

        int option = 10;
        boolean correctInput = false;
        String optionStr;

        while(true){
            System.out.println("BACK TO MENU");
            /*while (!correctInput) {
                optionStr = scan.nextLine();
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
            }*/

            while(!correctInput && scanner.hasNext()){
                if(scanner.hasNextInt()){
                    option = scanner.nextInt();
                    if(option >= 1 && option <= 9)
                        correctInput = true;
                } else {
                    scanner.next();
                }
            }

            switch (option){
                case 1:
                    Utils.printCreditList(account.getActiveCredits());
                    account.creditListOperate(account.getActiveCredits(), false);
                    break;
                case 2:{
                    String creditPurpose = Utils.getInputCredit("Input credit purpose", View.scanner);
                    List<Credit> list = account.findActiveCredit(creditPurpose);
                    Utils.printCreditList(list);
                    account.creditListOperate(list, false);
                }
                break;
                case 3:{
                    System.out.println("Choose an option");
                    System.out.println("1:LOOK THROUGH ALL POSSIBLE CREDITS");
                    System.out.println("2:LOOK THROUGH WISHED PURPOSE CREDITS");

                    int input = 100;
                    boolean correctInput1 = false;
                    String inputStr;

                    while (!correctInput1) {
                        inputStr = scanner.next();
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
                        list = account.findAllBankCredits();
                    else{
                        String purpose = Utils.getInputCredit("Input credit purpose",  View.scanner);
                        list = account.findAimBankCredits(purpose);
                    }

                    Utils.printCreditList(list);
                    account.creditListOperate(list, true);
                }
                break;
                case 4:{
                    Long input = Utils.getInputLong("Input wished credit limit",  View.scanner);
                    account.enlargeCreditLimit(input);
                }
                break;
                case 5:{
                    Long input = Utils.getInputLong("Input money to deposit",  View.scanner);
                    account.depositMoney(input);
                }
                break;
                case 6: {
                    Long input = Utils.getInputLong("Input money to withdraw",  View.scanner);
                    account.withdrawMoney(input);
                }
                break;
                case 7:
                    Utils.printAccountInfo(account);
                    break;
                case 8:
                    scanner.close();
                    return;
            }

            correctInput = false;
        }
    }
}
