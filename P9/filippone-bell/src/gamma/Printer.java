package gamma;


import java.io.*;
import basicConnector.*;
import gammaSupport.*;


public class Printer extends Thread {
    Connector in;

    public Printer(Connector in) {
        this.in = in;
        ThreadList.add(this);
    }

    public void run() {
        String input;
        try {
            while ((input = in.getReadEnd().getNextString()) != null)
                System.out.println(input);
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}
