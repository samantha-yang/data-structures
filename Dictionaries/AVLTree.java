package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    public AVLTree() {
        super();
        this.size = 0;
    }

    @Override
    public V insert(K key, V value) {
        // Key or value is null
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null!");
        }

        V data = super.find(key);
        this.root = insert(new AVLNode(key, value), (AVLNode) this.root);

        return data;
    }

    public AVLNode insert(AVLNode node, AVLNode root) {
        if (root == null) {
            this.size++;
            return node;
        }

        // Key is smaller than curr key, traverse left
        if (root.key.compareTo(node.key) > 0) {
            root.children[0] = insert(node, (AVLNode) root.children[0]);
        // Key is greater than curr key, traverse right
        } else if (root.key.compareTo(node.key) < 0) {
            root.children[1] = insert(node, (AVLNode) root.children[1]);
        // Key values are equal
        } else {
            root.value = node.value;
        }

        return balance(root);
    }

    public AVLNode balance(AVLNode node) {
        if (node == null) {
            return null;
        }

        // Left subtree unbalanced
        if (heightDiff(node) > 1) {
            // Case LL: Rotate once right
            if (heightDiff((AVLNode) node.children[0]) < 0) {
                node.children[0] = rightRotate((AVLNode) node.children[0]);
            }
            // Case RL: rotate right then rotate left
            node = leftRotate(node);
        // Right subtree unbalanced
        } else if (heightDiff(node) < -1) {
            // Case RR: Rotate once left
            if (heightDiff((AVLNode) node.children[1]) > 0) {
                node.children[1] = leftRotate((AVLNode) node.children[1]);
            }
            // Case LR: rotate left then rotate right
            node = rightRotate(node);
        }

        updateHeight(node);
        return node;
    }

    // Rotate left
    private AVLNode leftRotate(AVLNode node) {
        return rotate(node, 0, 1);
    }

    // Rotate right
    private AVLNode rightRotate(AVLNode node) {
        return rotate(node, 1, 0);
    }

    private AVLNode rotate(AVLNode node, int x, int y) {
        AVLNode updated = (AVLNode) node.children[x];
        AVLNode temp = (AVLNode) updated.children[y];
        node.children[x] = temp;
        updated.children[y] = node;
        updateHeight(node);
        updateHeight(updated);
        return updated;
    }

    public int height(AVLNode node) {
        if (node == null) {
            return -1;
        } else {
            return node.height;
        }
    }

    private void updateHeight(AVLNode node) {
        // Update height of tree by finding taller subtree
        int leftHeight = -1;
        int rightHeight = -1;
        if (node != null) {
            if (node.children[0] != null) {
                leftHeight = height((AVLNode) node.children[0]);
            }
            if (node.children[1] != null) {
                rightHeight = height((AVLNode) node.children[1]);
            }
        }
        node.height = Math.max(leftHeight, rightHeight) + 1;
    }


    private int heightDiff(AVLNode node) {
        // Checks height diff between left and right subtrees.
        // If > 0 then left heavy, < 0 right heavy
        // If 0 then balanced. Acceptable range is [-1, 1]
        if (node == null) {
            return 0;
        } else {
            int leftHeight = -1;
            if (node.children[0] != null) {
                leftHeight = height((AVLNode) node.children[0]);
            }
            int rightHeight = -1;
            if (node.children[1] != null) {
                rightHeight = height((AVLNode) node.children[1]);
            }
            return leftHeight - rightHeight;
        }
    }

    private class AVLNode extends BSTNode {
        public int height;

        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }
}
