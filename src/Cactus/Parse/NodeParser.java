package Cactus.Parse;

import Cactus.FIND.NodeFinder;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ezilizh
 * Date: 11/18/14
 * Time: 6:28 PM
 */
public class NodeParser
{
    private static Logger errLogger = Logger.getLogger("forERROR");
    private static Logger infoLogger = Logger.getLogger("forINFO");

    public static Object parse(Node node, Class c, String type) throws Exception
    {
        ArrayList<Node> tempNodeList = new ArrayList<Node>();
        tempNodeList.add(node);
        return parse(tempNodeList, c, type);
    }

    private static Object parse(ArrayList<Node> nodeList, Class c, String type) throws Exception
    {
        String value = nodeList.get(0).getFirstChild().getNodeValue();
        String nodeName = nodeList.get(0).getNodeName();
        //For base class case
        if ((c.equals(int.class)) || (c.equals(Integer.class)))
        {
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tInteger" + "\t\"" + value + "\"");
            return new Integer(value);
        } else if ((c.equals(long.class)) || (c.equals(Long.class)))
        {
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tLong" + "\t\"" + value + "\"");
            return new Long(value);
        } else if ((c.equals(short.class)) || (c.equals(Short.class)))
        {
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tShort" + "\t\"" + value + "\"");
            return new Short(value);
        } else if (c.equals(String.class))
        {
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tString" + "\t\"" + value + "\"");
            return value;
        } else if ((c.equals(float.class)) || (c.equals(Float.class)))
        {
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tFloat" + "\t\"" + value + "\"");
            return new Float(value);
        } else if ((c.equals(double.class)) || (c.equals(Double.class)))
        {
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tDouble" + "\t\"" + value + "\"");
            return new Double(value);
        } else if (c.equals(char.class))
        {
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tchar" + "\t\"" + value.toCharArray()[0] + "\"");
            return value.toCharArray()[0];
        } else if ((c.equals(Boolean.class)) || (c.equals(boolean.class)))
        {
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tBoolean" + "\t\"" + value + "\"");
            return Boolean.valueOf(value);
        } else if (c.equals(ArrayList.class))
        {   //For ArrayList case
            String innerClassName = type.substring(type.indexOf("<") + 1, type.lastIndexOf(">"));
            String innerType;
            if (innerClassName.indexOf("<") > 0)
            {
                /**
                 * for ArrayList<ArrayList<T>> case
                 */
                innerType = innerClassName.substring(innerClassName.indexOf("<") + 1, innerClassName.lastIndexOf(">"));
                innerClassName = innerClassName.substring(0, innerClassName.indexOf("<"));
            } else
            {
                innerType = "";
            }
            ArrayList<Object> resList = new ArrayList<Object>();
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tArrayList<" + type + ">");
            for (Node node : nodeList)
            {
                resList.add(parse(node, Class.forName(innerClassName), innerType));
            }
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\tArrayList<" + type + ">" + " size=" + resList.size());
            return resList;
        } else
        {   //For normal class case
            infoLogger.info("Set" + "\t" + nodeName + "\t" + "as\t" + c.getName());
            Object obj = InstanceCreator(c);
            for (Field field : c.getFields())
            {
                ArrayList<Node> tarNodeList = NodeFinder.find(nodeList.get(0), field.getName());
                if (tarNodeList.size() == 0)
                    continue;

                field.set(obj, parse(tarNodeList, field.getType(), field.getGenericType().toString()));
            }
            return obj;
        }
    }

    public static Object InstanceCreator(Class c) throws IllegalAccessException, ClassNotFoundException, InvocationTargetException, InstantiationException
    {
        if (c.getName().contains("$"))
        {
            /**
             * To create instance of inner class as below way
             * InnerClass.getDeclaredConstructors()[0].newInstance(new OutClass)
             */
            String[] classNameList = c.getName().split("\\$");
            Object resObj;
            try
            {
                resObj = Class.forName(classNameList[0]).newInstance();
            } catch (InstantiationException e)
            {
                errLogger.error("Fail to create instance of class \"" + classNameList[0] + "\"");
                throw e;
            }
            for (int i = 1; i < classNameList.length; i++)
            {
                String className = classNameList[0];
                for (int j = 1; j <= i; j++)
                {
                    className = className + "$" + classNameList[j];
                }
                try
                {
                    resObj = Class.forName(className).getDeclaredConstructors()[0].newInstance(resObj);
                } catch (InstantiationException e)
                {
                    errLogger.error("Fail to create instance of inner class \"" + className + "\"");
                    throw e;
                }
            }
            return resObj;
        } else
        {
            try
            {
                return c.newInstance();
            } catch (InstantiationException e)
            {
                errLogger.error("Fail to create instance of class \"" + c.getName() + "\"");
                throw e;
            }
        }
    }
}
