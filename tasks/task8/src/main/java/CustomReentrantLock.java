public class CustomReentrantLock {
    private int countOfWaitingThreads = 0;
    private Thread lockedThread = null;

    private final Object obj = new Object();
    public void lock() {
        synchronized (obj) {
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
                        obj.wait();

                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                countOfWaitingThreads++;
                lockedThread = Thread.currentThread();
            }
        }

    }



    public void unlock()  throws IllegalMonitorStateException{
        synchronized (obj) {
            if (countOfWaitingThreads == 0) {
                throw new IllegalMonitorStateException();
            }
            countOfWaitingThreads--;


            if (countOfWaitingThreads == 0) {
                obj.notify();
            }
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
