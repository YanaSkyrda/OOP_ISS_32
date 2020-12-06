import java.util.concurrent.atomic.AtomicMarkableReference;

public final class Node {
    private final int data;
    private int height;
    final AtomicMarkableReference<Node>[] next;

    public Node(int data,int height)
    {
        this.data = data;
        this.height = height;
        this.next = new AtomicMarkableReference[height + 1];
        for(int i = 0; i <= height; i++)
        {
            next[i] = new AtomicMarkableReference<Node>(null, false);
        }
    }

    public int getdata()
    {
        return this.data;
    }
    public int getheight()
    {
        return this.height;
    }
}