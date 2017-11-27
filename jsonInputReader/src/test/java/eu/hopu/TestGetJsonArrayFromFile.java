package eu.hopu;

import com.google.gson.JsonArray;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class TestGetJsonArrayFromFile {

    String path;

    @Before
    public void setUp() {
        path = "src/test/resources/";
    }

    @Test
    public void getJsonArrayFromFile() throws Exception {
        JsonArray jsonArray = new GetJsonArrayFromFile(path + "test_file.json").execute();
        assertEquals(2, jsonArray.size());

        assertTrue(jsonArray.get(0).getAsJsonObject().has("a"));
        assertTrue(jsonArray.get(1).getAsJsonObject().has("b"));
    }
}
