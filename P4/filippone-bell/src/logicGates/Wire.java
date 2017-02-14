package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.LinkedList;

public class Wire implements Printable {
    public InputPin i;
    public OutputPin o;
    
    public Wire( OutputPin o, InputPin i ) {
        this.o = o;
        this.i = i;
    }
    
    public Wire( InputPort o, Gate i, String name) {
        this(o.getOutput(), i.getInput(name));
    }
    
    public Wire( Gate from, String frompin, Gate to, String topin ) {
        this(from.getOutput(frompin),to.getInput(topin));
    }
    
    public Wire( Gate from, Gate to ,String topin ) {
        this(from, "o", to, topin);
    }
    
    public Wire( Gate from, OutputPort port) {
        this(from.getOutput("o"),port.getInput());
    }
    
    
    @Feature(Feature.tables)
    
    static LinkedList<Wire> table;
    
    public static void resetTable() {
        // TO DO
    }
    
    public static LinkedList<Wire> getTable() { 
        // TO DO
        return null;
    }
    
    public void printTableHeader() {
        System.out.println("table(wire,[\"from\",\"to\"]).");
    }
    
    public void print() {
        // TO DO
    }
    
    @Feature(Feature.constraints)
    
    public boolean isUsed() {
        // TO DO
        return false;
    }
    
    public static boolean verify() {
        // TO DO
        return false;
    }
    
    @Feature(Feature.eval)
    
    public Value getValue() {
        // TO DO
        return Value.UNKNOWN;
    }
}
