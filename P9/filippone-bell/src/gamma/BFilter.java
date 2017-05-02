package gamma;


import java.io.*;
import java.util.*;
import gammaSupport.*;
import basicConnector.*;


public class BFilter extends Thread {

    Connector in;
    Connector out;
    Connector bitmapIn;
    int joinkey;

    public BFilter(Connector in, Connector out, Connector bitmapIn, int joinkey) {
        this.in = in;
        this.out = out;
        this.bitmapIn = bitmapIn;
        this.joinkey = joinkey;

        this.out.setRelation(this.in.getRelation());

        ThreadList.add(this);
    }

    public void run() {
        Tuple t;
        BMap map = null;
        ReadEnd reader = in.getReadEnd();
        WriteEnd writer = out.getWriteEnd();
        ReadEnd bitmapReader = bitmapIn.getReadEnd();

        try {
            map = BMap.makeBMap(bitmapReader.getNextString());
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }

        if (map != null) {
            while (true) {
                try {
                    t = reader.getNextTuple();
                    if (t == null) {
                        break;
                    } else {
                        boolean exists = map.getValue(t.get(joinkey));
                        if (exists) {
                            writer.putNextTuple(t);
                        }
                    }
                } catch (Exception e) {
                    ReportError.msg(this.getClass().getName() + e);
                    break;
                }
            }
            writer.close();
        }
    }

}
