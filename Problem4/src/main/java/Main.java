import testClasses.TestClass1;
import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {



        //System.out.println(Utils.getClassNameFromPath(Utils.getFilePathTestClass1()));

        ClassInfoLoader.printClassInfo(Utils.getFilePathTestClass1());
        ClassInfoLoader.printClassInfo(Utils.getFilePathTestClass2());
        /*System.out.println(cl);
        //Получаем метод m1 из загруженного класса
        Method method = cl.getMethod("m1", new Class[] {String.class, int.class});
        System.out.println(method);
        //Выполняем метод m1. Нельзя забывать про метод newInstance(), если метод динамический.
        method.invoke(cl.newInstance(), new Object[]{"Test", 8});
        //Выполняем метод m2. Он статический, поэтому newInstance() в методе invoke писать не надо
        Method method2 = cl.getMethod("m2", new Class[]{String.class});
        method2.invoke(cl, "QWERRTY");*/

    }
}
