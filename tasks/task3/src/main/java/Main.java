public class Main {
    public static void main(String[] args) {
        ThreadGroupService threadGroupService = new ThreadGroupService();

        ThreadGroup threadGroup1 = new ThreadGroup("FIRST GROUP");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "SECOND GROUP");
        ThreadGroup threadGroup3 = new ThreadGroup(threadGroup2, "THIRD GROUP");

        new Thread(threadGroup1, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "First thread").start();


        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Second thread").start();

        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Third thread").start();

        new Thread(threadGroup3, () -> {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Fourth Thread").start();

        threadGroupService.printTreadsInfo(threadGroup1);
        threadGroupService.printTreadsInfo(threadGroup2);
        threadGroupService.printTreadsInfo(threadGroup3);
    }
}
