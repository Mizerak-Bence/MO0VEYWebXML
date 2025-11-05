package doma24e5z1029;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomRead
{
    public static void main(String argv[]) throws SAXException,
    IOException, ParserConfigurationException 
    {

        File xmlFile = new File("MO0VEYhallgato.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        Document A24E5Z = dBuilder.parse(xmlFile);

        A24E5Z.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + A24E5Z.getDocumentElement().getNodeName());

        for(int i = 0; i < nList.getLength(); i++) {
            
            Node nNode = nList.item(i);

            System.out.println("\Aktuális elem: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;

                String hid = elem.getAttribute("id");

                Node node1 = elem.getElementsByTagName("keresztnev").item(0);
                String kname = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("vezeteknev").item(0);
                String kname = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("foglalkozas").item(0);
                String kname = node3.getTextContent();

                System.out.println("Hallgató id: " + hid);
                System.out.println("Keresztnév: " + kname);
                System.out.println("Vezeteknév: " + vname);
                System.out.println("Foglalkozás: " + fname);


            }
        }
    }
}