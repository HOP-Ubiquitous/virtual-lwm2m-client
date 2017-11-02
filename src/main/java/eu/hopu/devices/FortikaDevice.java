package eu.hopu.devices;

import eu.hopu.objects.FortikaServiceObject;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.ObjectModel;

import java.util.List;

import static org.eclipse.leshan.LwM2mId.DEVICE;
import static org.eclipse.leshan.LwM2mId.SECURITY;
import static org.eclipse.leshan.LwM2mId.SERVER;

public class FortikaDevice extends DeviceBase {

    public static final String DEFINITION = "fortika_devices.json";

    private List<FortikaServiceObject> fortikaServices;

    public ObjectsInitializer getObjectInitializer(List<ObjectModel> models) {
        ObjectsInitializer initializer = super.getObjectInitializer(models);
        List<FortikaServiceObject> services = getFortikaServices();

        if (services != null)
            initializer.setInstancesForObject(28201, fortikaServiceListToArray(services));

        return initializer;
    }




    private FortikaServiceObject[] fortikaServiceListToArray(List<FortikaServiceObject> services) {
        FortikaServiceObject[] fortikaServices = new FortikaServiceObject[services.size()];
        int index = 0;

        for (FortikaServiceObject service : services) {
            fortikaServices[index++] = service;
        }
        return fortikaServices;
    }


    @Override
    List<LwM2mObjectEnabler> getDeviceEnabledObjects(ObjectsInitializer objectsInitializer) {
        return objectsInitializer.create(SECURITY, SERVER, DEVICE, 28201);
    }

    public List<FortikaServiceObject> getFortikaServices() {
        return fortikaServices;
    }

    public void setFortikaServices(List<FortikaServiceObject> fortikaServices) {
        this.fortikaServices = fortikaServices;
    }
}
