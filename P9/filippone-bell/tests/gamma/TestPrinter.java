package gamma;


import java.io.*;
import org.junit.Test;
import basicConnector.Connector;


public class TestPrinter {

    @Test
    public void testPrintClientTable() throws FileNotFoundException, InterruptedException {
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/client.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/client.txt", false);
    }

    @Test
    public void testPrintViewingTable() throws FileNotFoundException, InterruptedException {
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/viewing.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/viewing.txt", false);
    }

    @Test
    public void testPrintOrdersTable() throws FileNotFoundException, InterruptedException {
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/orders.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/orders.txt", false);
    }

    @Test
    public void testPrintPartsTable() throws FileNotFoundException, InterruptedException {
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/parts.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/parts.txt", false);
    }

    @Test
    public void testPrintOdetailsTable() throws FileNotFoundException, InterruptedException {
        RegTest.Utility.redirectStdOut("results/out.txt");

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test-tables/odetails.txt")));
        Printer printer = new Printer(in);
        printer.start();
        printer.join();

        RegTest.Utility.validate("results/out.txt", "test-tables/odetails.txt", false);
    }
}
