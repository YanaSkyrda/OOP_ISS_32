package problem4_skip_list;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class SkipList {
    private static class Node {
        final public int value;
        final public AtomicReference<Node>[] next;
        final public int level;

        public Node(int value, int level, int maxLevels) {
            this.value = value;
            this.level = level;
            this.next = (AtomicReference<Node>[]) new AtomicReference<?>[maxLevels];
            for (int i = 0; i < maxLevels; ++i)
                this.next[i] = null;
        }

    }

    final int maxLevels;
    final double p = 0.5;
    final public AtomicInteger size = new AtomicInteger(0);
    final AtomicInteger levels = new AtomicInteger(0);
    final AtomicReference<Node> head;

    public SkipList(int maxLevels) {
        this.maxLevels = maxLevels;
        this.head = new AtomicReference<Node>(new Node(Integer.MIN_VALUE, 0, maxLevels));
    }

    private final Random level = new Random(0);

    private int chooseRandomLevel() {
        int newLevel = 0;
        while (newLevel < maxLevels - 1 && level.nextFloat() < this.p) {
            newLevel += 1;
        }
        return newLevel;
    }

    public void add(int value) {
        AtomicReference<Node> currAtomic = head;
        Node current = currAtomic.get();
        Node[] update = new Node[maxLevels];
        int currentLevel = levels.get();

        for (int i = currentLevel; i >= 0; i--) {
            currAtomic = head;
            current = currAtomic.get();
            while (current.next[i] != null && current.next[i].get().value < value) {
                currAtomic = current.next[i];
                current = currAtomic.get();
            }
            update[i] = current;
        }
        currAtomic = current.next[0];
        if (currAtomic != null)
            current = currAtomic.get();

        if (currAtomic == null || current.value != value) {
            int rLevel = chooseRandomLevel();
            if (rLevel > currentLevel) {
                for (int i = currentLevel + 1; i <= rLevel; ++i)
                    update[i] = head.get();

                levels.compareAndSet(currentLevel, rLevel);
            }

            Node newNode = new Node(value, rLevel, maxLevels);

            for (int i = 0; i <= rLevel; ++i) {
                newNode.next[i] = update[i].next[i];
                update[i].next[i] = new AtomicReference<Node>(newNode);
            }

            size.incrementAndGet();
        }
    }

    public void remove(int value) {
        AtomicReference<Node> currAtomic = head;
        Node current = currAtomic.get();
        Node[] update = new Node[maxLevels];
        int currentLevel = levels.get();

        for (int i = currentLevel; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].get().value < value) {
                currAtomic = current.next[i];
                current = currAtomic.get();
            }
            update[i] = current;
        }
        currAtomic = current.next[0];
        if (currAtomic != null)
            current = currAtomic.get();

        if (currAtomic != null && current.value == value) {
            for (int i = 0; i <= currentLevel; ++i) {
                if (update[i].next[i] == null || update[i].next[i].get() != current)
                    break;
                update[i].next[i] = current.next[i];
            }

            int level = levels.get();
            while (level > 0 && head.get().next[level] == null) {
                --level;
            }
            levels.compareAndSet(currentLevel, level);
            size.decrementAndGet();
        }
    }

    public boolean contains(int value) {
        AtomicReference<Node> currAtomic = head;
        Node current = currAtomic.get();
        int currentLevel = levels.get();

        for (int i = currentLevel; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].get().value < value) {
                currAtomic = current.next[i];
                current = currAtomic.get();
            }
        }
        currAtomic = current.next[0];
        if (currAtomic != null)
            current = currAtomic.get();

		return currAtomic != null && current.value == value;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        int l = levels.get();
        for (int i = l; i >= 0; --i) {
            AtomicReference<Node> currAtomic = head.get().next[i];
            Node current = currAtomic.get();
            while (currAtomic != null) {
                str.append(" ").append(current.value);
                currAtomic = current.next[i];
                if (currAtomic != null)
                    current = currAtomic.get();
            }
            str.append('\n');
        }

        return str.toString();
    }

}
