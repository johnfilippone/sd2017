package gamma;


import java.io.*;
import java.util.*;
import gammaSupport.*;
import basicConnector.*;


public class Merge extends Thread {

    Connector[] in;
    Connector out;
    int roundRobbin = 0;

    public Merge(Connector[] in, Connector out) {
        this.in = in;
        this.out = out;
        this.out.setRelation(this.in[0].getRelation());
        ThreadList.add(this);
    }

    public void run() {
        Tuple t;
        WriteEnd writer = out.getWriteEnd();
        while (true) {
            try {
                t = getNextTuple();
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

    public Tuple getNextTuple(){
        int pipesToCheck = 4;
        Tuple tuple = null;
        try {
            while(tuple == null){
                tuple = in[roundRobbin].getReadEnd().getNextTuple();
                roundRobbin++;
                pipesToCheck--;
                if (pipesToCheck == 0) break;
            }
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
        return tuple;
    }

}
