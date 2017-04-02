/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package h6;

import org.junit.Test;

/**
 *
 * @author don
 */
public class oneTest {
    
    public oneTest() {
    }

    public void doit(String name)  throws Exception {
        xform1to2.main("Correct/"+name+".sol1.pl","results/"+name+".sol2.pl");
        RegTest.Utility.validate("results/"+name+".sol2.pl", "Correct/"+name+".sol2.pl", false);
        xform2to3.main("Correct/"+name+".sol2.pl","results/"+name+".sol3.pl");
        RegTest.Utility.validate("results/"+name+".sol3.pl", "Correct/"+name+".sol3.pl", false);
    }
    @Test
    public void testone() throws Exception {
        doit("p1");
    }
    
    @Test
    public void testtwo() throws Exception {
        doit("p2");
    }
    
    @Test
    public void testthree() throws Exception {
        doit("p3");
    }

    @Test
    public void test1to2_p1() throws Exception {
        xform1to2.main("Correct/p1.sol1.pl", "results/p1.sol2.pl");
        RegTest.Utility.validate("results/p1.sol2.pl", "Correct/p1.sol2.pl", false);
    }
    
    @Test
    public void test1to2_p2() throws Exception {
        xform1to2.main("Correct/p2.sol1.pl", "results/p2.sol2.pl");
        RegTest.Utility.validate("results/p2.sol2.pl", "Correct/p2.sol2.pl", false);
    }

    @Test
    public void test1to2_p3() throws Exception {
        xform1to2.main("Correct/p3.sol1.pl", "results/p3.sol2.pl");
        RegTest.Utility.validate("results/p3.sol2.pl", "Correct/p3.sol2.pl", false);
    }

    @Test
    public void test2to3_p1() throws Exception {
        xform2to3.main("Correct/p1.sol2.pl", "results/p1.sol3.pl");
        RegTest.Utility.validate("results/p1.sol3.pl", "Correct/p1.sol3.pl", false);
    }
    
    @Test
    public void test2to3_p2() throws Exception {
        xform2to3.main("Correct/p2.sol2.pl", "results/p2.sol3.pl");
        RegTest.Utility.validate("results/p2.sol3.pl", "Correct/p2.sol3.pl", false);
    }

    @Test
    public void test2to3_p3() throws Exception {
        xform2to3.main("Correct/p3.sol2.pl", "results/p3.sol3.pl");
        RegTest.Utility.validate("results/p3.sol3.pl", "Correct/p3.sol3.pl", false);
    }
}
