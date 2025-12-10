//package hu.domparse.mo0vey;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class MO0VEYDomModify {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("MO0VEY_XML.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // 1) Új gyógyszer hozzáadása
            addNewGyogyszer(doc);

            // 2) Egy meglévő rendelés megjegyzésének módosítása
            modifyRendelesMegjegyzes(doc, "R001", "Módosított megjegyzés: este 6 után szállítani.");

            // 3) Egy rendelés tétel mennyiségének növelése
            increaseTetelMennyiseg(doc, "RT001", 1);

            // 4) Egy beszállító törlése (S002)
            removeSzallito(doc, "S002");

            // Módosított dokumentum adatainak kiírása típus + érték formában (XML formátum nélkül)
            System.out.println("MÓDOSÍTOTT DOKUMENTUM ADATAI:\n");
            printDocumentData(doc.getDocumentElement(), 0);

            // Módosított dokumentum mentése fájlba (továbbra is XML-ben)
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult fileResult = new StreamResult(new File("MO0VEY_XML_modified.xml"));
            transformer.transform(source, fileResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addNewGyogyszer(Document doc) {
        Element root = doc.getDocumentElement();
        Element gyogyszerek = (Element) root.getElementsByTagName("Gyogyszerek").item(0);

        Element ujGy = doc.createElement("Gyogyszer");
        ujGy.setAttribute("Gyogyszer_ID", "G003");

        Element nev = doc.createElement("Nev");
        nev.setTextContent("Vitamin C 500 mg");
        ujGy.appendChild(nev);

        Element hatanyag = doc.createElement("Hatanyag");
        hatanyag.setTextContent("Aszkorbinsav");
        ujGy.appendChild(hatanyag);

        Element recept = doc.createElement("Receptkoteles");
        recept.setTextContent("nem");
        ujGy.appendChild(recept);

        Element kat = doc.createElement("Kategoria");
        kat.setTextContent("taplalekkiegeszito");
        ujGy.appendChild(kat);

        Element leiras = doc.createElement("Leiras");
        leiras.setTextContent("Immunrendszer tamogatasara.");
        ujGy.appendChild(leiras);

        gyogyszerek.appendChild(ujGy);
    }

    private static void modifyRendelesMegjegyzes(Document doc, String rendelesId, String ujMegjegyzes) {
        NodeList fejek = doc.getElementsByTagName("Rendeles_Fej");
        for (int i = 0; i < fejek.getLength(); i++) {
            Element fej = (Element) fejek.item(i);
            String id = fej.getAttribute("Rendeles_ID");
            if (rendelesId.equals(id)) {
                Element megj = (Element) fej.getElementsByTagName("Megjegyzes").item(0);
                megj.setTextContent(ujMegjegyzes);
                break;
            }
        }
    }

    private static void increaseTetelMennyiseg(Document doc, String tetelId, int delta) {
        NodeList tetelek = doc.getElementsByTagName("Rendeles_Tetel");
        for (int i = 0; i < tetelek.getLength(); i++) {
            Element tetel = (Element) tetelek.item(i);
            String id = tetel.getAttribute("Tetel_ID");
            if (tetelId.equals(id)) {
                Element mennyEl = (Element) tetel.getElementsByTagName("Mennyiseg").item(0);
                int menny = Integer.parseInt(mennyEl.getTextContent());
                menny += delta;
                mennyEl.setTextContent(Integer.toString(menny));

                Element egysegArEl = (Element) tetel.getElementsByTagName("Egysegar").item(0);
                Element osszegEl = (Element) tetel.getElementsByTagName("Osszeg").item(0);
                int egysegAr = Integer.parseInt(egysegArEl.getTextContent());
                osszegEl.setTextContent(Integer.toString(menny * egysegAr));
                break;
            }
        }
    }

    private static void removeSzallito(Document doc, String szallitoId) {
        NodeList szallitok = doc.getElementsByTagName("Szallito");
        for (int i = 0; i < szallitok.getLength(); i++) {
            Element sz = (Element) szallitok.item(i);
            String id = sz.getAttribute("Szallito_ID");
            if (szallitoId.equals(id)) {
                sz.getParentNode().removeChild(sz);
                break;
            }
        }
    }

    private static void printDocumentData(Element element, int indent) {
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

        if (!hasElementChild) {
            StringBuilder textBuilder = new StringBuilder();
            for (int i = 0; i < children.getLength(); i++) {
                Node n = children.item(i);
                if (n.getNodeType() == Node.TEXT_NODE) {
                    String t = n.getTextContent().trim();
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
                printDocumentData((Element) node, indent + 1);
                // Üres sor a "tételek" között a jobb tagoltságért
                if (indent == 1) {
                    System.out.println();
                }
            }
        }
    }
}
