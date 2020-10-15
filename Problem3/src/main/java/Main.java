import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        SimpleProgram program = new SimpleProgram();
        ThreadGroup group = program.getMainThreadGroup();

        ThreadManager threadManager = new ThreadManager();

        Runnable programRunnable;
        programRunnable = () -> {
            try {
                program.simulateWork();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread programThread = new Thread(programRunnable);
        programThread.start();

        threadManager.printThreadGroupHierarchy(group);

        char quitCh = ' ';

        while(true){
            quitCh = scan.next().charAt(0);
            if(quitCh == 'q'){
                program.setThreadFlag(false);
                threadManager.setThreadFlag(false);
                return;
            }
        }
    }
}
