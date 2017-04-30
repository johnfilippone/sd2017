/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamma;

import gammaSupport.*;
import basicConnector.*;
import org.junit.Test;
import RegTest.Utility;
import static org.junit.Assert.*;

public class TestDoNothing {

    @Test
    public void testDoNothing() {
        try {
            Utility.redirectStdOut("results/out.txt");

            ThreadList.init();
            Connector c = new Connector("readToNothing");
            Connector c2 = new Connector("nothingToPrint");

            ReadRelation r = new ReadRelation("test-tables/parts.txt", "parts", c);
            DoNothing d = new DoNothing(c, c2);
            Printer p = new Printer(c2);
            ThreadList.run(p);

            Utility.validate("results/out.txt", "correct/readNPrintParts.txt", false);
        } catch (Exception e) {
            fail("exception thrown: createRelation test fails.");
        }
    }

}
