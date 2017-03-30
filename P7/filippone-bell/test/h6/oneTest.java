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
        xform1to2.main("Correct/"+name+".sol1.pl",name+".sol2.pl");
        RegTest.Utility.validate(name+".sol2.pl", "Correct/"+name+".sol2.pl", false);
        xform2to3.main("Correct/"+name+".sol2.pl",name+".sol3.pl");
        RegTest.Utility.validate(name+".sol3.pl", "Correct/"+name+".sol3.pl", false);
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
    
}
