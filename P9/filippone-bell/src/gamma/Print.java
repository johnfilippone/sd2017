/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamma;

import basicConnector.Connector;
import basicConnector.ReadEnd;
import gammaSupport.*;

/**
 *
 * @author dsb
 */
public class Print extends Thread {
    ReadEnd re;
    Relation r;

    public Print(Connector in) {
        this.re = in.getReadEnd();
        this.r = re.getRelation();
        ThreadList.add(this);
    }

    @Override
    public void run() {
        try {
            while(true) {
                Tuple t = re.getNextTuple();
                if (t==null) break;
                t.println(r);
            }
        } catch(Exception e) {
            ReportError.msg(this,e); }
     }
}
