package Test;

import Cactus.FIND.NodeFinder;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ezilizh
 * Date: 11/18/14
 * Time: 6:01 PM
 */
public class TestNodeFind
{
    @Test
    public void test() throws Exception
    {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("C:\\Users\\ezilizh\\Leo\\code\\myeclipse\\MDFCPSimRefactor\\cfg.xml");
        assertEquals(NodeFinder.find(doc.getFirstChild(),"RTSPMessageFile").get(0).getFirstChild().getNodeValue(), "pt.vod.1deliverysession.xml");
        assertEquals(NodeFinder.find(doc.getFirstChild(),"RTSPMessageFile").get(1).getFirstChild().getNodeValue(), "pt.live.1deliverysession.xml");

    }
}
