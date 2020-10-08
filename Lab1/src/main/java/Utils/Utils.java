package Utils;

import BankSystem.Account.Account;
import BankSystem.Bank.Bank;
import BankSystem.Credits.ApartmentCredit;
import BankSystem.Credits.BusinessCredit;
import BankSystem.Credits.CarCredit;
import BankSystem.Credits.Credit;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Logger;

public class Utils {

   // private static final Logger log = Logger.getLogger(Account.class.getName());

    public static void printCreditList(List<Credit> list){
        if(list == null)
            return;
        int i = 1;
        for (Credit credit : list) {
            //log.info(credit.toString());
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
    public static String getInputCredit(String userMessage){

        System.out.println(userMessage);

        String output = Account.scan.next();
        output += " credit";

        return output;
    }

    /**
     * Get users input
     * @param userMessage message to be printed for the user
     * @return the user's input string + "bank"
     */
    public static String getInputBank(String userMessage){

        System.out.println(userMessage);

        String output = Account.scan.next();
        output += " bank";

        return output;
    }

    /**
     * Get users input
     * @param userMessage message to be printed for the user
     * @return the user's input Long (converted from string)
     */
    public static Long getInputLong(String userMessage){
        Scanner scan = new Scanner(System.in);

        System.out.println(userMessage);

        String str;

        while(true){
            str = scan.next();

            if(isInteger(str)){
                return (long) Integer.parseInt(str);
            }
            else{
                System.out.println("Wrong value");
            }
        }
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
        List<Credit> availableList1 = new Vector<>();
        List<Credit> availableList2 = new Vector<>();
        List<Bank> banks = new Vector<>();

        Bank bankBig = new Bank("Big bank");
        Bank bankPrivate = new Bank("Private bank");

        banks.add(bankBig);
        banks.add(bankPrivate);

        Credit avCredit1 = new ApartmentCredit(10000L, 12, bankBig.getName());
        Credit avCredit2 = new CarCredit(200000L, 50, bankBig.getName());
        Credit avCredit3 = new BusinessCredit(33333L, 22, bankBig.getName());
        Credit avCredit4 = new BusinessCredit(44444L, 33, bankPrivate.getName());
        Credit avCredit5 = new BusinessCredit(5555L, 44, bankPrivate.getName());

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
