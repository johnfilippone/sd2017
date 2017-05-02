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
            Tuple t1;
            Tuple t2;
            ReadEnd reader1 = this.in1.getReadEnd();
            ReadEnd reader2 = this.in2.getReadEnd();
            while (true) {
                t1 = reader1.getNextTuple();
                t2 = reader2.getNextTuple();
                if (t1 == null || t2 == null) {
                    break;
                }
                int hash = BMap.myhash(t.get(joinKey));
                this.outArray[hash].getWriteEnd().putNextTuple(t);
            }
            for(Connector connector : this.outArray)
                connector.getWriteEnd().close();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }
}
