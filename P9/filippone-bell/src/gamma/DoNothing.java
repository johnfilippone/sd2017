package gamma;


import java.io.*;
import java.util.*;
import gammaSupport.*;
import basicConnector.*;


public class DoNothing extends Thread {

    Connector in;
    Connector out;

    public DoNothing(Connector in, Connector out) {
        this.in = in;
        this.out = out;
        ThreadList.add(this);
    }

    public void run() {
        Tuple t;
        ReadEnd reader = in.getReadEnd();
        WriteEnd writer = out.getWriteEnd();
        while (true) {
            try {
                t = reader.getNextTuple();
                if (t == null) {
                    break;
                } else {
                    writer.putNextTuple(t);
                }
            } catch (Exception e) {
                ReportError.msg(this.getClass().getName() + e);
                break;
            }
        }
        writer.close();
    }

}
