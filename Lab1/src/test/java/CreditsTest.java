import BankSystem.Credits.ApartmentCredit;
import BankSystem.Credits.CarCredit;
import BankSystem.Credits.Credit;
import org.junit.Assert;
import org.junit.Test;

public class CreditsTest {

    @Test
    public void credit_TEST(){
        Credit credit = new CarCredit(-1200L, 0, "Test bank");
        Assert.assertEquals(credit.getMoneyAmount(), Long.valueOf(10000L));
        Assert.assertEquals(credit.getMoneyLeft(), Long.valueOf(10000L));
        Assert.assertEquals(credit.getMonthsDuration(), (Integer)12);
        Assert.assertEquals(credit.getMonthsLeft(), (Integer)12);
    }

    @Test
    public void payMonthPart_TEST(){
        Credit credit = new ApartmentCredit(12000L, 12, "Test bank");
        long startedValueMoney = 12000L;
        int startedValueMonths = 12;

        for(int i = 0; i < startedValueMonths; i++){
            if(i == startedValueMonths - 1)
                Assert.assertTrue(credit.payMonthPart());

            Assert.assertTrue(credit.payMonthPart());
            startedValueMoney -= 1000;
            startedValueMonths--;
            Assert.assertEquals(credit.getMoneyLeft(), Long.valueOf(startedValueMoney));
            Assert.assertEquals(credit.getMonthsLeft(), (Integer)startedValueMonths);
        }
    }
}
