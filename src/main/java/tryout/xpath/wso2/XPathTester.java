package tryout.xpath.wso2;

import net.sf.saxon.xpath.XPathExpressionImpl;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.impl.dom.DOOMAbstractFactory;
import org.apache.axiom.soap.impl.dom.factory.DOMSOAPFactory;

import java.lang.reflect.Field;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * SaxonHE v9.5.1-8 based XPath tester
 */
public class XPathTester
{
    private static javax.xml.xpath.XPath domXpath = XPathFactory.newInstance().newXPath();

    public static void main( String[] args )
    {
        try {
            evalXPath();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public static OMElement createOM(){
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMElement omElement = fac.createOMElement("people", "", "");
        OMElement person = fac.createOMElement("person", "", "");
        OMElement name = fac.createOMElement("name", "", "");

        name.setText("Eranda");
        person.addChild(name);
        omElement.addChild(person);

        return omElement;

    }

    public static String evalXPath() throws XPathExpressionException {
        DOMSynapseXPathNamespaceMap domNamespaceMap = new DOMSynapseXPathNamespaceMap();
        domNamespaceMap.addNamespace("fn","http://www.w3.org/2005/xpath-functions");
        OMElement element = createOM();
        OMElement doomElement = null;

        if (element == null) {
            doomElement = new DOMSOAPFactory().createOMElement(new QName(""));
        } else {
            doomElement = convertToDOOM(element);
        }

        String expression = "fn:remove( (\"a\", \"b\", \"c\"), 2)";
        //String expression = "//name";
//        String expression = "fn:remove(\"hello\",200)";
//        String expression = " fn:error(fn:QName('http://www.example.com/HR', 'myerr:toohighsal'), 'Does not apply because salary is too high') ";
//        String expression = " fn:distinct-values((1, 2.0, 1, 2))";

        domXpath.setNamespaceContext(domNamespaceMap);
        XPathExpression expr = domXpath.compile(expression);
        Object result = expr.evaluate(doomElement);

        //Debug
        System.out.println("OMElement : " +  element.toString());
        System.out.println("Evaluated Expression : " + expression);

        try {
            Field field =XPathExpressionImpl.class.getDeclaredField("expression");
            field.setAccessible(true);
            System.out.println("Compiled Expression : " + field.get(((XPathExpressionImpl) expr)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("Result : " + result.toString() );

        if (result != null) {
            return result.toString();
        }
        return null;

    }

    private static OMElement convertToDOOM(OMElement element) {
        XMLStreamReader llomReader = element.getXMLStreamReader();
        OMFactory doomFactory = DOOMAbstractFactory.getOMFactory();
        StAXOMBuilder doomBuilder = new StAXOMBuilder(doomFactory, llomReader);
        return doomBuilder.getDocumentElement();
    }
}
