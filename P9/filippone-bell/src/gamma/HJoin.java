package gamma;


import java.io.*;
import java.util.*;
import gammaSupport.*;
import basicConnector.*;


public class HJoin extends Thread {
    Connector in;
    Connector outArray[];
    int joinKey;

    public HJoin(Connector in, Connector[] outArray, int joinKey) {
        this.in = in;
        this.outArray = outArray;
        this.joinKey = joinKey;
        for(Connector connector : this.outArray)
            connector.setRelation(this.in.getRelation());
        ThreadList.add(this);
    }

     public void run() {
        try {
            Tuple t;
            ReadEnd reader = in.getReadEnd();
            while (true) {
                t = reader.getNextTuple();
                if (t == null) {
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
