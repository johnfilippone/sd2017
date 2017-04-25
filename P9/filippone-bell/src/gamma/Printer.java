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
            while ((input = in.readLine()) != null)
                System.out.println(input);
            in.close();
            System.out.flush();
        } catch (IOException e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}
