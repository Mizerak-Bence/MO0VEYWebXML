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
            File xmlFile = new File("MO0VEY_XML.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            System.out.println("========== GYÓGYSZERTÁR RENDSZER ==========");

            printSection(root, "Gyogyszerek", "GYÓGYSZEREK");
            printSection(root, "Kiszerelesek", "KISZERELESEK");
            printSection(root, "Betegsegek", "BETEGSÉGEK");
            printSection(root, "BetegsegGyogyszerKapcsolatok", "BETEGSÉG–GYÓGYSZER KAPCSOLATOK");
            printSection(root, "Rendelesek", "RENDELÉSEK");
            printSection(root, "Szallitok", "SZÁLLÍTÓK");
            printSection(root, "Beszerzesek", "BESZERZÉSEK");

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

    private static void printSection(Element root, String childName, String title) {
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                if (elem.getTagName().equals(childName)) {
                    System.out.println();
                    System.out.println("---- " + title + " ----");
                    printElement(elem, 0);
                    break;
                }
            }
        }
    }

    private static void printElement(Element element, int indent) {
        StringBuilder padBuilder = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            padBuilder.append("  ");
        }
        String pad = padBuilder.toString();

        NamedNodeMap attrs = element.getAttributes();
        NodeList children = element.getChildNodes();

        boolean hasElementChild = false;
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                hasElementChild = true;
                break;
            }
        }

        // Levél elem: csak típus (tag név) + érték, opcionálisan attribútumokkal
        if (!hasElementChild) {
            StringBuilder textBuilder = new StringBuilder();
            for (int i = 0; i < children.getLength(); i++) {
                Node node = children.item(i);
                if (node.getNodeType() == Node.TEXT_NODE) {
                    String t = node.getTextContent().trim();
                    if (!t.isEmpty()) {
                        if (textBuilder.length() > 0) {
                            textBuilder.append(" ");
                        }
                        textBuilder.append(t);
                    }
                }
            }
            String text = textBuilder.toString();

            StringBuilder line = new StringBuilder();
            line.append(pad).append(element.getTagName());

            if (attrs.getLength() > 0) {
                line.append(" [");
                for (int i = 0; i < attrs.getLength(); i++) {
                    Node attr = attrs.item(i);
                    if (i > 0) {
                        line.append(", ");
                    }
                    line.append(attr.getNodeName()).append("=").append(attr.getNodeValue());
                }
                line.append("]");
            }

            if (!text.isEmpty()) {
                line.append(" = ").append(text);
            }

            System.out.println(line.toString());
            return;
        }

        // Nem levél elem: típust és attribútumokat külön sorokban írjuk ki, majd a gyerekeket
        System.out.println(pad + element.getTagName());

        if (attrs.getLength() > 0) {
            for (int i = 0; i < attrs.getLength(); i++) {
                Node attr = attrs.item(i);
                System.out.println(pad + "  @" + attr.getNodeName() + " = " + attr.getNodeValue());
            }
        }

        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                printElement((Element) node, indent + 1);
                // Üres sor a "tételek" között a jobb tagoltságért
                if (indent == 1) {
                    System.out.println();
                }
            }
        }
    }
}
