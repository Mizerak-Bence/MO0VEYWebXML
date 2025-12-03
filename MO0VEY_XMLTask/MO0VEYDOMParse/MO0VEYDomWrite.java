//package hu.domparse.mo0vey;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class MO0VEYDomWrite {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("MO0VEY_XML.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Fastruktúra jellegű kiírás konzolra
            printTree(doc.getDocumentElement(), 0);

            // Mentés új fájlba
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("MO0VEY1XML.xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printTree(Node node, int indent) {
        StringBuilder pad = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            pad.append("  ");
        }

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element elem = (Element) node;
            System.out.println(pad + "<" + elem.getTagName() + ">");
        }

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                printTree(child, indent + 1);
            } else if (child.getNodeType() == Node.TEXT_NODE) {
                String text = child.getTextContent().trim();
                if (!text.isEmpty()) {
                    System.out.println(pad + "  " + text);
                }
            }
        }

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element elem = (Element) node;
            System.out.println(pad + "</" + elem.getTagName() + ">");
        }
    }
}
