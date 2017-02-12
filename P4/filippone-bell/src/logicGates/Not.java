package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.LinkedList;

public class Not extends Gate implements Printable {
    InputPin i1;
    OutputPin o;

    public Not(String name) {
        super(name);
        // TO DO
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<Not> table;
    
    public static void resetTable() {
        // TO DO
    }
    
    public static LinkedList<Not> getTable() { 
        // TO DO
        return null;
    }
    
    public void printTableHeader() {
        System.out.println("table(not,[name,\"input\",\"output\"]).");
    }
    
    public void print() {
        // TO DO
    }
    
    @Feature(Feature.eval)   /* for logic diagram evaluation */
    
    public Value getValue() {
        // TO DO
        return Value.UNKNOWN;
    }
    
}
