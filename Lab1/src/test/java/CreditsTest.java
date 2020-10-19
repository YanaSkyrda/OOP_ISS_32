import bankSystem.credit.Credit;
import org.junit.Assert;
import org.junit.Test;

public class CreditsTest {

    @Test
    public void payMonthPart_TEST(){
        Credit credit = new Credit("Apartment credit", 12000L, 12);
        long startedValueMoney = 12000L;
        int startedValueMonths = 12;

        for(int i = 0; i < startedValueMonths; i++){

            credit.payMonthPart();
            Assert.assertTrue(credit.isAlive());
            startedValueMoney -= 1000;
            startedValueMonths--;
            Assert.assertEquals(credit.getMoneyLeft(), Long.valueOf(startedValueMoney));
            Assert.assertEquals(credit.getMonthsLeft(), (Integer)startedValueMonths);
        }
    }
}
