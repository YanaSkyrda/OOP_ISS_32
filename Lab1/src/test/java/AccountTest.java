import bankSystem.account.Account;
import bankSystem.bank.Bank;
import bankSystem.credit.Credit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AccountTest {

    Account account;
    ByteArrayInputStream in = new ByteArrayInputStream("1\n2\n1".getBytes());

    @Before
    public void setUp(){
        account = new Account("Name", -21L, null, null);
    }

    @Test
    public void enlargeCreditLimit_TEST(){
        account.enlargeCreditLimit(500L);
        Assert.assertEquals(account.getCreditLimit(), Long.valueOf(1000));

        account.enlargeCreditLimit(1500L);
        Assert.assertEquals(account.getCreditLimit(), Long.valueOf(1500));
        Assert.assertEquals(account.getMoneyAmount(), Long.valueOf(1500));
    }

    @Test
    public void findActiveCredit_TEST(){
        Credit avCredit1 = new Credit("Apartment credit", 10000L, 12);
        Credit avCredit2 = new Credit("Car credit", 200000L, 50);
        Credit avCredit3 = new Credit("Business credit", 33333L, 22);
        Credit avCredit4 = new Credit("Business credit", 44444L, 33);
        Credit avCredit5 = new Credit("Business credit", 5555L, 44);

        List<Credit> allCredits = new ArrayList<>();
        allCredits.add(avCredit1);
        allCredits.add(avCredit2);
        allCredits.add(avCredit3);
        allCredits.add(avCredit4);
        allCredits.add(avCredit5);

        account.setActiveCredits(allCredits);

        List<Credit> foundList = account.findActiveCredit("Business credit");

        Assert.assertEquals(foundList.size(), 3);
        Assert.assertEquals(foundList.get(0), allCredits.get(2));
        Assert.assertEquals(foundList.get(1), allCredits.get(3));
        Assert.assertEquals(foundList.get(2), allCredits.get(4));
    }

    @Test
    public void findAimBankCredits_TEST(){
        List<Bank> banks = Utils.initializeBanks();
        account.setAvailableBanks(banks);

        List<Credit> foundList = account.findAimBankCredits("Business credit");
        Assert.assertEquals(foundList.size(), 3);

        for (Credit credit : foundList) Assert.assertEquals(credit.getCreditPurpose(), "Business credit");

    }

    @Test
    public void withdrawMoney_TEST(){
        account.withdrawMoney(-5L);
        Assert.assertEquals(account.getMoneyAmount(), Long.valueOf(1000));

        account.withdrawMoney(10000L);
        Assert.assertEquals(account.getMoneyAmount(), Long.valueOf(1000));

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        //in = new ByteArrayInputStream("1".getBytes());

        System.setIn(in);
        in.reset();

        account.withdrawMoney(600L);
        Assert.assertEquals(account.getMoneyAmount(), Long.valueOf(400));

        System.setIn(sysInBackup);
    }

    @Test
    public void depositMoney_TEST(){
        account.depositMoney(-5L);
        Assert.assertEquals(account.getMoneyAmount(), Long.valueOf(1000));

        account.depositMoney(10000L);
        Assert.assertEquals(account.getMoneyAmount(), Long.valueOf(11000));
    }

    @Test
    public void creditOperate_TEST(){
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        System.setIn(in);

        Credit credit = new Credit("Business credit", 12000L, 12);
        List<Credit> listCredits = new ArrayList<>();
        listCredits.add(credit);

        account.setActiveCredits(listCredits);
        account.depositMoney(1000000L);
        account.creditOperate(credit);

        Assert.assertEquals(credit.getMoneyLeft(), Long.valueOf(11000L));
        Assert.assertEquals(credit.getMonthsLeft(), Integer.valueOf(11));
        Assert.assertTrue(account.getActiveCredits().contains(credit));

        System.setIn(sysInBackup);

        System.setIn(in);

        account.creditOperate(credit);

        Assert.assertEquals(credit.getMoneyLeft(), Long.valueOf(11000L));
        Assert.assertEquals(credit.getMonthsLeft(), Integer.valueOf(11));
        Assert.assertFalse(account.getActiveCredits().contains(credit));
        Assert.assertEquals(account.getMoneyAmount(), Long.valueOf(988000L));

        System.setIn(sysInBackup);
    }
}
