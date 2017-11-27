package eu.hopu;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GetJsonArrayFromFile implements Command {

    private String file;
    private JsonParser jsonParser;


    public GetJsonArrayFromFile(String file) {
        jsonParser = new JsonParser();
        this.file = file;
    }


    @Override
    public JsonArray execute() {
        String json = "";
        System.out.println(file);
        try {
            json = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8.name());
        } catch (FileNotFoundException e) {
            errorPrintTrace(e, "File not found");
        } catch (IOException e) {
            errorPrintTrace(e, "Json input error");
        }

        return jsonParser.parse(json).getAsJsonArray();
    }

    private void errorPrintTrace(IOException e, String message) {
        System.err.println(message);
        e.printStackTrace();
    }
}
