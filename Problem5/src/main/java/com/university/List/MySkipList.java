package com.university.List;

import com.university.Utils.Utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class MySkipList {
    public class Node {
        final int value;
        final int towerLevel;
        final AtomicMarkableReference<Node>[] next;

        boolean markedForRemoval;

        public AtomicMarkableReference<Node>[] getNext() {
            return next;
        }

        public int getValue() {
            return value;
        }

        @SuppressWarnings("unchecked")
        public Node(int value, int towerLevel, int maxLevel) {
            this.value = value;
            this.towerLevel = towerLevel;
            this.next = (AtomicMarkableReference<Node>[])
                    new AtomicMarkableReference<?>[maxLevel];
            this.markedForRemoval = false;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }

    final AtomicMarkableReference<Node> header;
    final AtomicInteger currentListLevel;
    final AtomicInteger size;
    final int maxLevel;

    public MySkipList(int maxLevel){
        this.currentListLevel = new AtomicInteger(0);
        this.size = new AtomicInteger(0);
        this.maxLevel = maxLevel;
        Node headerNode = new Node(Integer.MAX_VALUE, 0, maxLevel);
        header = new AtomicMarkableReference<>(headerNode, true);
        for (int level = 0; level < maxLevel; level++) {
            headerNode.next[level] = header;
        }
    }

    public int size() {
        return size.get();
    }

    public AtomicMarkableReference<Node> getHeader() {
        return header;
    }

    public boolean contains (int inputValue) {
        AtomicMarkableReference<Node> currentListElement = this.header;
        AtomicMarkableReference<Node> previousListElement = this.header;
        Node currentNode = currentListElement.getReference();

        int level = currentListLevel.get() - 1;

        for (; level >= 0; level--) {

            currentListElement = currentNode.next[level];
            currentNode = currentListElement.getReference();
            
            while (currentNode.value < inputValue) {
                if(level == currentListLevel.get() - 1 && currentListElement == this.header){
                    return false;
                }
                previousListElement = currentListElement;
                currentListElement = currentNode.next[level];
                currentNode = currentListElement.getReference();
            }

            currentListElement = previousListElement;
            currentNode = currentListElement.getReference();
        }

        currentListElement = currentNode.next[0];
        currentNode = currentListElement.getReference();

        return currentNode.value == inputValue;
    }

    public boolean add(Integer inputValue){
        Node[] update = new Node[maxLevel];
        Node currentNode = formUpdateArray(update, inputValue);
        int levels = currentListLevel.get();

        if(currentNode.value == inputValue) {
            return false;
        } else {
            int newLevel = Utils.getRandomLevel(this.maxLevel);

            //if the level is higher, we create new level that equals currLevel + 1
            if (newLevel >= levels) {
                currentListLevel.incrementAndGet();
                levels += 1;
                newLevel = levels - 1;
                update[newLevel] = header.getReference();
                //header.attemptMark(header.getReference(), false);
            }

            Node newNode = new Node(inputValue, newLevel, maxLevel);
            AtomicMarkableReference<Node> atomicNewNode =
                    new AtomicMarkableReference<>(newNode, true);

            for (int level = 0; level < levels; level++) {

                //if we are on the zero level and see deletion mark we reform the update array again
                if(update[level].markedForRemoval && level == 0){
                    return add(inputValue);
                }

                //if we are on the level that greater then zero and see deletion mark simply return
                //consider it as success adding.
                if(update[level].markedForRemoval){
                    return true;
                }

                //if we want to add and delete the same key and add function sees the deletion func
                if(level > 0){
                    if(update[level - 1].next[level - 1].getReference().markedForRemoval){
                        return true;
                    }
                }

                newNode.next[level] = update[level].next[level];
                //Node beforeNode = update[level].next[level].getReference();
                //update[level].next[level].attemptMark(beforeNode, true);
                update[level].next[level] = atomicNewNode;
            }

            size.incrementAndGet();

            return true;
        }
    }

    public boolean remove(Integer inputValue){
        Node[] update = new Node[maxLevel];
        Node currentNode = formUpdateArray(update, inputValue);
        int levels = currentListLevel.get();

        if (currentNode.value == inputValue) {
            currentNode.markedForRemoval = true;
            //for (int level = 0; level < levels; level++) {
            for (int level = levels - 1; level >= 0; level--) {
                update[level].next[level] = currentNode.next[level];
                if (update[level].next[level] == null) {
                    update[level].next[level] = header;
                }
            }

            size.decrementAndGet();

            //delete excess levels of the tower
            while (levels > 1 && header.getReference().next[levels] == header) {
                levels = currentListLevel.decrementAndGet();
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        AtomicMarkableReference<Node> current = header;
        Node currentNode = current.getReference();
        current = currentNode.next[0];
        currentNode = current.getReference();

        StringBuilder out = new StringBuilder();
        out.append("[");
        while(current != header){
            out.append(currentNode.value);
            current = currentNode.next[0];

            currentNode = current.getReference();
            if(current != header){
                out.append(" ");
            }
        }
        out.append("]");

        return out.toString();
    }

    private Node formUpdateArray(Node[] update, Integer inputValue){
        AtomicMarkableReference<Node> currentListElement = this.header;
        AtomicMarkableReference<Node> previousListElement;
        Node currentNode = currentListElement.getReference();

        for(int level = currentListLevel.get() - 1; level >= 0; level--) {
            previousListElement = currentListElement;       //
            currentListElement = currentNode.next[level];
            currentNode = currentListElement.getReference();

            while (currentNode.value < inputValue) {
                if(currentNode.markedForRemoval){
                    unlink(previousListElement.getReference(), level);
                    return formUpdateArray(update, inputValue);
                }
                previousListElement = currentListElement;
                currentListElement = currentNode.next[level];
                currentNode = currentListElement.getReference();
            }
            update[level] = previousListElement.getReference();

            currentListElement = previousListElement;
            currentNode = currentListElement.getReference();
        }

        currentListElement = currentNode.next[0];
        currentNode = currentListElement.getReference();

        return currentNode;
    }

    private void unlink(Node previousElement, int level){
        previousElement.next[level] = previousElement.next[level].getReference().next[level];
    }
}
