package test.sketch4j.example.linkedList.deprecated;
import gov.nasa.jpf.jvm.Verify;
import java.util.HashMap;

public class Sketch_LL_HasLoop_Condition {
    // operators that can be sketched

    static class Entry {
        Entry next; 
        int val;
        public Entry(int val) {
            this.val = val;
        }
    }
    static class LinkedList {
        Entry head = new Entry(0);
        int size = 0;
    }
    /* program to sketch; has one arithmetic operator hole */
    //        static boolean expected_hasLoop(LinkedList l) {
    //                Entry ln1 = l.head;
    //                Entry ln2 = l.head;
    //            while (true) {
    //                ln1 = ln1.next;
    //                if (ln2.next == null || ln2.next.next == null)
    //                    return false;
    //                ln2 = ln2.next.next;
    //                if (\EXP\) 
    //                    return true;
    //            }
    //        }

    // expected result of sketching -- 
    static boolean expected_hasLoop(LinkedList l) {
        Entry ln1 = l.head;
        Entry ln2 = l.head;
        while (true) {
            ln1 = ln1.next;
            if (ln2.next == null || ln2.next.next == null)
                return false;
            ln2 = ln2.next.next;
            if (ln1 == ln2) 
                return true;
        }
    }

    // translated program to sketch
    // operator hole is replaced with method call
    static boolean sketchme_hasLoop(LinkedList l) {
        Entry ln1 = l.head;
        Entry ln2 = l.head;
        {
            _ln1_ = ln1;
            _ln2_ = ln2;
            _ll_  = l;
        }
        while (true) {
            _ln1_ = _ln1_.next;
            if (_ln2_.next == null || _ln2_.next.next == null)
                return false;
            _ln2_ = _ln2_.next.next;
            if (_EXP_(_ln1_, _ln2_, _ll_)) 
                return true;
        }
    }

    static Entry _ln1_, _ln2_;
    static LinkedList _ll_;
    static int[] _lhs_ = new int[]{-1};
    static int[] _exps_ = new int[]{-1,-1};
    static int _cond = -1; // equal or not equal, bigger or smaller
    static String[] _vars_ =  {"ln1","ln2","ln1.next","ln2.next","ln1.next.next","ln2.next.next","l.head","l.head.next","null"}; 

    static boolean _EXP_(Entry ln1, Entry ln2, LinkedList l) {
        if (_exps_[0]==-1) {
            _exps_[0] = Verify.getInt(0, 7);
        }
        if (_exps_[1]==-1) {
            _exps_[1] = Verify.getInt(0, 8); //RHS can be null
        }
        if (_cond == -1) {
            _cond = Verify.getInt(0, 1); //equal or not equal
        }
        Entry exp1 = null; Entry exp2 = null;

        switch (_exps_[0]) {
            case 0: exp1 = ln1; break;
            case 1: exp1 = ln2; break;
            case 2: exp1 = ln1.next; break;
            case 3: exp1 = ln2.next; break;
            case 4: exp1 = ln1.next.next; break;
            case 5: exp1 = ln2.next.next; break; 
            case 6: exp1 = l.head; break;
            case 7: exp1 = l.head.next; break;  
        }
        switch (_exps_[1]) {
            case 0: exp2 = ln1; break;
            case 1: exp2 = ln2; break;
            case 2: exp2 = ln1.next; break;
            case 3: exp2 = ln2.next; break;
            case 4: exp2 = ln1.next.next; break;
            case 5: exp2 = ln2.next.next; break; 
            case 6: exp2 = l.head; break;
            case 7: exp2 = l.head.next; break;  
            case 8: exp2 = null; break; 
        }       
        if (_cond==0) return  exp1 == exp2;
        else return exp1 != exp2;
    } 

    // test harness

    public static void main(String[] a) {
        // Test #1
        runTest(createList(new int[]{22,3,22}));

        // Test #2
        runTest(createList(new int[]{22,3}));

        // Test #3
        runTest(createList(new int[]{22}));

        // Test #4
        runTest(createList(new int[]{22, 3, 3}));
        System.out.println("****ALL TESTS PASSED!");
    }

    private static LinkedList createList(int[] arr) {
        LinkedList l = new LinkedList();
        Entry p = l.head;
        HashMap<Integer, Entry> map = new HashMap<Integer, Entry>();
        for (int i: arr) {
            Entry e ;
            if (map.containsKey(i)) e= map.get(i);
            else {
                e = new Entry(i);
                map.put(i,e);
            }
            p.next = e; 
            p = p.next;
        }
        return l;
    }

    static void runTest(LinkedList l) {
        boolean expected = expected_hasLoop(l);
        boolean actual = sketchme_hasLoop(l);
        boolean outcome = checkEq(expected, actual);
        String out = outcome ? "PASS" : "FAIL";
        System.out.print("\n[Test] expected: " + expected + ", actual: " + actual + " **" + out);
        System.out.print(" -- "+_vars_[_exps_[0]]+ (_cond==0? " == ": "!=") +_vars_[_exps_[1]] +",");

        if (!outcome) {
            System.out.println("BACKTRACKING: test failure");
            Verify.ignoreIf(true);
        }
    }

    static boolean checkEq(boolean x, boolean y) {
        return (x == y);
    }
}
