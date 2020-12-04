public class CustomReentrantLock {
    private int countOfWaitingThreads = 0;
    private Thread lockedThread = null;

    public synchronized void lock() {

        if (countOfWaitingThreads == 0) {
            lockedThread = Thread.currentThread();
            countOfWaitingThreads++;
        }
        else if (countOfWaitingThreads > 0 && lockedThread == Thread.currentThread()) {
            countOfWaitingThreads++;
        }
        else {
            while (countOfWaitingThreads > 0) {
                try {
                    //System.out.println(Thread.currentThread().getName() + " is waiting");
                    this.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            countOfWaitingThreads++;
            lockedThread = Thread.currentThread();
        }


    }



    public synchronized void unlock()  throws IllegalMonitorStateException{

        if (countOfWaitingThreads == 0) {
            throw new IllegalMonitorStateException();
        }
        countOfWaitingThreads--;


        if (countOfWaitingThreads == 0) {
            this.notify();
        }


    }
}
