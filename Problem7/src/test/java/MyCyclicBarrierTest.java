import com.university.MyCyclicBarrier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;

public class MyCyclicBarrierTest {

    MyCyclicBarrier myCyclicBarrier;

    @Before
    public void setUp(){
        myCyclicBarrier = new MyCyclicBarrier(5);
    }

    @Test
    synchronized public void await_TEST() throws InterruptedException {
        for(int i = 1; i < 21; i++){
            Runnable tempRun = () -> {
                try {
                    myCyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            };
            Thread temp = new Thread(tempRun);
            temp.start();
            wait(150);
            //temp.join();
            Assert.assertFalse(myCyclicBarrier.isBroken());
            Assert.assertEquals(myCyclicBarrier.getWaitingParties(), i % 5);
            Assert.assertEquals(myCyclicBarrier.getParties(), 5);
        }
    }
}
