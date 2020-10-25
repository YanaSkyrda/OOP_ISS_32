import cyclicbarrier.CustomCyclicBarrier;

import java.util.concurrent.BrokenBarrierException;

public class Main {
    static private CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3,
            () -> System.out.println("Barrier reached"));

    private static void createAndStartThreadWithWaitTime(long timeOutMillis) {
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Thread start");
                    synchronized (this) {
                        this.wait(timeOutMillis);
                    }
                    System.out.println("Thread waiting time over");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void main(String... args) {
        createAndStartThreadWithWaitTime(5000);
        createAndStartThreadWithWaitTime(10000);
        createAndStartThreadWithWaitTime(3000);
    }
}
