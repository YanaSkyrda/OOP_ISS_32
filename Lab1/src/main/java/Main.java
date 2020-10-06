import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        List<Credit> availableList = new Vector<>();
        Bank bank = new Bank("MyBank");
        Bank bank1 = new Bank("MyBank1");
        Credit avCredit1 = new ApartmentCredit(10000L, 12, bank.getName());
        Credit avCredit2 = new CarCredit(200000L, 50, bank.getName());
        Credit avCredit3 = new BusinessCredit(33333L, 22, bank.getName());

        Credit avCredit4 = new BusinessCredit(44444L, 33, bank1.getName());
        Credit avCredit5 = new BusinessCredit(5555L, 44, bank1.getName());
        availableList.add(avCredit1);
        availableList.add(avCredit2);
        availableList.add(avCredit3);
        availableList.add(avCredit4);
        availableList.add(avCredit5);

        Account account = new Account("Vitaliy Datsiuk", 1000L, availableList);

        List<Credit> temp = account.findCredit("Business credit", bank1.getName());
        Utils.logCreditList(temp);
    }
}
