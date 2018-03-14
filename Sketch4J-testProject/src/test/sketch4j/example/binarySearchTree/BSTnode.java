package test.sketch4j.example.binarySearchTree;

public class BSTnode {
    private BSTnode left_;
    private BSTnode right_;
//    public access only for sketch use
//    please use `getters` and `setters`
    public int key_;

    public BSTnode(BSTnode left, BSTnode right, int key) {
        this.left_ = left;
        this.right_ = right;
        this.key_ = key;
    }

    public BSTnode getLeft() {
        return left_;
    }

    public void setLeft(BSTnode left) {
        this.left_ = left;
    }

    public BSTnode getRight() {
        return right_;
    }

    public void setRight(BSTnode right) {
        this.right_ = right;
    }

    public int getKey() {
        return key_;
    }

    public void setKey(int key) {
        this.key_ = key;
    }

    @Override
    public String toString() {
        return Integer.toString(getKey());
    }
}
