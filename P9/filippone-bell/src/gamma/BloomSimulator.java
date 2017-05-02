/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamma;

import basicConnector.Connector;
import basicConnector.WriteEnd;
import gammaSupport.*;

/**
 *
 * @author dsb
 */
public class BloomSimulator extends Thread {
    WriteEnd we;

    public BloomSimulator(Relation r, Connector out) {
        out.setRelation(r);
        this.we = out.getWriteEnd();
        ThreadList.add(this);
    }

    @Override
    public void run() {
        try {
            String mapString = "ftfftffffftttfffffffttffffff";
            we.putNextString(mapString);
        } catch(Exception e) {
            ReportError.msg(this,e); 
        }
        we.close();
     }
}
