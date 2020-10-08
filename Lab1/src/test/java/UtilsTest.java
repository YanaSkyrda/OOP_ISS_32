import BankSystem.Bank.Bank;
import org.junit.Assert;
import org.junit.Test;

import Utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

public class UtilsTest {

    @Test
    public void getInputLong_TEST(){
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("321".getBytes());
        System.setIn(in);

        Assert.assertEquals(Utils.getInputLong(""), Long.valueOf(321));

        System.setIn(sysInBackup);
    }

    @Test
    public void isInteger_TEST(){
        String str1 = "";
        String str2 = " ";
        String str3 = "012";
        String str4 = "1 2";
        String str5 = "a123";
        String str6 = "123a";
        String str7 = "123 ";

        List<String> strWrong = new Vector<>();
        strWrong.add(str1);
        strWrong.add(str2);
        strWrong.add(str3);
        strWrong.add(str4);
        strWrong.add(str5);
        strWrong.add(str6);
        strWrong.add(str7);

        String str8 = "1";
        String str9 = "0";
        String str10 = "1000";
        String str11 = "111111111111";

        List<String> strRight = new Vector<>();
        strRight.add(str8);
        strRight.add(str9);
        strRight.add(str10);
        strRight.add(str11);

        for(int i = 0; i < 7; i++)
            Assert.assertFalse(Utils.isInteger(strWrong.get(i)));

        for(int i = 0; i < 4; i++)
            Assert.assertTrue(Utils.isInteger(strRight.get(i)));
    }

    @Test
    public void initializeBanks_TEST(){
        List<Bank> banks = Utils.initializeBanks();

        Assert.assertEquals(banks.get(0).getName(), "Big bank");
        Assert.assertEquals(banks.get(1).getName(), "Private bank");

        Assert.assertEquals(banks.get(0).getAvailableCredits().size(), 3);
        Assert.assertEquals(banks.get(1).getAvailableCredits().size(), 2);
    }

    @Test
    public void getInputCredit_TEST(){
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("Test".getBytes());
        System.setIn(in);

        Assert.assertEquals(Utils.getInputCredit(""), "Test credit");

        System.setIn(sysInBackup);
    }

    @Test
    public void getInputBank_TEST(){
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("Test".getBytes());
        System.setIn(in);

        Assert.assertEquals(Utils.getInputBank(""), "Test bank");

        System.setIn(sysInBackup);
    }
}
