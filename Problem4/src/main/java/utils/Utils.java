package utils;

import testClasses.TestClass1;
import testClasses.TestClass2;

public class Utils {

    public static String getFilePathTestClass1(){
        return TestClass1.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "testClasses/TestClass1.class";
    }

    public static String getFilePathTestClass2(){
        return TestClass2.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "testClasses/TestClass2.class";
    }

    public static String getClassNameFromPath(String path){
        int nameStart = path.lastIndexOf('/') + 1;
        int nameEnd = path.lastIndexOf('.');

        return "testClasses." + path.substring(nameStart, nameEnd);
    }

}
