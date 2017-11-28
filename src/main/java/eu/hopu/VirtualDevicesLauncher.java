package eu.hopu;

import com.google.gson.JsonArray;
import eu.hopu.devices.DeviceBase;
import org.eclipse.leshan.client.californium.LeshanClient;

import java.io.IOException;
import java.util.List;

public class VirtualDevicesLauncher {

    public static void main(String[] args) throws IOException {

        String path = (args.length == 0) ? "src/main/resources/" : "opt/lwm2m-iotagent-client/lib/";
        JsonArray jsonDevices = new GetJsonArrayFromFile(path + "smart_sdk.json").execute();

        List<DeviceBase> devices = new GetDevicesFromJsonObjects(jsonDevices).execute();
        List<LeshanClient> leshanClients = new GetLeshanClients(devices, path + "objectSpecs/objectspec_debug.json").execute();

        new StartLeshanClients(leshanClients).execute();
        new ShutdownHookDestroyLeshanClients(leshanClients, true).execute();
    }
}
