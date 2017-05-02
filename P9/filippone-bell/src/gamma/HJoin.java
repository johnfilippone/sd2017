package gamma;


import java.io.*;
import java.util.*;
import gammaSupport.*;
import basicConnector.*;


public class HJoin extends Thread {
    Connector in1;
    Connector in2;
    Connector out;
    int joinKey1;
    int joinKey2;

    public HJoin(Connector in1, Connector in2, Connector out, int joinKey1, int joinKey2) {
        this.in1 = in1;
        this.in2 = in2;
        this.out= out;
        this.joinKey1 = joinKey1;
        this.joinKey2 = joinKey2;
        out.setRelation(Relation.join(in1.getRelation(), in2.getRelation(), joinKey1, joinKey2));
        ThreadList.add(this);
    }

     public void run() {
        try {
            Tuple t;
            HashMap<String, Tuple> tHash = new HashMap<String, Tuple>();
            ReadEnd reader1 = this.in1.getReadEnd();
            ReadEnd reader2 = this.in2.getReadEnd();
            WriteEnd writer = this.out.getWriteEnd();
            while (true) {
                t = reader1.getNextTuple();
                if (t == null) {
                    break;
                }
                tHash.put(t.get(joinKey1), t);
            }
            while (true) {
                t = reader2.getNextTuple();
                if (t == null) {
                    break;
                }
                writer.putNextTuple(Tuple.join(tHash[t.get(joinKey2)], t, joinKey1, joinKey2));
            }
            writer.close();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }
}
