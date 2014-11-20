package Test;

import Cactus.CactusXMLParser;
import org.testng.annotations.Test;
import java.util.ArrayList;

import static org.testng.AssertJUnit.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: ezilizh
 * Date: 11/19/14
 * Time: 3:29 PM
 */
public class TestNodeParser
{
    public String UPAddress;
    public ArrayList<Integer> Interval = new ArrayList<Integer>();
    public ArrayList<TestInnerClass> Traffic;

    @Test
    public void test() throws Exception
    {
        String path = "C:\\Users\\ezilizh\\Leo\\code\\myeclipse\\MDFCPSimRefactor\\cfg.xml";
        TestNodeParser obj = (TestNodeParser) CactusXMLParser.parse(path, TestNodeParser.class);
        assertEquals(obj.UPAddress, "fec0::20c:29ff:feb5:daa2");
        assertEquals(2, obj.Traffic.size());
        assertEquals("pt.vod.1deliverysession.xml", obj.Traffic.get(0).RTSPMessageFile);
        assertEquals(false, obj.Traffic.get(0).TestInner.TestInnerAutoRestart);
        assertEquals("pt.live.1deliverysession.xml", obj.Traffic.get(1).RTSPMessageFile);
        assertEquals(true, obj.Traffic.get(1).TestInner.TestInnerAutoRestart);
        assertEquals(2, obj.Interval.size());
        assertEquals(3000, obj.Interval.get(0).intValue());
        assertEquals(3000, obj.Interval.get(1).intValue());
    }

    public class TestInnerClass
    {
        public String RTSPMessageFile;
        public TestInnerClass2 TestInner;

        public class TestInnerClass2
        {
            public boolean TestInnerAutoRestart;
        }
    }
}
