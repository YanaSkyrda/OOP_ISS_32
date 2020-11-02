import com.university.List.MySkipList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class MySkipListTest {

    MySkipList skipList;

    @Before
    public void setUp(){
        skipList = new MySkipList(16);

        skipList.add(5);
        skipList.add(1);
        skipList.add(3);
        skipList.add(4);
        skipList.add(6);
        skipList.add(13);
        skipList.add(511);
        skipList.add(111);
        skipList.add(332);
        skipList.add(522);
        skipList.add(112);
        skipList.add(3323);
    }

    private boolean checkListOrder(){
        AtomicMarkableReference<MySkipList.Node> prev = skipList.getHeader();
        prev = prev.getReference().getNext()[0];
        AtomicMarkableReference<MySkipList.Node> curr = prev;
        curr = curr.getReference().getNext()[0];

        for(int i = 0; i < skipList.size() - 1; i++){
            if(prev.getReference().getValue() >= curr.getReference().getValue()){
                return false;
            }
            prev = curr;
            curr = curr.getReference().getNext()[0];
        }
        return true;
    }

    @Test
    public void contains_TEST(){
        Assert.assertTrue(skipList.contains(1));
        Assert.assertTrue(skipList.contains(3));
        Assert.assertTrue(skipList.contains(4));
        Assert.assertTrue(skipList.contains(5));
        Assert.assertTrue(skipList.contains(6));
        Assert.assertTrue(skipList.contains(13));
        Assert.assertTrue(skipList.contains(111));
        Assert.assertTrue(skipList.contains(112));
        Assert.assertTrue(skipList.contains(332));
        Assert.assertTrue(skipList.contains(511));
        Assert.assertTrue(skipList.contains(522));
        Assert.assertTrue(skipList.contains(3323));
        Assert.assertFalse(skipList.contains(1111));
        Assert.assertFalse(skipList.contains(0));
        Assert.assertFalse(skipList.contains(32321));
        Assert.assertFalse(skipList.contains(145312));

        Assert.assertTrue(checkListOrder());
    }

    @Test
    public void add_TEST(){
        for(int i = 0; i < 100; i++){
            Assert.assertTrue(skipList.add(200 + i));
        }

        for(int i = 0; i < 100; i++){
            Assert.assertFalse(skipList.add(200 + i));
        }

        Assert.assertTrue(checkListOrder());
    }

    @Test
    public void delete_TEST(){
        for(int i = 0; i < 100; i++){
            skipList.add(200 + i);
        }

        for(int i = 0; i < 100; i++){
            Assert.assertTrue(skipList.remove(200 + i));
        }

        for(int i = 0; i < 100; i++){
            Assert.assertFalse(skipList.remove(200 + i));
        }

        Assert.assertTrue(checkListOrder());
    }

    @Test
    public void concurrentAdd_TEST() throws InterruptedException {
        Runnable thread2;
        thread2 = () -> {
            for(int i = 0; i < 50; i++){
                skipList.add(200 + i * 2);
            }
        };

        Thread testThread = new Thread(thread2);
        testThread.start();

        for(int i = 0; i < 50; i++){
            skipList.add(200 + i * 2 + 1);
        }

        testThread.join();

        for(int i = 0; i < 100; i++){
            Assert.assertTrue(skipList.contains(200 + i));
        }

        Assert.assertTrue(checkListOrder());
    }

    @Test
    public void concurrentDeletion_TEST() throws InterruptedException {

        for(int i = 0; i < 100; i++){
            skipList.add(200 + i);
        }

        Runnable thread2;
        thread2 = () -> {
            for(int i = 0; i < 50; i++){
                skipList.remove(200 + i * 2);
            }
        };

        Thread testThread = new Thread(thread2);
        testThread.start();

        for(int i = 0; i < 50; i++){
            skipList.remove(200 + i * 2 + 1);
        }

        testThread.join();

        for(int i = 0; i < 100; i++){
            Assert.assertFalse(skipList.contains(200 + i));
        }

        Assert.assertTrue(checkListOrder());
    }

    @Test
    public void concurrentMixedOperation_TEST() throws InterruptedException {
        Runnable thread2;
        thread2 = () -> {
            for(int i = 0; i < 50; i++){
                skipList.remove(200 + i);
            }
        };

        Thread testThread = new Thread(thread2);
        testThread.start();

        for(int i = 0; i < 50; i++){
            skipList.add(200 + i);
        }

        testThread.join();

        for(int i = 0; i < 100; i++){
            Assert.assertFalse(skipList.contains(200 + i));
        }

        Assert.assertTrue(checkListOrder());
    }
}
