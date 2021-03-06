package VPL;

import org.junit.Test;

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
    
    /* constraint tests */
    @Test 
    public void testMiddleLabel() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/middleLabel.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/middleLabel.txt",false);
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
    public void testClassWithSameNameAsInterface() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/classWithSameNameAsInterface.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/classWithSameNameAsInterface.txt",false);
    }
    @Test
    public void testNullNameClass() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/nullNameClass.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/nullNameClass.txt",false);
    }
    @Test
    public void testNullNameInterface() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/nullNameInterface.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/nullNameInterface.txt",false);
    }
    @Test
    public void testBlackDiamondBadCardinality() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/blackDiamondBadCardinality.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/blackDiamondBadCardinality.txt",false);
    }
    @Test
    public void testBlackDiamondValid() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/blackDiamondValid.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/blackDiamondValid.txt",false);
    }
    @Test
    public void testDiamondBadCardinality() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/diamondBadCardinality.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/diamondBadCardinality.txt",false);
    }
    @Test
    public void testDiamondValid() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/diamondValid.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/diamondValid.txt",false);
    }
    @Test
    public void testTriangleWithV() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/triangleWithV.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/triangleWithV.txt",false);
    }
    @Test
    public void testTriangleWithTriangle() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/triangleWithTriangle.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/triangleWithTriangle.txt",false);
    }
    @Test
    public void testOneLabelInInheritance() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/oneLabelInInheritance.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/oneLabelInInheritance.txt",false);
    }
    @Test
    public void testMultipleLabelInInheritance() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/multipleLabelInInheritance.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/multipleLabelInInheritance.txt",false);
    }
    @Test
    public void testDottedAssociation() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/dottedAssociation.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/dottedAssociation.txt",false);
    }
    @Test
    public void testDottedExtends() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/dottedExtends.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/dottedExtends.txt",false);
    }
    @Test
    public void testNonDottedImplements() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/nonDottedImplements.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/nonDottedImplements.txt",false);
    }
    @Test
    public void testInterfaceImplementsClass() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/interfaceImplementsClass.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/interfaceImplementsClass.txt",false);
    }
    @Test
    public void testSelfInheritance() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/selfInheritance.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/selfInheritance.txt",false);
    }

    /* Don's tests */
    @Test
    public void CarRental() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/CarRental.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/CarRental.txt",false);
    }
    @Test
    public void Comp() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/Comp.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/Comp.txt",false);
    }
    @Test
    public void DecayProduct() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/DecayProduct.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/DecayProduct.txt",false);
    }
    @Test
    public void diagram1() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/diagram1.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/diagram1.txt",false);
    }
    @Test
    public void squirrel() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/squirrel.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/squirrel.txt",false);
    }
    @Test
    public void straight() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/straight.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/straight.txt",false);
    }
    @Test
    public void PrettyBad() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/PrettyBad.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/PrettyBad.txt",false);
    }
    @Test
    public void bogus() {
        RegTest.Utility.redirectStdOut("out.txt");
        Conform.main("test-dependencies/vpl/bogus.vpl.pl");
        RegTest.Utility.validate("out.txt","test-dependencies/validation-files/bogus.txt",false);
    }

}
