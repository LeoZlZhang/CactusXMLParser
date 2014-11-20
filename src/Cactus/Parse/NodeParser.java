package Cactus.Parse;

import Cactus.FIND.NodeFinder;
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

    public static Object parse(Node node, Class c, String type) throws Exception
    {
        ArrayList<Node> tempNodeList = new ArrayList<Node>();
        tempNodeList.add(node);
        return parse(tempNodeList, c, type);
    }

    public static Object parse(ArrayList<Node> startNodeList, Class c, String type) throws Exception
    {
        Object obj = setInstanceAsFixedClass(startNodeList, c, type);        //ArrayList will be handle in here
        if (obj == null)
        {                   //Not base class
            obj = InstanceCreator(c);
            for (Field field : c.getFields())
            {
                ArrayList<Node> tarNodeList = NodeFinder.find(startNodeList.get(0), field.getName());
                if (tarNodeList.size() == 0)
                    continue;

                field.set(obj, parse(tarNodeList, field.getType(), field.getGenericType().toString()));
            }
            return obj;
        } else
        {
            return obj;       //base class
        }
    }

    public static Object InstanceCreator(Class c) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException
    {
        if (c.getName().contains("$"))
        {
            /**
             * To create instance of inner class as below way
             * InnerClass.getDeclaredConstructors()[0].newInstance(new OutClass)
             */
            String[] classNameList = c.getName().split("\\$");
            Object resObj = Class.forName(classNameList[0]).newInstance();
            for (int i = 1; i < classNameList.length; i++)
            {
                String className = classNameList[0];
                for (int j = 1; j <= i; j++)
                {
                    className = className + "$" + classNameList[j];
                }
                resObj = Class.forName(className).getDeclaredConstructors()[0].newInstance(resObj);
            }
            return resObj;
        } else
        {
            return c.newInstance();
        }
    }

    private static Object setInstanceAsFixedClass(ArrayList<Node> nodeList, Class c, String type) throws Exception
    {
        String value = nodeList.get(0).getFirstChild().getNodeValue();
        if ((c.equals(int.class)) || (c.equals(Integer.class)))
        {
            return new Integer(value);
        } else if ((c.equals(long.class)) || (c.equals(Long.class)))
        {
            return new Long(value);
        } else if ((c.equals(short.class)) || (c.equals(Short.class)))
        {
            return new Short(value);
        } else if (c.equals(String.class))
        {
            return value;
        } else if ((c.equals(float.class)) || (c.equals(Float.class)))
        {
            return new Float(value);
        } else if ((c.equals(double.class)) || (c.equals(Double.class)))
        {
            return new Double(value);
        } else if (c.equals(char.class))
        {
            return value.toCharArray()[0];
        } else if ((c.equals(Boolean.class)) || (c.equals(boolean.class)))
        {
            return Boolean.valueOf(value);
        } else if (c.equals(ArrayList.class))
        {
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
            for (Node node : nodeList)
            {
                resList.add(parse(node, Class.forName(innerClassName), innerType));
            }
            return resList;
        }
        return null;
    }
}
