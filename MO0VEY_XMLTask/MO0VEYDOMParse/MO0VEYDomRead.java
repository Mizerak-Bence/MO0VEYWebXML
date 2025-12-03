//package hu.domparse.mo0vey;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class MO0VEYDomRead {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("MO0VEY_XMLTask/MO0VEY_XML.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Teljes dokumentum kiírása blokk formában a konzolra
            printElement(doc.getDocumentElement(), 0);

            // Mentés fájlba (MO0VEY_XML_read_out.xml)
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("MO0VEY_XML_read_out.xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printElement(Element element, int indent) {
        // Behúzás előállítása
        StringBuilder pad = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            pad.append("  ");
        }

        System.out.println(pad + "<" + element.getTagName() + ">");

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                printElement((Element) node, indent + 1);
            } else if (node.getNodeType() == Node.TEXT_NODE) {
                String text = node.getTextContent().trim();
                if (!text.isEmpty()) {
                    System.out.println(pad + "  " + text);
                }
            }
        }

        System.out.println(pad + "</" + element.getTagName() + ">");
    }
}
