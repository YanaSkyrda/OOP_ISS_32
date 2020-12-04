public class CustomReentrantLock {
    private int countOfWaitingThreads = 0;
    private Thread lockedThread;


    public synchronized void lock() {
        if (countOfWaitingThreads == 0) {
            lockedThread = Thread.currentThread();
            countOfWaitingThreads++;
        }
        else if (lockedThread != Thread.currentThread()) {
            while (lockedThread != Thread.currentThread()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " is waiting");
                    wait();
                    countOfWaitingThreads++;
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lockedThread = Thread.currentThread();
            }
        } else {
            countOfWaitingThreads++;
        }
    }



    public synchronized void unlock()  throws IllegalMonitorStateException{
        if (countOfWaitingThreads == 0) {
            throw new IllegalMonitorStateException();
        }
        countOfWaitingThreads--;


        if (countOfWaitingThreads == 0) {
            notify();
        }

    }


    public synchronized boolean tryLock() {
        if (countOfWaitingThreads == 0) {
            lock();

            return true;
        }
        return false;
    }
}
