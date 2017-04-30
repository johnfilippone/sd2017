package gamma;


import java.io.*;
import java.util.*;
import gammaSupport.*;
import basicConnector.*;


public class ReadRelation extends Thread {

    BufferedReader in;
    Connector connector;
    String relationName;

    public ReadRelation(String filename, String relationName, Connector connector) throws IOException{
        this.in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        this.connector = connector;
        this.relationName = relationName;
        sendRelation();
        ThreadList.add(this);
    }

    public void run() {
        sendTuples();
    }

    public void sendRelation(){
        Relation relation = readRelationFromFile();
        this.connector.setRelation(relation);
        getNextRow();
    }

    public Relation readRelationFromFile() {
        StringTokenizer tokenizer = new StringTokenizer(getNextRow());
        Relation relation = new Relation(relationName, tokenizer.countTokens());

        while(tokenizer.hasMoreTokens())
            relation.addField(tokenizer.nextToken());
        return relation;
    }

    public String getNextRow(){
        try {
            return in.readLine();
        } catch (IOException e) {
            ReportError.msg(this.getClass().getName() + e);
            return null;
        }
    }

    public void sendTuples(){
        String nextRow;
        Tuple nextTuple;
        WriteEnd writeEnd = this.connector.getWriteEnd();

        while ((nextRow = getNextRow()) != null) {
            nextTuple = Tuple.makeTupleFromFileData(this.connector.getRelation(), nextRow);
            sendTuple(writeEnd, nextTuple);
        }
        writeEnd.close();
    }

    public void sendTuple(WriteEnd writeEnd, Tuple tuple){
        try {
            writeEnd.putNextTuple(tuple);
        } catch (Exception e){
            ReportError.msg(this.getClass().getName() + e);
        }
    }

}
