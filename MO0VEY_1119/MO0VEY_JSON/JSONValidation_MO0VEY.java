package JSONParseMO0VEY;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONValidationMO0VEY {
    public static void main(String[] args) {
        String jsonFile = "orarendMO0VEY.json";
        String schemaFile = "orarendJSONSchemaMO0VEY.json";
        try {
            try (InputStream js = new FileInputStream(schemaFile);
                 Reader jr = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
                JSONObject rawSchema = new JSONObject(new JSONTokener(js));
                Schema schema = SchemaLoader.load(rawSchema);
                JSONObject data = new JSONObject(new JSONTokener(jr));
                schema.validate(data);
                System.out.println("Validation: OK");
            }
        } catch (Exception ex) {
            System.out.println("Validation error: " + ex.getMessage());
        }
    }
}
