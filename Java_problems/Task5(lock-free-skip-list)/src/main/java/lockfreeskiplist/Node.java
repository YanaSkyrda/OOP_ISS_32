package lockfreeskiplist;

import java.util.concurrent.atomic.AtomicMarkableReference;

class Node<T> {
    private int key;
    private T value;
    private int levelHeight;

    private AtomicMarkableReference<Node<T>>[] next;

    Node(int key) {
        this.key = key;
        this.value = null;
        this.next = new AtomicMarkableReference[CustomSkipList.getMaxHeight() + 1];
        this.levelHeight = CustomSkipList.getMaxHeight();
        for (int i = 0; i < next.length; i++) {
            next[i] = new AtomicMarkableReference<>(null, false);
        }
    }

     Node(T value, int height) {
        this.value = value;
        this.key = value.hashCode();
        this.levelHeight = height;
        this.next = new AtomicMarkableReference[levelHeight + 1];
        for (int i = 0; i < next.length; i++) {
            next[i] = new AtomicMarkableReference<>(null,false);
        }
    }
    Node(int key, T value, int levelHeight, AtomicMarkableReference<Node<T>>[] next) {
        this.key = key;
        this.value = value;
        this.levelHeight = levelHeight;
        this.next = next;
    }

    int getKey() {
        return key;
    }

    void setKey(int key) {
        this.key = key;
    }

    T getValue() {
        return value;
    }

    void setValue(T value) {
        this.value = value;
    }

    int getLevelHeight() {
        return levelHeight;
    }

    void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    }
    AtomicMarkableReference<Node<T>>[]  getNext() {
        return next;
    }

    void setNext(AtomicMarkableReference[] next) {
        this.next = next;
    }
}
