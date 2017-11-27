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
    private String objectSpecPath;


    public GetLeshanClients(List<DeviceBase> devices, String objectSpecPath) {
        this.devices = devices;
        this.objectSpecPath = objectSpecPath;
    }

    public List<LeshanClient> execute() {

        try {
            List<ObjectModel> models = ObjectLoader.loadJsonStream(new FileInputStream(objectSpecPath));
            return getLeshanClientsfromDevices(models);
        } catch (FileNotFoundException e) {
            LOGGER.warning("ObjectSpec File NOT FOUND Returning Empty LeshanClients");
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private List<LeshanClient> getLeshanClientsfromDevices(List<ObjectModel> models) {
        List<LeshanClient> leshanClients = new ArrayList<>();

        for (DeviceBase device : devices)
            leshanClients.add(device.getLeshanClient(models));

        return leshanClients;
    }
}
