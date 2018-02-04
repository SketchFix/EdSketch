package juzi.test;
//import gov.nasa.jpf.jvm.Verify;

import juzi.Explorer;

public class Sketch_Operator_Int {
        // operators that can be sketched

        static enum ArithOp { PLUS, MINUS, TIMES, DIV };

        // /* program to sketch; has one arithmetic operator hole */
        // static int sketchme(int x, int y) {
        //     int result = 0;
        //         for (int i = 0; i < 4; i++) {
        //             result += x \AOP\ y;
        //     }
        //     return result;
        // }

        // expected result of sketching -- multiplication
        static int expected(int x, int y) {
                int result = 0;
                for (int i = 0; i < 4; i++) {
                        result += x * y;
                }
                return result;
        }

        // translated program to sketch
        // operator hole is replaced with method call
        static int sketchme(int x, int y) {
                int result = 0;
                for (int i = 0; i < 4; i++) {
                        result += _AOP_(x, y);
                }
                return result;
        }

        // handling one operator hole

        public static ArithOp _aop_ = null;

        static int _AOP_(int x, int y) {
                if (_aop_ == null) {
                        // this is the first time this operator is being used so
                        // explore all possibilities
                	int choice = Explorer.choose(3);
//                        int choice = Verify.getInt(0, 3);
                        if (choice == 0) _aop_ = ArithOp.PLUS;
                        else if (choice == 1) _aop_ = ArithOp.MINUS;
                        else if (choice == 2) _aop_ = ArithOp.TIMES;
                        else _aop_ = ArithOp.DIV;
                        System.out.println("INITIALIZATION: _aop_ = " + _aop_);
                }
                if (_aop_ == ArithOp.PLUS) return x + y;
                if (_aop_ == ArithOp.MINUS) return x - y;
                if (_aop_ == ArithOp.TIMES) return x * y;
                if (_aop_ == ArithOp.DIV) return x / y;
                throw new RuntimeException("Unexpected error");
        }

        // test harness

        public static void main(String[] a) {
                // Test #1
                runTest(1, 1);

                // Test #2
                runTest(2, 1);

                // Test #3
                runTest(1, 2);

                // Test #4
               // runTest(2, 2);
                System.out.println("****ALL TESTS PASSED!");
        }

        static void runTest(int x, int y) {
                int expected = expected(x, y);
                int actual = sketchme(x, y);
                boolean outcome = checkEq(expected, actual);
                String out = outcome ? "PASS" : "FAIL";
                System.out.println("[Test] expected: " + expected + ", actual: " + actual + " **" + out);
                if (!outcome) {
                        System.out.println("BACKTRACKING: test failure");
                        Explorer.backtrack();
//                        Verify.ignoreIf(true);
                }
        }

        static boolean checkEq(int x, int y) {
                return (x == y);
        }
}
