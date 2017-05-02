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

public class TestPrintMap {

    @Test
    public void testPrintMap() throws Exception {
        Utility.redirectStdOut("results/out.txt");

        ThreadList.init();
        int joinKey = 0;
        Connector read_bloom = new Connector("read_bloom");
        Connector bloom_print = new Connector("bloom_print");
        Connector bloom_printmap = new Connector("bloom_printmap");
        ReadRelation r = new ReadRelation("test-tables/parts.txt", "parts", read_bloom);
        Bloom bloom = new Bloom(read_bloom, bloom_print, bloom_printmap, joinKey);
        PrintMap p = new PrintMap(bloom_printmap);
        ThreadList.run(p);

        Utility.validate("results/out.txt", "correct/printmap.txt", false);
    }
}
