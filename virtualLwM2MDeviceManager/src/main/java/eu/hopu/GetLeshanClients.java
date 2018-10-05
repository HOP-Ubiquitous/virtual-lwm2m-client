package eu.hopu;

import eu.hopu.devices.DeviceBase;
import org.eclipse.leshan.client.californium.LeshanClient;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetLeshanClients {

    private final static Logger LOGGER = Logger.getLogger(GetLeshanClients.class.getName());

    private List<DeviceBase> devices;

    public GetLeshanClients(List<DeviceBase> devices) {
        this.devices = devices;
    }

    public List<LeshanClient> execute() {
        List<LeshanClient> leshanClients = new ArrayList<>();

        for (DeviceBase device : devices)
            leshanClients.add(device.getLeshanClient());

        return leshanClients;
    }
}
