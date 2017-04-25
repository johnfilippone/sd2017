package gamma;


import java.io.*;
import java.util.StringTokenizer;
import gammaSupport.*;
import basicConnector.*;


public class ReadRelation extends Thread {

    BufferedReader in;
    Connector connector;
    String relationName;

    public ReadRelation(String filename, String relationName, Connector connector){
        try {
            this.in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            this.connector = connector;
            this.relationName = relationName;
        } catch (Exception e) {
            ReportError.msg(e.getMessage());
        }
    }

    public void readAndSetRelationFromFile() throws IOException{
        String relationLine = in.readLine();
        StringTokenizer tokenizer = new StringTokenizer(relationLine);
        Relation relation = new Relation(relationName, tokenizer.countTokens());
        while(tokenizer.hasMoreTokens())
            relation.addField(tokenizer.nextToken());
        connector.setRelation(relation);

        in.readLine();
    }

    public void run() {
        try {
            readAndSetRelationFromFile();
            String input;
            Tuple nextTuple;
            WriteEnd writeEnd = connector.getWriteEnd();
            while ((input = in.readLine()) != null) {
                nextTuple = Tuple.makeTupleFromFileData(connector.getRelation(), input);
                writeEnd.putNextTuple(nextTuple);
            }
            writeEnd.close();
        } catch (Exception e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }
}
