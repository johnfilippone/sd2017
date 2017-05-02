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

public class TestBFilter {

    @Test
    public void testBFilter() throws Exception {
        Utility.redirectStdOut("results/out.txt");

        ThreadList.init();
        int joinKey = 0;
        Connector read_bloom = new Connector("read_bloom");
        Connector bloom_bfilter = new Connector("bloom_print");
        Connector bloom_bfiltermap = new Connector("bloom_printmap");
        Connector bfilter_print = new Connector("bfilter_print");
        ReadRelation r = new ReadRelation("test-tables/parts.txt", "parts", read_bloom);
        Bloom bloom = new Bloom(read_bloom, bloom_bfilter, bloom_bfiltermap, joinKey);
        BFilter bfilter = new BFilter(bloom_bfilter, bfilter_print, bloom_bfiltermap, joinKey);
        Print p = new Print(bfilter_print);
        ThreadList.run(p);

        Utility.validate("results/out.txt", "correct/readNPrintParts.txt", false);
    }
}
