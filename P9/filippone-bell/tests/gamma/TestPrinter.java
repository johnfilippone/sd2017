package gamma;


import java.io.*;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
import basicConnector.Connector;


public class TestPrinter {

    /*
    @Test
    public void testPrintClientTable() throws Exception{
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/client.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/client.txt", false);
    }

    @Test
    public void testPrintViewingTable() throws Exception{
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/viewing.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/viewing.txt", false);
    }

    @Test
    public void testPrintOrdersTable() throws Exception{
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/orders.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/orders.txt", false);
    }

    @Test
    public void testPrintPartsTable() throws Exception{
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/parts.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/parts.txt", false);
    }

    @Test
    public void testPrintOdetailsTable() throws Exception {
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/odetails.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/odetails.txt", false);
    }

    @Test
    public void testGetNextString() throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/client.txt")));
        Printer printer = new Printer(in);
        Scanner scan = new Scanner(new File("test-tables/client.txt"));

        assertEquals(printer.getNextString(), scan.nextLine());
        assertEquals(printer.getNextString(), scan.nextLine());
        assertEquals(printer.getNextString(), scan.nextLine());
        assertEquals(printer.getNextString(), scan.nextLine());
        assertEquals(printer.getNextString(), scan.nextLine());
        assertEquals(printer.getNextString(), scan.nextLine());
        assertEquals(printer.getNextString(), null);
    }
    */
}
