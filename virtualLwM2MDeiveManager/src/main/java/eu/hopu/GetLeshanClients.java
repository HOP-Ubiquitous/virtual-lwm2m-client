package eu.hopu;

import eu.hopu.devices.DeviceBase;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
