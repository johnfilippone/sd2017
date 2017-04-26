package gamma;


import java.io.*;
import gammaSupport.ReportError;


public class Printer extends Thread {
    BufferedReader in;

    public Printer( BufferedReader in ) {
        this.in = in;
    }

    public void run() {
        String input;
        while ((input = getNextString()) != null)
            System.out.println(input);
        closeBufferedReader();
    }

    public String getNextString(){
        try {
            return in.readLine();
        } catch (IOException e) {
            ReportError.msg(this.getClass().getName() + e);
            return null;
        }
    }

    public void closeBufferedReader(){
        try {
            in.close();
        } catch (IOException e) {
            ReportError.msg(this.getClass().getName() + e);
        }
        System.out.flush();
    }

}
