package logicGates;

import Errors.NoValueSet;
import Pins.*;
import GatesApp.*;
import java.util.*;

public class InputPort extends Gate implements Printable {
    OutputPin o;

    public InputPort(String name) {
        super(name);
        o = new OutputPin("o", this);
    }
    
    public OutputPin getOutput() {
        // TO DO
        return null;
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<InputPort> table;
    
    public static void resetTable() {
        table = new LinkedList<InputPort>();
    }
    
    public static LinkedList<InputPort> getTable() { 
        return table;
    }
    
    public void printTableHeader() {
        System.out.println("table(inputPort,[name,\"outputPin\"]).");
    }
    
    public void print() {
        System.out.printf("and(%s,'%s').\n", name, o);
    }
    
    @Feature(Feature.eval)   /* for evaluation */
            
    Value value = Value.UNKNOWN;
        
    public void setValue(Value v) {
        // TO DO
    }
    
    public Value getValue() {
        // TO DO
        return null;
    }
}
