package testClasses;

public final class TestClass2 {

    public final String c2;
    public volatile Double d2;

    private transient int a2;
    private static boolean b2;

    public TestClass2(){
        c2 = "";
    }

    public TestClass2(String c2){
        this.c2 = c2;
    }

    private synchronized Long class2Method1(){
        System.out.println("This is method 1 in class 2");
        return 1L;
    }

    public static char class2Method2(){
        System.out.println("This is method 2 in class 2");
        return ' ';
    }

}
