package test.sketch4j.example.operator;

import gov.nasa.jpf.jvm.Verify;


public class Sketch_Iterator_Size {
    // operators that can be sketched
    static enum ArithOp { PLUS, MINUS, TIMES, DIV };
    //loop size
    //static final int N = 11;
    // /* program to sketch; has one arithmetic operator hole */
    // static int sketchme(int x, int y) {
    //     int result = 1;
    //         for (int i = 0; i < \N\; i++) {
    //              result = result + (x - y);
    //     }
    //     return result;
    // }

    // expected result of sketching -- 
    static int expected(int x, int y) {
        int result = 1;
        for (int i = 0; i < 11; i++) {
            result = result + (x - y);
        }
        return result;
    }

    // translated program to sketch
    // operator hole is replaced with method call
    static int sketchme(int x, int y) {
        int result = 1;
        for (int i = 0; i < _SIZE_(); i++) {
            result = result + (x - y);
        }
        return result;
    }

    static int _N_ = -1;

    static int _SIZE_() {
        if (_N_ == -1) {
            _N_ = Verify.getInt(0, 13);
            System.out.println("INIT: loop count hole: "+ _N_);
        }
        return _N_;
    }

    // test harness

    public static void main(String[] a) {
        Verify.resetCounter(0);
        Verify.resetCounter(1);
        Verify.resetCounter(2);
        Verify.resetCounter(3);

        // Test #1
        runTest(10,15, 0);

        // Test #2
        runTest(2,1, 1);

        // Test #3
        runTest(1,2, 2);

        // Test #4
        runTest(1,1, 3);

        System.out.println("****ALL TESTS PASSED!");

    }

    static void runTest(int x, int y, int counter) {
        int expected = expected(x, y);
        int actual = sketchme(x,y);
        boolean outcome = checkEq(expected, actual);
        String out = outcome ? "PASS" : "FAIL";
        System.out.println("[Test] expected: " + expected + ", actual: " + actual + " **" + out);
        Verify.incrementCounter(counter);
        System.out.println("counter#" + counter + ": " + Verify.getCounter(counter));
        if (!outcome) {
            System.out.println("BACKTRACKING: test failure");
            Verify.ignoreIf(true);
        }
    }

    static boolean checkEq(int x, int y) {
        return (x == y);
    }
}
