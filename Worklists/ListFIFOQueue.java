package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private Node<E> front;
    private Node<E> back;
    private int size;

    public ListFIFOQueue() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(E work) {
        Node<E> added = new Node<>(work);
        if (!hasWork()) {
            this.front = added;
        } else {
            back.next = added;
        }
        back = added;
        size++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException("There is nothing in the queue!");
        } else {
            return front.data;
        }
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException("There is nothing in the queue!");
        } else {
            E data = front.data;
            front = front.next;
            size--;
            return data;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.front = null;
        this.back = null;
        size = 0;
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;

        private Node(E data) {
            this.data = data;
            this.next = null;
        }
    }
}
