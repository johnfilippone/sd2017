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

public class TestHSplit {

    @Test
    public void testHSplit0() throws Exception {
        Utility.redirectStdOut("results/split0.txt");

        ThreadList.init();
        int joinKey = 0;
        Connector read_join = new Connector("read_join");
        ReadRelation r = new ReadRelation("test-tables/parts.txt", "parts", read_join);
        Connector[] splits = ArrayConnectors.initConnectorArray("splits");
        HSplit hsplit = new HSplit(read_join, splits, joinKey);
        Print p0 = new Print(splits[0]);
        ThreadList.run(p0);

        Utility.validate("results/out.txt", "correct/readNPrintParts.txt", false);
    }

    @Test
    public void testHSplit1() throws Exception {
        Utility.redirectStdOut("results/split1.txt");

        ThreadList.init();
        int joinKey = 0;
        Connector read_join = new Connector("read_join");
        ReadRelation r = new ReadRelation("test-tables/parts.txt", "parts", read_join);
        Connector[] splits = ArrayConnectors.initConnectorArray("splits");
        HSplit hsplit = new HSplit(read_join, splits, joinKey);
        Print p1 = new Print(splits[1]);
        ThreadList.run(p1);

        Utility.validate("results/out.txt", "correct/readNPrintParts.txt", false);
    }

    @Test
    public void testHSplit2() throws Exception {
        Utility.redirectStdOut("results/split2.txt");

        ThreadList.init();
        int joinKey = 0;
        Connector read_join = new Connector("read_join");
        ReadRelation r = new ReadRelation("test-tables/parts.txt", "parts", read_join);
        Connector[] splits = ArrayConnectors.initConnectorArray("splits");
        HSplit hsplit = new HSplit(read_join, splits, joinKey);
        Print p2 = new Print(splits[2]);
        ThreadList.run(p2);

        Utility.validate("results/out.txt", "correct/readNPrintParts.txt", false);
    }

    @Test
    public void testHSplit3() throws Exception {
        Utility.redirectStdOut("results/split3.txt");

        ThreadList.init();
        int joinKey = 0;
        Connector read_join = new Connector("read_join");
        ReadRelation r = new ReadRelation("test-tables/parts.txt", "parts", read_join);
        Connector[] splits = ArrayConnectors.initConnectorArray("splits");
        HSplit hsplit = new HSplit(read_join, splits, joinKey);
        Print p3 = new Print(splits[3]);
        ThreadList.run(p3);

        Utility.validate("results/out.txt", "correct/readNPrintParts.txt", false);
    }
}
