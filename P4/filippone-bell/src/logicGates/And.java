package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.*;

public class And extends Gate implements Printable {
    InputPin i1, i2;
    OutputPin o;

    public And(String name) {
        super(name);
        // TO DO
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<And> table;
    
    public static void resetTable() {
        // TO DO
    }
    
    public static LinkedList<And> getTable() { 
        // TO DO
        return null;
        
    }
    
    public void printTableHeader() {
        System.out.println("table(and,[name,\"input1\",\"input2\",\"output\"]).");
    }
    
    public void print() {
        // TO DO
    }
        
    @Feature(Feature.eval)    /* for evaluation */
    
    public Value getValue() { 
        Value v1 = i1.getValue();
        Value v2 = i2.getValue();
        if (v1==Value.TRUE && v2==Value.TRUE)
            return Value.TRUE;
        else
            return Value.FALSE;
    }
    
}
