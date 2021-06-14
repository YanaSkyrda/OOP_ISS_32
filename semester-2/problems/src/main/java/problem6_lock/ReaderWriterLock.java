package problem6_lock;

import java.util.concurrent.Semaphore;

public class ReaderWriterLock {
    private final Semaphore service;
    private final Semaphore readCountAccess;
    private final Semaphore write;
    private int readCount;

    public ReaderWriterLock() {
        readCount = 0;
        service = new Semaphore(1, true);
        readCountAccess = new Semaphore(1, true);
        write = new Semaphore(1, true);
    }
    public void lockReader() {
        try {
            service.acquire();
            readCountAccess.acquire();
            try {
                if (readCount == 0) {
                    write.acquire();
                }
                ++readCount;
            } catch (InterruptedException ignored) {
            } finally {
                readCountAccess.release();
            }
        } catch (InterruptedException ignored) {
        } finally {
            service.release();
        }
    }

    public void unlockReader() {
        try {
            readCountAccess.acquire();
            --readCount;
            if (readCount == 0) {
                write.release();
            }
            readCountAccess.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lockWriter() {
        try {
            service.acquire();
            write.acquire();
        } catch (InterruptedException ignored) {
        } finally {
            service.release();
        }
    }

    public void unlockWriter() {
        write.release();
    }
}
