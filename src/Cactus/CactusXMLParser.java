package Cactus;

import Cactus.Parse.NodeParser;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created with IntelliJ IDEA.
 * User: ezilizh
 * Date: 11/20/14
 * Time: 4:47 PM
 */
public class CactusXMLParser
{
    public static Object parse(String xmlFilePath, Class c) throws Exception
    {
        Node rootNode = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFilePath).getFirstChild();

        return NodeParser.parse(rootNode, c, "");
    }
}
