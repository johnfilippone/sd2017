package Pins;

import java.util.*;
import logicGates.*;
import GatesApp.*;
import logicGates.Wire;

public class OutputPin {
    Value value;
    public String name;
    Gate outputOf;
    AbstractList<Wire> wiresFrom;
    
    public OutputPin(String name, Gate parent) {
        this.name = name;
        outputOf = parent;
        wiresFrom = new ArrayList<Wire>();
    }
    
    public void addWire(Wire w) {
        wiresFrom.add(w);
    }
    
    public String toString() {
        return outputOf.name+ "->" + name;
    }
    
    public String nameOfGate() {
        return outputOf.name;
    }
    
    @Feature(Feature.constraints)
    
    public boolean isUsed() {
        boolean valid = false;
        for (Wire wire : wiresFrom){
            valid = valid || (wire.isUsed() && wire.i.nameOfGate() != null);
        }
        return valid;
    }
    
    @Feature(Feature.eval)
    
    public Value getValue() {
        return outputOf.getValue();
    }
}
