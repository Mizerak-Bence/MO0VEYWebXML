package MO0VEY_1112.xPathMO0VEY;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class xPathMO0VEY {
    public static void main(String[] args) {
        final String NEPTUNKOD = "MO0VEY";
        try {
            File f = new File("MO0VEY_orarend.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);
            NodeList list = doc.getElementsByTagName("ora");
            int eloadas = 0; int gyakorlat = 0;
            for (int i = 0; i < list.getLength(); i++) {
                Node n = list.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;
                    String tipus = e.getAttribute("tipus");
                    if ("eloadas".equalsIgnoreCase(tipus)) eloadas++; else if ("gyakorlat".equalsIgnoreCase(tipus)) gyakorlat++;
                }
            }
            System.out.println("Neptunkod=" + NEPTUNKOD);
            System.out.println("Eloadas=" + eloadas);
            System.out.println("Gyakorlat=" + gyakorlat);
            for (int i = 0; i < list.getLength(); i++) {
                Node n = list.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;
                    String id = e.getAttribute("id");
                    String tipus = e.getAttribute("tipus");
                    String targy = text(e, "targy");
                    Element idopont = (Element) e.getElementsByTagName("idopont").item(0);
                    String nap = idopont != null ? text(idopont, "nap") : "";
                    String tol = idopont != null ? text(idopont, "tol") : "";
                    String ig = idopont != null ? text(idopont, "ig") : "";
                    String helyszin = text(e, "helyszin");
                    String oktato = text(e, "oktato");
                    System.out.println(id + ":" + tipus + ":" + targy + ":" + nap + ":" + tol + "-" + ig + ":" + helyszin + ":" + oktato);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static String text(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        if (nl == null || nl.getLength() == 0) return "";
        Node n = nl.item(0);
        return n != null ? n.getTextContent() : "";
    }
}
