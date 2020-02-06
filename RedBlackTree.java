public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given BTree (2-3-4) TREE. */
    public RedBlackTree(BTree<T> tree) {
        Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3-4 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        // TODO: YOUR CODE HERE
        // HINT: Having a case for each number of items in r might help
        RBTreeNode rootNode = null;
        if (r == null) {
            rootNode = null;
        }

        else if (r.getItemCount() == 1) {
            rootNode = new RBTreeNode(true, r.getItemAt(0), buildRedBlackTree(r.getChildAt(0)), buildRedBlackTree(r.getChildAt(1)));
        }

        else if (r.getItemCount() == 2) {
            rootNode = new RBTreeNode(true, r.getItemAt(0), buildRedBlackTree(r.getChildAt(0)), null);
            rootNode.right = new RBTreeNode(false, r.getItemAt(1), buildRedBlackTree(r.getChildAt(1)), buildRedBlackTree(r.getChildAt(2)));
        }

        else if (r.getItemCount() == 3) {
            rootNode = new RBTreeNode(true, r.getItemAt(1));
            rootNode.left = new RBTreeNode(false, r.getItemAt(0), buildRedBlackTree(r.getChildAt(0)), buildRedBlackTree(r.getChildAt(1)));
            rootNode.right = new RBTreeNode(false, r.getItemAt(2), buildRedBlackTree(r.getChildAt(2)), buildRedBlackTree(r.getChildAt(3)));
        }

        return rootNode;
//        return null;
    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
//        return null;
        RBTreeNode rootNode = new RBTreeNode(node.isBlack, node.left.item, node.left.left, null);
        rootNode.right = new RBTreeNode(false, node.item, node.left.right, node.right);
        return rootNode;

    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
//        return null;
        RBTreeNode rootNode = new RBTreeNode(node.isBlack, node.right.item, null, node.right.right);
        rootNode.left = new RBTreeNode(false, node.item, node.left, node.right.left);
        return rootNode;
    }

    void insert(T item) {
        // TODO: YOUR CODE HERE
        root = insert(root, item);
        root.isBlack = true;

//        if (root == null) {
//            root =  new RBTreeNode(false, item);
//            return;
//        }
//
//        int compare = item.compareTo(root.item);
//
//        if (compare == 0){
//
//        }
//        else if (compare < 0) {
//            root.left = insert(root.left, item);
//        }
//        else if (compare > 0) {
//            root.right = insert(root.right, item);
//        }
//
//
//        if (isRed(root.right) && !isRed(root.left)) {
//            root = rotateLeft(root);
//        }
//
//        if (isRed(root.left) && isRed(root.left.left)) {
//            root = rotateRight(root);
//        }
//
//        if (isRed(root.left) && isRed(root.right)) {
//            flipColors(root);
//        }
//
//        root.isBlack = true;

    }

    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
    	// Optional helper method
    	// HINT: Remember to handle each of the cases from the spec
//    	return null;
        if (node == null) {
            return new RBTreeNode(false, item);
        }

        int compare = item.compareTo(node.item);
        if (compare == 0) {
            return node;
        }
        else if (compare < 0) {
            node.left = insert(node.left, item);
        }

        else if (compare > 0) {
            node.right = insert(node.right, item);
        }

        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }

        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;


    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Main method to help test constructor. Feel free to modify */
    public static void main(String[] args) {
        BTree<Integer> bTree = new BTree<>();
        bTree.root = new BTree.TwoThreeFourNode<>(3, 4);
        RedBlackTree<Integer> rbTree = new RedBlackTree<>(bTree);
        System.out.println((rbTree.root != null));
        System.out.println((rbTree.root.left == null));
        System.out.println((rbTree.root.right != null));
        System.out.println((rbTree.root.isBlack));
        System.out.println((!rbTree.root.right.isBlack));
        System.out.println(3 == rbTree.root.item);
        System.out.println(4 == rbTree.root.right.item);
    }

}
