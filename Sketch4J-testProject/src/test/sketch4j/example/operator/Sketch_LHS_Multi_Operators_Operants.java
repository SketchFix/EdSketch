package test.sketch4j.example.operator;
import gov.nasa.jpf.jvm.Verify;


public class Sketch_LHS_Multi_Operators_Operants {
    // operators that can be sketched
    static enum ArithOp { PLUS, MINUS, TIMES, DIV };
    //loop size
    static final int N = 11;
    // /* program to sketch; has one arithmetic operator hole */
    // static int sketchme(int x, int y) {
    //     int result = 1;
    //         for (int i = 0; i < N; i++) {
    //              \IV\ = result \AOP\ (\IV\ \AOP\ \IV\);
    //     }
    //     // result += complexfuncion(x, y); 
    //     return result;
    // }

    // expected result of sketching -- 
    static int expected(int x, int y) {
        int result = 1;
        for (int i = 0; i < N; i++) {
            result = result + (x - y);
        }
        return result;
    }

    // translated program to sketch
    // operator hole is replaced with method call
    static int sketchme(int x, int y) {
        int result = 1;
        {
        _result_ = result;
        _x_ = x;
        _y_ = y;
        }
        for (int i = 0; i < N; i++) {
            _LHS_(0, _AOP_(0, _IV_(0, new int[]{_x_, _y_,_result_}), _AOP_(1, _IV_(1, new int[]{_x_, _y_,_result_}), _IV_(2, new int[]{_x_, _y_,_result_}))));
        }
        {
        result = _result_;
        }
        return result;
    }

    static int _x_, _y_, _result_;
    static int[] _lhs_ = new int[]{-1};
    
    static void _LHS_(int _l_, int val) {
        if (_lhs_[_l_]==-1) {
            _lhs_[_l_] = Verify.getInt(0, 2);
            //System.out.println("INITIALIZATION: _lhs_[" + _l_ + "] = " + new String[]{"x", "y", "result"}[_lhs_[_l_]]);
        }
        if (_lhs_[_l_] == 0) _x_ = val;
        if (_lhs_[_l_] == 1) _y_ = val;
        if (_lhs_[_l_] == 2) _result_ = val;
    }

    static int[] _ivs_ = new int[]{-1, -1, -1};

    static int _IV_(int _v_, int[] vals) {
        if (_ivs_[_v_] == -1) {
            _ivs_[_v_] = Verify.getInt(0, vals.length - 1);
            //System.out.println("INITIALIZATION: _ivs_[" + _v_ + "] = " + vars[_ivs_[_v_]]);
        }
        return vals[_ivs_[_v_]];
    }

    // handling one operator hole

    static ArithOp[] _aop_  = new ArithOp[]{null, null};

    static int _AOP_(int _op_, int x, int y) {
        if (_aop_[_op_]==null) {
            // this is the first time this operator is being used so
            // explore all possibilities
            int choice = Verify.getInt(0, 3);
            if (choice == 0) _aop_[_op_] = ArithOp.PLUS;
            else if (choice == 1) _aop_[_op_] = ArithOp.MINUS;
            else if (choice == 2) _aop_[_op_] = ArithOp.TIMES;
            else  _aop_[_op_] = ArithOp.DIV;      
            //System.out.println("INITIALIZATION: _aop_[" +_op_ +"] = "+_aop_[_op_]);
        }
        if (_aop_[_op_] == ArithOp.PLUS) return x + y;
        if (_aop_[_op_] == ArithOp.MINUS) return x - y; 
        if (_aop_[_op_] == ArithOp.TIMES) return x * y;   
        if (_aop_[_op_] == ArithOp.DIV) return (y==0)? x: x / y;   
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

   static String[] _var_ = {"x", "y", "result"};
    static void runTest(int x, int y, int counter) {
        int expected = expected(x, y);
        
       // try{
            int actual = sketchme(x,y);
        
        boolean outcome = checkEq(expected, actual);
        String out = outcome ? "PASS" : "FAIL";
        System.out.println("[Test] ("+x+","+y+") expected: " + expected + ", actual: " + actual + " **" + out+" -- _aop_[0] = "+_aop_[0]+" _ivs_[0] = " + " _aop_[1] = "+_aop_[1]+" _ivs_[0] = " + _var_[_ivs_[0]]+" _ivs_[1] = " + _var_[_ivs_[1]]+ " _lhs_[0] = " + _var_[_lhs_[0]]);
         Verify.incrementCounter(counter);
        System.out.println("counter#" + counter + ": " + Verify.getCounter(counter));
        if (!outcome) {
            System.out.println("BACKTRACKING: test failure");
            Verify.ignoreIf(true);
        }
       //} catch (Exception e) {}
    }

    static boolean checkEq(int x, int y) {
        return (x == y);
    }
}
