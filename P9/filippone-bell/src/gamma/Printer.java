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
        Tuple tuple;
        try {
            while ((tuple = connector.getReadEnd().getNextTuple()) != null)
                System.out.println(tuple);
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}
