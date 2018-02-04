package test.sketch4j.example.linkedList.deprecated;


import gov.nasa.jpf.jvm.Verify;
import java.util.HashMap;

public class Sketch_LL_HasLoop_Assign {
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
    //                \IV\ = \IV\;
    //                if (ln1 == ln2) 
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
            _LHS_(0, _IV_(0));
            if (_ln1_ == _ln2_) 
                return true;
        }
    }

    static Entry _ln1_, _ln2_;
    static LinkedList _ll_;
    static int[] _lhs_ = new int[]{-1};
    static int[] _ivs_ = new int[]{-1};
    static String[] _vars_ =  {"ln1","ln2","ln1.next","ln2.next","ln1.next.next","ln2.next.next","null"}; //now I didn't consider null 

    static void _LHS_(int _l_, Entry val) {
        if (_lhs_[_l_]==-1) {
            _lhs_[_l_] = Verify.getInt(0, 5);
        }
        switch (_lhs_[_l_]) {
            case 0: _ln1_ = val; break;
            case 1: _ln2_ = val; break;
            case 2: _ln1_.next = val; break;
            case 3: _ln2_.next = val; break;
            case 4: _ln1_.next.next = val; break;
            default: _ln2_.next.next = val; break; 
        }
    } 

    static Entry _IV_(int _i_) {
        if (_ivs_[_i_]==-1) {
            _ivs_[_i_] = Verify.getInt(0, 6);
        }
        switch (_ivs_[_i_]) {
            case 0: return _ln1_; 
            case 1: return _ln2_; 
            case 2: return _ln1_.next; 
            case 3: return _ln2_.next; 
            case 4: return _ln1_.next.next; 
            case 5: return _ln2_.next.next; 
            case 6: return null;
        }
        throw new RuntimeException("RT exception");
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
         System.out.println(" -- "+_vars_[_lhs_[0]]+ " = "+_vars_[_ivs_[0]] );
        if (!outcome) {
            System.out.println("BACKTRACKING: test failure");
            Verify.ignoreIf(true);
        }
    }

    static boolean checkEq(boolean x, boolean y) {
        return (x == y);
    }
}
