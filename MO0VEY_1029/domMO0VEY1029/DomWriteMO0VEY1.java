package doma24e5z1029;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DOMWrite1 {

    public static void main(String[] args) throws ParserConfigurationException,
    TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.newDocument();

        Element root = doc.createElementNS("doma24e5z1029", "A24E5Z_orarend");
        doc.appendChild(root);


        root.appendChild(createOra(doc, "1", "eloadas", "Web Technologiak 1", 
                "hetfo", "10", "12", "A1 magasfsz V.ea.", "Agradi Anita", "Programtervezo informatikus"));

        root.appendChild(createOra(doc, "2", "eloadas", "Muszaki szaknyelv 1", 
                "hetfo", "12", "14", "A1/316 III.ea.", "Pataki Janosne", "Programtervezo informatikus"));

        root.appendChild(createOra(doc, "3", "eloadas", "Mesterseges Intelligencia alapjai", 
                "kedd", "10", "12", "A1 XXXII.ea.", "Kunne Dr. Tamas Judit", "Programtervezo informatikus"));

        root.appendChild(createOra(doc, "4", "gyakorlat", "Mesterseges Intelligencia alapjai", 
                "kedd", "12", "14", "A1 XXXII.ea.", "Fazekas Levente", "Programtervezo informatikus"));

        root.appendChild(createOra(doc, "5", "eloadas", "Algoritmusok es vizsgalatuk", 
                "kedd", "14", "16", "A1/305 XXX.ea.", "Dr. Hazy Attila", "Programtervezo informatikus"));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);

        File myFile = new File("A24E5Z_orarend.xml");

        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(myFile);

        transf.transform(source, console);
        transf.transform(source, file);
    }

    private static Node createOra(Document doc, String id, String tipus, String targy,
                                  String nap, String tol, String ig, String helyszin,
                                  String oktato, String szak) {

        Element ora = doc.createElement("ora");
        ora.setAttribute("id", id);
        ora.setAttribute("tipus", tipus);

        ora.appendChild(createTextElement(doc, "targy", targy));

        Element idopont = doc.createElement("idopont");
        idopont.appendChild(createTextElement(doc, "nap", nap));
        idopont.appendChild(createTextElement(doc, "tol", tol));
        idopont.appendChild(createTextElement(doc, "ig", ig));
        ora.appendChild(idopont);

        ora.appendChild(createTextElement(doc, "helyszin", helyszin));
        ora.appendChild(createTextElement(doc, "oktato", oktato));
        ora.appendChild(createTextElement(doc, "szak", szak));

        return ora;
    }

    private static Node createTextElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
