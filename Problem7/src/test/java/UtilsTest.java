import com.university.Utils;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void generator_TEST(){
        for(int i = 0; i < 10000; i++){
            int temp = Utils.generateTimeWait();
            Assert.assertTrue(temp >= 1000 && temp <= 2500);
        }
    }
}
