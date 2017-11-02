package eu.hopu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.hopu.devices.DeviceBase;
import eu.hopu.devices.FortikaDevice;
import eu.hopu.devices.SmartSpot;
import org.apache.commons.io.IOUtils;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class VirtualSmartSpotsLauncher {

    public static void main(String[] args) throws IOException {

        String path = (args.length == 0) ? "src/main/resources/" : "opt/lwm2m-iotagent-client/lib/";

        List<DeviceBase> devices = getDevicesDefinition(FortikaDevice.DEFINITION, path);
        List<ObjectModel> models = ObjectLoader.loadJsonStream(new FileInputStream(path + "objectspec_debug.json"));
        List<LeshanClient> clientsLaunched = new LinkedList<>();

        for (DeviceBase device : devices) {

            final LeshanClient client = device.getLeshanClient(models);
            clientsLaunched.add(client);
            new Thread() {
                @Override
                public void run() {
                    client.start();
                }
            }.run();
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (LeshanClient client : clientsLaunched)
                    client.destroy(true); // send de-registration request before destroy
            }
        });
    }

    private static List<DeviceBase> getDevicesDefinition(String deviceDevinitions, String path) throws IOException {
        FileInputStream stream = new FileInputStream(path + deviceDevinitions);
        String json = IOUtils.toString(stream, StandardCharsets.UTF_8.name());

        ObjectMapper mapper = new ObjectMapper();
        List<DeviceBase> devices = mapper.readValue(json, new TypeReference<List<FortikaDevice>>() {
        });
        System.out.println(devices);
        return devices;
    }
}
