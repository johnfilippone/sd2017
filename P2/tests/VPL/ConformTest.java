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
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/usage.txt",false);
    }
    @Test 
    public void testBadArgs() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("abc.vpl.pl abc");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/usage.txt",false);
    }
    @Test 
    public void testBadFilenameAndBadArgs() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("abc abc");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/usage.txt",false);
    }
    
    /* basic constraint test */
    @Test 
    public void testMiddleLabelRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/middleLabelRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/middleLabelRule.txt",false);
    }
    @Test 
    public void testClassesWithSameName() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/classesWithSameName.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/classesWithSameName.txt",false);
    }
    @Test 
    public void testInterfacesWithSameName() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/interfacesWithSameName.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/interfacesWithSameName.txt",false);
    }
    @Test 
    public void testClassWithSameNameAsInterface() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/classWithSameNameAsInterface.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/classWithSameNameAsInterface.txt",false);
    }
    /*
    @Test 
    public void testNullNamesRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/nullNamesRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/nullNamesRule.txt",false);
    }
    @Test 
    public void testBlackDiamondRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/blackDiamondRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/blackDiamondRule.txt",false);
    }
    @Test 
    public void testDiamondsRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/diamondsRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/diamondsRule.txt",false);
    }
    @Test 
    public void testTriangleRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/triangleRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/triangleRule.txt",false);
    }
    @Test 
    public void testNonEmptyRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/nonEmptyRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/nonEmptyRule.txt",false);
    }
    @Test 
    public void testDottenInheritanceRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/dottedInheritanceRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/dottedInheritanceRule.txt",false);
    }
    @Test 
    public void testInterfaceCannotImplementClassRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/interfaceCannotImplementClassRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/interfaceCannotImplementClassRule.txt",false);
    }
    @Test 
    public void testSelfInheritanceRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/selfInheritanceRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/selfInheritanceRule.txt",false);
    }
    @Test 
    public void testDottedAssociationRule() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/dottedAssociationRule.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/dottedAssociationRule.txt",false);
    }


    //don's tests
    @Test
    public void CarRental() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/validation-files/CarRental.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/CarRental.txt",false);
    }
    @Test
    public void Comp() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/validation-files/Comp.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/Comp.txt",false);
    }
    @Test
    public void DecayProduct() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/validation-files/DecayProduct.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/DecayProduct.txt",false);
    }
    @Test
    public void diagram1() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/validation-files/diagram1.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/diagram1.txt",false);
    }
    @Test
    public void squirrel() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/validation-files/squirrel.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/squirrel.txt",false);
    }
    @Test
    public void straight() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/validation-files/straight.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/straight.txt",false);
    }
    @Test
    public void PrettyBad() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("TestData/PrettyBad.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/PrettyBad.txt",true);
    }
    @Test
    public void bogus() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("TestData/bogus.vpl.pl");
        RegTest.Utility.validate("out.txt","Correct/bogus.txt",false);
    }
    */
}
