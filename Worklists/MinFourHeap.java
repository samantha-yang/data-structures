package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private Comparator<E> comp;
    private static final int capacity = 10;

    public MinFourHeap(Comparator<E> c) {
        this.data = (E[]) new Object[capacity];
        this.size = 0;
        this.comp = c;
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        if (size == data.length) {
            E[] newHeap = (E[]) new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                newHeap[i] = data[i];
            }
            data = newHeap;
        }
        data[size] = work;
        this.size++;
        percolateUp(size - 1);
    }

    private void percolateUp(int num) {
        int parent = (num - 1) / 4;
        while (num > 0) {
            if (comp.compare(data[num], data[parent]) >= 0) {
                return;
            }

            E temp = data[num];
            data[num] = data[parent];
            data[parent] = temp;
            num = parent;
            parent = (num - 1) / 4;
        }
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException("Heap is empty!");
        } else {
            return data[0];
        }
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException("Heap is empty!");
        }
        E front = data[0];
        this.size--;
        data[0] = data[this.size];
        percolateDown(0);

        return front;
    }

    private void percolateDown(int num) {
        int min = num;
        int index = 4 * num + 1;

        while (min < size) {
            for (int i = 0; i < 4; i++) {
                int child = index + i;
                if (child < size && comp.compare(data[child], data[min]) < 0) {
                    min = child;
                }
            }

            if (num != min) {
                E value = data[num];
                data[num] = data[min];
                data[min] = value;
                num = min;
                min = num;
                index = 4 * num + 1;
            } else {
                return;
            }
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.data = (E[]) new Object[capacity];
    }
}
