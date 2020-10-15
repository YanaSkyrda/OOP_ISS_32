public class ThreadManager {

    private boolean threadFlag = true;

    public void setThreadFlag(boolean threadFlag){
        this.threadFlag = threadFlag;
    }

    public void printThreadGroupHierarchy(ThreadGroup threadGroup){
        if(threadGroup == null)
            return;


        Runnable printThreadInfo;
        printThreadInfo = () -> {
            synchronized (this) {
                while (!threadGroup.isDestroyed() && threadFlag) {
                    ThreadGroup[] allThreadGroups = new ThreadGroup[threadGroup.activeCount()];
                    int sizeThreadArray = threadGroup.activeCount();
                    threadGroup.enumerate(allThreadGroups, true);

                    for(int i = 0; i < sizeThreadArray; i++){
                        if(allThreadGroups[i] != null)
                            allThreadGroups[i].list();
                    }

                    try {
                        wait(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("\n");
                }
            }
        };

        Thread printingThread = new Thread(printThreadInfo);
        printingThread.start();

    }
}
