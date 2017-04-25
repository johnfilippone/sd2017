package gamma;


import java.io.*;
import java.util.StringTokenizer;
import gammaSupport.Relation;
import gammaSupport.ReportError;


public class ReadRelation extends Thread {

    BufferedReader in;
    PrintStream out;
    //String relationName;

    public ReadRelation(String fileName, PrintStream out) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (Exception e) {
            ReportError.msg(e.getMessage());
        }
        this.out = out;
        //relationName = fileName;

        //readAndSetRelationFromFile();
    }

    public static void readAndSetRelatoinFromFile(){
        //String relationLine = in.readLine();
        //StringTokenizer tokenizer = new StringTokenizer(relationLine);
        //Relation relation = new Relation(relationName, tokenizer.countTokens());
        //in.readLine();
    }

    public void run() {
        try {
            String input;
            while (true) {
                input = in.readLine();
                if (input == null) {
                    break;
                }
                out.println(input);
                out.flush();
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            ReportError.msg(this.getClass().getName() + e);
        }
    }
}
