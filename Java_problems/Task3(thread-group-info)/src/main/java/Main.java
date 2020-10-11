import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<ThreadGroup> associateThreadsWithGroups() {
        List<ThreadGroup> threadGroups = new ArrayList<>(3);
        ThreadGroup threadGroup1 = new ThreadGroup("Grandparent");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "Parent");
        ThreadGroup threadGroup3 = new ThreadGroup(threadGroup2, "Child");
        threadGroups.add(threadGroup1);
        threadGroups.add(threadGroup2);
        threadGroups.add(threadGroup3);

        new Thread(threadGroup1, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(threadGroup3, () -> {
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        return threadGroups;
    }
    public static void main(String[] args) {
        List<ThreadGroup> threadGroups = associateThreadsWithGroups();

        ThreadGroupInfoPrinter printer1 = new ThreadGroupInfoPrinter(threadGroups.get(0));
        printer1.printInfoPeriodically(5000);
    }
}
