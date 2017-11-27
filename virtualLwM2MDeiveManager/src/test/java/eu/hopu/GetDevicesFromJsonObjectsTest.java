package eu.hopu;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import eu.hopu.devices.DeviceBase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GetDevicesFromJsonObjectsTest {

    private static final JsonParser jsonParser = new JsonParser();

    @Before
    public void setUp() {

    }

    @Test
    public void given_a_not_found_model_return_a_null_device() throws Exception{
        JsonArray jsonArray = (JsonArray) jsonParser.parse("[\n" +
                "  {\n" +
                "    \"name\": \"HOP010203040506\",\n" +
                "    \"serverUrl\": \"coap://glueandblue.com\",\n" +
                "    \"serverPort\": \"5683\",\n" +
                "    \"lifetime\": 30,\n" +
                "    \"device\": {\n" +
                "      \"model\": \"MarioSmartSpot\"\n" +
                "    },\n" +
                "    \"localAddress\": \"0.0.0.0\",\n" +
                "    \"localPort\": 1234\n" +
                "  }\n" +
                "]");

        ArrayList<DeviceBase> devicesBase = new GetDevicesFromJsonObjects(jsonArray).execute();
        assertEquals(1, devicesBase.size());
        assertEquals(DeviceBase.NULL, devicesBase.get(0));
    }


    @Test
    public void given_a_json_device_array_returns_a_device_list() throws Exception {
        JsonArray jsonArray = (JsonArray) jsonParser.parse("[\n" +
                "  {\n" +
                "    \"name\": \"HOP010203040506\",\n" +
                "    \"serverUrl\": \"coap://glueandblue.com\",\n" +
                "    \"serverPort\": \"5683\",\n" +
                "    \"lifetime\": 30,\n" +
                "    \"device\": {\n" +
                "      \"batteryStatus\": 0,\n" +
                "      \"batteryLevel\": 100,\n" +
                "      \"model\": \"SmartSpot\"\n" +
                "    },\n" +
                "    \"localAddress\": \"0.0.0.0\",\n" +
                "    \"localPort\": 1234\n" +
                "  }\n" +
                "]");

        ArrayList<DeviceBase> devicesBase = new GetDevicesFromJsonObjects(jsonArray).execute();
        assertEquals(1, devicesBase.size());
        assertNotEquals(DeviceBase.NULL, devicesBase.get(0));
    }

}
