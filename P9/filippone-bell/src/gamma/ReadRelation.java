package gamma;


import java.io.*;
import java.util.StringTokenizer;
import gammaSupport.Relation;
import gammaSupport.ReportError;


public class ReadRelation extends Thread {

    BufferedReader in;
    Connector connector;

    public ReadRelation(String fileName, Connector out) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (Exception e) {
            ReportError.msg(e.getMessage());
        }

        this.connector = out;
        readAndSetRelationFromFile();

    }

    public static void readAndSetRelationFromFile(){
        String relationLine = in.readLine();
        StringTokenizer tokenizer = new StringTokenizer(relationLine);
        Relation relation = new Relation(relationName, tokenizer.countTokens());

        in.readLine();
    }

    public void run() {
        try {
            String input;
            while (true) {
                input = in.readLine();
                if (input == null) {
                    break;
                }

                Tuple newTuple = 
                    Tuple.makeTupleFromFileData(connector.getRelation(), input);
                connector.getWriteEnd().putNextTuple();
            }
        } catch (IOException e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }
}
