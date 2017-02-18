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
        if (Feature.tables) {
            table.add(this);
        }
    }
    
    public OutputPin getOutput() {
        return o;
    }
    
    @Feature(Feature.tables) 

    static LinkedList<InputPort> table = new LinkedList<InputPort>();
    
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
        System.out.printf("inputPort(%s,'%s').\n", name, o);
    }
    
    @Feature(Feature.eval)   /* for evaluation */
            
    Value value = Value.UNKNOWN;
        
    public void setValue(Value v) {
        value = v;
    }
    
    public Value getValue() {
        return value;
    }
}
