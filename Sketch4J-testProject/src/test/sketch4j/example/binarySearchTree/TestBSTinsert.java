package test.sketch4j.example.binarySearchTree;

public class TestBSTinsert {

    public static boolean  testBSTinsert() {
        BSTinsertFactory factory = new BSTinsertFactory();
        factory.insert(8, factory.getRoot());
        factory.insert(4, factory.getRoot());
        factory.insert(5, factory.getRoot());
        factory.insert(7, factory.getRoot());
        return factory.inOrder(factory.getRoot()).equals("45678");
    }


    public static boolean testBSTinsertSketch() {
        BSTinsertFactory factory = new BSTinsertFactory();
        factory.insertSketch(8, factory.getRootS());
        factory.insertSketch(5, factory.getRootS());
        return factory.inOrder(factory.getRootS()).equals("568");
    }
   
}

