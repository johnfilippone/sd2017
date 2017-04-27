package gamma;


import java.io.*;
import basicConnector.*;
import gammaSupport.*;


public class Printer extends Thread {
    Connector connector;

    public Printer(Connector connector) {
        this.connector = connector;
        printRelation(connector.r);
    }

    public void run() {
        printTuples();
    }

    public void printRelation(Relation relation){
        String tableHeader = String.join(" ", relation.getFieldNames());
        System.out.println(tableHeader);
    }

    public void printTuples(){
        String input;
        try {
            while ((input = connector.getReadEnd().getNextString()) != null)
                printTuple(Tuple.makeTupleFromPipeData(input));
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

    public void printTuple(Tuple tuple){
        String tableRow = String.join(" ", tuple.getFields());
        System.out.println(tableRow);
    }

}
