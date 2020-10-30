import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassInfo {

    private String path;
    private Class loadedClass;

    public ClassInfo(String path) {
        CustomClassLoader classLoader = new CustomClassLoader();
        this.path = path;
        this.loadedClass = classLoader.findClass(path);
    }
    public void printInfo() {
        System.out.println("Class from: " + loadedClass.getPackage().getName());
        System.out.println("Class name: " + loadedClass.getName());
        printPackageInfo();
        printClassType();
        printFields();
    }

    private void printFields() {
        Field[] fields = loadedClass.getDeclaredFields();
        if (fields.length == 0) {
            System.out.println("There is no fields in the class");
            return;
        }
        else {
            System.out.println("Fields: ");
            for (Field field : fields) {
                System.out.println("Name of the field: " + field.getName());
                System.out.println("Modifier: " + Modifier.toString(field.getModifiers()));
                System.out.println("Type: " + field.getType());
            }
        }

    }



    private void printPackageInfo() {
        System.out.println("Package:  " + loadedClass.getPackage().getName());
        if (loadedClass.getSuperclass() != null) {
            System.out.println("This class extends " + loadedClass.getSuperclass());
        }
        else {
            System.out.println("This class doesn't extend any class");
        }
        for (Class implementedInterface: loadedClass.getInterfaces()) {
            System.out.println("This class implements: "+ implementedInterface.getName() + " interface");
        }

    }

    private void printClassType() {
        if (loadedClass.isInterface()) {
            System.out.println("This class is an interface");
        }
        if (loadedClass.isEnum()) {
            System.out.println("This class is an enum");
        }
    }
}
