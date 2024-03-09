package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private ListNode<K, V> front;

    public MoveToFrontList() {
        this.front = new ListNode<K, V>();
        this.size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null!");
        }

        ListNode<K, V> curr = front;
        ListNode<K, V> prev = null;
        V val = null;

        while (curr.next != null) {
            // If key is found
            if (key.equals(curr.key)) {
                val = curr.value;
                curr.value = value;
                if (prev != null) {
                    front = curr;
                }
                return val;
            }
            curr = curr.next;
        }

        // Key is not found
        this.size++;
        ListNode node = new ListNode(key, value, this.front);
        this.front = node;
        return null;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null!");
        }

        ListNode<K, V> prev = null;
        ListNode<K, V> curr = front;

        // Find key
        while (curr != null && !key.equals(curr.key)) {
            prev = curr;
            curr = curr.next;
        }

        // If key is found
        if (curr != null) {
            // If not at front of list
            if (prev != null) {
                prev.next = curr.next;
                curr.next = front;
                front = curr;
            }
            return curr.value;
        }

        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontIterator();
    }

    private class ListNode<K, V> {
        public ListNode next;
        public K key;
        public V value;

        public ListNode() {
            this(null, null, null);
        }
        public ListNode(K key, V value) {
            this(key, value, null);
        }

        public ListNode(K key, V value, ListNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private class MoveToFrontIterator extends SimpleIterator<Item<K, V>> {
        public ListNode<K, V> curr;

        public MoveToFrontIterator() {
            this.curr = MoveToFrontList.this.front;
        }

        public Item<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is nothing next");
            }
            Item<K, V> item = new Item<>(curr.key, curr.value);
            curr = curr.next;
            return item;
        }

        public boolean hasNext() {
            return curr != null && curr.next != null;
        }
    }
}
