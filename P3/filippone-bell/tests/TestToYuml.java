import org.junit.Test;

public class TestToYuml {
    
    /* Test files with 'correct' yuml files */
    @Test 
    public void testUniversity() {
        String[] args = {"pl/University.vpl.pl", "../src/toYuml.vm", "yuml/University.yuml"};
        MDL.Vm2t.main(args);
        RegTest.Utility.validate("yuml/University.yuml","correct/University.yuml",false);
    }
    @Test 
    public void testSchool() {
        String[] args = {"pl/school.vpl.pl", "../src/toYuml.vm", "yuml/school.yuml"};
        MDL.Vm2t.main(args);
        RegTest.Utility.validate("yuml/school.yuml","correct/school.yuml",false);
    }
    @Test 
    public void testShoppingCart() {
        String[] args = {"pl/shoppingCart.vpl.pl", "../src/toYuml.vm", "yuml/shoppingCart.yuml"};
        MDL.Vm2t.main(args);
        RegTest.Utility.validate("yuml/shoppingCart.yuml","correct/shoppingCart.yuml",false);
    }
    @Test
    public void testERMetaModel() {
        String[] args = {"pl/ERMetaModel.vpl.pl", "../src/toYuml.vm", "yuml/ERMetaModel.yuml"};
        MDL.Vm2t.main(args);
        RegTest.Utility.validate("yuml/ERMetaModel.yuml","correct/ERMetaModel.yuml",false);
    }
    @Test
    public void testHard() {
        String[] args = {"pl/hard.vpl.pl", "../src/toYuml.vm", "yuml/hard.yuml"};
        MDL.Vm2t.main(args);
        RegTest.Utility.validate("yuml/hard.yuml","correct/hard.yuml",false);
    }
    @Test
    public void testGates() {
        String[] args = {"pl/gates.vpl.pl", "../src/toYuml.vm", "yuml/gates.yuml"};
        MDL.Vm2t.main(args);
        RegTest.Utility.validate("yuml/gates.yuml","correct/gates.yuml",false);
    }

}
