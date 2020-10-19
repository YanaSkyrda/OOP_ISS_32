package utils;

import bankSystem.account.Account;
import bankSystem.bank.Bank;
import bankSystem.credit.Credit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static void printCreditList(List<Credit> list){
        if(list == null)
            return;
        int i = 1;
        for (Credit credit : list) {
            System.out.println(i + ": " + credit.toString());
            i++;
        }
    }

    public static void printAccountInfo(Account account){
        System.out.println(account.toString());
    }

    /**
     * Get users input
     * @param userMessage message to be printed for the user
     * @return the user's input string + "credit"
     */
    public static String getInputCredit(String userMessage, Scanner scan){

        //Scanner scan = View.scanner;
        System.out.println(userMessage);

        String output = "";

        if(scan.hasNext())
            output = scan.next();
        output += " credit";

        return output;
    }

    /**
     * Get users input
     * @param userMessage message to be printed for the user
     * @return the user's input Long (converted from string)
     */
    public static Long getInputLong(String userMessage, Scanner scanner){

        /*
        String str;

        while(true){
            str = View.scanner.next();

            if(isInteger(str)){
                return (long) Integer.parseInt(str);
            }
            else{
                System.out.println("Wrong value");
            }
        }*/

        System.out.println(userMessage);

        //Scanner scanner = View.scanner;
        long output;

        while(scanner.hasNext()){
            if(scanner.hasNextLong()){
                output = scanner.nextLong();
                return output;
            } else {
                scanner.next();
            }
        }

        return null;
    }

    public static boolean isInteger(String s) {
        if(s == null)
            return false;
        if(s.isEmpty())
            return false;

        if(s.charAt(0) == '0' && s.length() != 1)
            return false;

        for(int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            if(!Character.isDigit(curr))
                return false;
        }
        return true;
    }

    /**
     * Pre-initialize banks in order to use it in main function
     * @return list of initialized examples of banks
     */
    public static List<Bank> initializeBanks(){
        List<Credit> availableList1 = new ArrayList<>();
        List<Credit> availableList2 = new ArrayList<>();
        List<Bank> banks = new ArrayList<>();

        Bank bankBig = new Bank("Big bank");
        Bank bankPrivate = new Bank("Private bank");

        banks.add(bankBig);
        banks.add(bankPrivate);

        Credit avCredit1 = new Credit("Apartment credit", 10000L, 12);
        Credit avCredit2 = new Credit("Car credit", 200000L, 50);
        Credit avCredit3 = new Credit("Business credit", 33333L, 22);
        Credit avCredit4 = new Credit("Business credit", 44444L, 33);
        Credit avCredit5 = new Credit("Business credit", 5555L, 44);

        availableList1.add(avCredit1);
        availableList1.add(avCredit2);
        availableList1.add(avCredit3);
        availableList2.add(avCredit4);
        availableList2.add(avCredit5);

        bankBig.setAvailableCredits(availableList1);
        bankPrivate.setAvailableCredits(availableList2);

        return banks;
    }
}
