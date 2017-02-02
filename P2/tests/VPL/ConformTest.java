package VPL;

import org.junit.Test;

/**
 *
 */
public class ConformTest {
    
    public ConformTest() {
    }

    @Test 
    public void testBadFilename() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("abc");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/usage.txt",false);
    }
    @Test 
    public void testBadArgs() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("abc.vpl.pl abc");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/usage.txt",false);
    }
    @Test 
    public void testBadFilenameAndBadArgs() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("abc abc");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/usage.txt",false);
    }
    @Test 
    public void testMiddleLabel() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/middleLabel.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/testMiddleLabel.txt",false);
    }
    /*

    //don's tests
    @Test
    public void CarRental() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/validation-files/CarRental.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/CarRental.txt",false);
    }
    @Test
    public void Comp() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/validation-files/Comp.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/Comp.txt",false);
    }
    @Test
    public void DecayProduct() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/validation-files/DecayProduct.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/DecayProduct.txt",false);
    }
    @Test
    public void diagram1() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/validation-files/diagram1.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/diagram1.txt",false);
    }
    @Test
    public void squirrel() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/validation-files/squirrel.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/squirrel.txt",false);
    }
    @Test
    public void straight() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/validation-files/straight.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/straight.txt",false);
    }
    */
}
