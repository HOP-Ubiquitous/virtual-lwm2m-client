package eu.hopu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.hopu.dto.Device;
import eu.hopu.dto.DeviceDto;
import eu.hopu.dto.Location;
import eu.hopu.dto.Sensor;
import eu.hopu.objects.*;
import org.apache.commons.io.IOUtils;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.request.BindingMode;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import static org.eclipse.leshan.LwM2mId.*;
import static org.eclipse.leshan.client.object.Security.noSec;

public class VirtualSmartSpotsLauncher {

    public static void main(String[] args) throws IOException {

        String path = (args.length == 0) ? "src/main/resources/" : "opt/lwm2m-iotagent-client/lib/";

        FileInputStream stream = new FileInputStream(path + "devices.json");
        String json = IOUtils.toString(stream, StandardCharsets.UTF_8.name());

        ObjectMapper mapper = new ObjectMapper();
        List<Device> devices = mapper.readValue(json, new TypeReference<List<Device>>() {});
        System.out.println(devices);

        List<ObjectModel> models = ObjectLoader.loadJsonStream(new FileInputStream(path + "objectspec_debug.json"));

        List<LeshanClient> clientsLaunched = new LinkedList<>();

        for (Device device : devices) {
            ObjectsInitializer initializer = new ObjectsInitializer(new LwM2mModel(models));

            initializer.setInstancesForObject(SECURITY,
                    noSec(device.getServerUrl() + ":" + device.getServerPort(), 123));

            initializer.setInstancesForObject(SERVER,
                    new Server(123, device.getLifetime(), BindingMode.U, false));

            DeviceDto deviceDto = device.getDevice();
            if (deviceDto != null)
                initializer.setInstancesForObject(DEVICE,
                        new DeviceObject(deviceDto.getBatteryStatus(), deviceDto.getBatteryLevel()));

            Location location = device.getLocation();
            if (location != null)
                initializer.setInstancesForObject(LOCATION,
                        new LocationObject(location.getLatitude(), location.getLongitude(), location.getAltitude()));

            List<Sensor> temperatures = device.getTemperatures();
            if (temperatures != null) {
                IpsoTemperatureObject[] ipsoTemp = new IpsoTemperatureObject[temperatures.size()];
                int index = 0;
                for (Sensor temperature : temperatures) {
                    ipsoTemp[index++] = new IpsoTemperatureObject(
                            temperature.getMaxValue(), temperature.getMinValue(), temperature.getSensorValue());
                }
                initializer.setInstancesForObject(3303, ipsoTemp);
            }

            List<Sensor> humidities = device.getHumidities();
            if (humidities != null) {
                IpsoHumidityObject[] ipsoHum = new IpsoHumidityObject[humidities.size()];
                int index = 0;
                for (Sensor humidity : humidities) {
                    ipsoHum[index++] = new IpsoHumidityObject(
                            humidity.getMaxValue(), humidity.getMinValue(), humidity.getSensorValue());
                }
                initializer.setInstancesForObject(3304, ipsoHum);
            }

            Sensor loudness = device.getLoudness();
            if (loudness != null)
                initializer.setInstancesForObject(3324, new IpsoLoudnessObject(
                        loudness.getMaxValue(), loudness.getMinValue(), loudness.getSensorValue()));

            List<Sensor> gasses = device.getGasses();
            if (gasses != null) {
                IpsoConcentrationObject[] ipsoGasses = new IpsoConcentrationObject[gasses.size()];
                int index = 0;
                for (Sensor gas : gasses) {
                    ipsoGasses[index++] = new IpsoConcentrationObject(
                            gas.getMaxValue(), gas.getMinValue(), gas.getSensorValue());
                }
                initializer.setInstancesForObject(3325, ipsoGasses);
            }

            if (device.getPhysicalUrl() != null)
                initializer.setInstancesForObject(10000, new SmartSpotObject(device.getPhysicalUrl()));

            if (device.hasCrowdMonitoring())
                initializer.setInstancesForObject(10001, new NearWifiDevicesObject());

            List<LwM2mObjectEnabler> enablers = initializer.create(
                    SECURITY, SERVER, DEVICE, LOCATION, 3303, 3304, 3324, 3325, 10000, 10001);

            LeshanClientBuilder builder = new LeshanClientBuilder(device.getName());
            builder.setLocalAddress(device.getLocalAddress(), device.getLocalPort());
            builder.setObjects(enablers);
            final LeshanClient client = builder.build();
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

}
