import BankSystem.Bank.Bank;
import BankSystem.Account.Account;
import Utils.Utils;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Bank> banks = Utils.initializeBanks();
        Account account = new Account("Vitaliy Datsiuk", 1000L, null, banks);

        account.menu();

    }
}
