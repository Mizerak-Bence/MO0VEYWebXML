package JSONParseMO0VEY;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONWriteMO0VEY {
    public static void main(String[] args) {
        String inFile = "orarend_MO0VEY.json";
        String outFile = "orarend_MO0VEY1.json";
        try {
            JSONParser parser = new JSONParser();
            JSONObject root;
            try (Reader r = new InputStreamReader(new FileInputStream(inFile), StandardCharsets.UTF_8)) {
                root = (JSONObject) parser.parse(r);
            }
            Object scheduleObj = root.get("MO0VEY_orarend");
            if (!(scheduleObj instanceof JSONObject)) {
                System.out.println("Hiba: MO0VEY_orarend objektum nem található");
                return;
            }
            JSONObject schedule = (JSONObject) scheduleObj;
            Object lessonsObj = schedule.get("ora");
            if (!(lessonsObj instanceof JSONArray)) {
                System.out.println("Hiba: ora tömb nem található");
                return;
            }
            JSONArray lessons = (JSONArray) lessonsObj;
            for (Object o : lessons) {
                if (!(o instanceof JSONObject)) continue;
                JSONObject lesson = (JSONObject) o;
                Map<String, String> flat = new LinkedHashMap<>();
                Object attrsObj = lesson.get("_attributes");
                if (attrsObj instanceof JSONObject) {
                    JSONObject attrs = (JSONObject) attrsObj;
                    Object id = attrs.get("id");
                    Object tipus = attrs.get("tipus");
                    if (id != null) flat.put("id", String.valueOf(id));
                    if (tipus != null) flat.put("tipus", String.valueOf(tipus));
                }
                Object targy = lesson.get("targy");
                if (targy != null) flat.put("targy", String.valueOf(targy));
                Object idopontObj = lesson.get("idopont");
                if (idopontObj instanceof JSONObject) {
                    JSONObject t = (JSONObject) idopontObj;
                    Object nap = t.get("nap");
                    Object tol = t.get("tol");
                    Object ig = t.get("ig");
                    if (nap != null) flat.put("nap", String.valueOf(nap));
                    if (tol != null) flat.put("tol", String.valueOf(tol));
                    if (ig != null) flat.put("ig", String.valueOf(ig));
                }
                Object helyszin = lesson.get("helyszin");
                if (helyszin != null) flat.put("helyszin", String.valueOf(helyszin));
                Object oktato = lesson.get("oktato");
                if (oktato != null) flat.put("oktato", String.valueOf(oktato));
                Object szak = lesson.get("szak");
                if (szak != null) flat.put("szak", String.valueOf(szak));
                for (Map.Entry<String, String> e : flat.entrySet()) {
                    System.out.println(e.getKey() + ": " + e.getValue());
                }
                System.out.println();
            }
            try (Writer w = new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8)) {
                w.write(root.toJSONString());
            }
        } catch (Exception ex) {
            System.out.println("Hiba: " + ex.getMessage());
        }
    }
}
