public class SimpleProgram {

    //private static final Logger log = Logger.getLogger(SimpleProgram.class.getName());

    private Runnable sumOnes;
    private Runnable sumTwos;
    private Runnable sumThrees;
    private Runnable sumMinusOnes;
    private Runnable sumMinusTwos;

    private final ThreadGroup mainThreadGroup;

    private boolean threadFlag;

    SimpleProgram(){
        sumOnes = sumTwos = sumThrees = sumMinusOnes = sumMinusTwos = null;
        mainThreadGroup = new ThreadGroup("Main thread group");
        threadFlag = true;
    }

    public ThreadGroup getMainThreadGroup() {
        return mainThreadGroup;
    }

    public void setThreadFlag(boolean threadFlag) {
        this.threadFlag = threadFlag;
    }

    private void prepareThreads(){

        this.sumOnes = () -> {
            synchronized (this) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //log.info("1 + 1 = 2");
            }
        };

        this.sumTwos = () -> {
            synchronized (this) {
                try {
                    wait(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //log.info("2 + 2 = 4");
            }
        };

        this.sumThrees = () -> {
            synchronized (this) {
                try {
                    wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //log.info("3 + 3 = 6");
            }
        };

        this.sumMinusOnes = () -> {
            synchronized (this) {
                try {
                    wait(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //log.info("(-1) + (-1) = -2");
            }
        };

        this.sumMinusTwos = () -> {
            synchronized (this) {
                try {
                    wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //log.info("(-2) + (-2) = -4");
            }
        };
    }

    public void simulateWork() throws InterruptedException {
        synchronized (this){
            ThreadGroup threadGroupPos = new ThreadGroup(mainThreadGroup, "Positive numbers");
            ThreadGroup threadGroupNeg = new ThreadGroup(mainThreadGroup, "Negative numbers");

            prepareThreads();

            while(threadFlag){
                new Thread(threadGroupPos, this.sumOnes, "sumOnes thread").start();
                new Thread(threadGroupPos, this.sumTwos, "sumTwos thread").start();
                new Thread(threadGroupPos, this.sumThrees, "sumThrees thread").start();
                new Thread(threadGroupNeg, this.sumMinusOnes, "sumMinusOnes thread").start();
                new Thread(threadGroupNeg, this.sumMinusTwos, "sumMinusTwos thread").start();

                wait(2000);
            }
        }
    }
}
