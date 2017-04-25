package gamma;


import java.io.*;
import org.junit.Test;
import basicConnector.Connector;


public class TestPrinter {

    @Test
    public void testPrintTable() throws FileNotFoundException {
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/client.txt")));
        Printer printer = new Printer(in);
        printer.start();

        RegTest.Utility.validate("results/out.txt", "test-tables/client.txt", false);
    }
}
