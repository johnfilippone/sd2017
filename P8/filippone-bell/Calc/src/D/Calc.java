/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package D;

/**
 *
 * @author Don
 */
public class Calc {
    
    Double value = 0.0;

    void sub(String n) { value = value - Double.parseDouble(n); }
    void mul(String n) { value = value*Double.parseDouble(n); }
    void add(String n) { value = value+Double.parseDouble(n); }
    void div(String n) { value = value/Double.parseDouble(n); }
    String get() { return value.toString(); }
    void set(String n) { value = Double.parseDouble(n); }
    void clear() { value =0.0; }

}
