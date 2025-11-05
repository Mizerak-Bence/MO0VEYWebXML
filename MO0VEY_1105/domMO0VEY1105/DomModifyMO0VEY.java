import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class DomModifyMO0VEY {
    public static void main(String argv[]) {
        try {
            // Use the existing XML file in this folder
            File inputFile = new File("MO0VEYhallgato.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            Node hallgatok = doc.getFirstChild();
            Node hallgat = doc.getElementsByTagName("hallgato").item(0);

            // hallgato attribute update
            NamedNodeMap attr = hallgat.getAttributes();
            Node nodeAttr = attr.getNamedItem("id");
            nodeAttr.setTextContent("01");

            // loop the hallgato child nodes
            NodeList list = hallgat.getChildNodes();

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    if ("keresztnev".equals(eElement.getNodeName())) {
                        if ("Pál".equals(eElement.getTextContent())) {
                            eElement.setTextContent("Olívia");
                        }
                    }
                    if ("vezeteknev".equals(eElement.getNodeName())) {
                        if ("Kiss".equals(eElement.getTextContent())) {
                            eElement.setTextContent("Erős");
                        }
                    }
                }
            }

            // Print modified content to console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Create DOMSource from the DOM tree
            DOMSource source = new DOMSource(doc);

            System.out.println("--Módosított fájl--");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
