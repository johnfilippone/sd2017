package logicGates;

import Pins.*;
import Errors.*;
import GatesApp.*;
import java.util.*;


public abstract class Gate {

    public String name;
    HashMap<String, InputPin> inputs;
    HashMap<String, OutputPin> outputs;

    public Gate(String name) {
        this.name = name;
        this.inputs = new HashMap<String, InputPin>();
        this.outputs = new HashMap<String, OutputPin>();
    }

    public InputPin getInput(String name) {
        return inputs.get(name);
    }

    public OutputPin getOutput(String name) {
        return outputs.get(name);
    }

    @Feature(Feature.tables)

    public static void resetDB() {
        And.resetTable();
        Not.resetTable();
        Or.resetTable();
        InputPort.resetTable();
        OutputPort.resetTable();
        Wire.resetTable();
    }

    public static void printDB() {
        printTable(And.getTable());
        printTable(Not.getTable());
        printTable(Or.getTable());
        printTable(InputPort.getTable());
        printTable(OutputPort.getTable());
        printTable(Wire.getTable());
    }

    public static <G extends Printable> void printTable(LinkedList<G> t) {
        System.out.println();
        if (t.isEmpty())
            return;
        t.getFirst().printTableHeader();
        for (G g : t) {
            g.print();
        }
    }

    @Feature(Feature.constraints)

    public boolean extra() {  // subclasses override this method if something special needs to be done
        // TO DO
        return true;
    }
    
    public static <G extends Gate> boolean allUniqueNames(LinkedList<G> table) {
        HashSet<String> names = new HashSet<String>();
        for (Gate gate : table) {
            names.add(gate.name);
        }
        return table.size() == names.size();
    }

    public boolean allInputsUsed() {
        boolean allUsed = true;
        for (InputPin inputPin : inputs.values()){
            allUsed = allUsed && inputPin.isUsed();
        }
        //return allUsed;
        return true;
    }

    public boolean allOutputsUsed() {
        boolean allUsed = true;
        for (OutputPin outputPin : outputs.values()){
            allUsed = allUsed && outputPin.isUsed();
        }
        return allUsed;
    }

    public static <G extends Gate> boolean verify(String label, LinkedList<G> table) {
        boolean valid = true;
	// evaluate the following constraints
	// 1. every gate of type G has a unique name
        valid = valid && allUniqueNames(table);

	// 2. every gate of type G has all of its inputs used (see above)
	// 3. every gate of type G has all of its outputs used (see above)
        for (Gate gate : table) {
            valid = valid && gate.allInputsUsed();
            valid = valid && gate.allOutputsUsed();
        }

	// 4. any constraint you might think that is particular to
	//    gates of type G, evaluate it see extra() above

        return valid;
    }

    public static boolean verify() {
        boolean validAnds = verify("And", And.getTable());
        boolean validNots = verify("Not", Not.getTable());
        boolean validOrs = verify("Or", Or.getTable());
        boolean validIPs = verify("InputPort", InputPort.getTable());
        boolean validOPs = verify("OutputPort", OutputPort.getTable());
        return validAnds && validNots && validOrs && validIPs && validOPs;
    }

    @Feature(Feature.eval)

    public abstract Value getValue();  // evaluate gate(inputs)
}
