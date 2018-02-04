package test.sketch4j.example.operator;

import gov.nasa.jpf.jvm.Verify;


public class Sketch_LHS_Int {
    // operators that can be sketched
    static enum ArithOp { PLUS, MINUS, TIMES, DIV };
    //loop size
    static final int N = 11;
    // /* program to sketch; has one arithmetic operator hole */
    // static int sketchme(int x, int y) {
    //     int result = 1;
    //         for (int i = 0; i < N; i++) {
    //              \IV\ = \IV\ \AOP\ (\IV\ \AOP\ \IV\);
    //     }
    //     // result += complexfuncion(x, y); 
    //     return result;
    // }

    // expected result of sketching -- 
    static int expected(int x, int y) {
        int result = 1;
        for (int i = 0; i < N; i++) {
            result = result + (x - y);   //_LHS_() = result +x-y; (x, y, result) 
        }
        return result;
    }

    static int _x_, _y_, _result_;
    static int var_hole_init = -1;
    
    
    // translated program to sketch
    // operator hole is replaced with method call
    static int sketchme(int x, int y) {
        _x_ = x;
        _y_ = y;
        _result_ = 1;
        for (int i = 0; i < N; i++) {
            _ASSIGN_LHS_(_result_ + _x_ - _y_);
        }
        return _result_;
    }
    
    static void _ASSIGN_LHS_(int rhs) {
        if (var_hole_init == -1) {
            var_hole_init = Verify.getInt(0, 2);
            System.out.println("INIT: variable hole: " + new String[]{"x", "y", "result"}[var_hole_init]);
        }
        if (var_hole_init == 0) _x_ = _result_ + (_x_ - _y_);
        if (var_hole_init == 1) _y_ = _result_ + (_x_ - _y_);
        if (var_hole_init == 2) _result_ = _result_ + (_x_ - _y_);
    }

    //LHS holes
    static int[] _lhs_ = new int[]{-1}; 

    static void _LHS_(int _l_, int[] vals, String[] vars) {
        if (_lhs_[_l_]==-1) {
            _lhs_[_l_] = Verify.getInt(0,vals.length-1);
            System.out.println("INITIALIZATION: _lhs_[" + _l_ + "] = " + vars[_lhs_[_l_]]);
        }
    }

    static int[] _ivs_ = new int[]{-1, -1};

    static int _IV_(int _v_, int[] vals, String[] vars) {
        if (_ivs_[_v_] == -1) {
            _ivs_[_v_] = Verify.getInt(0, vals.length - 1);
            System.out.println("INITIALIZATION: _ivs_[" + _v_ + "] = " + vars[_ivs_[_v_]]);
        }
        return vals[_ivs_[_v_]];
    }

    // handling one operator hole

    static ArithOp[] _aop_  = new ArithOp[]{null};


    static int _AOP_(int _op_, int x, int y) {
        if (_aop_[_op_]==null) {
            // this is the first time this operator is being used so
            // explore all possibilities
            int choice = Verify.getInt(0, 3);
            if (choice == 0) _aop_[_op_] = ArithOp.PLUS;
            else if (choice == 1) _aop_[_op_] = ArithOp.MINUS;
            else if (choice == 2) _aop_[_op_] = ArithOp.TIMES;
            else  _aop_[_op_] = ArithOp.DIV;      
            System.out.println("INITIALIZATION: _aop_[" +_op_ +"] = "+_aop_[_op_]);
        }
        if (_aop_[_op_] == ArithOp.PLUS) return x + y;
        if (_aop_[_op_] == ArithOp.MINUS) return x - y; 
        if (_aop_[_op_] == ArithOp.TIMES) return x * y;   
        if (_aop_[_op_] == ArithOp.DIV) return x / y;   
        throw new RuntimeException("Unexpected error");
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
