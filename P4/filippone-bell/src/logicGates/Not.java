package logicGates;

import Pins.*;
import GatesApp.*;
import java.util.LinkedList;

public class Not extends Gate implements Printable {
    InputPin i1;
    OutputPin o;

    public Not(String name) {
        super(name);
        i1 = new InputPin("i1",this);
        inputs.put("i1",i1);
        o = new OutputPin("o",this);
        outputs.put("o", o);
        if (Feature.tables) {
            table.add(this);
        }
    }
    
    @Feature(Feature.tables) 
    
    static LinkedList<Not> table = new LinkedList<Not>();
    
    public static void resetTable() {
        table = new LinkedList<Not>();
    }
    
    public static LinkedList<Not> getTable() { 
        return table;
    }
    
    public void printTableHeader() {
        System.out.println("table(not,[name,\"input\",\"output\"]).");
    }
    
    public void print() {
        System.out.printf("not(%s,'%s','%s').\n", name, i1, o);
    }
    
    @Feature(Feature.eval)   /* for logic diagram evaluation */
    
    public Value getValue() {
        // TO DO
        return Value.UNKNOWN;
    }
    
}
