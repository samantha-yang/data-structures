package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null");
        }

        V elem = null;
        if (!key.isEmpty()) {
            HashTrieNode curr = (HashTrieNode) this.root;
            Iterator<A> iterator = key.iterator();

            while (iterator.hasNext()) {
                A data = iterator.next();
                if (!curr.pointers.containsKey(data)) {
                    curr.pointers.put(data, new HashTrieNode());
                }
                curr = curr.pointers.get(data);
            }
            elem = curr.value;
            curr.value = value;
        } else {
            elem = this.root.value;
            this.root.value = value;
        }

        if (elem == null) {
            this.size++;
        }

        return elem;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }

        if (this.root == null) {
            return null;
        }

        HashTrieNode curr = (HashTrieNode) this.root;
        Iterator<A> iterator = key.iterator();
        while (iterator.hasNext()) {
            A data = iterator.next();
            curr = curr.pointers.get(data);
            if (curr == null) {
                return null;
            }
        }

        return curr.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }

        if (this.root == null) {
            return false;
        }

        HashTrieNode curr = (HashTrieNode) this.root;
        Iterator<A> iterator = key.iterator();
        while (iterator.hasNext()) {
            A data = iterator.next();
            curr = curr.pointers.get(data);
            if (curr == null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }

        HashTrieNode curr = (HashTrieNode) this.root;
        HashTrieNode prev = null;
        A letter = null;

        Iterator<A> iterator = key.iterator();
        while (iterator.hasNext()) {
            A data = iterator.next();
            if (!curr.pointers.containsKey(data)) {
                return;
            }
            if (curr.pointers.size() > 1 || curr.value != null) {
                prev = curr;
                letter = data;
            }
            curr = curr.pointers.get(data);
        }

        if (!curr.pointers.isEmpty()){
            curr.value = null;
        } else if (letter != null) {
            prev.pointers.remove(letter);
        } else {
            this.root = new HashTrieNode();
        }

        this.size--;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = new HashTrieNode();
    }
}
