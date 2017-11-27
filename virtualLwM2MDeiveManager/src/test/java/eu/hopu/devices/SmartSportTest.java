package eu.hopu.devices;

import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.JsonObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class SmartSportTest {


    private JsonObject jsonSmartSpotObj;

    @Before
    public void setUp() {
        JsonParser jsonParser = new JsonParser();
        jsonSmartSpotObj = (JsonObject) jsonParser.parse(DeviceJsonDefinitionsUtils.getSmartSpotDefinition());
    }

    @Test
    public void given_a_json_definitin_create_a_device() throws Exception {
        SmartSpot smartSpot = new SmartSpot(jsonSmartSpotObj);
        assertEquals("HOP010203040506", smartSpot.getName());
        assertEquals("coap://glueandblue.com", smartSpot.getServerUrl());
        assertEquals("5683", smartSpot.getServerPort());
        assertEquals(30, smartSpot.getLifetime());

        assertEquals("SmartSpot", smartSpot.getModel());
        assertEquals(0, smartSpot.getDevice().getBatteryStatus());
        assertEquals(55, smartSpot.getDevice().getBatteryLevel());

        assertEquals(38.0770456, smartSpot.getLocation().getLatitude(), 0.0001);
        assertEquals(-1.2712549000000308, smartSpot.getLocation().getLongitude(), 0.0001);
        assertEquals(1, smartSpot.getLocation().getAltitude(), 0.0001);

        assertEquals(2, smartSpot.getTemperatures().size());
        assertEquals(27, smartSpot.getTemperatures().get(1).getMaxValue(), 0.0001);
        assertEquals(19, smartSpot.getTemperatures().get(1).getMinValue(), 0.0001);
        assertEquals(22, smartSpot.getTemperatures().get(1).getSensorValue(), 0.0001);

        assertEquals(2, smartSpot.getHumidities().size());
        assertEquals(60, smartSpot.getHumidities().get(1).getMaxValue(), 0.0001);
        assertEquals(30, smartSpot.getHumidities().get(1).getMinValue(), 0.0001);
        assertEquals(42, smartSpot.getHumidities().get(1).getSensorValue(), 0.0001);

        assertEquals(20, smartSpot.getLoudness().getMaxValue(), 0.0001);
        assertEquals(10, smartSpot.getLoudness().getMinValue(), 0.0001);
        assertEquals(15, smartSpot.getLoudness().getSensorValue(), 0.0001);

        assertEquals(5, smartSpot.getGasses().size());
        assertEquals(56, smartSpot.getGasses().get(2).getMaxValue(), 0.0001);
        assertEquals(0, smartSpot.getGasses().get(2).getMinValue(), 0.0001);
        assertEquals(21, smartSpot.getGasses().get(2).getSensorValue(), 0.0001);

        assertEquals("http://laperalimonera.com", smartSpot.getPhysicalUrl());
        assertTrue(smartSpot.hasCrowdMonitoring());
        assertEquals(smartSpot.getLocalAddress(), "0.0.0.0");
        assertEquals(smartSpot.getLocalPort(), 40000);
    }
}
