package gamma;


import java.io.*;
import java.util.*;
import gammaSupport.*;
import basicConnector.*;


public class GammaHJoin extends Thread {

    Connector in1;
    Connector in2;
    Connector out;
    int joinKey1;
    int joinKey2;

    public GammaHJoin(Connector in1, Connector in2, Connector out, int joinKey1, int joinKey2) {
        this.in1 = in1;
        this.in2 = in2;
        this.out = out;
        this.joinKey1 = joinKey1;
        this.joinKey2 = joinKey2;
        out.setRelation(Relation.join(in2.getRelation(), in1.getRelation(), joinKey2, joinKey1));
        ThreadList.add(this);
    }
/*
    public void run() {
        Tuple t;
        ReadEnd reader1 = in.getReadEnd();
        ReadEnd reader2 = in.getReadEnd();
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
*/
}
