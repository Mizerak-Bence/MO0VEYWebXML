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

public class DomRead1
{
        public static void main(String argv[]) throws SAXException,
    IOException, ParserConfigurationException 
    {

        File xmlFile = new File("MO0VEY_XML.xml");

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

                String ekod = elem.getAttribute("ekod");
                String nev = elem.getElementsByTagName("nev").item(0).getTextContent();
                String varos = elem.getElementsByTagName("varos").item(0).getTextContent();
                String utca = elem.getElementsByTagName("utca").item(0).getTextContent();
                String hazszam = elem.getElementsByTagName("hazszam").item(0).getTextContent();
                String csillag = elem.getElementsByTagName("csillag").item(0).getTextContent();

                System.out.println("Étterem kódja: " + ekod);
                System.out.println("Név: " + nev);
                System.out.println("Cím: " + varos + ", " + utca + " " + hazszam);
                System.out.println("Csillag: " + csillag);
            }
        }
    }
}