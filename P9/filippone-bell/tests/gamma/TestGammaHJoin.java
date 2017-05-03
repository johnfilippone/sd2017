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

public class TestGammaHJoin {

    @Test
    public void testGammaHJoinClientViewing() throws Exception {
        Utility.redirectStdOut("results/out.txt");

        ThreadList.init();
        int joinKey1 = 0;
        int joinKey2 = 0;
        Connector read_join1 = new Connector("read_join1");
        Connector read_join2 = new Connector("read_join2");
        Connector join_print = new Connector("join_print");
        ReadRelation r1 = new ReadRelation("test-tables/client.txt", "client", read_join1);
        ReadRelation r2 = new ReadRelation("test-tables/viewing.txt", "viewing", read_join2);
        GammaHJoin gjoin = new GammaHJoin(read_join1, read_join2, join_print, joinKey1, joinKey2);
        Print p = new Print(join_print);
        ThreadList.run(p);

        Utility.validate("results/out.txt", "correct/clientXviewing.txt", true);
    }
    @Test
    public void testGammaHJoinOrdersOdetails() throws Exception {
        Utility.redirectStdOut("results/out.txt");

        ThreadList.init();
        int joinKey1 = 0;
        int joinKey2 = 0;
        Connector read_join1 = new Connector("read_join1");
        Connector read_join2 = new Connector("read_join2");
        Connector join_print = new Connector("join_print");
        ReadRelation r1 = new ReadRelation("test-tables/orders.txt", "orders", read_join1);
        ReadRelation r2 = new ReadRelation("test-tables/odetails.txt", "odetails", read_join2);
        GammaHJoin gjoin = new GammaHJoin(read_join1, read_join2, join_print, joinKey1, joinKey2);
        Print p = new Print(join_print);
        ThreadList.run(p);

        Utility.validate("results/out.txt", "correct/ordersXodetails.txt", true);
    }
    @Test
    public void testGammaHJoinPartsOdetails() throws Exception {
        Utility.redirectStdOut("results/out.txt");

        ThreadList.init();
        int joinKey1 = 0;
        int joinKey2 = 1;
        Connector read_join1 = new Connector("read_join1");
        Connector read_join2 = new Connector("read_join2");
        Connector join_print = new Connector("join_print");
        ReadRelation r1 = new ReadRelation("test-tables/parts.txt", "parts", read_join1);
        ReadRelation r2 = new ReadRelation("test-tables/odetails.txt", "odetails", read_join2);
        GammaHJoin gjoin = new GammaHJoin(read_join1, read_join2, join_print, joinKey1, joinKey2);
        Print p = new Print(join_print);
        ThreadList.run(p);

        Utility.validate("results/out.txt", "correct/partsXodetails.txt", true);
    }
}
