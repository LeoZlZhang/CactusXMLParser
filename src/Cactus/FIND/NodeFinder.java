package Cactus.FIND;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ezilizh
 * Date: 11/18/14
 * Time: 4:09 PM
 */
public class NodeFinder
{
    public static ArrayList<Node> find(Node node, String condition)
    {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            if (node.getNodeName().equalsIgnoreCase(condition))
            {
                nodeList.add(node);
                return nodeList;
            } else if (node.getChildNodes().getLength() >= 3)
            {
                //Have child nodes, dig deeper
                NodeList subNodeList = node.getChildNodes();
                for (int i = 0; i < subNodeList.getLength(); i++)
                {
                    nodeList.addAll(find(subNodeList.item(i), condition));
                }
                return nodeList;
            } else
            {
                return nodeList;
            }
        } else
        {
            return nodeList;
        }
    }
}
