import components.ClassInfoPrinter;
import utils.Utils;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassInfoPrinter.printClassInfo(Utils.getFilePathTestClass1());
        ClassInfoPrinter.printClassInfo(Utils.getFilePathTestClass2());
    }
}
