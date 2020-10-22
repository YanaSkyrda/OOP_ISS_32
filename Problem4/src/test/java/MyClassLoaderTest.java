import components.MyClassLoader;
import org.junit.Assert;
import org.junit.Test;
import utils.Utils;

public class MyClassLoaderTest {

    @Test(expected = ClassNotFoundException.class)
    public void findClass_FAILTEST() throws ClassNotFoundException {
        MyClassLoader myClassLoader = new MyClassLoader();
        myClassLoader.findClass(" ");
    }

    @Test
    public void findClass_SUCCESSTEST() throws ClassNotFoundException {
        MyClassLoader loader = new MyClassLoader();
        Class<?> inputClass1 = loader.findClass(Utils.getFilePathTestClass1());

        Assert.assertEquals(inputClass1.getName(), "testClasses.TestClass1");

        Class<?> inputClass2 = loader.findClass(Utils.getFilePathTestClass2());

        Assert.assertEquals(inputClass2.getName(), "testClasses.TestClass2");
    }
}
