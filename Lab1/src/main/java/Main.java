import bankSystem.bank.Bank;
import bankSystem.account.Account;
import utils.Utils;
import view.View;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Bank> banks = Utils.initializeBanks();
        Account account = new Account("Vitaliy Datsiuk", 1000L, null, banks);

        View.menu(account);


    }
}
