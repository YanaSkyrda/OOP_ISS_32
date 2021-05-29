package task6;

import java.util.concurrent.Semaphore;

public class ReaderWriterPattern implements MyReadWriteLock {
    private Semaphore serviceQueue;
    private Semaphore readCountAccess;
    private Semaphore write;
    private int readCount;

    public ReaderWriterPattern(){
        readCount = 0;
        serviceQueue = new Semaphore(1, true);
        readCountAccess = new Semaphore(1, true);
        write = new Semaphore(1, true);
    }

    @Override
    public void lockReader() {
        try{
            serviceQueue.acquire();
            readCountAccess.acquire();
            try {
                if (readCount == 0)
                    write.acquire();
                readCount++;
            } catch (InterruptedException ignored){
            } finally {
                readCountAccess.release();
            }
        } catch (InterruptedException ignored){
        } finally {
            serviceQueue.release();
        }
    }

    @Override
    public void unlockReader() {
        try {
            readCountAccess.acquire();
            readCount--;
            if (readCount == 0) {
                write.release();
            }
            readCountAccess.release();
        } catch (InterruptedException ignored){
        }
    }

    @Override
    public void lockWriter() {
        try {
            serviceQueue.acquire();
            write.acquire();
        } catch (InterruptedException ignored){
        } finally {
            serviceQueue.release();
        }
    }

    @Override
    public void unlockWriter() {
        write.release();
    }
}
