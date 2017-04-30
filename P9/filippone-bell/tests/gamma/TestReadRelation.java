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

public class TestReadRelation {

    @Test
    public void testReadRelation() {
        try {
            Utility.redirectStdOut("results/out.txt");

            ThreadList.init();
            Connector c = new Connector("singleton");
            ReadRelation r = new ReadRelation("test-tables/parts.txt", "parts", c);
            Print p = new Print(c);
            ThreadList.run(p);

            Utility.validate("results/out.txt", "correct/readNPrintParts.txt", false);
        } catch (Exception e) {
            fail("exception thrown: createRelation test fails.");
        }
    }

}
