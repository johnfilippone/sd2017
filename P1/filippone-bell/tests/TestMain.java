import org.junit.Test;
import RegTest.Utility;
import static org.junit.Assert.assertEquals;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import introspect.Main;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.StringBuilder;

public class TestMain {
    
    @Test
    public void testReflectionTest() throws IOException, ClassNotFoundException {
        //String correct = new Scanner(new File("validation-files/p1package-correct.txt")).useDelimiter("\\Z").next();
        //assertEquals(getSortedFile(correct), getSortedFile(Main.getTables("p1package", "p1package")));
        String[] args = {"p1package", "tests/p1package"};
        Main.main(args);
        Utility.validate("results/p1package.prog1.pl","tests/validation-files/p1package-correct.txt", false);
    } 
//    @Test
//    public void testReflectSelf() throws FileNotFoundException, ClassNotFoundException {
//        String correct = new Scanner(new File("validation-files/introspect-correct.txt")).useDelimiter("\\Z").next();
//        assertEquals(getSortedFile(correct), getSortedFile(Main.getTables("introspect", "../introspect")));
//    } 
//    @Test
//    public void testReflectGraff() throws FileNotFoundException, ClassNotFoundException {
//        String correct = new Scanner(new File("validation-files/graff-correct.txt")).useDelimiter("\\Z").next();
//        assertEquals(getSortedFile(correct), getSortedFile(Main.getTables("graff", "ClassesOdd/graff")));
//    } 
//    @Test
//    public void testReflectNotepad() throws FileNotFoundException, ClassNotFoundException {
//        String correct = new Scanner(new File("validation-files/Notepad-correct.txt")).useDelimiter("\\Z").next();
//        assertEquals(getSortedFile(correct), getSortedFile(Main.getTables("Notepad", "ClassesOdd/Notepad")));
//    } 
//    @Test
//    public void testReflectQuark() throws FileNotFoundException, ClassNotFoundException {
//        String correct = new Scanner(new File("validation-files/quark-correct.txt")).useDelimiter("\\Z").next();
//        assertEquals(getSortedFile(correct), getSortedFile(Main.getTables("quark", "ClassesOdd/quark")));
//    } 
//    @Test
//    public void testReflectYparser() throws FileNotFoundException, ClassNotFoundException {
//        String correct = new Scanner(new File("validation-files/yparser-correct.txt")).useDelimiter("\\Z").next();
//        assertEquals(getSortedFile(correct), getSortedFile(Main.getTables("yparser", "ClassesOdd/yparser")));
//    } 
    
    private static String getSortedFile(String unsorted) {
        ArrayList<String> lines = new ArrayList<String>();
        Scanner scan = new Scanner(unsorted);
        while (scan.hasNextLine()){
            lines.add(scan.nextLine());
        }
        Collections.sort(lines);
        StringBuilder correct = new StringBuilder();
        for (String line : lines) {
            correct.append(line + "\n");
        }
        System.out.println("************************");
        System.out.println(correct.toString());
        return correct.toString();
    } 
}
