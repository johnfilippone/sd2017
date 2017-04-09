/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DPlugIn;


public class Calc extends Framework.Calc {

    Double value = 0.0;

    public void sub(String n) { value = value - Double.parseDouble(n); }
    public void mul(String n) { value = value*Double.parseDouble(n); }
    public void add(String n) { value = value+Double.parseDouble(n); }
    public void div(String n) { value = value/Double.parseDouble(n); }
    public String get() { return value.toString(); }
    public void set(String n) { value = Double.parseDouble(n); }
    public void clear() { value =0.0; }

}
