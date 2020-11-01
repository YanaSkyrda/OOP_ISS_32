import com.university.Utils.Utils;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void getRandomLevel_TEST(){
        for(int i = 0; i < 1000; i++){
            Assert.assertTrue(Utils.getRandomLevel(16) < 16);
        }

        for(int i = 0; i < 1000; i++){
            Assert.assertEquals(0, Utils.getRandomLevel(0));
        }
    }
}
