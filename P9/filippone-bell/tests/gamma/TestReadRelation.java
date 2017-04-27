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

        ThreadList.init();
        Connector read_print = new Connector("read_print");
        ReadRelation reader = new ReadRelation(
                "test-tables/client.txt", "client", read_print);
        Printer printer = new Printer(read_print);
        ThreadList.run(printer);

        RegTest.Utility.validate(
                "results/out.txt", "correct/allClientTuples.txt", false);
    }

    @Test
    public void testSendRelation() throws Exception {
        String relationName = "84hf($uF39TE837hG%jsk87#";
        Connector connector = new Connector("testConnector");
        ReadRelation reader = new ReadRelation(
                "test-tables/client.txt", relationName, connector);
        reader.sendRelation();

        assertEquals(connector.getRelation().getRelationName(), 
                relationName);
    }

    /*@Test
    public void testReadRelationFromFile() throws Exception {
        Connector connector = new Connector("testConnector");
        ReadRelation reader = new ReadRelation("test-tables/client.txt", "relationName", connector);
        Relation relation = reader.readRelationFromFile();
        String[] correctFieldNames = {"CLIENTNO", "FNAME", "LNAME", "TELNO", "PREFTYPE", "MAXRENT"};

        assertArrayEquals(relation.getFieldNames(), correctFieldNames);
    }*/

    /*@Test
    public void testGetNextTableRow() throws Exception {
        Connector connector = new Connector("testConnector");
        ReadRelation reader = new ReadRelation(
                "test-tables/client.txt", "relationName", connector);
        Scanner scan = new Scanner(new File("test-tables/client.txt"));

        assertEquals(reader.getNextRow(), scan.nextLine());
        assertEquals(reader.getNextRow(), scan.nextLine());
        assertEquals(reader.getNextRow(), scan.nextLine());
        assertEquals(reader.getNextRow(), scan.nextLine());
        assertEquals(reader.getNextRow(), scan.nextLine());
        assertEquals(reader.getNextRow(), scan.nextLine());
        assertEquals(reader.getNextRow(), null);
    }*/

    @Test
    public void testSendTuple() throws Exception {
        Connector connector = new Connector("testConnector");
        ReadRelation reader = new ReadRelation(
                "test-tables/client.txt", "relationName", connector);
        WriteEnd writeEnd = connector.getWriteEnd();
        ReadEnd readEnd = connector.getReadEnd();
        Tuple tuple = Tuple.makeTupleFromPipeData("5#abc#123#$*&^");
        reader.sendTuple(writeEnd, tuple);

        assertEquals(readEnd.getNextString(), tuple.toString());
    }
}
