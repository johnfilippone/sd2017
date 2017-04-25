package gamma;


import java.io.*;
import gammaSupport.ReportError;


public class Printer extends Thread {
    BufferedReader in;

    public Printer( BufferedReader in ) {
        this.in = in;
    }

    public void run() {
        try {
            String input;
            while (true) {
                input = in.readLine();
                if (input == null) {
                    in.close();
                    break;
                }
                System.out.println(input);
            }
            System.out.flush();
        } catch (IOException e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}
