package eu.hopu;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.hopu.devices.DeviceBase;
import eu.hopu.devices.DeviceJsonDefinitionsUtils;
import eu.hopu.devices.FortikaDevice;
import eu.hopu.devices.SmartSpot;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetLeshanClientsFromDeviceTest {

    private final String PATH = "src/test/resources/";
    private JsonObject jsonSmartSpotObj;

    @Before
    public void setUp() {
        JsonParser jsonParser = new JsonParser();
        jsonSmartSpotObj = (JsonObject) jsonParser.parse(DeviceJsonDefinitionsUtils.getSmartSpotDefinition());
    }


    @Test
    public void given_a_device_list_return_a_leshan_client_list() throws Exception {
        List<DeviceBase> devices = new ArrayList<>();
        devices.add(new SmartSpot(jsonSmartSpotObj));

        List<LeshanClient> leshanClients = new GetLeshanClients(devices).execute();

        assertEquals(devices.size(), leshanClients.size());
    }

}
