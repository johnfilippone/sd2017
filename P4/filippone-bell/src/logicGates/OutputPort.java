package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.*;

public class OutputPort extends Gate implements Printable {
    InputPin i1;

    public OutputPort(String name) {
        super(name);
        i1 = new InputPin("i1", this);
    }
    
    public InputPin getInput() { 
        return i1;
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<OutputPort> table;
    
    public static void resetTable() {
        table = new LinkedList<OutputPort>();
    }
    
    public static LinkedList<OutputPort> getTable() { 
        return table;
    }
    
    public void printTableHeader() {
        System.out.println("table(outputPort,[name,\"inputPin\"]).");
    }
    
    public void print() {
        System.out.printf("outputPort(%s,'%s').\n", name, i1);
    }
    
    @Feature(Feature.eval)
    
    public Value getValue() {
        // TO DO
        return Value.UNKNOWN;
    }
}
