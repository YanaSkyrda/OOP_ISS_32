import org.junit.Assert;
import org.junit.Test;
import utils.Utils;

public class UtilsTest {

    @Test
    public void getClassNameFromPath_TEST(){
        Assert.assertEquals(Utils.getClassNameFromPath(Utils.getFilePathTestClass1()), "testClasses.TestClass1");
        Assert.assertEquals(Utils.getClassNameFromPath(Utils.getFilePathTestClass2()), "testClasses.TestClass2");
    }
}
