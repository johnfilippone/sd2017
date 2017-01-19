import java.io.*;
import org.apache.commons.io.IOUtils;


public class runem {
    
    public static void main(String[] args) throws IOException {
        Process proc;
        InputStream in;

        proc = Runtime.getRuntime().exec("java setdate.Main");
        in = proc.getInputStream();
        System.out.println(IOUtils.toString(in, "UTF-8"));
        
        proc = Runtime.getRuntime().exec("java javaapplication53.Main");
        in = proc.getInputStream();
        System.out.println(IOUtils.toString(in, "UTF-8"));

        proc = Runtime.getRuntime().exec("java ziptest.Main");
        in = proc.getInputStream();
        System.out.println(IOUtils.toString(in, "UTF-8"));

        proc = Runtime.getRuntime().exec("java WHExample.WH");
        in = proc.getInputStream();
        System.out.println(IOUtils.toString(in, "UTF-8"));

    }
}
