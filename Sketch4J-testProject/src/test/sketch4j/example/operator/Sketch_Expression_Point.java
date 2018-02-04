package test.sketch4j.example.operator;
import gov.nasa.jpf.jvm.Verify;

public class Sketch_Expression_Point {
    static final int N = 11;

    static class Point {
        int x;
        int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public String toString() {
            return "("+x+","+y+")";
        }
    }

    //sketch operator
    static enum ArithOp { PLUS, MINUS, TIMES, DIV };

    //sketch RHS operants
    static int[] _ivs_ = new int[]{-1, -1};

    static int _EXP_(int _v_, int[] vals, String[] vars) {
        if (_ivs_[_v_] == -1) {
            _ivs_[_v_] = Verify.getInt(0, vals.length - 1);
            System.out.println("INITIALIZATION: _ivs_[" + _v_ + "] = " + vars[_ivs_[_v_]]);
        }
        return vals[_ivs_[_v_]];
    }



    /* program to sketch; has one arithmetic operator hole */
    // static Point sketchme(Point p) {
    //         for (int i = 0; i < N; i++) {
    //              \IV\ = \IV\ \AOP\ (\IV\ \AOP\ \IV\);
    //     }
    //     // result += complexfuncion(x, y); 
    //     return result;
    // }
    static Point expected(Point p) {
        for (int i = 0; i < N; i++) {
            p.x = p.x + 1;
            p.y = p.x;
        }
        return p;
    }

    static Point sketchme(Point p) {
        for (int i = 0; i < N; i++) {
            p.x = p.x + 1;
            p.y = _EXP_(0, new int[]{p.x, p.y}, new String[]{"p.x", "p.y"});
        }
        return p;
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
        Point expected = expected(new Point(x,y));
        Point actual = sketchme(new Point(x,y));
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

    static boolean checkEq(Point p1, Point p2) {
        return (p1.x == p2.x && p1.y == p2.y);
    }

}
