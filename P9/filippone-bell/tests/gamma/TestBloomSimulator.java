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

public class TestBloomSimulator {

    @Test
    public void testBloomSimulator() throws Exception {
        Utility.redirectStdOut("results/out.txt");

        ThreadList.init();
        int joinKey = 0;
        Connector read_nothing = new Connector("read_nothing");
        Connector sim_printmap = new Connector("sim_printmap");

        ReadRelation r = new ReadRelation("test-tables/parts.txt", "parts", read_nothing);
        Relation relation = read_nothing.getRelation();

        BloomSimulator bloomSimulator = new BloomSimulator(relation, sim_printmap);
        PrintMap p = new PrintMap(sim_printmap);
        ThreadList.run(p);

        Utility.validate("results/out.txt", "correct/printmap.txt", false);
    }
}
