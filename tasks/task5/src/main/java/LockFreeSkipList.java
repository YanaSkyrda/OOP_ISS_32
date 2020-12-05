import java.util.Random;
import java.util.concurrent.atomic.AtomicMarkableReference;


public class LockFreeSkipList {
    private static final int maxheight = 20;
    private final Node head = new Node(Integer.MIN_VALUE, maxheight);
    private final Node tail = new Node(Integer.MAX_VALUE, maxheight);


    public LockFreeSkipList()
    {
        for(int i=0;i < head.next.length;i++)
        {
            head.next[i] = new AtomicMarkableReference<Node>(tail, false);
        }
    }

    private int randomheight()
    {
        Random rand = new Random();
        int height = 0;
        while(rand.nextInt(2) == 1)
        {
            height++;
        }
        return height;
    }

    public boolean find(int value,Node[] pred,Node[] succ)
    {
        Node before = null, curr = null, after = null;
        while(true)
        {
            before = head;
            boolean done = true;


            for(int level = maxheight; level >= 0 && done; level--)
            {
                curr = before.next[level].getReference();
                while(true)
                {
                    boolean[] temp = {false};

                    after = curr.next[level].get(temp);

                    while(temp[0])
                    {

                        done = before.next[level].compareAndSet(curr, after, false, false);
                        if(!done)
                            break;
                        curr = before.next[level].getReference();
                        after = curr.next[level].get(temp);
                    }
                    if(curr.getdata() < value && done)
                    {
                        before = curr;
                        curr = after;
                    }
                    else
                        break;
                }

                if(done)
                {
                    pred[level] = before;
                    succ[level] = curr;
                }
                else
                    break;
            }
            if(done)
                return curr.getdata()==value;
        }
    }

    public boolean contains(int value)
    {
        Node before = null,curr = null,after = null;
        while(true)
        {
            before = head;


            for(int level = maxheight; level >= 0; level--)
            {
                curr = before.next[level].getReference();
                while(true)
                {
                    boolean[] temp = {false};
                    after = curr.next[level].get(temp);

                    while(temp[0])
                    {
                        before = curr;
                        curr = before.next[level].getReference();
                        after = curr.next[level].get(temp);
                    }

                    if(curr.getdata() < value)
                    {
                        before = curr;
                        curr = after;
                    }
                    else
                        break;
                }
            }
            return curr.getdata()==value;
        }
    }

    public void print()
    {
        for(int level = maxheight; level >= 0; level--)
        {
            System.out.print(level);
            boolean[] temp = {false};
            Node curr = head;
            Node after = curr.next[level].get(temp);
            while(after != null)
            {
                System.out.print(Integer.toString(curr.getdata()) + ' ');
                int flag = 0;
                while(temp[0] && after != null)
                {
                    flag = 1;
                    curr = after;
                    after = after.next[level].get(temp);
                }
                if(flag == 0)
                {
                    after = after.next[level].get(temp);
                }
            }
            System.out.println();

        }
    }


    public boolean add(int value)
    {

        int highestlevel = randomheight();
        Node[] preds = new Node[maxheight+1];
        Node[] succs = new Node[maxheight+1];
        while(true)
        {

            boolean present = find(value,preds,succs);


            if(present)
            {
                return false;
            }
            else
            {

                Node curr = new Node(value,highestlevel);
                for(int level = 0; level <= highestlevel; level++)
                {
                    curr.next[level].set(succs[level], false);
                }

                boolean added = preds[0].next[0].compareAndSet(succs[0], curr, false, false);


                if(!added)
                {
                    continue;
                }

                for(int level = 1; level <= highestlevel; level++)
                {

                    while(true)
                    {
                        boolean temp = preds[level].next[level].compareAndSet(succs[level], curr, false, false);
                        if(temp)
                            break;

                        find(value,preds,succs);
                    }
                }
                return true;
            }
        }
    }

    public boolean remove(int value)
    {

        Node[] preds = new Node[maxheight+1];
        Node[] succs = new Node[maxheight+1];
        while(true)
        {

            boolean present = find(value,preds,succs);

            if(!present)
            {
                return false;
            }

            else
            {
                Node curr = succs[0];

                for(int level = curr.getheight(); level >= 1; level--)
                {

                    boolean[] temp = {false};
                    Node succ = curr.next[level].get(temp);

                    while(!temp[0])
                    {
                        curr.next[level].attemptMark(succ, true);
                        succ = curr.next[level].get(temp);
                    }
                }
                boolean[] temp = {false};
                Node succ = curr.next[0].get(temp);
                while(true)
                {

                    boolean marked = curr.next[0].compareAndSet(succ, succ, false, true);

                    if(marked)
                    {
                        find(value,preds,succs);
                        return true;
                    }
                    else
                    {
                        succ = curr.next[0].get(temp);

                        if(temp[0])
                            return false;
                    }
                }
            }
        }
    }
}