package Pins;

import GatesApp.*;
import logicGates.Gate;
import Errors.*;
import logicGates.Wire;


public class InputPin {

    public String name;
    Gate inputOf;
    Wire wireFrom; // only from one source
    
    public InputPin(String name, Gate parent) {
        this.name = name;
        inputOf = parent;
    }
    
    public void addWire(Wire w) {
        wireFrom = w;
    }
    
    public String toString() {
        return name + "->" +inputOf.name ;
    }
    
    @Feature(Feature.constraints)
    
    public boolean isUsed() {
        if (wireFrom == null){
            return false;
        }
        return wireFrom.o != null;
    }
    
    public String nameOfGate() {
         return inputOf.name;
    }
    
    @Feature(Feature.eval)    /*  this is for circuit execution */
    
    public Value getValue() {
        return wireFrom.getValue();
    }
}
