/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamma;

import RegTest.Utility;
import gamma.Print;
import gamma.ReadRelation;
import basicConnector.Connector;
import gammaSupport.ThreadList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dsb
 */
public class ReadRelationTest {

    public ReadRelationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createRelation method, of class ReadRelation.
     */
    @Test
    public void testReadRelation() {
        try {
            Utility.redirectStdOut("out.txt");

            ThreadList.init();
            Connector c = new Connector("singleton"); 
            ReadRelation r = new ReadRelation("parts", c);
            Print p = new Print(c);
            ThreadList.run(p);

            Utility.validate("out.txt", "CorrectOutput/readNPrintParts.txt",true);
        } catch (Exception e) {
            fail("exception thrown: createRelation test fails.");
        }
    }

}
