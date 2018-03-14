package test.sketch4j.example.binarySearchTree;

import sketch4j.request.Sketch4J;

public class BSTinsertFactory extends TreeTraversal{
    private BSTnode root;
    private BSTnode rootS;

    public BSTinsertFactory() {
        this.root = new BSTnode(null, null, 6);
        this.rootS = new BSTnode(null, null, 6);
    }

    public BSTnode getRoot() {
        return root;
    }

    public BSTnode getRootS() {
        return rootS;
    }

    // insert normal
    public BSTnode insert(int key, BSTnode node) {
        if (node == null)
            return new BSTnode(null, null, key);

        if (key > node.getKey())
            node.setRight(insert(key, node.getRight()));
        else if (key < node.getKey())
            node.setLeft(insert(key, node.getLeft()));

        return node;
    }

    // insert sketch
    public BSTnode insertSketch(int key, BSTnode node) {
        if (node == null)
            return new BSTnode(null, null, key);

        boolean cond1 = Sketch4J.COND(int.class, new String[] { "key", "node.key_" }, new Object[] {key, node.key_}, 1,
                false, 1, 0);
        if (cond1) node.setRight(insert(key, node.getRight()));
        else node.setLeft(insert(key, node.getLeft()));

        return node;
    }

    @Override
    public String inOrder(BSTnode node) {
        String result = "";

        if (node.getLeft() != null)
            result += inOrder(node.getLeft());

        result += node;

        if (node.getRight() != null)
            result += inOrder(node.getRight());

        return result;
    }
}
