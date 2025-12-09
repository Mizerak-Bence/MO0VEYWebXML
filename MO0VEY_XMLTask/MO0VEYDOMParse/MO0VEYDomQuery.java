//package hu.domparse.mo0vey;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class MO0VEYDomQuery {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("MO0VEY_XML.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("\nEgy konkrét rendelés adatai (R001):");
            printRendelesById(doc, "R001");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printRendelesById(Document doc, String rendelesId) {
        NodeList rendelFejek = doc.getElementsByTagName("Rendeles_Fej");
        for (int i = 0; i < rendelFejek.getLength(); i++) {
            Element fej = (Element) rendelFejek.item(i);
            String rId = fej.getAttribute("Rendeles_ID");
            if (rendelesId.equals(rId)) {
                String nev = fej.getElementsByTagName("Nev").item(0).getTextContent();
                String email = fej.getElementsByTagName("Email").item(0).getTextContent();
                String datum = fej.getElementsByTagName("Datum").item(0).getTextContent();
                String megjegyzes = fej.getElementsByTagName("Megjegyzes").item(0).getTextContent();

                System.out.println("Rendelés ID: " + rId);
                System.out.println("Név: " + nev);
                System.out.println("E-mail: " + email);
                System.out.println("Dátum: " + datum);
                System.out.println("Megjegyzés: " + megjegyzes);

                int osszeg = 0;
                NodeList tetelek = doc.getElementsByTagName("Rendeles_Tetel");
                for (int j = 0; j < tetelek.getLength(); j++) {
                    Element tetel = (Element) tetelek.item(j);
                    String tetelRId = tetel.getAttribute("Rendeles_ID");
                    if (rendelesId.equals(tetelRId)) {
                        int tetelOsszeg = Integer.parseInt(tetel.getElementsByTagName("Osszeg").item(0).getTextContent());
                        osszeg += tetelOsszeg;
                    }
                }
                System.out.println("Végösszeg: " + osszeg + " Ft");
                break;
            }
        }
    }
}
