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
public class PrintMap extends Thread {
    ReadEnd re;

    public PrintMap(Connector in) {
        this.re = in.getReadEnd();
        ThreadList.add(this);
    }

    @Override
    public void run() {
        try {
            String mapString = re.getNextString();
            System.out.println(mapString);
        } catch(Exception e) {
            ReportError.msg(this,e); }
     }
}
