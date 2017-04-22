import java.io.*;

/**
  * @author John Filippone and Jeff Bell
  */
public class ReadRelation extends Thread {

    BufferedReader in;
    PrintStream out;

    public ReadRelation(String fileName, PrintStream out) {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (Exception e) {
            ReportError.msg(e.getMessage());
        }
        this.out = out;
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
