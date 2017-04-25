package gamma;


import org.junit.Test;
import basicConnector.Connector;


public class TestReadRelation {

    @Test
    public void testReadClientTable() throws InterruptedException {
        RegTest.Utility.redirectStdOut("results/out.txt");

        Connector read_print = new Connector("read_print");
        ReadRelation reader = new ReadRelation("test-tables/client.txt", read_print.out);
        Printer printer = new Printer(read_print.in);
        reader.start();
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/client.txt", false);
    }
}
