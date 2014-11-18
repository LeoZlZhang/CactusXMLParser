package TYPE;

/**
 * Created with IntelliJ IDEA.
 * User: ezilizh
 * Date: 11/18/14
 * Time: 4:13 PM
 */
public interface Parser
{
    public Parser getXMLParserInstance();
    public Object configure(Object targetObject);
}
