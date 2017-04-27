package gamma;


import java.io.*;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
import gammaSupport.*;
import basicConnector.*;


public class TestReadRelation {

    @Test
    public void testReadClientTable() throws Exception {
        RegTest.Utility.redirectStdOut("results/out.txt");

        Connector read_print = new Connector("read_print");
        ReadRelation reader = new ReadRelation("test-tables/client.txt", "client", read_print);
        Printer printer = new Printer(read_print);

        ThreadList.init();
        ThreadList.add(reader);
        ThreadList.add(printer);
        ThreadList.run(printer);

        RegTest.Utility.validate("results/out.txt", "correct/allClientTuples.txt", false);
    }

    @Test
    public void testGetNextTableRow() throws Exception {
        Connector connector = new Connector("testConnector");
        ReadRelation reader = new ReadRelation("test-tables/client.txt", "relationName", connector);
        Scanner scan = new Scanner(new File("test-tables/client.txt"));
        scan.nextLine();

        assertEquals(scan.nextLine(), reader.getNextRow());
        assertEquals(scan.nextLine(), reader.getNextRow());
        assertEquals(scan.nextLine(), reader.getNextRow());
        assertEquals(scan.nextLine(), reader.getNextRow());
        assertEquals(scan.nextLine(), reader.getNextRow());
        assertEquals(null, reader.getNextRow());
    }

    @Test
    public void testSendTuple() throws Exception {
        Connector connector = new Connector("testConnector");
        ReadRelation reader = new ReadRelation("test-tables/client.txt", "relationName", connector);
        WriteEnd writeEnd = connector.getWriteEnd();
        ReadEnd readEnd = connector.getReadEnd();
        Tuple tuple = Tuple.makeTupleFromPipeData("5#abc#123#$*&^");
        reader.sendTuple(writeEnd, tuple);

        assertEquals(readEnd.getNextString(), tuple.toString());
    }
}
