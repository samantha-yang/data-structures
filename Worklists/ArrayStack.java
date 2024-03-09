package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private static final int CAPACITY = 10;
    private int size;
    private E[] array;


    public ArrayStack() {
        this.array = (E[])new Object[CAPACITY];
        this.size = 0;
    }

    @Override
    public void add(E work) {
        if (this.size == this.array.length) {
            int length = this.array.length * 2;
            E[] biggerArray = (E[])new Object[length];
            for (int i = 0; i < size; i++) {
                biggerArray[i] = this.array[i];
            }
            this.array = biggerArray;
        }
        this.array[size++] = work;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException("Array is empty!");
        } else {
            return this.array[size - 1];
        }
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException("Array is empty!");
        } else {
            E data = this.array[--size];
            this.array[size] = null;
            return data;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        E[] clearedArray = (E[])new Object[this.array.length];
        this.array = clearedArray;
        this.size = 0;
    }
}
