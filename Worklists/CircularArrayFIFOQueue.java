package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
    private E[] circleArray;
    private int front;
    private int back;
    private int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.circleArray = (E[])new Comparable[capacity];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }

    @Override
    public void add(E work) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full!");
        } else {
            this.circleArray[back] = work;
            this.back = ++back % capacity();
            this.size++;
        }
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException("Queue is empty!");
        } else {
            return circleArray[this.front];
        }
    }

    @Override
    public E peek(int i) {
        if (!hasWork()) {
            throw new NoSuchElementException("Queue is empty!");
        } else if (i < 0 || i >= size()){
            throw new IndexOutOfBoundsException("Index i is out of bounds!");
        } else {
            int index = this.front + i;
            return circleArray[index % capacity()];
        }
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException("Queue is empty!");
        } else {
            E data = this.circleArray[front];
            this.front = ++front % capacity();
            this.size--;
            return data;
        }
    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException("Queue is empty!");
        } else if (i < 0 || i >= size()){
            throw new IndexOutOfBoundsException("Index i is out of bounds!");
        } else {
            circleArray[(front + i) % capacity()] = value;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear()  {
        E[] clearedArray = (E[])new Comparable[this.circleArray.length];
        this.circleArray = clearedArray;
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
