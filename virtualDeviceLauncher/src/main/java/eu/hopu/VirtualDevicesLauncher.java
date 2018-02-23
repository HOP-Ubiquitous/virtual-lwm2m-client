package eu.hopu;

import com.google.gson.JsonArray;
import eu.hopu.devices.DeviceBase;
import org.eclipse.leshan.client.californium.LeshanClient;

import java.io.IOException;
import java.util.List;

public class VirtualDevicesLauncher {

    public static void main(String[] args) throws IOException {

        String path = (args.length == 0) ? "virtualDeviceLauncher/src/main/resources/" : "/opt/lwm2m-client/lib/";
        JsonArray jsonDevices = new GetJsonArrayFromFile(path + "devices.json").execute();

        String source = "";

        List<DeviceBase> devices = new GetDevicesFromJsonObjects(jsonDevices).execute();
        for (DeviceBase base: devices) {
            source = base.getServerUrl() + ":" + base.getServerPort();
            OrionRegistration registration = new OrionRegistration(base.getName(), source);
            registration.registration();
        }
        List<LeshanClient> leshanClients = new GetLeshanClients(devices).execute();

        new StartLeshanClients(leshanClients).execute();
        new ShutdownHookDestroyLeshanClients(leshanClients, true).execute();
    }
}
