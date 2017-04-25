package gamma;


import org.junit.Test;
import static org.junit.Assert.*;
import basicConnector.Connector;


public class TestReadRelation {

    @Test
    public void testReadAndSetRelationFromFile() throws Exception {
        String relationName = "name of relation";
        Connector connector = new Connector("testRelation");
        ReadRelation reader = new ReadRelation("test-tables/client.txt", relationName, connector);
        reader.readAndSetRelationFromFile();

        assertEquals(connector.getRelation().getRelationName(), relationName);
    }

    @Test
    public void testReadClientTable() throws Exception {
        RegTest.Utility.redirectStdOut("results/out.txt");

        Connector read_print = new Connector("read_print");
        ReadRelation reader = new ReadRelation("test-tables/client.txt", "client", read_print);
        Printer printer = new Printer(read_print.in);
        reader.start();
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "correct/clientTuples.txt", false);
    }
}
