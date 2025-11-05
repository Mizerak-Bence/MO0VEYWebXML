import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomQueryMO0VEY {
	private static String text(Element parent, String tag) {
		NodeList nl = parent.getElementsByTagName(tag);
		if (nl == null || nl.getLength() == 0) return "";
		Node n = nl.item(0);
		return n != null ? n.getTextContent() : "";
	}

	public static void main(String[] args) {
		final String NEPTUNKOD = "MO0VEY";
		try {
			File inputFile = new File("MO0VEY_orarend.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(inputFile);

			NodeList orak = doc.getElementsByTagName("ora");
			int total = orak.getLength();
			int eloadas = 0, gyakorlat = 0;

			for (int i = 0; i < orak.getLength(); i++) {
				Node n = orak.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element ora = (Element) n;
					String tipus = ora.getAttribute("tipus");
					if ("eloadas".equalsIgnoreCase(tipus)) eloadas++;
					else if ("gyakorlat".equalsIgnoreCase(tipus)) gyakorlat++;
				}
			}

			System.out.println("Neptunkod: " + NEPTUNKOD);
			System.out.println("Osszes ora: " + total);
			System.out.println("Eloadas: " + eloadas + ", Gyakorlat: " + gyakorlat);

			for (int i = 0; i < orak.getLength(); i++) {
				Node n = orak.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element ora = (Element) n;
					String id = ora.getAttribute("id");
					String tipus = ora.getAttribute("tipus");
					String targy = text(ora, "targy");
					Element idopont = (Element) ora.getElementsByTagName("idopont").item(0);
					String nap = idopont != null ? text(idopont, "nap") : "";
					String tol = idopont != null ? text(idopont, "tol") : "";
					String ig = idopont != null ? text(idopont, "ig") : "";
					String helyszin = text(ora, "helyszin");
					String oktato = text(ora, "oktato");
					System.out.println(String.format("#%s [%s] %s | %s %s-%s | %s | %s", id, tipus, targy, nap, tol, ig, helyszin, oktato));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

