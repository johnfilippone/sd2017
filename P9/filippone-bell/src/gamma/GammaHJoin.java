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
        //out.setRelation(Relation.join(in2.getRelation(), in1.getRelation(), joinKey2, joinKey1));
        //ThreadList.add(this);

        Connector[] split_bloom = ArrayConnectors.initConnectorArray("split_bloom");
        Connector[] split_bfilter = ArrayConnectors.initConnectorArray("split_bfilter");
        Connector[] bloom_bfilter = ArrayConnectors.initConnectorArray("bloom_bfilter");
        Connector[] bloom_hjoin = ArrayConnectors.initConnectorArray("bloom_hjoin");
        Connector[] bfilter_hjoin = ArrayConnectors.initConnectorArray("bfilter_hjoin");
        Connector[] hjoin_merge = ArrayConnectors.initConnectorArray("hjoin_merge");

        HSplit hsplit1 = new HSplit(this.in1, split_bloom, joinKey1);
        HSplit hsplit2 = new HSplit(this.in2, split_bfilter, joinKey2);
        Bloom bloom1 = new Bloom(split_bloom[0], bloom_hjoin[0], bloom_bfilter[0], joinKey1);
        Bloom bloom2 = new Bloom(split_bloom[1], bloom_hjoin[1], bloom_bfilter[1], joinKey1);
        Bloom bloom3 = new Bloom(split_bloom[2], bloom_hjoin[2], bloom_bfilter[2], joinKey1);
        Bloom bloom4 = new Bloom(split_bloom[3], bloom_hjoin[3], bloom_bfilter[3], joinKey1);
        BFilter bfilter1 = new BFilter(split_bfilter[0], bfilter_hjoin[0], bloom_bfilter[0], joinKey2);
        BFilter bfilter2 = new BFilter(split_bfilter[1], bfilter_hjoin[1], bloom_bfilter[1], joinKey2);
        BFilter bfilter3 = new BFilter(split_bfilter[2], bfilter_hjoin[2], bloom_bfilter[2], joinKey2);
        BFilter bfilter4 = new BFilter(split_bfilter[3], bfilter_hjoin[3], bloom_bfilter[3], joinKey2);
        HJoin hjoin1 = new HJoin(bloom_hjoin[0], bfilter_hjoin[0], hjoin_merge[0], joinKey1, joinKey2);
        HJoin hjoin2 = new HJoin(bloom_hjoin[1], bfilter_hjoin[1], hjoin_merge[1], joinKey1, joinKey2);
        HJoin hjoin3 = new HJoin(bloom_hjoin[2], bfilter_hjoin[2], hjoin_merge[2], joinKey1, joinKey2);
        HJoin hjoin4 = new HJoin(bloom_hjoin[3], bfilter_hjoin[3], hjoin_merge[3], joinKey1, joinKey2);
        Merge merge = new Merge(hjoin_merge, out);
    }
}
