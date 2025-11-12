package MO0VEY_1112.xPathMO0VEY;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class xPathQueryMO0VEY {
    public static void main(String[] args) {
        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse("Hallgato.xml");

            document.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = "//hallgato";

            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                System.out.println("\nAktuális elem: " + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("hallgato")) {

                    Element element = (Element) node;

                    System.out.println("Hallgato ID: " + element.getAttribute("id"));

                    System.out.println("Keresztnev: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());

                    System.out.println("Vezeteknev: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());

                    System.out.println("Becenev: " + element.getElementsByTagName("becenev").item(0).getTextContent());

                    System.out.println("Kor: " + element.getElementsByTagName("kor").item(0).getTextContent());
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}

