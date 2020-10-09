import BankSystem.Account.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {

    Account account;

    @Before
    public void setUp(){
        account = new Account("Name", -21L, null, null);
    }

    @Test
    public void Account_TEST(){
        Assert.assertEquals(account.getFullName(), "Name");
        Assert.assertEquals(account.getCreditLimit(), Long.valueOf(1000));
    }
}
