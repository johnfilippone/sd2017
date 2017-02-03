package VPL;

import org.junit.Test;

/**
 *
 */
public class ConformTest {
    
    public ConformTest() {
    }

    /* test user input */
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
    
    /* basic constraint test */
    @Test 
    public void testMiddleLabelRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/middleLabelRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/middleLabelRule.txt",false);
    }
    @Test 
    public void testClassesWithSameName() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/classesWithSameName.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/classesWithSameName.txt",false);
    }
    @Test 
    public void testInterfacesWithSameName() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/interfacesWithSameName.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/interfacesWithSameName.txt",false);
    }
    /*
    @Test 
    public void testClassWithSameNameAsInterface() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/classWithSameNameAsInterface.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/classWithSameNameAsInterface.txt",false);
    }
    @Test 
    public void testNullNamesRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/nullNamesRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/nullNamesRule.txt",false);
    }
    @Test 
    public void testBlackDiamondRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/blackDiamondRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/blackDiamondRule.txt",false);
    }
    @Test 
    public void testDiamondsRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/diamondsRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/diamondsRule.txt",false);
    }
    @Test 
    public void testTriangleRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/triangleRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/triangleRule.txt",false);
    }
    @Test 
    public void testNonEmptyRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/nonEmptyRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/nonEmptyRule.txt",false);
    }
    @Test 
    public void testDottenInheritanceRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/dottedInheritanceRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/dottedInheritanceRule.txt",false);
    }
    @Test 
    public void testInterfaceCannotImplementClassRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/interfaceCannotImplementClassRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/interfaceCannotImplementClassRule.txt",false);
    }
    @Test 
    public void testSelfInheritanceRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/selfInheritanceRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/selfInheritanceRule.txt",false);
    }
    @Test 
    public void testDottedAssociationRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependancies/vpl/dottedAssociationRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependancies/validation-files/dottedAssociationRule.txt",false);
    }


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
