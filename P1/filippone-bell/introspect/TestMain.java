import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TestMain {
    
    @Test
    public void testReflectionTest() throws FileNotFoundException {
        String correctRT = new Scanner(new File("introspect/correctRT.txt")).useDelimiter("\\Z").next();
        assertEquals(correctRT, introspect.Main.getTables("p1package", "p1package"));
    } 

}
