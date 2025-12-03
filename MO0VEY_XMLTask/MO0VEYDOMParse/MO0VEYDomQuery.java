//package hu.domparse.mo0vey;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class MO0VEYDomQuery {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("MO0VEY_XMLTask/MO0VEY_XML.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("\n1) Minden gyógyszer neve és hatóanyaga:");
            listGyogyszerek(doc);

            System.out.println("\n2) Kiszerelések egy adott gyógyszerhez (G001):");
            listKiszerelesekGyogyszerhez(doc, "G001");

            System.out.println("\n3) Betegségek és a hozzájuk ajánlott gyógyszerek:");
            listBetegsegekGyogyszerekkel(doc);

            System.out.println("\n4) Rendelések végösszege (R001 és R002):");
            rendeleseSosszeg(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listGyogyszerek(Document doc) {
        NodeList gyogyszerLista = doc.getElementsByTagName("Gyogyszer");
        for (int i = 0; i < gyogyszerLista.getLength(); i++) {
            Element gy = (Element) gyogyszerLista.item(i);
            String id = gy.getElementsByTagName("Gyogyszer_ID").item(0).getTextContent();
            String nev = gy.getElementsByTagName("Nev").item(0).getTextContent();
            String hatanyag = gy.getElementsByTagName("Hatanyag").item(0).getTextContent();
            System.out.println("- " + id + ": " + nev + " (" + hatanyag + ")");
        }
    }

    private static void listKiszerelesekGyogyszerhez(Document doc, String gyogyszerId) {
        NodeList kiszLista = doc.getElementsByTagName("Kiszereles");
        for (int i = 0; i < kiszLista.getLength(); i++) {
            Element k = (Element) kiszLista.item(i);
            String gyId = k.getElementsByTagName("Gyogyszer_ID").item(0).getTextContent();
            if (gyogyszerId.equals(gyId)) {
                String kiszId = k.getElementsByTagName("Kiszereles_ID").item(0).getTextContent();
                String forma = k.getElementsByTagName("Forma").item(0).getTextContent();
                String menny = k.getElementsByTagName("Mennyiseg").item(0).getTextContent();
                String ar = k.getElementsByTagName("Ar").item(0).getTextContent();
                System.out.println("- " + kiszId + ": " + forma + ", " + menny + " db, " + ar + " Ft");
            }
        }
    }

    private static void listBetegsegekGyogyszerekkel(Document doc) {
        NodeList betegsegLista = doc.getElementsByTagName("Betegseg");
        for (int i = 0; i < betegsegLista.getLength(); i++) {
            Element b = (Element) betegsegLista.item(i);
            String bId = b.getElementsByTagName("Betegseg_ID").item(0).getTextContent();
            String nev = b.getElementsByTagName("Nev").item(0).getTextContent();
            System.out.println("Betegség: " + nev + " (" + bId + ")");

            NodeList kapcsolatLista = doc.getElementsByTagName("BetegsegGyogyszer");
            for (int j = 0; j < kapcsolatLista.getLength(); j++) {
                Element kapcs = (Element) kapcsolatLista.item(j);
                String kapcsBetId = kapcs.getElementsByTagName("Betegseg_ID").item(0).getTextContent();
                if (bId.equals(kapcsBetId)) {
                    String gyId = kapcs.getElementsByTagName("Gyogyszer_ID").item(0).getTextContent();
                    Element gy = findGyogyszerById(doc, gyId);
                    if (gy != null) {
                        String gyNev = gy.getElementsByTagName("Nev").item(0).getTextContent();
                        String ajanlas = kapcs.getElementsByTagName("AjánlasLeiras").item(0).getTextContent();
                        System.out.println("  - " + gyNev + " (" + gyId + "): " + ajanlas);
                    }
                }
            }
        }
    }

    private static Element findGyogyszerById(Document doc, String id) {
        NodeList gyogyszerLista = doc.getElementsByTagName("Gyogyszer");
        for (int i = 0; i < gyogyszerLista.getLength(); i++) {
            Element gy = (Element) gyogyszerLista.item(i);
            String gyId = gy.getElementsByTagName("Gyogyszer_ID").item(0).getTextContent();
            if (id.equals(gyId)) {
                return gy;
            }
        }
        return null;
    }

    private static void rendeleseSosszeg(Document doc) {
        NodeList rendelFejek = doc.getElementsByTagName("Rendeles_Fej");
        for (int i = 0; i < rendelFejek.getLength(); i++) {
            Element fej = (Element) rendelFejek.item(i);
            String rId = fej.getElementsByTagName("Rendeles_ID").item(0).getTextContent();

            int osszeg = 0;
            NodeList tetelek = doc.getElementsByTagName("Rendeles_Tetel");
            for (int j = 0; j < tetelek.getLength(); j++) {
                Element tetel = (Element) tetelek.item(j);
                String tetelRId = tetel.getElementsByTagName("Rendeles_ID").item(0).getTextContent();
                if (rId.equals(tetelRId)) {
                    int tetelOsszeg = Integer.parseInt(tetel.getElementsByTagName("Osszeg").item(0).getTextContent());
                    osszeg += tetelOsszeg;
                }
            }
            System.out.println("- Rendelés " + rId + " végösszeg: " + osszeg + " Ft");
        }
    }
}
