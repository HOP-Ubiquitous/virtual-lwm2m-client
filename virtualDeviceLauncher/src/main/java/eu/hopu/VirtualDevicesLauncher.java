package eu.hopu;

import com.google.gson.JsonArray;
import eu.hopu.devices.DeviceBase;
import org.eclipse.leshan.client.californium.LeshanClient;

import java.util.List;

public class VirtualDevicesLauncher {

    public static void main(String[] args) {
        String path = (args.length == 0) ? "virtualDeviceLauncher/src/main/resources/" : "/opt/lwm2m-client/lib/";
        JsonArray jsonDevices = new GetJsonArrayFromFile(path + "devices.json").execute();

        List<DeviceBase> devices = new GetDevicesFromJsonObjects(jsonDevices).execute();
        List<LeshanClient> leshanClients = new GetLeshanClients(devices).execute();

        new StartLeshanClients(leshanClients).execute();
        new ShutdownHookDestroyLeshanClients(leshanClients, true).execute();
    }

}