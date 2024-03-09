package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
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
        int size1 = this.size();
        int size2 = other.size();
        int min = Math.min(size1, size2);

        for (int i = 0; i < min; i++) {
            E elem1 = this.peek(i);
            E elem2 = other.peek(i);

            int num = elem1.compareTo(elem2);
            if (num != 0) {
                return num;
            }
        }

        // Check if sizes are equal, atp elements have been checked
        if (size1 > size2) {
            return 1;
        } else if (size1 < size2) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if (this.size() != other.size()) {
                return false;
            } else {
                for (int i = 0; i < this.size(); i++) {
                    if (this.peek(i) != other.peek(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    @Override
    public int hashCode() {
        int result = 1;
        int num = 31;

        for (int i = 0; i < this.circleArray.length; i++) {
            E data = this.circleArray[i];
            if (data != null) {
                result = num * result + data.hashCode();
            }
        }
        return result;
    }
}