package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * - You must implement a generic chaining hashtable. You may not
 *   restrict the size of the input domain (i.e., it must accept
 *   any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given PRIME_SIZES list.
 *   Past this, it should continue to resize using some other mechanism (primes not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 *   dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private final Supplier<Dictionary<K, V>> newChain;
    private double loadFactor;
    private int index;
    private Dictionary<K, V>[] array;

    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.loadFactor = 0.0;
        this.array = new Dictionary[11];
        this.index = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null");
        }

        this.loadFactor = (size + 1) / array.length;
        if (this.loadFactor > 1.0) {
            this.array = resize();
        }

        V val = null;
        int i = hash(key, array.length);

        if (array[i] == null) {
            array[i] = newChain.get();
        } else {
            val = find(key);
        }

        array[i].insert(key, value);
        if (val == null) {
            this.size++;
        }

        return val;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }

        int i = hash(key, array.length);
        if (array[i] == null) {
            return null;
        } else {
            return array[i].find(key);
        }

    }

    private int hash(K key, int size) {
        int num = key.hashCode();
        if (num < 0) {
            return Math.abs(num) % size;
        } else {
            return num % size;
        }
    }

    private Dictionary<K, V>[] resize() {
        Dictionary<K, V>[] table = new Dictionary[PRIME_SIZES[index]];
        this.index++;

        for (Dictionary<K, V> data : this.array) {
            if (data != null) {
                for (Item<K, V> item : data) {
                    int curr = hash(item.key, table.length);
                    if (table[curr] == null) {
                        table[curr] = newChain.get();
                    }
                    table[curr].insert(item.key, item.value);
                }
            }
        }

        return table;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainHashTableIterator();
    }

    private class ChainHashTableIterator extends SimpleIterator<Item<K, V>> {
        private int currIndex;
        private Iterator<Item<K, V>> iter;

        public ChainHashTableIterator() {
            while (currIndex < array.length && array[currIndex] == null) {
                this.currIndex++;
            }
            if (currIndex < array.length) {
                iter = array[currIndex].iterator();
            }
        }

        @Override
        public boolean hasNext() {
            while (currIndex < array.length) {
                if(iter.hasNext()) {
                    return true;
                } else {
                    this.currIndex++;
                    while (currIndex < array.length && array[currIndex] == null) {
                        this.currIndex++;
                    }
                    if (currIndex < array.length) {
                        iter = array[currIndex].iterator();
                    }
                }
            }
            return false;
        }

        @Override
        public Item<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No existing next element!");
            }
            return iter.next();
        }
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
}
