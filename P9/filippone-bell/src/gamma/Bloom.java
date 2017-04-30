package gamma;


import java.io.*;
import java.util.*;
import gammaSupport.*;
import basicConnector.*;


public class Bloom extends Thread {

    Connector in;
    Connector out;
    Connector bitmapOut;
    int joinkey;

    public Bloom(Connector in, Connector out, Connector bitmapOut, int joinkey) {
        this.in = in;
        this.out = out;
        this.bitmapOut = bitmapOut;
        this.joinkey = joinkey;

        this.out.setRelation(this.in.getRelation());
        this.bitmapOut.setRelation(this.in.getRelation());

        ThreadList.add(this);
    }

    public void run() {
        Tuple t;
        BMap map = BMap.makeBMap();
        ReadEnd reader = in.getReadEnd();
        WriteEnd writer = out.getWriteEnd();
        WriteEnd bitmapWriter = bitmapOut.getWriteEnd();

        while (true) {
            try {
                t = reader.getNextTuple();
                if (t == null) {
                    String bloom = map.getBloomFilter();
                    bitmapWriter.putNextString(bloom);
                    break;
                } else {
                    map.setValue(t.get(joinkey), true);
                    writer.putNextTuple(t);
                }
            } catch (Exception e) {
                ReportError.msg(this.getClass().getName() + e);
                break;
            }
        }
        writer.close();
        bitmapWriter.close();
    }

}
