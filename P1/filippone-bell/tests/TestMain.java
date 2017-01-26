import org.junit.Test;
import RegTest.Utility;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import introspect.Main;

public class TestMain {
    
    static String[] eliminate = {"\\(m\\d+,","\\(c\\d+,"};

    @Test
    public void testReflectP1package() throws IOException, ClassNotFoundException {
        String[] args = {"p1package", "tests/p1package"};
        Main.main(args);
        Utility.validate("results/p1package.prog1.pl","tests/validation-files/p1package-correct.txt", true, eliminate);
    } 
    @Test
    public void testReflectIntrospect() throws IOException, ClassNotFoundException {
        String[] args = {"introspect", "introspect"};
        Main.main(args);
        Utility.validate("results/introspect.prog1.pl","tests/validation-files/introspect-correct.txt", true, eliminate);
    } 
    @Test
    public void testReflectGraff() throws IOException, ClassNotFoundException {
        String[] args = {"graff", "tests/ClassesOdd/graff"};
        Main.main(args);
        Utility.validate("results/graff.prog1.pl","tests/validation-files/graff-correct.txt", true, eliminate);
    } 
    @Test
    public void testReflectNotepad() throws IOException, ClassNotFoundException {
        String[] args = {"Notepad", "tests/ClassesOdd/Notepad"};
        Main.main(args);
        Utility.validate("results/Notepad.prog1.pl","tests/validation-files/Notepad-correct.txt", true, eliminate);
    } 
    @Test
    public void testReflectQuark() throws IOException, ClassNotFoundException {
        String[] args = {"quark", "tests/ClassesOdd/quark"};
        Main.main(args);
        Utility.validate("results/quark.prog1.pl","tests/validation-files/quark-correct.txt", true, eliminate);
    } 
    @Test
    public void testReflectYparser() throws IOException, ClassNotFoundException {
        String[] args = {"yparser", "tests/ClassesOdd/yparser"};
        Main.main(args);
        Utility.validate("results/yparser.prog1.pl","tests/validation-files/yparser-correct.txt", true, eliminate);
    } 
    
}
